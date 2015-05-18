package com.github.kospiotr.springcore.model;

public class Loan {
	int amount;
	User user;

	public Loan() {
	}

	public Loan(int amount, User user) {
		this.amount = amount;
		this.user = user;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Loan{" +
				"amount=" + amount +
				", user=" + user +
				'}';
	}
}
