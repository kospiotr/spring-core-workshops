package com.github.kospiotr.springcore.scoring;

import com.github.kospiotr.springcore.model.Loan;

public interface ScoringRule {
	Integer getScore(Loan loan);
}
