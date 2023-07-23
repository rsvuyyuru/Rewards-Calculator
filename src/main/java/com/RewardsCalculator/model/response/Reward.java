package com.RewardsCalculator.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Reward {
    private String customerId;
    private String firstName;
    private String lastName;
    private long firstMonthRewards;
    private long secondMonthRewards;
    private long thirdMonthRewards;
    private long totalRewards;
}
