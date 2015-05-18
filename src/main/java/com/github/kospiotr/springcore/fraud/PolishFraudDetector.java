package com.github.kospiotr.springcore.fraud;

import org.springframework.beans.factory.annotation.Qualifier;

@Qualifier("polishFraudDetector")
public class PolishFraudDetector implements FraudDetector {

	public PolishFraudDetector() {
		System.out.println("Constructed PolishFraudDetector");
	}

	@Override
	public boolean isFraud(int age) {
		return age < 20;
	}
}
