package com.config;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Order {


	private Long orderId;

	private String firstName;

	private String lastName;

	private String email;

	private BigDecimal cost;

	private String itemId;

	private String itemName;

	private String shipDate;


	@Override
	public String toString() {
		return "Order [orderId=" + orderId + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ ", cost=" + cost + ", itemId=" + itemId + ", itemName=" + itemName + ", shipDate=" + shipDate + "]";
	}

}
