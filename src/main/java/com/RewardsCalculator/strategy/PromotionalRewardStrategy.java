package com.RewardsCalculator.strategy;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component ("promotionalReward")
public class PromotionalRewardStrategy implements RewardCalculationStrategy{

    @Override
    public long calculateRewards(long purchaseAmount) {

        // Calculate reward points based on the given logic in the problem statement
        int pointsOver100 = Math.max((int) (purchaseAmount - 100) * 2, 0);
        int pointsBetween50And100 = Math.max((int) (Math.min(purchaseAmount, 100) - 50), 0);
        return (pointsOver100 + pointsBetween50And100);
    }
}
