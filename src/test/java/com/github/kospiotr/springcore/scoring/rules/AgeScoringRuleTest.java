package com.github.kospiotr.springcore.scoring.rules;

import com.github.kospiotr.springcore.models.CreditRequest;
import com.github.kospiotr.springcore.models.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import static org.fest.assertions.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class AgeScoringRuleTest {

	@InjectMocks
	private AgeScoringRule instance;

	@Test
	public void shouldGet0PointsWhenVeryYoungLoanTaker() throws Exception {
		Integer score = instance.getScore(createLoanWithLoanTakerAgeOf(15));
		assertThat(score).isEqualTo(0);
	}

	@Test
	public void shouldGet10PointsWhenYoungLoanTaker() throws Exception {
		Integer score = instance.getScore(createLoanWithLoanTakerAgeOf(25));
		assertThat(score).isEqualTo(10);
	}

	@Test
	public void shouldGet20PointsWhenOldLoanTaker() throws Exception {
		Integer score = instance.getScore(createLoanWithLoanTakerAgeOf(35));
		assertThat(score).isEqualTo(20);
	}

	private CreditRequest createLoanWithLoanTakerAgeOf(int age) {
		return new CreditRequest("Testing purpose", null, 0, createUserWithAge(age));
	}

	private User createUserWithAge(int age) {
		return new User("Testing user", age, null);
	}
}