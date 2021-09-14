package com.config.chunk.jdbc;

import org.springframework.beans.BeanUtils;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TrackedOrder extends Order {

	private String trackingNumber;

	private boolean freeShipping;

	public TrackedOrder() {

	}

	public TrackedOrder(Order order) {
		BeanUtils.copyProperties(order, this);
	}

}
