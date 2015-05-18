package com.github.kospiotr.springcore;

import com.github.kospiotr.springcore.model.Loan;
import com.github.kospiotr.springcore.model.User;

public class App {

	public static void main(String[] args) {
		Loan loan = new Loan(1000, User.youngRichUser());

		System.out.println("loan = " + loan);

	}
}
