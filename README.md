# **Rewards Calculator Application (Web Api Application)**

The Rewards Calculator Application is a web service constructed using Java, which computes rewards points for clients according to their past transactions. The application offers different endpoints for accessing rewards data for individual clients or all clients within a given date period.  
 ```
Question: A retailer offers a rewards program to its customers, awarding points based on each recorded purchase.  
A customer receives 2 points for every dollar spent over $100 in each transaction, plus 1 point for every
dollar spent between $50 and $100 in each transaction.
(e.g., a $120 purchase = 2x$20 + 1x$50 = 90 points).  
Given a record of every transaction during a three-month period, calculate the reward points earned for
each customer per month and total.
```
## Table of contents
- [Getting Started](#gettingStarted)
    - [Prerequisites](#prerequisites)
    - [Running Application](#runningProject)
- [Design](#design)
- [API Endpoints](#apiEndpoints)
- [ProjectStructure](#structure)
- [Project Explanation](#project)
- [Database](#input)
- [Testing](#testing)
- [Future Improvements](#future)


## Getting Started <a name="gettingStarted"></a>
The project is implemented using the following technologies:

### Prerequisites <a name="Prerequisites"></a>
- Java 17
- Spring Boot 3.x
- Maven
- JUnit
- Mockito

### Running Application <a name="runningProject"></a>

1. Clone the repository  
  `git clone https://github.com/rsvuyyuru/Rewards-Calculator.git`
2. Navigate to the project directory
  `cd Rewards-Calculator` 
3. To run the application, simply execute the main class RewardApplication located in the `src/main/java/com/RewardsCalculator` folder.
4. The application will start and will be accessible at `http://localhost:8080`.

## Design <a name="design"></a>
 
For this application, I have used **strategy design pattern** to calculate rewards.
For now  we are calculating rewards based on the rules given in question in the `PromotionalRewardStrategy.java`. For any transaction, service implementation class selects the **calculateRewards()** function from the **`PromotionalRewardStrategy`** class by using **`@Qualifier`**. Lets say we got **new type of rewards logic**, now we can create new strategy class implementing **calcualteRewards()** and **change name** in **@Qualifier** use that. This is my idea behind implementing different strategy.


## API Endpoints <a name="apiEndpoints"></a>
This application exposes the following endpoints:

1. **GET "/rewards/totalRewards"** : Retrieves rewards information for all customers within the last three months billing cycle.  To get rewards information of the specific customer you can use the same end point by using as **GET "/rewards/totalRewards?id='customerId'** "

2. **POST "/rewards/customDateRange"** :  Retrieves the rewards for a customer within a specified date range. 
  If the date is given more than the three-month range, currently I am calculating the extra rewards also and showing in total Rewards. So in this case, Total Rewards will not be sum of three months Rewards.
3. **GET "/rewards/transaction/{id}"** : Retrieves the rewards for single transaction.
4. **GET "rewards/allTransactions"** : The total transactions list for all the customers

## Project Structure <a name="structure"></a>
```
reward/
├── README.md
├── src
      └── main/resources
           └── data.sql
           └── schema.sql
     └── main/java/com/RewardsCalculator
        ├── entity
        │   ├── Customer.java
        │   └── Transactions.java        
        ├── controller
        │   └── RewardsController.java       
        ├── model
        │   ├── request/ customerAndDataRangeRequest.java
        │   └── response/ Reward.java
        ├── repository
        │   ├── CustomerRepository.java (interface)
        │   └── TransactionsRepository.java (interface)
        ├── service
        │  ├── RewardPointsService.java (interface)
        │  └── RewardPointsServiceImpl.java
        │  └── StoreType.java
        ├── strategy
        │   ├── FixedRewardStrategy.java
        │   └── PromotionalRewardStrategy.java
        │   └── RewardCalculationStrategy.java (interface)
        │   └── StrategyType.java 
        └── RewardApplication.java       
    ├── test/java/com/RewardsCalculator
          ├── service
          │  └── RewardsPointsServiceImplTest.java
          ├── strategy
          │  └── PromotionalRewardStrategy.java
          └── RewardApplicationTests.java
    
```
## Project Explanation <a name="project"></a>

- **getAllRewards()**: Returns List of 'Reward' Objects for all the customers within the **past three months** Range. The reward object contains the information of each Month rewards and also the total Rewards for each customer. If a customer has no rewards earned for that month, it will be returning as '0'. Total rewards are calculated based on the sum of each month rewards.
- **getRewardsById(String customerId)**: Returns the Reward object for the specified customer. So it calculates each month and total rewards for the customer earned within the past three months from the system date
- **getRewardMapForCustomerIdWithinDateRange(String customerId, LocalDate startDate,
  LocalDate endDate)**: Returns the Reward Map having String as key, rewards amount as value for specified customer within the date range. For dates, end date should be past than start date  
(Example: start date = "2023-07-23" end date = "2023-06-20" so that you can get the rewards in between your dates).  

  Also if you try to start date which is in the Future, it throws error. 

  Each key-value pair in Reward Map looks like:

  ```
  Example: 
  
  {
    "customerId": "101",
    "firstName": "Mike",
    "lastName": "Ross",
    "firstMonthRewards": 30,
    "secondMonthRewards": 50,
    "thirdMonthRewards": 70,
    "totalRewards": 150
  }
  ```
  
- **getCustomerRewardsWithinDateRange(String customerId, Map<String, Long> rewardsMap)**: Returns the Reward Object to controller for given customer and their Rewards Map.
- **getRewardsForTransactionId(String transactionId)**: Returns the reward object for the particular transaction. Here if we want to see the rewards only for a transaction, it calculates rewards for that Purchase Amount and only shows total Rewards. so you will find the object having all other months fields as '0'. So object could look like:
```
{
    "customerId": "101",
    "firstName": "Mike",
    "lastName": "Ross",
    "firstMonthRewards": 0,
    "secondMonthRewards": 0,
    "thirdMonthRewards": 0,
    "totalRewards": 50
  }
  
```
- **getLastThreeMonthsBillingCycle(LocalDate givenDate)**: Returns the Map having String as key and LocalDate as value. It gives the three-month prior dates from the given date. The map looks like:
```
"firstMonthStartDate" : 2023-06-23
"firstMonthEndDate": 2023-07-22 (given date)
"secondMonthStartDate": 2023-05-23
"secondMonthEndDate": 2023-06-22
"thirdMonthStartDate": 2023-04-23;
"thirdMonthEndDate": 2023-05-22;
```
For Example: When given date is 2023-07-22, you will now have the dates as above so the date range is 2023-04-23 to 2023-07-22.  

**For the entire project the FirstMonth, SecondMonth, ThirdMonth date ranges are calculated in the above format only.**

- findAllCustomers(): Returns all the customer objects from the repository
- findByCustomerId(String customerId): Returns customer object for the given customer Id
- findByTransactionId(String transactionId): Return the transaction object, when user gives the transaction Id

>**Note**: Throughout the project, specify a valid range for the Start Date and End Date to calculate rewards. Kindly ensure that the Start Date is the most recent date and the End Date falls prior to it  it as the computation of rewards is based on past three months data (Start Date > End Date).

### Request and Response
  - **Request**: For the POST API end point the request object should be sent as JSON with the fields Customer ID, Start and End dates
  - **Response**: Reward object is returned as response for  the API calls in JSON format, it contains below details.  
 ```{ customerId, firstName, lastName, firstMonthRewards, secondMonthRewards, thirdMonthRewards, totalRewards }```

## Database <a name="input"></a>
Currently, there are two tables for the database. For this project , I have used the H2 database and tables 
- CUSTOMER(CUSTOMER_ID,FIRST_NAME, LAST_NAME) : Contains all the customers details. 
- TRANSACTIONS(TRANSACTION_ID, CUSTOMER_ID, TRANSACTION_DATE, AMOUNT) : Contains all the transaction details. 

For this application, the customer table has the CUSTOMER_ID as its primary key. And for the transaction table TRANSACTION_ID is Primary key, CUSTOMER_ID is the foreign key.  

**schema.sql** 

This SQL file contains the information for creating the two tables.

> CREATE TABLE IF NOT EXISTS `customer`  (
`CUSTOMER_ID` VARCHAR(50) PRIMARY KEY,
`FIRST_NAME` VARCHAR(255) ,
`LAST_NAME` VARCHAR(255)
);

> CREATE TABLE IF NOT EXISTS `transactions`(
`TRANSACTION_ID` VARCHAR(50) PRIMARY KEY,
FOREIGN KEY (`CUSTOMER_ID`) REFERENCES Customer(`CUSTOMER_ID`),
`TRANSACTION_DATE` TIMESTAMP,
`AMOUNT` DECIMAL(13,2)
);

**data.sql**

This SQL file contains all the insert data statements to the tables.

**To view data H2 in-memory database**  
The 'test-db' runs on H2 in-memory database. To view and query the database you can browse to http://localhost:8080/h2-console. Default username is 'sa' and password as 'password'.

## Testing <a name="testing"></a>
The project includes a set of unit tests for the Reward Calculator application. The tests are located in the `src/test/java/com/RewardsCalculator` folder. The tests use JUnit 5 and Mockito to test the functionality. Implemented tests for Service Class (**`RewardPointsServiceImplTest.java`**) and also for calculation of Rewards for the given amount in the class(**`PromotionalRewardStrategyTest.java`**).

## My Idea Over Project

In an ideal case, it may not be necessary to constantly calculate rewards from api, however, given their periodic change, it's compulsory to calculate them for every transaction and store in the database. This is primarily due to promotional rewards changes periodically.


The assessment did not provide explicit specifications,  as it mentioned to retrieve and then calculate the reward based on the logic of a customer receives 2 points for every dollar spent over $100 in each transaction, plus 1 point for every
dollar spent between $50 and $100 in each transaction. Following this, I implemented accordingly. 

Practically, reward calculations would occur within particular time frames, so always doing a **'fetch from database'** might affect the rewards amounts as we calculate total based on pass three months data and also cost increasing.  For a production level system, for every transaction, the computed rewards will need to be stored in the database under a **new **`rewardsAmount`** column.**



## Future Improvements<a name="future"></a>

- Currently, we are only dealing with a single retailer. However, if there are plans to bring onboard multiple retailers, each with its own unique rule for reward calculation. In order to be prepared for this change, I have already developed a map that aligns with the `Store Type` enum. This will create a map having key-value pairs where the key represents the **'Store Name'** and the value represents for the reward promotional rule.


- I have designed the '**`StrategyType`**' class in a way that it will go through all the available strategies and sets the reward calculation strategy according to the `Store Name`. For instance, the "Store_1" has `PromotionalRewardStrategy` logic has been applied  (as per the logic outlined in the question), whereas the `FixedRewardStrategy` class is attached with "Store_2".


- Also, if we include store names as a column in the database. Therefore, each transaction will carry the `Store Name` making it easier to identify the applicable strategy. This logic can also be applied when dealing with multiple retailers moving forward.
I've already developed the `StrategyType` class and `StoreType` enum, although they are not currently in use within the application.


The API specifications have not been particularly detailed, therefore, at this point in time, I've restricted data collection to the past three months if not date has given. If date is more than three months then I have just added them to `totalRewards`. So you sometimes find discrepancy in the totalRewards and total of each Month Rewards.

