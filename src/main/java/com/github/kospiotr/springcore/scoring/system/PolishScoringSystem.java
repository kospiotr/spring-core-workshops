package com.github.kospiotr.springcore.scoring.system;

import com.github.kospiotr.springcore.models.CreditRequest;
import com.github.kospiotr.springcore.scoring.rules.AgeScoringRule;
import com.github.kospiotr.springcore.scoring.rules.JobScoringRule;
import com.github.kospiotr.springcore.scoring.ScoreCalculator;
import com.github.kospiotr.springcore.scoring.rules.ScoringRule;

import java.util.List;

import static java.util.Arrays.asList;

public class PolishScoringSystem implements ScoringSystem {

	private List<ScoringRule> rules = asList(
			new AgeScoringRule(),
			new JobScoringRule(3000, 11000)
	);

	private ScoreCalculator scoreCalculator = new ScoreCalculator();

	public boolean isUserEligibleForCredit(CreditRequest creditRequest) {
		return scoreCalculator.calculate(creditRequest, rules) >= 20;
	}
}
