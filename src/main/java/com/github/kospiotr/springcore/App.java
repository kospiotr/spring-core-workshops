package com.github.kospiotr.springcore;

import com.github.kospiotr.springcore.models.*;

public class App {
	public static void main(String[] args) {
		Lender polishLender = new Lender(Country.POLAND, Currency.PLN);
		CreditRequest creditRequestForPlnLender = new CreditRequest("Credit for bike", new Money(1000, Currency.PLN), 100, new User("Jan Kowalski", 29, new Money(1000, Currency.PLN)));
		CreditDecision creditDecisionFromPolishLender = polishLender.lend(creditRequestForPlnLender);
		System.out.println("creditDecision from polish lender = " + creditDecisionFromPolishLender);

		Lender ukLender = new Lender(Country.UNIED_KINDOM, Currency.GBP);
		CreditRequest creditRequestForUkLender = new CreditRequest("Credit for bike", new Money(1000, Currency.GBP), 100, new User("Jan Kowalski", 29, new Money(600, Currency.PLN)));
		CreditDecision creditDecisionFromUkLender = ukLender.lend(creditRequestForUkLender);
		System.out.println("creditDecision from UK lender = " + creditDecisionFromUkLender);
	}
}
