package com.github.kospiotr.springcore;

import com.github.kospiotr.springcore.fraud.EnglishFraudDetector;
import com.github.kospiotr.springcore.fraud.PolishFraudDetector;
import com.github.kospiotr.springcore.scoring.ScoringRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import java.util.List;

@Configuration
@PropertySource(value = "classpath:config.properties", name = "locations")
@ComponentScan(basePackages = {"com.github.kospiotr.springcore"})
public class AppConfig {

	@Bean
	@Autowired
	public ScoreCalculator plCalculator(
			PolishFraudDetector polishFraudDetector,
			List<ScoringRule> scoringRules,
			UserScoreRegistry userScoreRegistry) {
		return new ScoreCalculator(polishFraudDetector, scoringRules, userScoreRegistry);
	}

	@Bean
	@Autowired
	public ScoreCalculator enCalculator(
			EnglishFraudDetector englishFraudDetector,
			List<ScoringRule> scoringRules,
			UserScoreRegistry userScoreRegistry) {
		return new ScoreCalculator(englishFraudDetector, scoringRules, userScoreRegistry);
	}

	@Bean
	public static PropertySourcesPlaceholderConfigurer properties() {
		return new PropertySourcesPlaceholderConfigurer();
	}
}
