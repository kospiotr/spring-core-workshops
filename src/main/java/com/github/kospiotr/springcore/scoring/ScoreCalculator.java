package com.github.kospiotr.springcore.scoring;

import com.github.kospiotr.springcore.models.CreditRequest;
import com.github.kospiotr.springcore.scoring.rules.ScoringRule;

import java.util.List;

public class ScoreCalculator {

	public Integer calculate(final CreditRequest creditRequest, List<ScoringRule> rules) {
		Integer out = 0;
		for (ScoringRule rule : rules) {
			Integer score = rule.getScore(creditRequest);
			System.out.println("score = " + score + " from " + rule.getClass().getSimpleName() + " rule");
			out = Integer.sum(out, score);
		}
		return out;
	}


}