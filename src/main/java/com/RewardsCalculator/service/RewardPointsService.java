package com.RewardsCalculator.service;

import com.RewardsCalculator.entity.Customer;
import com.RewardsCalculator.entity.Transactions;
import com.RewardsCalculator.model.response.Reward;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface RewardPointsService {

    List<Reward> getAllRewards();

    Reward getRewardsById(String customerId);

    Map<String, Long> getRewardMapForCustomerIdWithinDateRange(String customerId, LocalDate startDate,
                                                               LocalDate endDate);

    Reward getCustomerRewardsWithinDateRange(String customerId, Map<String, Long> rewardsMap);

    Reward getRewardsForTransactionId(String transactionId);

    List<Transactions> findAllTransactions();

    List<Customer> findAllCustomers();

}

