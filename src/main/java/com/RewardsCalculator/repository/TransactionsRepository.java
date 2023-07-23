package com.RewardsCalculator.repository;

import com.RewardsCalculator.entity.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionsRepository extends JpaRepository<Transactions, Long> {

    @Override
    List<Transactions> findAll();

    Transactions findByTransactionId(String transactionId);

    /**
     * Retrieve the transactions for a specific customer within a date range.
     */
    List<Transactions> findByCustomerIdAndTransactionDateBetween(String customerId, LocalDateTime startDate, LocalDateTime endDate);

}