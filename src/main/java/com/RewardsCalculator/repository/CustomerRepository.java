package com.RewardsCalculator.repository;

import com.RewardsCalculator.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Override
    List<Customer> findAll();

    /**
     * Retrieve the first name and last name of a customer with the given customer ID.
     */
    Customer findFirstNameAndLastNameByCustomerId(String customerId);

    Customer findByCustomerId(String customerId);
}