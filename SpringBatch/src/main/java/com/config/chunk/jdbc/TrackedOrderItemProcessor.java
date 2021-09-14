package com.config.chunk.jdbc;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;


public class TrackedOrderItemProcessor implements ItemProcessor<Order, TrackedOrder> {

	private static Logger log = LoggerFactory.getLogger(TrackedOrderItemProcessor.class);
	@Override
	public TrackedOrder process(Order item) throws Exception {
		log.info("Processing order Id :- {}", item.getOrderId());
		log.info("Processing with thread :- {}", Thread.currentThread().getName());
		TrackedOrder trackedOrder = new TrackedOrder(item);
		trackedOrder.setTrackingNumber(getTrackingNumber());
		return trackedOrder;
	}

	private String getTrackingNumber() throws OrderProcessingException {

		if (Math.random() < .05) {
			throw new OrderProcessingException();
		}

		return UUID.randomUUID().toString();
	}

}
