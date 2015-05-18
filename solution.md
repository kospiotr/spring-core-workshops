---
layout: default
---

# Add dependency

```
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-context</artifactId>
    <version>...</version>
</dependency>
```

# Initialize Application Context

```App.java``` :

```java
ApplicationContext ctx = new ClassPathXmlApplicationContext("context.xml");
```

# Register ScoreCalculator with constructor injection

* Use PolishFraudDetector inner bean
* Use empty ScoringRule list
* What is scoring result for young and rich user? - 0

```context.xml``` :

```xml
<bean id="calculator" class="com.github.kospiotr.springcore.ScoreCalculator">
    <constructor-arg>
        <bean class="com.github.kospiotr.springcore.fraud.PolishFraudDetector"/>
    </constructor-arg>
    <constructor-arg>
        <list>
        </list>
    </constructor-arg>
</bean>

```

```App.java``` :

```java
Loan loan = new Loan(1000, User.youngRichUser());
ApplicationContext ctx = new ClassPathXmlApplicationContext("context.xml");
ScoreCalculator calculator = ctx.getBean("calculator", ScoreCalculator.class);
System.out.println("score = " + calculator.getScore(loan));
```

# Register ScoreCalculator with setter injection

* Use PolishFraudDetector inner bean
* Use empty ScoringRule list
* What is scoring result for young and rich user? - 0

```context.xml``` :

```xml
<bean id="calculator" class="com.github.kospiotr.springcore.ScoreCalculator">
    <property name="fraudDetector">
        <bean class="com.github.kospiotr.springcore.fraud.PolishFraudDetector"/>
    </property>
    <property name="scoringRules">
        <list>
        </list>
    </property>
</bean>
```

```App.java``` :

```java
Loan loan = new Loan(1000, User.youngRichUser());
ApplicationContext ctx = new ClassPathXmlApplicationContext("context.xml");
ScoreCalculator calculator = ctx.getBean("calculator", ScoreCalculator.class);
System.out.println("score = " + calculator.getScore(loan));
```

# Register calculator ScoreCalculator with a factory method

* Use PolishFraudDetector inner bean
* Use empty ScoringRule list
* What is scoring result for young and rich user? - 0

```context.xml``` :

```xml
<bean id="calculator" class="com.github.kospiotr.springcore.ScoreCalculator" factory-method="createInstance">
    <constructor-arg>
        <bean class="com.github.kospiotr.springcore.fraud.PolishFraudDetector"/>
    </constructor-arg>
    <constructor-arg>
        <list>
        </list>
    </constructor-arg>
</bean>
```

```App.java``` :

```java
Loan loan = new Loan(1000, User.youngRichUser());
ApplicationContext ctx = new ClassPathXmlApplicationContext("context.xml");
ScoreCalculator calculator = ctx.getBean("calculator", ScoreCalculator.class);
System.out.println("score = " + calculator.getScore(loan));
```

# Register Scoring Rules with a parent (bean definition inheritance)

* Which beans applies for this method? - All works.
* What is scoring result for young and rich user now? - 126 000
* Is it clean? How can we define rule list in one place and use it in all beans? - No, should use composition over inheritance!

```context.xml``` :

```xml
<bean id="scoringRules" abstract="true">
    <property name="scoringRules">
        <list>
            <bean class="com.github.kospiotr.springcore.scoring.JobScoringRule"/>
            <bean class="com.github.kospiotr.springcore.scoring.AgeScoringRule"/>
        </list>
    </property>
</bean>

<bean id="calculator" class="com.github.kospiotr.springcore.ScoreCalculator" parent="scoringRules">
    <constructor-arg>
        <bean class="com.github.kospiotr.springcore.fraud.PolishFraudDetector"/>
    </constructor-arg>
    <constructor-arg>
        <list>
        </list>
    </constructor-arg>
</bean>

<bean id="calculator" class="com.github.kospiotr.springcore.ScoreCalculator" parent="scoringRules">
    <property name="fraudDetector">
        <bean class="com.github.kospiotr.springcore.fraud.PolishFraudDetector"/>
    </property>
</bean>

<bean id="calculator" class="com.github.kospiotr.springcore.ScoreCalculator" factory-method="createInstance"
      parent="scoringRules">
    <constructor-arg>
        <bean class="com.github.kospiotr.springcore.fraud.PolishFraudDetector"/>
    </constructor-arg>
    <constructor-arg>
        <list>
        </list>
    </constructor-arg>
</bean>
```

Without using inheritance:

```context.xml``` :

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:util="http://www.springframework.org/schema/util"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
       http://www.springframework.org/schema/util http://www.springframework.org/schema/util/spring-util.xsd">

    <util:list id="rulesList" value-type="com.github.kospiotr.springcore.scoring.ScoringRule">
        <bean class="com.github.kospiotr.springcore.scoring.JobScoringRule"/>
        <bean class="com.github.kospiotr.springcore.scoring.AgeScoringRule"/>
    </util:list>

    <bean id="calculator" class="com.github.kospiotr.springcore.ScoreCalculator">
        <constructor-arg>
            <bean class="com.github.kospiotr.springcore.fraud.PolishFraudDetector"/>
        </constructor-arg>
        <constructor-arg ref="rulesList"/>
    </bean>

    <bean id="calculator" class="com.github.kospiotr.springcore.ScoreCalculator">
        <property name="fraudDetector">
            <bean class="com.github.kospiotr.springcore.fraud.PolishFraudDetector"/>
        </property>
        <property name="scoringRules" ref="rulesList"/>
    </bean>

    <bean id="calculator" class="com.github.kospiotr.springcore.ScoreCalculator" factory-method="createInstance">
        <constructor-arg>
            <bean class="com.github.kospiotr.springcore.fraud.PolishFraudDetector"/>
        </constructor-arg>
        <constructor-arg ref="rulesList"/>
    </bean>
</beans>
```

#Register two ScoreCalculators, one for PL, one for UK

* Use bean reference

```context.xml``` :

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:util="http://www.springframework.org/schema/util"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
       http://www.springframework.org/schema/util http://www.springframework.org/schema/util/spring-util.xsd">

    <bean id="polishFraudDetector" class="com.github.kospiotr.springcore.fraud.PolishFraudDetector"/>
    <bean id="englishFraudDetector" class="com.github.kospiotr.springcore.fraud.EnglishFraudDetector"/>

    <util:list id="rulesList" value-type="com.github.kospiotr.springcore.scoring.ScoringRule">
        <bean class="com.github.kospiotr.springcore.scoring.JobScoringRule"/>
        <bean class="com.github.kospiotr.springcore.scoring.AgeScoringRule"/>
    </util:list>

    <bean id="plCalculator" class="com.github.kospiotr.springcore.ScoreCalculator">
        <property name="fraudDetector" ref="englishFraudDetector"/>
        <property name="scoringRules" ref="rulesList"/>
    </bean>

    <bean id="enCalculator" class="com.github.kospiotr.springcore.ScoreCalculator">
        <property name="fraudDetector" ref="polishFraudDetector"/>
        <property name="scoringRules" ref="rulesList"/>
    </bean>
    
</beans>
```

#Make a new Rule: remembering last score for a given user. Add %10 points of last score to new score

* Add this rule to ScoringRules. Register it once, and verify what object hash for this rule, both scoring calculators (PL and UK) have.

```RememberingLastScoreRule``` :

```java
package com.github.kospiotr.springcore.scoring;

import com.github.kospiotr.springcore.UserScoreRegistry;
import com.github.kospiotr.springcore.model.Loan;
import com.github.kospiotr.springcore.model.User;

public class RememberingLastScoreRule implements ScoringRule {

	private UserScoreRegistry userScoreRegistry;

	public RememberingLastScoreRule(UserScoreRegistry userScoreRegistry) {
		this.userScoreRegistry = userScoreRegistry;
	}

	@Override
	public Integer getScore(Loan loan) {
		User user = loan.getUser();
		Double bonusScore = userScoreRegistry.getLastScoreForUser(user) * 0.1;
		return bonusScore.intValue();
	}
}
```

```UserScoreRegistry``` :

```java
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
```

```ScoreCalculator``` :

```java
package com.github.kospiotr.springcore;

import com.github.kospiotr.springcore.fraud.FraudDetector;
import com.github.kospiotr.springcore.model.Loan;
import com.github.kospiotr.springcore.scoring.ScoringRule;

import java.util.List;

public class ScoreCalculator {

	private FraudDetector fraudDetector;
	private List<ScoringRule> scoringRules;
	private UserScoreRegistry userScoreRegistry;

	public ScoreCalculator(FraudDetector fraudDetector, List<ScoringRule> scoringRules, UserScoreRegistry userScoreRegistry) {
		this.fraudDetector = fraudDetector;
		this.scoringRules = scoringRules;
		this.userScoreRegistry = userScoreRegistry;
	}

	public Integer getScore(Loan loan) {
		Integer score = scoringRules
				.stream()
				.mapToInt(rule -> rule.getScore(loan))
				.sum();
		userScoreRegistry.setLastScoreForUser(loan.getUser(), score);
		return score;
	}

}
```

```context.xml``` :

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:util="http://www.springframework.org/schema/util"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
       http://www.springframework.org/schema/util http://www.springframework.org/schema/util/spring-util.xsd">

    <bean id="polishFraudDetector" class="com.github.kospiotr.springcore.fraud.PolishFraudDetector"/>
    <bean id="englishFraudDetector" class="com.github.kospiotr.springcore.fraud.EnglishFraudDetector"/>
    <bean id="userScoreRegistry" class="com.github.kospiotr.springcore.UserScoreRegistry"/>

    <util:list id="rulesList" value-type="com.github.kospiotr.springcore.scoring.ScoringRule">
        <bean class="com.github.kospiotr.springcore.scoring.JobScoringRule"/>
        <bean class="com.github.kospiotr.springcore.scoring.AgeScoringRule"/>
        <bean class="com.github.kospiotr.springcore.scoring.RememberingLastScoreRule">
            <constructor-arg ref="userScoreRegistry"/>
        </bean>
    </util:list>

    <bean id="plCalculator" class="com.github.kospiotr.springcore.ScoreCalculator">
        <constructor-arg ref="polishFraudDetector"/>
        <constructor-arg ref="rulesList"/>
        <constructor-arg ref="userScoreRegistry"/>
    </bean>

    <bean id="enCalculator" class="com.github.kospiotr.springcore.ScoreCalculator">
        <constructor-arg ref="englishFraudDetector"/>
        <constructor-arg ref="rulesList"/>
        <constructor-arg ref="userScoreRegistry"/>
    </bean>
</beans>
```

```App.java``` :

```java
Loan loan = new Loan(1000, User.youngRichUser());

ApplicationContext ctx = new ClassPathXmlApplicationContext("context.xml");
ScoreCalculator calculator = ctx.getBean("plCalculator", ScoreCalculator.class);
System.out.println("score = " + calculator.getScore(loan));
System.out.println("score = " + calculator.getScore(loan));
System.out.println("score = " + calculator.getScore(loan));
System.out.println("score = " + calculator.getScore(loan));
}

```

Result:

```
score = 126000
score = 138600
score = 139860
score = 139986
```

# Make it so that each Scoring Caclulator has a different instance of this bean, using prototype scope.

* Check if object hashes differ between this rule for PL and UK

```context.xml``` :

```xml
...    
<bean id="userScoreRegistry" class="com.github.kospiotr.springcore.UserScoreRegistry" scope="prototype"/>
...
```

Result:

```
score = 126000
score = 126000
score = 126000
score = 126000
```


# Make both ScoreCalculator autowire dependencies

* Is it possible?
* Which properties can be autowired? - Only rulesList can be autowired by type and by name

```xml
    <bean id="plCalculator" class="com.github.kospiotr.springcore.ScoreCalculator" autowire="byType">
        <property name="fraudDetector" ref="polishFraudDetector"/>
        <property name="scoringRules" ref="rulesList"/>
    </bean>
```

# Remove all xml definitions

```context.xml``` :

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd

       http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd">

    <context:component-scan base-package="com.github.kospiotr.springcore"/>

</beans>
```

```ScoreCalculator``` :

* Mark class with ```@Component``` or ```@Named```
* Mark ```fraudDetector``` with ```@Autowired``` and ```@Qualifier("polishFraudDetector")``` 
* Mark ```scoringRules``` with ```@Autowired```
* Mark ```userScoreRegistry``` with ```@Autowired```

```UserScoreRegistry``` , ```AgeScoringRule``` , ```JobScoringRule``` :

* Mark class with ```@Component``` or ```@Named```

```EnglishFraudDetector``` :

* Mark class with ```@Component``` or ```@Named``` and ```@Qualifier("englishFraudDetector")```

```PolishFraudDetector``` :

* Mark class with ```@Component``` or ```@Named``` and ```@Qualifier("polishFraudDetector")```

```RememberingLastScoreRule``` :

* Mark class with ```@Component``` or ```@Named```
* Mark constructor with ```@Autowired```
    
