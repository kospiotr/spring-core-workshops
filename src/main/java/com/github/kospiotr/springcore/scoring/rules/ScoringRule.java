package com.github.kospiotr.springcore.scoring.rules;

import com.github.kospiotr.springcore.models.CreditRequest;

public interface ScoringRule {

	Integer getScore(CreditRequest creditRequest);

}
