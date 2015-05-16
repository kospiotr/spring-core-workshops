package com.github.kospiotr.springcore.scoring.rules;

import com.github.kospiotr.springcore.models.CreditRequest;

public class JobScoringRule implements ScoringRule {

	final long minWage;
	final long secureWage;

	public JobScoringRule(long minWage, long secureWage) {
		if (minWage > secureWage) {
			throw new IllegalArgumentException(String.format("Minimal wage %s must be lower than secure %s", minWage, secureWage));
		}
		this.minWage = minWage;
		this.secureWage = secureWage;
	}

	public Integer getScore(CreditRequest creditRequest) {
		long loanTakerSalary = creditRequest.getLoanTaker().getWage().getAmount();

		if (loanTakerSalary < minWage) {
			return 0;
		}

		if (loanTakerSalary < secureWage) {
			return 10;
		}

		return 20;
	}
}
