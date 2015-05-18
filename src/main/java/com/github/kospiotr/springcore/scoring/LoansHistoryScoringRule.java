package com.github.kospiotr.springcore.scoring;

import com.github.kospiotr.springcore.model.Loan;

public class LoansHistoryScoringRule implements ScoringRule {

	int previousScore = 0;

	@Override
	public Integer getScore(Loan loan) {
		return previousScore += 100;
	}
}
