package com.simplify.insurance.repository;

import com.simplify.insurance.model.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
    Purchase findByTransactionId(String transactionId);
}
