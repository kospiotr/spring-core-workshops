---
layout: default
---

# Goals
* Learn what is Inversion of Control and Dependency Injection
* What problems it solves and how
* Get familiar with Spring and its ecosystem components

# Agenda

##Day 1

* Spring Core
  * Motivation
  * XML configuration
  * Object management
  * Scope
  * Lifecycle
  * Dependency injection
  * Profiles
  * Java configuration
  * Testing
  
* Lectures: [http://kospiotr.github.io/wiki/spring-framework-core.html](http://kospiotr.github.io/wiki/spring-framework-core.html)
* Workshop branch: spring-core

##Day 2

* Spring MVC [TBD]
* Spring AOP [TBD]

##Day 3

* Spring + JPA [TBD]
* Spring Boot [TBD]

# Prerequisites

* JDK 8
* Maven
* GIT
* IDE

Clone initial project:

```bash
git clone git@github.com:kospiotr/spring-workshops.git
```

Install with Maven:

```bash
mvn clean install
```

# Application architecture
Existing application doesn't use Spring Framework but it's fully functional product. As a maintaining team your goal is to make it working with Spring and then add new features.

## Model

Loans scoring system. You give it a Loan Application (application - somebody applies for a creditRequest). It fires up Fraud Detection System, verifies if it is a fraud or not. If not, it fires up scoring rules (many), sums up scores. Returns score. All scoring rules, have configurable max score. There are two Fraud Detectors implementations, one for Poland, one for UK. There should be two scoring systems (UK, Poland), but they work the same, except for Fraud Detector.

## Class structure

* ScoreCalculator (main class)
* FraudDetector
  * PolishFraudDetector
  * EnglishFraudDetector
* Scoring Rule
  * AgeScoringRule
  * LoansHistoryScoringRule
  * JobScoringRule



# Spring Core

## XML configuration source

* Register ScoreCalculator with constructor injection
* Register ScoreCalculator with setter injection
* Register ScoreCalculator with a factory method
* Register Scoring Rules with a parent (bean definition inheritance)
* Register two ScoreCalculators, one for PL, one for UK


## Scopes

* Make a new Rule: remembering last score for a given user. Add %10 points of last score to new score.
* Add this rule to ScoringRules. Register it once, and verify what object hash for this rule, both scoring calculators (PL and UK) have.
* Make it so that each Scoring Caclulator has a different instance of this bean, using prototype scope.
* Check if object hashes differ between this rule for PL and UK

## Profiles

* Create LoansHistoryScoringRuleStub, that always gives 100 points.
* Register LoansHistoryScoringRuleStub so that it works only in tests.
* Register LoansHistoryScoringRule so that it works only in production and development.

## Annotations

* Remove xml
* Register ScoreCalculator with ```@Component```
* Inject other bean with ```@Inject```
* Inject FraudDetector while having two beans implementing an interface (```@Qualifier```)
* ```@Inject``` a constructor
* Try to register two ScoreCalculators, one for PL, one for UK

## Properties
* Move database login and password to properties file
* Move security login and password to properties file

## Testing
* Create integration test

## Java Config

* Remove all annotations
* Create a @Configuration class and register two ScoreCalculators, one for PL, one for UK via @Bean
* How many times AgeScoringRule are created?
* Remove @Configuration, add @Bean to @Component. How many times AgeScoringRule are created?

# Spring MVC

Requirements we're going to work on:

* We need an application for giving loans.
* We give loans for a week, two weeks, three weeks, or for a month.
* A logged user can see his loans, see his extensions, and what he owns.
* A logged user can apply for a creditRequest by entering the amount and choosing term.
* A logged user can extend his creditRequest for 1 week.
* The interest for a creditRequest is 1% per day. But when extending, the interest goes up by 10%.
* User cannot have more than 3 active loans. The number of extensions is unlimited.
* We need an HTTP API for another system, to inform us, when a creditRequest is paid up.
* When we have an error, user should be redirected to “we are sorry” page.

# Spring AOP
* In order to improve logging create logger for input and output data of annotated method.
* The front page is often accessed. Create an aspect, which will cache list of loans for a user, without hitting a repository.

# Spring Data JPA
* Change our creditRequest application so that it works on the database

# Spring Boot

Create a library application from the scratch (no copy&paste) with Spring Boot Requirements:

* JSON API
* As a user, I can add a book
* As a user, I can find available books by author or title
* As a user, I can borrow the book

# References
* Spring Workshops based on [https://github.com/jakubnabrdalik/spring-workshops](https://github.com/jakubnabrdalik/spring-workshops)

