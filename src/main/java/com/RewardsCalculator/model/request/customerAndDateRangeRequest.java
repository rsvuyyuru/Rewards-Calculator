package com.RewardsCalculator.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;

@Data
public class customerAndDateRangeRequest {

    @JsonProperty("customerId")
    private String customerId;

    @JsonProperty("startDate")
    private String startDate;

    @JsonProperty("endDate")
    private String endDate;

}
