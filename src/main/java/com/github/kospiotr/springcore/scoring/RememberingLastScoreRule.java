package com.github.kospiotr.springcore.scoring;

import com.github.kospiotr.springcore.UserScoreRegistry;
import com.github.kospiotr.springcore.model.Loan;
import com.github.kospiotr.springcore.model.User;
import org.springframework.beans.factory.annotation.Autowired;

public class RememberingLastScoreRule implements ScoringRule {

	private UserScoreRegistry userScoreRegistry;

	@Autowired
	public RememberingLastScoreRule(UserScoreRegistry userScoreRegistry) {
		this.userScoreRegistry = userScoreRegistry;
	}

	@Override
	public Integer getScore(Loan loan) {
		User user = loan.getUser();
		Double bonusScore = userScoreRegistry.getLastScoreForUser(user) * 0.1;
		return bonusScore.intValue();
	}
}
