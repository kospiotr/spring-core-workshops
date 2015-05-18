package com.github.kospiotr.springcore;

import com.github.kospiotr.springcore.fraud.FraudDetector;
import com.github.kospiotr.springcore.model.Loan;
import com.github.kospiotr.springcore.scoring.ScoringRule;

import java.util.List;

public class ScoreCalculator {

	private FraudDetector fraudDetector;
	private List<ScoringRule> scoringRules;
	private UserScoreRegistry userScoreRegistry;

	public Integer getScore(Loan loan) {
		System.out.println("Using fraudDetector: " + fraudDetector);
		System.out.println("Using userScoreRegistry: " + userScoreRegistry);
		Integer score = scoringRules
				.stream()
				.mapToInt(rule -> rule.getScore(loan))
				.sum();
		userScoreRegistry.setLastScoreForUser(loan.getUser(), score);
		return score;
	}

	public void setFraudDetector(FraudDetector fraudDetector) {
		this.fraudDetector = fraudDetector;
	}

	public void setScoringRules(List<ScoringRule> scoringRules) {
		this.scoringRules = scoringRules;
	}

	public void setUserScoreRegistry(UserScoreRegistry userScoreRegistry) {
		this.userScoreRegistry = userScoreRegistry;
	}
}
