package com.github.kospiotr.springcore.scoring;

import com.github.kospiotr.springcore.model.Loan;

public class LoansHistoryScoringRule implements ScoringRule {

	private int previousScore = 0;

	public LoansHistoryScoringRule() {
		System.out.println("Constructed LoansHistoryScoringRule " + toString());
	}

	@Override
	public Integer getScore(Loan loan) {
		return previousScore += 100;
	}
}
