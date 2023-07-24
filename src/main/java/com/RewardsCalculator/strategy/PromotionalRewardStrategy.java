package com.RewardsCalculator.strategy;

import com.RewardsCalculator.service.StoreType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component ("promotionalReward")
public class PromotionalRewardStrategy implements RewardCalculationStrategy{

    //As the calculation in the question is not too clear. For between 50-100, based on th example
    //I have implemented as when amount is 50 my method returns 0, for 100 it returns 50.

    @Override
    public long calculateRewards(long purchaseAmount) {

        // Calculate reward points based on the given logic in the problem statement
        int pointsOver100 = Math.max((int) (purchaseAmount - 100) * 2, 0);
        int pointsBetween50And100 = Math.max((int) (Math.min(purchaseAmount, 100) - 50), 0);
        return (pointsOver100 + pointsBetween50And100);
    }

    @Override
    public StoreType getStoreName() {
        return StoreType.STORE_1;
    }
}
