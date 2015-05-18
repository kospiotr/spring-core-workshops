package com.github.kospiotr.springcore;

import com.github.kospiotr.springcore.fraud.FraudDetector;
import com.github.kospiotr.springcore.scoring.ScoringRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EnglishScoreCalculator extends ScoreCalculator {

	@Autowired
	public EnglishScoreCalculator(@Qualifier("englishFraudDetector") FraudDetector fraudDetector, List<ScoringRule> scoringRules, UserScoreRegistry userScoreRegistry) {
		super(fraudDetector, scoringRules, userScoreRegistry);
	}
}