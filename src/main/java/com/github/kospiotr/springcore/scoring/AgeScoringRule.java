package com.github.kospiotr.springcore.scoring;

import com.github.kospiotr.springcore.model.Loan;

public class AgeScoringRule implements ScoringRule {

	public AgeScoringRule() {
		System.out.println("Constructed AgeScoringRule");
	}

	@Override
	public Integer getScore(Loan loan) {
		return loan.getUser().getAge() * 1000;
	}
}
