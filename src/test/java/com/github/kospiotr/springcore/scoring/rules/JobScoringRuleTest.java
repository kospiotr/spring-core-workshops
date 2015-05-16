package com.github.kospiotr.springcore.scoring.rules;

import com.github.kospiotr.springcore.models.CreditRequest;
import com.github.kospiotr.springcore.models.Money;
import com.github.kospiotr.springcore.models.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.fest.assertions.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)

public class JobScoringRuleTest {
	JobScoringRule instance = new JobScoringRule(100, 200);

	@Test
	public void shouldGet0PointsWhenBelowMinimalWage() throws Exception {
		instance.getScore(new CreditRequest("Testing purpose", null, 100, createUserWithJobWage(50)));
	}

	@Test
	public void shouldGet10PointsWhenBetweenMinimalAndSecureWage() throws Exception {
		instance.getScore(new CreditRequest("Testing purpose", null, 100, createUserWithJobWage(150)));
	}

	@Test
	public void shouldGet20PointsWhenAboveSecureWage() throws Exception {
		instance.getScore(new CreditRequest("Testing purpose", null, 100, createUserWithJobWage(250)));
	}

	@Test
	public void shouldNotInitializeWhenMinimalWageIsGreaterThanSecure() throws Exception {
		try {
			new JobScoringRule(200, 100);
		} catch (Exception e) {
			assertThat(e).hasMessage("Minimal wage 200 must be lower than secure 100");
		}
	}

	private User createUserWithJobWage(int wage) {
		return new User("Testing user", -1, new Money(wage, null));
	}
}