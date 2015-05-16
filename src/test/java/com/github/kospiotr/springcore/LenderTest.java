package com.github.kospiotr.springcore;

import com.github.kospiotr.springcore.models.Country;
import com.github.kospiotr.springcore.models.Currency;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;


public class LenderTest {

	@Test
	public void shouldNotCreateLenderForUnknownCurrency() throws Exception {

		try {
			new Lender(Country.GERMANY, Currency.PLN);
		} catch (Exception e) {
			assertThat(e).hasMessage("Our company doesn't support credits in your country: GERMANY");
		}
	}
}