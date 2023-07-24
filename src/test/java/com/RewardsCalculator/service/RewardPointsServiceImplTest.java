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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

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
    private RewardPointsServiceImpl rewardPointsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        rewardPointsService.setCustomerRepository(customerRepository);
        rewardPointsService.setTransactionRepository(transactionRepository);
    }

    @Test
    public void testGetAllRewards() {
        List<Customer> customers = new ArrayList<>();
        customers.add(new Customer("1", "John", "Doe"));
        customers.add(new Customer("2", "Jane", "Smith"));

        List<Transactions> transactionsList = new ArrayList<>();
        transactionsList.add(new Transactions("101","1", LocalDate.now().minusDays(10).atStartOfDay(),
                100));
        transactionsList.add(new Transactions("102","1",LocalDate.now().minusDays(32).atStartOfDay(),
                120));

        List<Transactions> transactionsList2 = List.of(
                new Transactions("201", "2", LocalDate.now().minusDays(10).atStartOfDay(),
                        100),
                new Transactions("202", "2", LocalDate.now().minusDays(70).atStartOfDay(),
                        120),
                new Transactions("203", "2", LocalDate.now().minusDays(32).atStartOfDay(),
                        80)
        );

        // Mock the current date
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now().minusDays(90);
        LocalDateTime start = startDate.atStartOfDay();
        LocalDateTime end = endDate.atTime(LocalTime.MAX);

        when(customerRepository.findAll()).thenReturn(customers);

        when(transactionRepository.findByCustomerIdAndTransactionDateBetween("1", end, start))
                .thenReturn(transactionsList);
        when(transactionRepository.findByCustomerIdAndTransactionDateBetween("2", end, start))
                .thenReturn(transactionsList2);


        when(rewardCalculationStrategy.calculateRewards(100L)).thenReturn(50L);
        when(rewardCalculationStrategy.calculateRewards(120L)).thenReturn(90L);
        when(rewardCalculationStrategy.calculateRewards(80L)).thenReturn(30L);

        List<Reward> result = rewardPointsService.getAllRewards();

        assertEquals(2, result.size());

        //Assert rewards for the first customer
        Reward firstCustomerReward = result.get(0);
        assertEquals("1", firstCustomerReward.getCustomerId());
        assertEquals("John", firstCustomerReward.getFirstName());
        assertEquals("Doe", firstCustomerReward.getLastName());
        assertEquals(140L, firstCustomerReward.getTotalRewards());
        assertEquals(50L, firstCustomerReward.getFirstMonthRewards());
        assertEquals(90L, firstCustomerReward.getSecondMonthRewards());
        assertEquals(0L, firstCustomerReward.getThirdMonthRewards());

        // Assert rewards for the second customer
        Reward secondCustomerReward = result.get(1);
        assertEquals("2", secondCustomerReward.getCustomerId());
        assertEquals("Jane", secondCustomerReward.getFirstName());
        assertEquals("Smith", secondCustomerReward.getLastName());
        assertEquals(170L, secondCustomerReward.getTotalRewards());
        assertEquals(50L, secondCustomerReward.getFirstMonthRewards());
        assertEquals(30L, secondCustomerReward.getSecondMonthRewards());
        assertEquals(90L, secondCustomerReward.getThirdMonthRewards());

    }

    @Test
    public void testGetRewardsForExistingCustomer() {
        // Create test data
        String customerId = "1";
        Customer customer = new Customer(customerId, "John", "Doe");

        List<Transactions> transactionsList = new ArrayList<>();
        transactionsList.add(new Transactions("101",customerId, LocalDate.now().minusDays(10).atStartOfDay(),
                160));
        transactionsList.add(new Transactions("102",customerId,LocalDate.now().minusDays(62).atStartOfDay(),
                120));

        // Mock the current date
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now().minusDays(90);
        LocalDateTime start = startDate.atStartOfDay();
        LocalDateTime end = endDate.atTime(LocalTime.MAX);

        when(transactionRepository.findByCustomerIdAndTransactionDateBetween(customerId, end, start))
                .thenReturn(transactionsList);

        when(rewardCalculationStrategy.calculateRewards(160L)).thenReturn(170L);
        when(rewardCalculationStrategy.calculateRewards(120L)).thenReturn(90L);
        when(customerRepository.findFirstNameAndLastNameByCustomerId("1")).thenReturn(customer);
        Reward customerRewards = rewardPointsService.getRewardsById(customerId);

        //Assert rewards for the  customer
        assertEquals("1", customerRewards.getCustomerId());
        assertEquals("John", customerRewards.getFirstName());
        assertEquals("Doe", customerRewards.getLastName());
        assertEquals(260L, customerRewards.getTotalRewards());
        assertEquals(170L, customerRewards.getFirstMonthRewards());
        assertEquals(0L, customerRewards.getSecondMonthRewards());
        assertEquals(90L, customerRewards.getThirdMonthRewards());
   }

    @Test
    public void testGetRewardsForNonExistingCustomer() {
        String customerId = "2";

        List<Transactions> transactionsList = new ArrayList<>();
        transactionsList.add(new Transactions("101",customerId, LocalDate.now().minusDays(10).atStartOfDay(),
                100));

        // Mock the current date
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now().minusDays(90);
        LocalDateTime start = startDate.atStartOfDay();
        LocalDateTime end = endDate.atTime(LocalTime.MAX);

        when(transactionRepository.findByCustomerIdAndTransactionDateBetween(customerId, end, start))
                .thenReturn(transactionsList);

        when(rewardCalculationStrategy.calculateRewards(100L)).thenReturn(170L);
        when(customerRepository.findFirstNameAndLastNameByCustomerId("2")).thenReturn(null);

        // Call the method under test
        Reward result = rewardPointsService.getRewardsById(customerId);

        // Assert the result
        assertEquals(null, result.getCustomerId());
        assertNull(null,result.getFirstName());
        assertNull(null,result.getLastName());
        assertEquals(0L, result.getFirstMonthRewards());
        assertEquals(0L, result.getSecondMonthRewards());
        assertEquals(0L, result.getThirdMonthRewards());
        assertEquals(0L, result.getTotalRewards());
    }

    @Test
    public void testGetRewardMapForCustomerIdWithinDateRange() {
        // Mock the data for the test
        String customerId = "1";
        LocalDate startDate = LocalDate.of(2023, 7, 10);
        LocalDate endDate = LocalDate.of(2023, 3, 31);

        LocalDateTime start = startDate.atStartOfDay();
        LocalDateTime end = endDate.atTime(LocalTime.MAX);

        List<Transactions> transactionsList = List.of(
                new Transactions("101", customerId, LocalDateTime.of(2023, 6, 15, 12, 0),
                        100),
                new Transactions("102", customerId, LocalDateTime.of(2023, 5, 10, 8, 0),
                        120),
                new Transactions("103", customerId, LocalDateTime.of(2023, 4, 10, 8, 0),
                        80)
        );

        // Mock the behavior of the repositories and strategy
        when(rewardCalculationStrategy.calculateRewards(100)).thenReturn(50L);
        when(rewardCalculationStrategy.calculateRewards(120)).thenReturn(90L);
        when(rewardCalculationStrategy.calculateRewards(80)).thenReturn(30L);
        when(transactionRepository.findByCustomerIdAndTransactionDateBetween("1", end, start))
                .thenReturn(transactionsList);

//        // Call the method under test
        Map<String, Long> rewardsMap = rewardPointsService.getRewardMapForCustomerIdWithinDateRange(customerId, startDate, endDate);

        // Assert the rewards map
        assertNotNull(rewardsMap);
        assertEquals(50L, rewardsMap.get("firstMonthRewards"));
        assertEquals(0, rewardsMap.get("secondMonthRewards"));
        assertEquals(90L, rewardsMap.get("thirdMonthRewards"));
        assertEquals(170L, rewardsMap.get("totalRewards"));
    }

    @Test
    public void testGetRewardMapForCustomerIdWithinDateRangeInvalidDateRange() {
        // Mock the data for the test
        String customerId = "1";
        LocalDate startDate = LocalDate.of(2023, 3, 31);
        LocalDate endDate = LocalDate.of(2023, 4, 10);

        // Call the method under test and expect it to throw an IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () ->
                rewardPointsService.getRewardMapForCustomerIdWithinDateRange(customerId, startDate, endDate));
    }

    @Test
    public void testGetRewardMapForCustomerIdWithinDateRangeInvalidStartDate() {
        // Mock the data for the test
        String customerId = "1";
        LocalDate startDate = LocalDate.of(2024, 10, 1);
        LocalDate endDate = LocalDate.of(2023, 9, 30);

        // Call the method under test and expect it to throw an IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> rewardPointsService.getRewardMapForCustomerIdWithinDateRange(customerId, startDate, endDate));
    }


    @Test
    public void testGetRewardsForTransactionFound(){

        Transactions transaction = new Transactions("101","1", LocalDateTime.now(),
                100);
        when(transactionRepository.findByTransactionId(any())).thenReturn(transaction);
        when(rewardCalculationStrategy.calculateRewards(100L)).thenReturn(50L);
        when(customerRepository.findByCustomerId("1")).thenReturn(new Customer("1", "John",
                "Doe"));

        Reward result = rewardPointsService.getRewardsForTransactionId("101");

        // Assert the result
        assertEquals("1", result.getCustomerId());
        assertEquals("John", result.getFirstName());
        assertEquals("Doe", result.getLastName());
        assertEquals(50L, result.getTotalRewards());
        assertEquals(0L, result.getFirstMonthRewards());
    }
}