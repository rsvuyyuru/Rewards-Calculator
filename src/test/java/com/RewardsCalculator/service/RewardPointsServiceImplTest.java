package com.RewardsCalculator.service;

import com.RewardsCalculator.entity.Customer;
import com.RewardsCalculator.entity.Transactions;
import com.RewardsCalculator.model.response.Reward;
import com.RewardsCalculator.repository.CustomerRepository;
import com.RewardsCalculator.repository.TransactionsRepository;
import com.RewardsCalculator.strategy.RewardCalculationStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

class RewardPointsServiceImplTest {
    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private TransactionsRepository transactionRepository;

    @Mock
    private RewardCalculationStrategy rewardCalculationStrategy;

    @InjectMocks
    private RewardPointsServiceImpl rewardService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        rewardService.setCustomerRepository(customerRepository);
        rewardService.setTransactionRepository(transactionRepository);
    }

    @Test
    void testGetAllRewards() {
        List<Customer> customers = new ArrayList<>();
        customers.add(new Customer("1", "John", "Doe"));
        customers.add(new Customer("2", "Jane", "Smith"));

        List<Transactions> transactionsList = new ArrayList<>();
        transactionsList.add(new Transactions("101","1", LocalDateTime.parse("2023-07-10T12:00:00"), 100));
        transactionsList.add(new Transactions("102","1",LocalDateTime.parse("2023-06-25T10:00:00"), 200));
        transactionsList.add(new Transactions("103","2", LocalDateTime.parse("2023-07-20T15:00:00"), 150));

        when(customerRepository.findAll()).thenReturn(customers);
        when(transactionRepository.findByCustomerIdAndTransactionDateBetween(anyString(), any(), any()))
                .thenReturn(transactionsList);
        when(customerRepository.findFirstNameAndLastNameByCustomerId("1")).thenReturn(new Customer("1", "John", "Doe"));
        when(customerRepository.findFirstNameAndLastNameByCustomerId("2")).thenReturn(new Customer("2", "Jane", "Smith"));
        when(rewardCalculationStrategy.calculateRewards(anyLong())).thenReturn(50L);

        List<Reward> result = rewardService.getAllRewards();

        assertEquals(2, result.size());

        // Assert rewards for the first customer
//        Reward firstCustomerReward = result.get(0);
//        assertEquals("1", firstCustomerReward.getCustomerId());
//        assertEquals("John", firstCustomerReward.getFirstName());
//        assertEquals("Doe", firstCustomerReward.getLastName());
//        assertEquals(750L, firstCustomerReward.getTotalRewards());
//        assertEquals(150L, firstCustomerReward.getFirstMonthRewards());
//        assertEquals(150L, firstCustomerReward.getSecondMonthRewards());
//        assertEquals(450L, firstCustomerReward.getThirdMonthRewards());
//
//        // Assert rewards for the second customer
//        Reward secondCustomerReward = result.get(1);
//        assertEquals("2", secondCustomerReward.getCustomerId());
//        assertEquals("Jane", secondCustomerReward.getFirstName());
//        assertEquals("Smith", secondCustomerReward.getLastName());
//        assertEquals(150L, secondCustomerReward.getTotalRewards());
//        assertEquals(150L, secondCustomerReward.getFirstMonthRewards());
//        assertEquals(0L, secondCustomerReward.getSecondMonthRewards());
//        assertEquals(0L, secondCustomerReward.getThirdMonthRewards());

//        verify(customerRepository, times(1)).findAll();
//        verify(transactionRepository, times(2)).findByCustomerIdAndTransactionDateBetween(anyString(), any(), any());
//        verify(rewardCalculationStrategy, times(3)).calculateRewards(anyLong());
    }

//    @Test
//    void testGetRewardsById() {
//        String customerId = "1";
//        Customer customer = new Customer(customerId, "John", "Doe");
//
//        List<Transactions> transactionsList = new ArrayList<>();
//        transactionsList.add(new Transactions("1", 100));
//        transactionsList.add(new Transactions("1", 200));
//
//        when(customerRepository.findFirstNameAndLastNameByCustomerId(customerId)).thenReturn(customer);
//        when(transactionRepository.findByCustomerIdAndTransactionDateBetween(anyString(), any(), any()))
//                .thenReturn(transactionsList);
//        when(rewardCalculationStrategy.calculateRewards(anyLong())).thenReturn(50L);
//
//        Reward result = rewardService.getRewardsById(customerId);
//
//        assertEquals(customerId, result.getCustomerId());
//        assertEquals("John", result.getFirstName());
//        assertEquals("Doe", result.getLastName());
//        assertEquals(300L, result.getTotalRewards());
//        assertEquals(100L, result.getFirstMonthRewards());
//        assertEquals(200L, result.getSecondMonthRewards());
//        assertEquals(0L, result.getThirdMonthRewards());
//
//        verify(customerRepository, times(1)).findFirstNameAndLastNameByCustomerId(customerId);
//        verify(transactionRepository, times(2)).findByCustomerIdAndTransactionDateBetween(anyString(), any(), any());
//        verify(rewardCalculationStrategy, times(2)).calculateRewards(anyLong());
//    }

}