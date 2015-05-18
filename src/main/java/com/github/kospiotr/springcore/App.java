package com.github.kospiotr.springcore;

import com.github.kospiotr.springcore.model.Loan;
import com.github.kospiotr.springcore.model.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {

	public static void main(String[] args) {
		Loan loan = new Loan(1000, User.youngRichUser());

		ApplicationContext ctx = new ClassPathXmlApplicationContext("context.xml");
		PolishScoreCalculator plCalculator = ctx.getBean(PolishScoreCalculator.class);
		System.out.println("score = " + plCalculator.getScore(loan));
		System.out.println("score = " + plCalculator.getScore(loan));
		System.out.println("score = " + plCalculator.getScore(loan));

		EnglishScoreCalculator enCalculator = ctx.getBean(EnglishScoreCalculator.class);
		System.out.println("score = " + enCalculator.getScore(loan));
		System.out.println("score = " + enCalculator.getScore(loan));
		System.out.println("score = " + enCalculator.getScore(loan));

	}
}
