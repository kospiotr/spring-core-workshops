package com.github.kospiotr.springcore;

import com.github.kospiotr.springcore.fraud.FraudDetector;
import com.github.kospiotr.springcore.scoring.ScoringRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PolishScoreCalculator extends ScoreCalculator {

	@Autowired
	public PolishScoreCalculator(@Qualifier("polishFraudDetector") FraudDetector fraudDetector, List<ScoringRule> scoringRules, UserScoreRegistry userScoreRegistry) {
		super(fraudDetector, scoringRules, userScoreRegistry);
	}
}