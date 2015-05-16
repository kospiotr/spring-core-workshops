package com.github.kospiotr.springcore.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreditDecision {
	boolean creditDecision;
	CreditRequest creditRequest;
}
