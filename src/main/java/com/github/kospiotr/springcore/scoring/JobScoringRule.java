package com.github.kospiotr.springcore.scoring;

import com.github.kospiotr.springcore.model.Loan;
import org.springframework.stereotype.Component;

@Component
public class JobScoringRule implements ScoringRule {

	public JobScoringRule() {
		System.out.println("Constructed JobScoringRule");
	}

	@Override
	public Integer getScore(Loan loan) {
		return loan.getUser().getWage() * 10;
	}
}
