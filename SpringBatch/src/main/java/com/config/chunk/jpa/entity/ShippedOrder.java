package com.config.chunk.jpa.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "SHIPPED_ORDER")
@Getter
@Setter
@NoArgsConstructor
public class ShippedOrder {
	
	@Id
	@Column(name = "ORDER_ID")
	private int orderId;

	@Column(name = "FIRST_NAME")
	private String firstName;

	@Column(name = "LAST_NAME")
	private String lastName;

	@Column(name = "EMAIL")
	private String emailId;

	private String cost;

	@Column(name = "ITEM_ID")
	private String itemId;

	@Column(name = "ITEM_NAME")
	private String itemName;

	@Column(name = "SHIP_DATE")
	private String shipDate;
	
	private String status;
	

}
