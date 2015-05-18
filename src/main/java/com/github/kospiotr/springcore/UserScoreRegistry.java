package com.github.kospiotr.springcore;

import com.github.kospiotr.springcore.model.User;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UserScoreRegistry {

	private Map<User, Integer> lastScoreForUser = new ConcurrentHashMap<>();

	public Integer getLastScoreForUser(User user) {
		Integer lastScore = lastScoreForUser.get(user);
		return lastScore == null ? 0 : lastScore;
	}

	public void setLastScoreForUser(User user, Integer lastScore) {
		lastScoreForUser.put(user, lastScore);
	}

}
