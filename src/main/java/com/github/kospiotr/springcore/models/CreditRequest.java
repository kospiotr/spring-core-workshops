package com.github.kospiotr.springcore.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreditRequest {
	String purpose;
	Money amount;
	long days;
	User loanTaker;

}
