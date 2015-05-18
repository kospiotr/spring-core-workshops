package com.github.kospiotr.springcore.fraud;

public class PolishFraudDetector implements FraudDetector {

	public PolishFraudDetector() {
		System.out.println("Constructed PolishFraudDetector");
	}

	@Override
	public boolean isFraud(int age) {
		return age < 20;
	}
}
