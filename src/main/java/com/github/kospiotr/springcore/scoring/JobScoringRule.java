package com.github.kospiotr.springcore.scoring;

import com.github.kospiotr.springcore.model.Loan;

public class JobScoringRule implements ScoringRule {
	@Override
	public Integer getScore(Loan loan) {
		return loan.getUser().getWage() * 10;
	}
}
