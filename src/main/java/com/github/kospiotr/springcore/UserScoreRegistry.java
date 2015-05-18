package com.github.kospiotr.springcore;

import com.github.kospiotr.springcore.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class UserScoreRegistry {

	private Map<User, Integer> lastScoreForUser = new ConcurrentHashMap<>();

	@Value("${username}")
	private String username = "DefaultUsername";

	@Value("${password}")
	private String password = "DefaultPassword";

	@PostConstruct
	public void init() {
		System.out.println("Initializing WebService for UserScoreRegistry with credentials: " + username + "/" + password);
	}

	public Integer getLastScoreForUser(User user) {
		System.out.println("Using credentials for UserStoreRegistry: " + username + "/" + password);
		Integer lastScore = lastScoreForUser.get(user);
		return lastScore == null ? 0 : lastScore;
	}

	public void setLastScoreForUser(User user, Integer lastScore) {
		lastScoreForUser.put(user, lastScore);
	}

}