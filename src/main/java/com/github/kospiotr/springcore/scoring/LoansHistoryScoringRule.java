package com.github.kospiotr.springcore.scoring;

import com.github.kospiotr.springcore.model.Loan;
import org.springframework.stereotype.Component;

@Component
public class LoansHistoryScoringRule implements ScoringRule {

	private int previousScore = 0;

	public LoansHistoryScoringRule() {
		System.out.println("Constructed LoansHistoryScoringRule");
	}

	@Override
	public Integer getScore(Loan loan) {
		return previousScore += 100;
	}
}
