package com.github.kospiotr.springcore.scoring.system;

import com.github.kospiotr.springcore.models.CreditRequest;

public interface ScoringSystem {
	boolean isUserEligibleForCredit(CreditRequest creditRequest);
}
