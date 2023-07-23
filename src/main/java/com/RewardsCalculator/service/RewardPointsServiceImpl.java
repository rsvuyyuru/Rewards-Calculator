package com.RewardsCalculator.service;

import com.RewardsCalculator.entity.Customer;
import com.RewardsCalculator.entity.Transactions;
import com.RewardsCalculator.model.response.Reward;
import com.RewardsCalculator.repository.CustomerRepository;
import com.RewardsCalculator.repository.TransactionsRepository;
import com.RewardsCalculator.strategy.RewardCalculationStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

@Slf4j
@Service
public class RewardPointsServiceImpl implements RewardPointsService {

    @Autowired
    @Qualifier("promotionalReward")
    private RewardCalculationStrategy rewardCalculationStrategy;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    TransactionsRepository transactionRepository;

    public RewardPointsServiceImpl(RewardCalculationStrategy strategy) {
        this.rewardCalculationStrategy = strategy;
    }

    public List<Transactions> findAllTransactions() {
        return transactionRepository.findAll();
    }

    public List<Customer> findAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer findByCustomerId(String customerId) {
        return customerRepository.findByCustomerId(customerId);
    }

    public Transactions findByTransactionId(String transactionId) {
        return transactionRepository.findByTransactionId(transactionId);
    }


    public List<Reward> getAllRewards() {


        List<Reward> allCustomersRewards = new ArrayList<>();

        // Get the start and end dates for the last three months billing cycle.
        Map<String, LocalDate> threeMonthDateRange = getLastThreeMonthsBillingCycle(LocalDate.now());
        try {
            List<Customer> customerList = findAllCustomers();

            // Calculate rewards for the current customer within the three months date range
            for (Customer customer : customerList) {
                Map<String, Long> rewardsMap = getRewardMapForCustomerIdWithinDateRange(customer.getCustomerId(),
                        threeMonthDateRange.get("firstMonthEndDate"), threeMonthDateRange.get("thirdMonthStartDate"));

                allCustomersRewards.add(Reward.builder()
                        .customerId(customer.getCustomerId())
                        .firstName(customer.getFirstName())
                        .lastName(customer.getLastName())
                        .firstMonthRewards(rewardsMap.get("firstMonthRewards"))
                        .secondMonthRewards(rewardsMap.get("secondMonthRewards"))
                        .thirdMonthRewards(rewardsMap.get("thirdMonthRewards"))
                        .totalRewards(rewardsMap.get("totalRewards"))
                        .build());
            }
        }catch (Exception e) {
            log.error("Error occurred while fetching rewards for all customers: " + e.getMessage());
        }
        return allCustomersRewards;
    }

    public Reward getRewardsById(String customerId) {

        try{
            // Get the start and end dates for the last three months billing cycle.
            Map<String, LocalDate> threeMonthDateRange = getLastThreeMonthsBillingCycle(LocalDate.now());

            // Get the rewards for the customer within the last three months date range.
            Map<String, Long> rewardsMap =  getRewardMapForCustomerIdWithinDateRange(customerId,
                    threeMonthDateRange.get("firstMonthEndDate"), threeMonthDateRange.get("thirdMonthStartDate") );

            // Fetch the customer first and last name details from the repository.
            Customer customer = customerRepository.findFirstNameAndLastNameByCustomerId(customerId);
            if(customer == null) {

                log.info("Customer Not Found");
                return new Reward();
            }
            return Reward.builder()
                    .customerId(customerId)
                    .firstName(customer.getFirstName())
                    .lastName(customer.getLastName())
                    .firstMonthRewards(rewardsMap.get("firstMonthRewards"))
                    .secondMonthRewards(rewardsMap.get("secondMonthRewards"))
                    .thirdMonthRewards(rewardsMap.get("thirdMonthRewards"))
                    .totalRewards(rewardsMap.get("totalRewards"))
                    .build();
        } catch (Exception e) {
            log.error("Error occurred while fetching rewards for customer with ID " + customerId + ": " + e.getMessage());
            throw new RuntimeException("Error occurred while fetching rewards for customer with ID " + customerId, e);
        }
    }


    public Map<String,Long> getRewardMapForCustomerIdWithinDateRange(String customerId, LocalDate startDate,
                                                                   LocalDate endDate) {

        if (endDate.isAfter(startDate)) {
            String errorMsg = "Invalid date range provided in the request. Start date cannot be after end date.";
            log.error(errorMsg);
            throw new IllegalArgumentException(errorMsg);
        }

        if (startDate.isAfter(LocalDate.now())) {
            String errorMsg = "Invalid start date provided in the request. Start date cannot be after the current date.";
            log.error(errorMsg);
            throw new IllegalArgumentException(errorMsg);
        }


        //Get the start and end dates for the last three months billing cycle.
        Map<String, LocalDate> threeMonthDateRange = getLastThreeMonthsBillingCycle(startDate);
        LocalDateTime start = startDate.atStartOfDay();
        LocalDateTime end = endDate.atTime(LocalTime.MAX);

        long firstMonthRewards = 0;
        long secondMonthRewards = 0;
        long thirdMonthRewards = 0;
        long totalRewards = 0;

        // Get the list of transactions for the customer within the specified date range.
        List<Transactions> transactionsList = transactionRepository.
                findByCustomerIdAndTransactionDateBetween(customerId, end, start);

        // Calculate rewards for each transaction and assign them to the appropriate time period.
        // If it is within three months Range the "else" case won't be added, it gets added only when you try to
        // retrieve user specifically wants more than three Month Range by selecting the dates explicitly
        for (Transactions transaction : transactionsList) {
            long amount = transaction.getPurchaseAmount();
            long rewards = rewardCalculationStrategy.calculateRewards(amount);

            LocalDate transactionDate = LocalDate.from(transaction.getTransactionDate());
            if (transactionDate.isAfter(threeMonthDateRange.get("firstMonthStartDate")) &&
                    transactionDate.isBefore(threeMonthDateRange.get("firstMonthEndDate"))) {
                firstMonthRewards += rewards;
            } else if (transactionDate.isAfter(threeMonthDateRange.get("secondMonthStartDate")) &&
                    transactionDate.isBefore(threeMonthDateRange.get("secondMonthEndDate"))) {
                secondMonthRewards += rewards;
            } else if (transactionDate.isAfter(threeMonthDateRange.get("thirdMonthStartDate")) &&
                    transactionDate.isBefore(threeMonthDateRange.get("thirdMonthEndDate"))) {
                thirdMonthRewards += rewards;
            } else {
                totalRewards += rewards;
            }
        }

        // Calculate the total rewards earned in the entire date range.
        totalRewards += firstMonthRewards + secondMonthRewards + thirdMonthRewards;

        Map<String, Long> rewardsMap = new HashMap<>();
        rewardsMap.put("firstMonthRewards", firstMonthRewards);
        rewardsMap.put("secondMonthRewards", secondMonthRewards);
        rewardsMap.put("thirdMonthRewards", thirdMonthRewards);
        rewardsMap.put("totalRewards", totalRewards);

        return rewardsMap;
    }

    public Reward getCustomerRewardsWithinDateRange(String customerId, Map<String, Long> rewardsMap) {
        try {
            Customer customer = customerRepository.findFirstNameAndLastNameByCustomerId(customerId);

            // Build and return the Reward object with the provided rewards from the rewards map and customer details.
            return Reward.builder()
                    .customerId(customerId)
                    .firstName(customer.getFirstName())
                    .lastName(customer.getLastName())
                    .firstMonthRewards(rewardsMap.get("firstMonthRewards"))
                    .secondMonthRewards(rewardsMap.get("secondMonthRewards"))
                    .thirdMonthRewards(rewardsMap.get("thirdMonthRewards"))
                    .totalRewards(rewardsMap.get("totalRewards"))
                    .build();

        } catch (Exception e) {
            log.error("Error occurred while creating the reward object for customer with ID " + customerId + ": " + e.getMessage());
            throw new RuntimeException("Error occurred while creating the reward object for customer with ID " + customerId, e);
        }
    }

    public Reward getRewardsForTransaction(String transactionId){
        try{
            Transactions transaction = transactionRepository.findByTransactionId(transactionId);
            long rewards = rewardCalculationStrategy.calculateRewards(transaction.getPurchaseAmount());

            Customer customer = customerRepository.findByCustomerId(transaction.getCustomerId());

            return Reward.builder()
                    .customerId(customer.getCustomerId())
                    .firstName(customer.getFirstName())
                    .lastName(customer.getLastName())
                    .totalRewards(rewards)
                    .build();
        }catch (Exception e){
            log.error("Error occurred while creating the reward object for given transaction" + transactionId + ": " + e.getMessage());
            throw new RuntimeException("Error occurred while creating the reward object for given transaction " + transactionId, e);
        }
    }

    private static Map<String, LocalDate> getLastThreeMonthsBillingCycle(LocalDate givenDate) {
        try {

            // Calculated the start date of the first month in the billing cycle.
            LocalDate firstMonthStartDate = givenDate.minusMonths(1).plusDays(1);

            // Calculated the end date of the second month in the billing cycle (one day before the start of the first month).
            LocalDate secondMonthEndDate = firstMonthStartDate.minusDays(1);

            // Calculated the start date of the second month in the billing cycle (one day after the end of the second month).
            LocalDate secondMonthStartDate = secondMonthEndDate.minusMonths(1).plusDays(1);

            // Calculated the end date of the third month in the billing cycle (one day before the start of the second month).
            LocalDate thirdMonthEndDate = secondMonthStartDate.minusDays(1);

            // Calculated the start date of the third month in the billing cycle (one day after the end of the third month).
            LocalDate thirdMonthStartDate = thirdMonthEndDate.minusMonths(1).plusDays(1);

            // Map to store the billing dates.
            Map<String, LocalDate> billingDatesMap = new LinkedHashMap<>();
            billingDatesMap.put("firstMonthStartDate", firstMonthStartDate);
            billingDatesMap.put("firstMonthEndDate", givenDate);
            billingDatesMap.put("secondMonthStartDate", secondMonthStartDate);
            billingDatesMap.put("secondMonthEndDate", secondMonthEndDate);
            billingDatesMap.put("thirdMonthStartDate", thirdMonthStartDate);
            billingDatesMap.put("thirdMonthEndDate", thirdMonthEndDate);

            return billingDatesMap;
        } catch (Exception e) {
            log.error("Error occurred while retrieving the three month prior dates from given date"  + e.getMessage());
            throw new RuntimeException(" Error occurred while retrieving the three month prior dates from given date ", e);
        }
    }

    public void setCustomerRepository(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public void setTransactionRepository(TransactionsRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }
}
