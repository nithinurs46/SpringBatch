package com.config.chunk.jdbc;

import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.Pattern;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Order {

	private Long orderId;

	private String firstName;

	private String lastName;

	@Pattern(regexp = ".*\\.gov")
	private String email;

	private BigDecimal cost;

	private String itemId;

	private String itemName;

	private Date shipDate;
	
	private String status;

	@Override
	public String toString() {
		return "Order [orderId=" + orderId + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ ", cost=" + cost + ", itemId=" + itemId + ", itemName=" + itemName + ", shipDate=" + shipDate + "]";
	}

}
