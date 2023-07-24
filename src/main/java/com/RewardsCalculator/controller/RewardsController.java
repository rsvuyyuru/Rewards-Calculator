package com.RewardsCalculator.controller;

import com.RewardsCalculator.entity.Transactions;
import com.RewardsCalculator.model.request.customerAndDateRangeRequest;
import com.RewardsCalculator.model.response.Reward;
import com.RewardsCalculator.service.RewardPointsServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Controller
@RequestMapping("/rewards")
@Slf4j
public class RewardsController {


    @Autowired
    RewardPointsServiceImpl rewardService;


    /**
     * To get all transactions
     *
     * @return The total transactions list for all the customers
     */
    @GetMapping("/allTransactions")
    public ResponseEntity<List<Transactions>> getAllTransactions() {

        List<Transactions> transactionsList = rewardService.findAllTransactions();
        return new ResponseEntity<>(transactionsList, HttpStatus.OK);
    }

    /**
     * Get total rewards earned by a specific customer when id is specified.
     *
     * @param customerId customerId ID of the customer for whom total rewards are requested.
     *                   It is extracted from the request URL query parameter using @RequestParam
     *                   annotation with the name "id".
     * @return The total reward points earned by the customer in the last three months.
     * <p>
     * <p>
     * If id is not specified, gets total rewards for all the customers.
     * @return A map containing the reward points earned by each customer and the total for last three months.
     **/

    @GetMapping("/totalRewards")
    public ResponseEntity<List<Reward>> getTotalRewards(@RequestParam(name = "id") Optional<String> customerId) {
        if (customerId.isPresent()) {
            List<Reward> customerRewardsList = new ArrayList<>();
            customerRewardsList.add(rewardService.getRewardsById(customerId.get()));
            return new ResponseEntity<>(customerRewardsList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(rewardService.getAllRewards(), HttpStatus.OK);
        }

    }

    /**
     * Retrieves the rewards for a customer within a specified date range.
     *
     * @param request The request object containing the customer ID, start date, and end date.
     * @return ResponseEntity with the calculated rewards for the customer within the specified date range.
     */

    @PostMapping("/customDateRange")
    public ResponseEntity<Reward> getCustomerRewardsWithinDateRange(@RequestBody customerAndDateRangeRequest request) {

        String customerId = request.getCustomerId();
        String requestStartDate = request.getStartDate();
        String requestEndDate = request.getEndDate();

        try {
            LocalDate startDate = LocalDate.parse(requestStartDate);
            LocalDate endDate = LocalDate.parse(requestEndDate);
            Map<String, Long> rewardMap = rewardService.getRewardMapForCustomerIdWithinDateRange(customerId,
                    startDate, endDate);
            return new ResponseEntity<>(rewardService.getCustomerRewardsWithinDateRange(customerId, rewardMap),
                    HttpStatus.OK);
        } catch (DateTimeParseException e) {
            log.error("Invalid date format provided in the request. Date format should be 'yyyy-MM-dd'.");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    /**
     * Retrieves the rewards for single transaction.
     *
     * @return ResponseEntity with the calculated rewards for single Transaction with Customer information.
     * @PathVariable Id containing the transaction ID.
     */

    @GetMapping("/transaction/{id}")
    public ResponseEntity<Reward> getRewardsForTransaction(@PathVariable(name = "id") String transactionId) {
        return new ResponseEntity<>(rewardService.getRewardsForTransactionId(transactionId), HttpStatus.OK);
    }

}
