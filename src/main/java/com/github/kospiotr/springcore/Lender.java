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
	private final Country country;
	private final PolishScoringSystem polishScoringSystem = new PolishScoringSystem();
	private final EnglishScoringSystem englishScoringSystem = new EnglishScoringSystem();

	public Lender(Country country, Currency currency) {
		this.currency = currency;
		this.country = country;
	}

	private ScoringSystem detectScoringSystem(Country country) {
		if (Country.POLAND == country) {
			return polishScoringSystem;
		}
		if (Country.UNIED_KINDOM == country) {
			return englishScoringSystem;
		}
		throw new IllegalArgumentException("Our company doesn't support credits in your country: " + country);
	}

	public CreditDecision lend(CreditRequest creditRequest) {
		ScoringSystem scoringSystem = detectScoringSystem(country);
		boolean userEligibleForCredit = scoringSystem.isUserEligibleForCredit(creditRequest);
		return new CreditDecision(userEligibleForCredit, creditRequest);
	}
}
