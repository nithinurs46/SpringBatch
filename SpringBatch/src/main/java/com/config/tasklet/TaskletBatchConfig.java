package com.config.tasklet;


import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.batch.core.job.flow.support.SimpleFlow;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TaskletBatchConfig {

	@Autowired
	JobBuilderFactory jobBuilderFactory;

	@Autowired
	StepBuilderFactory stepBuilderFactory;

	@Bean
	public JobExecutionDecider deliveryDecider() {
		return new DeliveryDecider();
	}

	@Bean
	public JobExecutionDecider receiptDecider() {
		return new ReceiptDecider();
	}
	
	@Bean
	public Flow deliveryFlow() {
		return new FlowBuilder<SimpleFlow>("deliveryFlow").start(driveToAddress())
				.on("FAILED").to(storePackage())
			.from(driveToAddress())
				.on("*").to(deliveryDecider())
					.on("PRESENT").to(deliverPackageToCustomer())
						.next(receiptDecider()).on("CORRECT").to(thankCustomer())
						.from(receiptDecider()).on("INCORRECT").to(refundOnFailure())
				.from(deliveryDecider())
					.on("NOT_PRESENT").to(dropPackageAtDoor()).build();
	}

	@Bean
	public Step packageItem() {
		return stepBuilderFactory.get("packageItem").tasklet(new Tasklet() {
			@Override
			public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
				String item = chunkContext.getStepContext().getJobParameters().get("item").toString();
				String date = chunkContext.getStepContext().getJobParameters().get("run.date").toString();
				System.out.println(String.format("The %s has been packaged on %s.", item, date));
				return RepeatStatus.FINISHED;
			}
		}).build();

	}

	@Bean
	public Step driveToAddress() {
		return stepBuilderFactory.get("driveToAddress").tasklet(new Tasklet() {
			@Override
			public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
				System.out.println("Destination address reached successfully.");
				return RepeatStatus.FINISHED;
			}
		}).build();
	}

	@Bean
	public Step dropPackageAtDoor() {
		return this.stepBuilderFactory.get("dropPackageAtDoor").tasklet(new Tasklet() {
			@Override
			public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
				System.out.println("Dropping the package at the door.");
				return RepeatStatus.FINISHED;
			}
		}).build();
	}

	@Bean
	public Step storePackage() {
		return this.stepBuilderFactory.get("storePackage").tasklet(new Tasklet() {
			@Override
			public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
				System.out.println("Storing the package until customer address is located.");
				return RepeatStatus.FINISHED;
			}
		}).build();
	}

	@Bean
	public Step deliverPackageToCustomer() {
		return this.stepBuilderFactory.get("deliverPackageToCustomer").tasklet(new Tasklet() {

			@Override
			public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
				System.out.println("Handed over the package to the customer.");
				return RepeatStatus.FINISHED;
			}
		}).build();
	}

	@Bean
	public Step thankCustomer() {
		return this.stepBuilderFactory.get("thankCustomer").tasklet(new Tasklet() {

			@Override
			public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
				System.out.println("Thanking for ordering...");
				return RepeatStatus.FINISHED;
			}
		}).build();
	}

	@Bean
	public Step refundOnFailure() {
		return this.stepBuilderFactory.get("refund").tasklet(new Tasklet() {

			@Override
			public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
				System.out.println("Refunding the amount to customer.");
				return RepeatStatus.FINISHED;
			}
		}).build();
	}

	/*@Bean
	public Job deliverPackageWithoutFlow() {
		return this.jobBuilderFactory.get("deliverPackage")
				.start(packageItem())
				.next(driveToAddress())
					.on("FAILED").to(storePackage())
				.from(driveToAddress())
					.on("*").to(deliveryDecider())
						.on("PRESENT").to(deliverPackageToCustomer())
							.next(receiptDecider()).on("CORRECT").to(thankCustomer())
							.from(receiptDecider()).on("INCORRECT").to(refundOnFailure())
					.from(deliveryDecider())
						.on("NOT_PRESENT").to(dropPackageAtDoor())
				.end()
				.build();
	}*/
	
	@Bean
	public Job deliverPackage() {
		return this.jobBuilderFactory.get("deliverPackage")
				.start(packageItem())
				.on("*").to(deliveryFlow())
				.end()
				.build();
	}
	
	

}
