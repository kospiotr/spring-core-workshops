package com.github.kospiotr.springcore;

import com.github.kospiotr.springcore.models.Country;
import com.github.kospiotr.springcore.models.CreditDecision;
import com.github.kospiotr.springcore.models.CreditRequest;
import com.github.kospiotr.springcore.models.Currency;
import com.github.kospiotr.springcore.scoring.system.EnglishScoringSystem;
import com.github.kospiotr.springcore.scoring.system.PolishScoringSystem;
import com.github.kospiotr.springcore.scoring.system.ScoringSystem;

public class Lender {
	private final Currency currency;
	private final ScoringSystem scoringSystem;

	public Lender(Country country, Currency currency) {
		this.currency = currency;
		this.scoringSystem = detectScoringSystem(country);
	}

	private ScoringSystem detectScoringSystem(Country country) {
		if (Country.POLAND == country) {
			return new PolishScoringSystem();
		}
		if (Country.UNIED_KINDOM == country) {
			return new EnglishScoringSystem();
		}
		throw new IllegalArgumentException("Our company doesn't support credits in your country: " + country);
	}

	public CreditDecision lend(CreditRequest creditRequest) {
		boolean userEligibleForCredit = scoringSystem.isUserEligibleForCredit(creditRequest);
		return new CreditDecision(userEligibleForCredit, creditRequest);
	}
}
