package com.github.kospiotr.springcore.scoring.rules;

import com.github.kospiotr.springcore.models.CreditRequest;

public class AgeScoringRule implements ScoringRule {

	public Integer getScore(CreditRequest creditRequest) {
		int loanTakerAge = creditRequest.getLoanTaker().getAge();
		if (loanTakerAge < 18) {
			return 0;
		}
		if (loanTakerAge < 30) {
			return 10;
		}
		return 20;
	}
}
