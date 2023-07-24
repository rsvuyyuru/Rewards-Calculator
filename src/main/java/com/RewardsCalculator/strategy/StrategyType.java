package com.RewardsCalculator.strategy;

import com.RewardsCalculator.service.StoreType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
public class StrategyType {
    Map<StoreType, RewardCalculationStrategy> promotoionMap = new HashMap<>();


    @Autowired
    public StrategyType(Set<RewardCalculationStrategy> rewardCalculationStrategySet) {
        rewardCalculationStrategySet.forEach(
                calculationStrategy -> promotoionMap.put(calculationStrategy.getStoreName(), calculationStrategy)
        );
    }

    public Map<StoreType, RewardCalculationStrategy> getPromotionMap() {
        return promotoionMap;
    }
}

