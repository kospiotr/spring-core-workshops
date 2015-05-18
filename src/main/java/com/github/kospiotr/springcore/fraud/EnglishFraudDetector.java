package com.github.kospiotr.springcore.fraud;

public class EnglishFraudDetector implements FraudDetector {
	@Override
	public boolean isFraud(int age) {
		return age < 16;
	}
}
