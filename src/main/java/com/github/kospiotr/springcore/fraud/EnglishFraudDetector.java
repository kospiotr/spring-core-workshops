package com.github.kospiotr.springcore.fraud;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Qualifier("englishFraudDetector")
public class EnglishFraudDetector implements FraudDetector {

	public EnglishFraudDetector() {
		System.out.println("Constructed EnglishFraudDetector");
	}

	@Override
	public boolean isFraud(int age) {
		return age < 16;
	}
}
