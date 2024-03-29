package com.github.kospiotr.springcore.model;

public class User {
	String name;
	int age;
	int wage;

	public User() {
	}

	public User(String name, int age, int wage) {
		this.name = name;
		this.age = age;
		this.wage = wage;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int getWage() {
		return wage;
	}

	public void setWage(int wage) {
		this.wage = wage;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		User user = (User) o;

		return !(name != null ? !name.equals(user.name) : user.name != null);

	}

	@Override
	public int hashCode() {
		return name != null ? name.hashCode() : 0;
	}

	@Override
	public String toString() {
		return "User{" +
				"name='" + name + '\'' +
				", age=" + age +
				", wage=" + wage +
				'}';
	}

	public static User youngRichUser() {
		return new User("User", 26, 10000);
	}
}
