package com.github.kospiotr.springcore.fraud;

public class PolishFraudDetector implements FraudDetector {

	@Override
	public boolean isFraud(int age) {
		return age < 20;
	}
}
