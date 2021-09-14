package com.config;

import java.util.List;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

@Configuration
public class BatchConfig {

	@Autowired
	JobBuilderFactory jobBuilderFactory;

	@Autowired
	StepBuilderFactory stepBuilderFactory;

	private static final String[] columns = new String[] { "order_id", "first_name", "last_name", "email", "cost",
			"item_id", "item_name", "ship_date" };

	@Bean
	public FlatFileItemReader<Order> itemReader() {
		FlatFileItemReader<Order> itemReader = new FlatFileItemReader<>();
		itemReader.setLinesToSkip(1);
		itemReader.setResource(new FileSystemResource("src/main/resources/shipped_orders.csv"));
		itemReader.setName("csvItemReader");
		itemReader.setLineMapper(lineMapper());
		return itemReader;
	}

	@Bean
	public LineMapper<Order> lineMapper() {
		DefaultLineMapper<Order> defaultLineMapper = new DefaultLineMapper<>();
		DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
		lineTokenizer.setDelimiter(",");
		lineTokenizer.setStrict(false);
		lineTokenizer.setNames(columns);
		BeanWrapperFieldSetMapper<Order> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
		fieldSetMapper.setTargetType(Order.class);
		defaultLineMapper.setLineTokenizer(lineTokenizer);
		defaultLineMapper.setFieldSetMapper(fieldSetMapper);
		return defaultLineMapper;
	}

	@Bean
	public Step step() {
		return stepBuilderFactory.get("csvStep").<Order, Order>chunk(10).reader(itemReader())
				.writer(new ItemWriter<Order>() {
					@Override
					public void write(List<? extends Order> items) throws Exception {
						items.forEach(i -> System.out.println("Item details ->" + i));
					}

				}).build();
	}

	@Bean
	public Job job() {
		return jobBuilderFactory.get("csvJob")
				.incrementer(new RunIdIncrementer())
				.start(step()).build();
	}

}
