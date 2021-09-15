package com.config.chunk.jdbc;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.PagingQueryProvider;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean;
import org.springframework.batch.item.json.JacksonJsonObjectMarshaller;
import org.springframework.batch.item.json.builder.JsonFileItemWriterBuilder;
import org.springframework.batch.item.support.builder.CompositeItemProcessorBuilder;
import org.springframework.batch.item.validator.BeanValidatingItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class JdbcBatchConfig {

	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;

	@Autowired
	public DataSource dataSource;
	
	public static final String INSERT_ORDER_SQL_PREPARED_STMT = "insert into "
			+ "SHIPPED_ORDER_OUTPUT(order_id, first_name, last_name, email, item_id, item_name, cost, ship_date)"
			+ " values(?,?,?,?,?,?,?,?)";
	
	public static String INSERT_ORDER_SQL_NAMED_PARAMETERS = "insert into "
			+ "SHIPPED_ORDER_OUTPUT(order_id, first_name, last_name, email, item_id, item_name, cost, ship_date)"
			+ " values(:orderId,:firstName,:lastName,:email,:itemId,:itemName,:cost,:shipDate)";
	
	public static final String UPDATE_ORDER_SQL_NAMED_PARAMETERS = "update shipped_order set status = :status where order_id = :orderId";
	

	@Bean
	public ItemProcessor<Order, TrackedOrder> compositeItemProcessor() {
		return new CompositeItemProcessorBuilder<Order, TrackedOrder>()
				.delegates(orderValidatingItemProcessor(), trackedOrderItemProcessor(), freeShippingItemProcessor())
				.build();
	}

	@Bean
	public ItemProcessor<Order, Order> orderValidatingItemProcessor() {
		BeanValidatingItemProcessor<Order> itemProcessor = new BeanValidatingItemProcessor<>();
		itemProcessor.setFilter(true);
		return itemProcessor;
	}

	@Bean
	public ItemProcessor<Order, TrackedOrder> trackedOrderItemProcessor() {
		return new TrackedOrderItemProcessor();
	}

	@Bean
	public ItemProcessor<TrackedOrder, TrackedOrder> freeShippingItemProcessor() {
		return new FreeShippingItemProcessor();
	}

	@Bean
	public ItemWriter<TrackedOrder> jsonItemWriter() {
		return new JsonFileItemWriterBuilder<TrackedOrder>().jsonObjectMarshaller(new JacksonJsonObjectMarshaller<>())
				.resource(new FileSystemResource("src/main/resources/shipped_orders_output.json"))
				.name("jsonItemWriter").build();
	}
	
	@Bean
	public ItemWriter<Order> jdbcItemWriter(){
		return new JdbcBatchItemWriterBuilder<Order>()
				.dataSource(dataSource)
				.sql(INSERT_ORDER_SQL_PREPARED_STMT)
				//.sql(INSERT_ORDER_SQL_NAMED_PARAMETERS)
				//.beanMapped()
				.itemPreparedStatementSetter(new OrderItemPreparedStatementSetter())
				.build();
	}
	
	@Bean
	public ItemWriter<Order> jdbcItemWriterUpdate(){
		return new JdbcBatchItemWriterBuilder<Order>()
				.dataSource(dataSource)
				.sql(UPDATE_ORDER_SQL_NAMED_PARAMETERS)
				.beanMapped()
				.build();
	}

	@Bean
	public PagingQueryProvider queryProvider() throws Exception {
		Map<String, org.springframework.batch.item.database.Order> sort = new HashMap<>();
		sort.put("order_id", org.springframework.batch.item.database.Order.ASCENDING);
		SqlPagingQueryProviderFactoryBean factory = new SqlPagingQueryProviderFactoryBean();
		factory.setSelectClause("select order_id, first_name, last_name, email, cost, item_id, item_name, ship_date");
		factory.setFromClause("from SHIPPED_ORDER");
		factory.setSortKeys(sort);
		factory.setDataSource(dataSource);
		return factory.getObject();
	}

	@Bean
	public ItemReader<Order> jdbcItemReader() throws Exception {
		return new JdbcPagingItemReaderBuilder<Order>().dataSource(dataSource).name("jdbcItemReader")
				.queryProvider(queryProvider()).rowMapper(new OrderRowMapper()).pageSize(10).build();
		
	}
	
	@Bean
	public TaskExecutor taskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(2);
		executor.setMaxPoolSize(10);
		return executor;
	}

	@Bean
	public Step jdbcJsonChunkBasedStep() throws Exception {
		return this.stepBuilderFactory.get("jdbcJsonChunkBasedStep").<Order, TrackedOrder>chunk(10)
				.reader(jdbcItemReader())
				.processor(compositeItemProcessor())
				.faultTolerant()
				.retry(OrderProcessingException.class)
				.retryLimit(3)
				.listener(new CustomRetryListener())
				//.skip(OrderProcessingException.class)
				//.skipLimit(5)
				//.listener(new CustomSkipListener())
				.writer(jsonItemWriter())
				.taskExecutor(taskExecutor())
				.build();
	}
	
	@Bean
	public Step jdbcChunkBasedStep() throws Exception {
		return this.stepBuilderFactory.get("jdbcChunkBasedStep").<Order, TrackedOrder>chunk(10)
				.reader(jdbcItemReader())
				.writer(jdbcItemWriter())
				.taskExecutor(taskExecutor())
				.build();
	}
	
	@Bean
	public Step jdbcChunkUpdateStep() throws Exception {
		return this.stepBuilderFactory.get("jdbcChunkUpdateStep").<Order, Order>chunk(10)
				.reader(jdbcItemReader())
				.processor(new ItemProcessor<Order, Order>() {
					@Override
					public Order process(Order item) throws Exception {
						item.setStatus("99");
						return item;
					}
				
				})
				.writer(jdbcItemWriterUpdate())
				.taskExecutor(taskExecutor())
				.build();
	}

	@Bean
	public Job job() throws Exception {
		return this.jobBuilderFactory.get("job").start(jdbcJsonChunkBasedStep())
				.next(jdbcChunkBasedStep())
				.next(jdbcChunkUpdateStep())
				.build();
	}

}
