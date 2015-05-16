package com.github.kospiotr.springcore.scoring.system;

import com.github.kospiotr.springcore.models.CreditRequest;
import com.github.kospiotr.springcore.scoring.rules.JobScoringRule;
import com.github.kospiotr.springcore.scoring.ScoreCalculator;
import com.github.kospiotr.springcore.scoring.rules.ScoringRule;

import java.util.Arrays;
import java.util.List;

public class EnglishScoringSystem implements ScoringSystem {

	private List<ScoringRule> rules = Arrays.<ScoringRule>asList(
			new JobScoringRule(500, 1000)
	);

	private ScoreCalculator scoreCalculator = new ScoreCalculator();

	public boolean isUserEligibleForCredit(CreditRequest creditRequest) {
		return scoreCalculator.calculate(creditRequest, rules) >= 10;
	}
}
