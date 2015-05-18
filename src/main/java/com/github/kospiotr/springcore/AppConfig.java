package com.github.kospiotr.springcore;

import com.github.kospiotr.springcore.fraud.EnglishFraudDetector;
import com.github.kospiotr.springcore.fraud.PolishFraudDetector;
import com.github.kospiotr.springcore.scoring.AgeScoringRule;
import com.github.kospiotr.springcore.scoring.JobScoringRule;
import com.github.kospiotr.springcore.scoring.RememberingLastScoreRule;
import com.github.kospiotr.springcore.scoring.ScoringRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

import static java.util.Arrays.asList;

@Configuration
public class AppConfig {


	@Bean
	public ScoreCalculator plCalculator() {
		return new ScoreCalculator(new PolishFraudDetector(), scoringRules(), userScoreRegistry());
	}

	@Bean
	@Autowired
	public ScoreCalculator enCalculator(UserScoreRegistry userScoreRegistry) {
		return new ScoreCalculator(new EnglishFraudDetector(), scoringRules(), userScoreRegistry);
	}

	@Bean
	public UserScoreRegistry userScoreRegistry() {
		return new UserScoreRegistry();
	}

	@Bean
	public List<ScoringRule> scoringRules() {
		List<ScoringRule> scoringRules = asList(
				new AgeScoringRule(),
				new JobScoringRule(),
				new RememberingLastScoreRule(userScoreRegistry()));
		return scoringRules;
	}
}
