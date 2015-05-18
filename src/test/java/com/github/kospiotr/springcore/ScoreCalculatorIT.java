package com.github.kospiotr.springcore;

import com.github.kospiotr.springcore.model.Loan;
import com.github.kospiotr.springcore.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.fest.assertions.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class ScoreCalculatorIT {

	@Autowired
	ScoreCalculator plCalculator;

	@Test
	public void shouldPolishCalculatorReturnProperScore() throws Exception {
		Loan loan = new Loan(1000, User.youngRichUser());
		assertThat(plCalculator.getScore(loan)).isEqualTo(126100);
		assertThat(plCalculator.getScore(loan)).isEqualTo(138810);
	}
}