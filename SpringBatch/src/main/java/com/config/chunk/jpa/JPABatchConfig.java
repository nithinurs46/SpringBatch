package com.config.chunk.jpa;

import javax.persistence.EntityManagerFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JpaCursorItemReader;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.builder.JpaCursorItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.config.chunk.jpa.entity.ShippedOrder;

@Configuration
public class JPABatchConfig {

	private static Logger log = LoggerFactory.getLogger(JPABatchConfig.class);

	@Autowired
	EntityManagerFactory entityManagerFactory;

	@Autowired
	JobBuilderFactory jobBuilderFactory;

	@Autowired
	StepBuilderFactory stepBuilderFactory;

	private String inputQuery = "select order from ShippedOrder order where status = null";

	@Bean
	public JpaCursorItemReader<ShippedOrder> jpaItemReader() {
		return new JpaCursorItemReaderBuilder<ShippedOrder>().name("ShippedOrderReader")
				.entityManagerFactory(entityManagerFactory).queryString(inputQuery).build();
	}

	@Bean
	public JpaItemWriter<ShippedOrder> jpaItemWriter() {
		JpaItemWriter<ShippedOrder> writer = new JpaItemWriter<>();
		writer.setEntityManagerFactory(entityManagerFactory);
		return writer;
	}

	@Bean
	public ItemProcessor<ShippedOrder, ShippedOrder> processor() {
		return (item) -> {
			log.info("Processing item name :- {}", item.getItemName());
			item.setStatus("02");
			return item;
		};
	}
	 

	@Bean
	public Job runJob() {
		return jobBuilderFactory.get("runJob").incrementer(new RunIdIncrementer()).listener(listener()).start(step1())
				.build();
	}

	@Bean
	public Step step1() {
		return stepBuilderFactory.get("step1").<ShippedOrder, ShippedOrder>chunk(10).reader(jpaItemReader())
				.processor(processor()).writer(jpaItemWriter()).build();
	}

	@Bean
	public JobExecutionListener listener() {
		return new JobExecutionListener() {
			@Override
			public void beforeJob(JobExecution jobExecution) {
				// Do nothing
			}

			@Override
			public void afterJob(JobExecution jobExecution) {
				if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
					log.info("Job completed :- {}", jobExecution.getJobId());
					//System.exit(0);
				}

			}
		};

	}

}
