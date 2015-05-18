package com.github.kospiotr.springcore;

import com.github.kospiotr.springcore.model.Loan;
import com.github.kospiotr.springcore.model.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {

	public static void main(String[] args) {
		Loan loan = new Loan(1000, User.youngRichUser());

		ApplicationContext ctx = new ClassPathXmlApplicationContext("context.xml");
		ScoreCalculator calculator = ctx.getBean("calculator", ScoreCalculator.class);
		System.out.println("score = " + calculator.getScore(loan));
		System.out.println("score = " + calculator.getScore(loan));
		System.out.println("score = " + calculator.getScore(loan));

	}
}
