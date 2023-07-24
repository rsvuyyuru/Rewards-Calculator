package com.RewardsCalculator.strategy;

import com.RewardsCalculator.service.StoreType;

public interface RewardCalculationStrategy {
    long calculateRewards(long amount);
    StoreType getStoreName();
}
