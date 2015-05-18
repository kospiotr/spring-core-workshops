package com.github.kospiotr.springcore.fraud;

import org.springframework.stereotype.Component;

@Component
public class EnglishFraudDetector implements FraudDetector {

	public EnglishFraudDetector() {
		System.out.println("Constructed EnglishFraudDetector");
	}

	@Override
	public boolean isFraud(int age) {
		return age < 16;
	}
}
