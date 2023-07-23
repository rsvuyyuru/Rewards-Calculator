package com.RewardsCalculator.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;



@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TRANSACTIONS")

public class Transactions {

        @Id
        //@GeneratedValue( strategy = GenerationType.IDENTITY)
        @Column(name = "TRANSACTION_ID")
        private String transactionId;

        @Column(name="CUSTOMER_ID")
        private String customerId;

        @Column(name = "TRANSACTION_DATE")
        private LocalDateTime transactionDate;

        @Column(name = "AMOUNT")
        private long purchaseAmount;
}
