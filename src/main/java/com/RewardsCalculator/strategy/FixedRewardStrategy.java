package com.RewardsCalculator.strategy;

import com.RewardsCalculator.service.StoreType;

public class FixedRewardStrategy implements RewardCalculationStrategy {

    @Override
    public long calculateRewards(long amount) {
        return 0;
    }

    @Override
    public StoreType getStoreName() {
        return StoreType.STORE_2;
    }
}
