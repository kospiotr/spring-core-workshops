package com.github.kospiotr.springcore;

import com.github.kospiotr.springcore.models.*;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class LenderIT {

	@Test
	public void shouldDeclinePolishCreditDeclarationForPoorPerson() throws Exception {
		Lender lender = new Lender(Country.POLAND, Currency.PLN);
		CreditRequest creditRequest = new CreditRequest("Test purpose", new Money(1000, Currency.PLN), 100, createPoorPolishUser());
		CreditDecision creditDecision = lender.lend(creditRequest);
		assertThat(creditDecision.isCreditDecision()).isFalse();
	}

	@Test
	public void shouldAcceptPolishCreditDeclarationForRichPerson() throws Exception {
		Lender lender = new Lender(Country.POLAND, Currency.PLN);
		CreditRequest creditRequest = new CreditRequest("Test purpose", new Money(1000, Currency.PLN), 100, createRichPolishUser());
		CreditDecision creditDecision = lender.lend(creditRequest);
		assertThat(creditDecision.isCreditDecision()).isTrue();
	}

	@Test
	public void shouldDeclineUKCreditDeclarationForPoorPerson() throws Exception {
		Lender lender = new Lender(Country.UNIED_KINDOM, Currency.GBP);
		CreditRequest creditRequest = new CreditRequest("Test purpose", new Money(1000, Currency.GBP), 100, createPoorUKUser());
		CreditDecision creditDecision = lender.lend(creditRequest);
		assertThat(creditDecision.isCreditDecision()).isFalse();
	}

	@Test
	public void shouldAcceptUKCreditDeclarationForRichPerson() throws Exception {
		Lender lender = new Lender(Country.POLAND, Currency.GBP);
		CreditRequest creditRequest = new CreditRequest("Test purpose", new Money(1000, Currency.GBP), 100, createRichUKUser());
		CreditDecision creditDecision = lender.lend(creditRequest);
		assertThat(creditDecision.isCreditDecision()).isTrue();
	}

	private User createPoorPolishUser() {
		return new User("Poor Testing User", 16, new Money(1500, Currency.PLN));
	}

	private User createRichPolishUser() {
		return new User("Rich Testing User", 46, new Money(15000, Currency.PLN));
	}

	private User createPoorUKUser() {
		return new User("Poor Testing User", 16, new Money(200, Currency.GBP));
	}

	private User createRichUKUser() {
		return new User("Rich Testing User", 46, new Money(15000, Currency.GBP));
	}
}