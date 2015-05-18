package com.github.kospiotr.springcore;

import com.github.kospiotr.springcore.fraud.FraudDetector;
import com.github.kospiotr.springcore.model.Loan;
import com.github.kospiotr.springcore.scoring.ScoringRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ScoreCalculator {

	@Qualifier("polishFraudDetector")
	@Autowired
	private FraudDetector fraudDetector;
	@Autowired
	private List<ScoringRule> scoringRules;
	@Autowired
	private UserScoreRegistry userScoreRegistry;

	public ScoreCalculator() {
		System.out.println("Constructing ScoreCalculator");
	}

	public ScoreCalculator(FraudDetector fraudDetector, List<ScoringRule> scoringRules, UserScoreRegistry userScoreRegistry) {
		System.out.println("Constructing ScoreCalculator with all arguments");
		this.fraudDetector = fraudDetector;
		this.scoringRules = scoringRules;
		this.userScoreRegistry = userScoreRegistry;
	}

	public Integer getScore(Loan loan) {
		System.out.println("getScore for: " + loan);
		System.out.println("Using fraudDetector: " + fraudDetector);
		Integer score = scoringRules
				.stream()
				.mapToInt(rule -> rule.getScore(loan))
				.sum();
		userScoreRegistry.setLastScoreForUser(loan.getUser(), score);
		return score;
	}

	public FraudDetector getFraudDetector() {
		return fraudDetector;
	}

	public void setFraudDetector(FraudDetector fraudDetector) {
		System.out.println("Injecting fraudDetector=" + fraudDetector);
		this.fraudDetector = fraudDetector;
	}

	public List<ScoringRule> getScoringRules() {
		return scoringRules;
	}

	public void setScoringRules(List<ScoringRule> scoringRules) {
		System.out.println("Injecting scoringRules=" + scoringRules);
		this.scoringRules = scoringRules;
	}

	public UserScoreRegistry getUserScoreRegistry() {
		return userScoreRegistry;
	}

	public void setUserScoreRegistry(UserScoreRegistry userScoreRegistry) {
		System.out.println("Injecting userScoreRegistry=" + userScoreRegistry);
		this.userScoreRegistry = userScoreRegistry;
	}
}
