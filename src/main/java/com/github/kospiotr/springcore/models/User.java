package com.github.kospiotr.springcore.models;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {
	String name;
	int age;
	Money wage;
}
