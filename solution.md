---
layout: default
---

# Register ScoreCalculator with constructor injection

* Level: easy
* Branch: ```register_scorecalculator_with_constructor_injection```
* Use ```PolishFraudDetector``` inner bean
* Use empty ```ScoringRule``` list
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
ApplicationContext ctx = new ClassPathXmlApplicationContext("context.xml");
ScoreCalculator calculator = ctx.getBean("calculator", ScoreCalculator.class);
System.out.println("score = " + calculator.getScore(loan));
```

# Register calculator ScoreCalculator with a factory method

* Level: medium
* Branch: ```register_calculator_scorecalculator_with_a_factory_method```
* What is scoring result for young and rich user? - 0

```context.xml``` :

```xml
<bean id="calculator" class="com.github.kospiotr.springcore.ScoreCalculator" 
    factory-method="createInstance"/>
```

# Register ScoreCalculator with setter injection

* Level: easy
* Branch: ```register_scorecalculator_with_setter_injection```
* Use ```PolishFraudDetector``` inner bean
* Use empty ```ScoringRule``` list
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

# Register Scoring Rules with a parent (bean definition inheritance)

* Level: hard
* Branch: ```register_scoring_rules_with_a_parent_with_inheritance```
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

<bean id="calculator" class="com.github.kospiotr.springcore.ScoreCalculator" 
    parent="scoringRules">
    <property name="fraudDetector">
        <bean class="com.github.kospiotr.springcore.fraud.PolishFraudDetector"/>
    </property>
</bean>
```

Without using inheritance:

* Level: hard
* Branch: ```register_scoring_rules_with_a_parent_with_ref```

```context.xml``` :

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:util="http://www.springframework.org/schema/util"
       xsi:schemaLocation="
       http://www.springframework.org/schema/beans 
       http://www.springframework.org/schema/beans/spring-beans.xsd
       http://www.springframework.org/schema/util 
       http://www.springframework.org/schema/util/spring-util.xsd">

    <util:list id="rulesList" value-type="com.github.kospiotr.springcore.scoring.ScoringRule">
        <bean class="com.github.kospiotr.springcore.scoring.JobScoringRule"/>
        <bean class="com.github.kospiotr.springcore.scoring.AgeScoringRule"/>
    </util:list>

    <bean id="calculator" class="com.github.kospiotr.springcore.ScoreCalculator">
        <property name="fraudDetector">
            <bean class="com.github.kospiotr.springcore.fraud.PolishFraudDetector"/>
        </property>
        <property name="scoringRules" ref="rulesList"/>
    </bean>

</beans>
```

#Register two ScoreCalculators, one for PL, one for UK

* Level: easy
* Branch: ```register_two_scorecalculators_one_for_pl_one_for_uk```
* Use bean reference

```context.xml``` :

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

    <bean id="polishFraudDetector" class="com.github.kospiotr.springcore.fraud.PolishFraudDetector"/>
    <bean id="englishFraudDetector" class="com.github.kospiotr.springcore.fraud.EnglishFraudDetector"/>

    <bean id="plCalculator" class="com.github.kospiotr.springcore.ScoreCalculator">
        <property name="fraudDetector" ref="englishFraudDetector"/>
        <property name="scoringRules">
            <list>
                <bean class="com.github.kospiotr.springcore.scoring.JobScoringRule"/>
                <bean class="com.github.kospiotr.springcore.scoring.AgeScoringRule"/>
            </list>
        </property>
    </bean>

    <bean id="enCalculator" class="com.github.kospiotr.springcore.ScoreCalculator">
        <property name="fraudDetector" ref="polishFraudDetector"/>
        <property name="scoringRules">
            <list>
                <bean class="com.github.kospiotr.springcore.scoring.JobScoringRule"/>
                <bean class="com.github.kospiotr.springcore.scoring.AgeScoringRule"/>
            </list>
        </property>
    </bean>

</beans>
```

```App``` :

```java
Loan loan = new Loan(1000, User.youngRichUser());

ApplicationContext ctx = new ClassPathXmlApplicationContext("context.xml");
ScoreCalculator plCalculator = ctx.getBean("plCalculator", ScoreCalculator.class);
System.out.println("score = " + plCalculator.getScore(loan));
System.out.println("score = " + plCalculator.getScore(loan));
System.out.println("score = " + plCalculator.getScore(loan));

ScoreCalculator enCalculator = ctx.getBean("enCalculator", ScoreCalculator.class);
System.out.println("score = " + enCalculator.getScore(loan));
System.out.println("score = " + enCalculator.getScore(loan));
System.out.println("score = " + enCalculator.getScore(loan));
```

#Make a new Rule: remembering last score for a given user. Add %10 points of last score to new score

* Level: medium
* Branch: ```make_a_new_rememberinglastscorerule_rule```
* Add this rule to ```ScoringRules``` . Register it once, and verify what object hash for this rule, both scoring calculators (PL and UK) have.

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

    ...

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
    
    ...

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
        <property name="fraudDetector" ref="polishFraudDetector"/>
        <property name="scoringRules" ref="rulesList"/>
        <property name="userScoreRegistry" ref="userScoreRegistry"/>
    </bean>

    <bean id="enCalculator" class="com.github.kospiotr.springcore.ScoreCalculator">
        <property name="fraudDetector" ref="englishFraudDetector"/>
        <property name="scoringRules" ref="rulesList"/>
        <property name="userScoreRegistry" ref="userScoreRegistry"/>
    </bean>

</beans>
```

Result:

```
PL
score = 126000
score = 138600

EN:
score = 139860
score = 139986
```

# Make it so that each Scoring Calculator has a different instance of this bean, using prototype scope.

* Level: easy
* Branch: ```using_prototype_scope```
* What happened?
* Check if object hashes differ between this rule for PL and UK

```context.xml``` :

```xml
...    
<bean id="userScoreRegistry" class="com.github.kospiotr.springcore.UserScoreRegistry" scope="prototype"/>
...
```

Result:

```
PL:
score = 126000
score = 126000

EN:
score = 126000
score = 126000
```


# Make both ScoreCalculator autowire dependencies

* Level: medium
* Branch: ```autowire_xml```
* Is it possible?
* Which properties can be autowired? - Only rulesList can be autowired by type and by name

```xml
    <bean id="plCalculator" class="com.github.kospiotr.springcore.ScoreCalculator" autowire="byType">
        <property name="fraudDetector" ref="polishFraudDetector"/>
        <property name="scoringRules" ref="rulesList"/>
    </bean>
```

# Remove all xml definitions

* Level: medium
* Branch: ```annotations_initialization```

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
* Mark ```scoringRules``` and ```userScoreRegistry``` with ```@Autowired``` or ```@Inject```

```UserScoreRegistry``` , ```AgeScoringRule``` , ```JobScoringRule``` :

* Mark class with ```@Component``` or ```@Named```

```EnglishFraudDetector``` :

* Mark class with ```@Component``` or ```@Named``` and ```@Qualifier("englishFraudDetector")```

```PolishFraudDetector``` :

* Mark class with ```@Component``` or ```@Named``` and ```@Qualifier("polishFraudDetector")```

```RememberingLastScoreRule``` :

* Mark class with ```@Component``` or ```@Named```
* Mark constructor with ```@Autowired``` or ```@Inject```
    
```App``` : 

```java
Loan loan = new Loan(1000, User.youngRichUser());

ApplicationContext ctx = new ClassPathXmlApplicationContext("context.xml");
ScoreCalculator calculator = ctx.getBean(ScoreCalculator.class);
System.out.println("score = " + calculator.getScore(loan));
System.out.println("score = " + calculator.getScore(loan));
System.out.println("score = " + calculator.getScore(loan));
```

# Inject dependencies with constructor

* Level: simple
* Branch: ```annotations_inject_with_constructor```

```ScoreCalculator``` :

* Remove annotations from fields 
* Mark constructor with ```@Autowired``` or ```@Inject``` and add ```@Qualifier("polishFraudDetector")``` to ```fraudDetector``` parameter:

```java
@Autowired
public ScoreCalculator(@Qualifier("polishFraudDetector") FraudDetector fraudDetector, List<ScoringRule> scoringRules, UserScoreRegistry userScoreRegistry) {
    this.fraudDetector = fraudDetector;
    this.scoringRules = scoringRules;
    this.userScoreRegistry = userScoreRegistry;
}
```

# Try to register two ScoreCalculators, one for PL, one for UK

* Level: hard
* Branch: ```multiple_beans_with_annotations```

It is not possible, the only option is to extend ```ScoreCalculator``` or make a composition.

Create ```PolishScoreCalculator``` :

```java
package com.github.kospiotr.springcore;

import com.github.kospiotr.springcore.fraud.FraudDetector;
import com.github.kospiotr.springcore.scoring.ScoringRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PolishScoreCalculator extends ScoreCalculator {

	@Autowired
	public PolishScoreCalculator(@Qualifier("polishFraudDetector") FraudDetector fraudDetector, List<ScoringRule> scoringRules, UserScoreRegistry userScoreRegistry) {
		super(fraudDetector, scoringRules, userScoreRegistry);
	}
}
```

Create ```EnglishScoreCalculator``` :

```java
package com.github.kospiotr.springcore;

import com.github.kospiotr.springcore.fraud.FraudDetector;
import com.github.kospiotr.springcore.scoring.ScoringRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EnglishScoreCalculator extends ScoreCalculator {

	@Autowired
	public EnglishScoreCalculator(@Qualifier("englishFraudDetector") FraudDetector fraudDetector, List<ScoringRule> scoringRules, UserScoreRegistry userScoreRegistry) {
		super(fraudDetector, scoringRules, userScoreRegistry);
	}
}
```

Remove all annotations from ```ScoreCalculator``` and make it abstract:

```java
public abstract class ScoreCalculator {

	private FraudDetector fraudDetector;
	private List<ScoringRule> scoringRules;
	private UserScoreRegistry userScoreRegistry;

	public ScoreCalculator() {
		System.out.println("Constructing ScoreCalculator");
	}

	public ScoreCalculator(FraudDetector fraudDetector, List<ScoringRule> scoringRules, UserScoreRegistry userScoreRegistry) {
		System.out.println("Constructing ScoreCalculator with fraudDetector: " + fraudDetector + ", " + scoringRules + ", " + userScoreRegistry);
		this.fraudDetector = fraudDetector;
		this.scoringRules = scoringRules;
		this.userScoreRegistry = userScoreRegistry;
	}

    ...
```

```App``` :

```java

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
```

# Create sample String properties in ```UserScoreRegistry``` with default values and move them to properties file

* Level: medium
* Branch: ```properties_placeholder```

Create ```config.properties``` in resources:

```
username: kospiotr
password: pass
```

Initialize ```PropertyPlaceholderConfigurer``` :

```xml
<bean class="org.springframework.beans.factory.config.PropertyPlaceholderConfigurer">
    <property name="locations" value="classpath:config.properties"/>
</bean>
```

Use it in ```UserScoreRegistry``` :

```java
    ...
	@Value("${username}")
	private String username = "DefaultUsername";
	
	@Value("${password}")
	private String password = "DefaultPassword";

	public Integer getLastScoreForUser(User user) {
		System.out.println("Using credentials for UserStoreRegistry: " + username + "/" + password);
    ...
```

# Make ```UserScoreRegistry``` to initialize webservice after constructing object

* Level: easy
* Branch: ```lifecycle_with_annotations```

Add ```init``` method with ```@PostConstruct``` annotation:

```java
	@PostConstruct
	public void init() {
		System.out.println("Initializing WebService for UserScoreRegistry with credentials: " + username + "/" + password);
	}
```

# Replace XML with Java configuration with removed all annotations in previous classes

* Level: medium
* Branch: ```java_config_simple```
* What are credentials now in USerStoreRegistry?

Remove classes ```PolishScoreCalculator```, ```EnglishScoreCalculator```, remove all annotations and make ```ScoreCalculator``` non abstract again.

Create ```AppConfig``` :

```java
package com.github.kospiotr.springcore;

import com.github.kospiotr.springcore.fraud.EnglishFraudDetector;
import com.github.kospiotr.springcore.fraud.PolishFraudDetector;
import com.github.kospiotr.springcore.scoring.AgeScoringRule;
import com.github.kospiotr.springcore.scoring.JobScoringRule;
import com.github.kospiotr.springcore.scoring.RememberingLastScoreRule;
import com.github.kospiotr.springcore.scoring.ScoringRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

import static java.util.Arrays.asList;

@Configuration
public class AppConfig {


	@Bean
	public ScoreCalculator plCalculator() {
		return new ScoreCalculator(new PolishFraudDetector(), scoringRules(), userScoreRegistry());
	}

	@Bean
	public ScoreCalculator enCalculator() {
		return new ScoreCalculator(new EnglishFraudDetector(), scoringRules(), userScoreRegistry());
	}

	@Bean
	public UserScoreRegistry userScoreRegistry() {
		return new UserScoreRegistry();
	}

	@Bean
	public List<ScoringRule> scoringRules() {
		List<ScoringRule> scoringRules = asList(
				new AgeScoringRule(),
				new JobScoringRule(),
				new RememberingLastScoreRule(userScoreRegistry()));
		;
		return scoringRules;
	}
}
```

```App``` :

```java
Loan loan = new Loan(1000, User.youngRichUser());

ApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);

ScoreCalculator plCalculator = ctx.getBean("plCalculator", ScoreCalculator.class);
System.out.println("score = " + plCalculator.getScore(loan));
System.out.println("score = " + plCalculator.getScore(loan));
System.out.println("score = " + plCalculator.getScore(loan));

ScoreCalculator enCalculator = ctx.getBean("enCalculator", ScoreCalculator.class);
System.out.println("score = " + enCalculator.getScore(loan));
System.out.println("score = " + enCalculator.getScore(loan));
System.out.println("score = " + enCalculator.getScore(loan));
```

# Make UK ```ScoreCalculator``` using autowired ```userScoreRegistry``` property

* Level: medium
* Branch: ```java_config_simple_with_autowire```

```AppConfig``` :

```java
...

@Bean
@Autowired
public ScoreCalculator enCalculator(UserScoreRegistry userScoreRegistry) {
    return new ScoreCalculator(new EnglishFraudDetector(), scoringRules(), userScoreRegistry);
}

...
```

# Fix credentials for ```UserScoreRegistry```

* Level: medium
* Branch: ```java_config_fix_placeholders```


Add ```@PropertySource``` and ```PropertySourcesPlaceholderConfigurer``` baen:

```java
...

@Configuration
@PropertySource(value = "classpath:config.properties", name = "locations")
public class AppConfig {

...

	@Bean
	public static PropertySourcesPlaceholderConfigurer properties() {
		return new PropertySourcesPlaceholderConfigurer();
	}
}

```

# Add annotations and use ```@ComponentScan``` where possible

* Level: medium
* Branch: ```java_config_with_component_scan```

Add ```@Component``` or ```@Named``` annotations to class: ```UserScoreRegistry```, ```EnglishFraudDetector```, ```PolishFraudDetector```, ```AgeScoringRule```, ```JobScoringRule```, ```LoansHistoryScoringRule```, ```RememberingLastScoreRule```.

Add ```@Autowired``` or ```@Named``` to ```RememberingLastScoreRule``` constructor.
 
```App``` :

```java
@Configuration
@PropertySource(value = "classpath:config.properties", name = "locations")
@ComponentScan(basePackages = {"com.github.kospiotr.springcore"})
public class AppConfig {

	@Bean
	@Autowired
	public ScoreCalculator plCalculator(
			PolishFraudDetector polishFraudDetector,
			List<ScoringRule> scoringRules,
			UserScoreRegistry userScoreRegistry) {
		return new ScoreCalculator(polishFraudDetector, scoringRules, userScoreRegistry);
	}

	@Bean
	@Autowired
	public ScoreCalculator enCalculator(
			EnglishFraudDetector englishFraudDetector,
			List<ScoringRule> scoringRules,
			UserScoreRegistry userScoreRegistry) {
		return new ScoreCalculator(englishFraudDetector, scoringRules, userScoreRegistry);
	}

	@Bean
	public static PropertySourcesPlaceholderConfigurer properties() {
		return new PropertySourcesPlaceholderConfigurer();
	}
}
```

# Create integration test

* Level: easy
* Branch: ```integration_testing```

```java
package com.github.kospiotr.springcore;

import com.github.kospiotr.springcore.model.Loan;
import com.github.kospiotr.springcore.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.fest.assertions.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class ScoreCalculatorIT {

	@Autowired
	ScoreCalculator plCalculator;

	@Test
	public void shouldPolishCalculatorReturnProperScore() throws Exception {
		Loan loan = new Loan(1000, User.youngRichUser());
		assertThat(plCalculator.getScore(loan)).isEqualTo(126100);
		assertThat(plCalculator.getScore(loan)).isEqualTo(138810);
	}
}
```