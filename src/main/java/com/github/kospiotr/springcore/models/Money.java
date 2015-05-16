package com.github.kospiotr.springcore.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Money {
	long amount;
	Currency currency;
}
