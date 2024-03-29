package com.github.kospiotr.springcore;

import com.github.kospiotr.springcore.fraud.FraudDetector;
import com.github.kospiotr.springcore.fraud.PolishFraudDetector;
import com.github.kospiotr.springcore.model.Loan;
import com.github.kospiotr.springcore.scoring.AgeScoringRule;
import com.github.kospiotr.springcore.scoring.JobScoringRule;
import com.github.kospiotr.springcore.scoring.ScoringRule;

import java.util.List;

import static java.util.Arrays.asList;

public class ScoreCalculator {

	private FraudDetector fraudDetector;
	private List<ScoringRule> scoringRules;

	public static ScoreCalculator createInstance() {
		return new ScoreCalculator(new PolishFraudDetector(), asList(new AgeScoringRule(), new JobScoringRule()));
	}

	private ScoreCalculator() {
		System.out.println("Constructing ScoreCalculator");
	}

	public ScoreCalculator(FraudDetector fraudDetector, List<ScoringRule> scoringRules) {
		System.out.println("Constructing ScoreCalculator with all arguments");
		this.fraudDetector = fraudDetector;
		this.scoringRules = scoringRules;
	}

	public Integer getScore(Loan loan) {
		System.out.println("getScore for: " + loan);
		System.out.println("Using fraudDetector: " + fraudDetector);
		Integer score = scoringRules
				.stream()
				.mapToInt(rule -> rule.getScore(loan))
				.sum();
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

}
