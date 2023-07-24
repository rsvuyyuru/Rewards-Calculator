package com.RewardsCalculator.strategy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

class PromotionalRewardStrategyTest {

    private RewardCalculationStrategy rewardCalculationStrategy;

    @BeforeEach
    void setUp() {
        // Create an instance of PromotionalRewardStrategy before each test
        rewardCalculationStrategy = new PromotionalRewardStrategy();
    }

    @Test
    void testCalculateRewardsWithPurchaseAmountLessThan50() {

        // Test the case when the purchase amount is less than 50
        long expectedRewards = 0L;

        long actualRewards = rewardCalculationStrategy.calculateRewards(30);

        assertEquals(expectedRewards, actualRewards, "Incorrect rewards for purchase amount < 50");
    }

    @Test
    void testCalculateRewardsWithPurchaseAmountBetween50And100() {

        // Test the case when the purchase amount is between 50 and 100
        long purchaseAmount1 = 75L;
        long purchaseAmount2 = 50L;
        long purchaseAmount3 = 25L;

        long actualRewards1 = rewardCalculationStrategy.calculateRewards(purchaseAmount1);
        long actualRewards2 = rewardCalculationStrategy.calculateRewards(purchaseAmount2);
        long actualRewards3 = rewardCalculationStrategy.calculateRewards(purchaseAmount3);

        assertEquals(25, actualRewards1, "Incorrect rewards for purchase amount between 50 and 100");
        assertEquals(0, actualRewards2, "Incorrect rewards for purchase amount between 50 and 100");
        assertEquals(0, actualRewards3, "Incorrect rewards for purchase amount between 50 and 100");
    }

    @Test
    void testCalculateRewardsWithPurchaseAmountEqualTo100() {
        // Test the case when the purchase amount is equal to 100
        long purchaseAmount = 100L;
        long expectedRewards = 50L;

        long actualRewards = rewardCalculationStrategy.calculateRewards(purchaseAmount);

        assertEquals(expectedRewards, actualRewards, "Incorrect rewards for purchase amount equal to 100");
    }

    @Test
    void testCalculateRewardsWithPurchaseAmountGreaterThan100() {
        // Test the case when the purchase amount is greater than 100
        long purchaseAmount = 200;
        long expectedRewards = 250L;

        long actualRewards = rewardCalculationStrategy.calculateRewards(purchaseAmount);

        assertEquals(expectedRewards, actualRewards, "Incorrect rewards for purchase amount > 100");
    }


}