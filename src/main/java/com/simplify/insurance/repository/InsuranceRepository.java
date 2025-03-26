package com.simplify.insurance.repository;

import com.simplify.insurance.model.Insurance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface InsuranceRepository extends JpaRepository<Insurance, Long> {


    @Query("SELECT i FROM Insurance i WHERE " +
            "(:age BETWEEN i.minAge AND i.maxAge OR i.minAge IS NULL) AND " +
            "(i.gender IS NULL OR i.gender = :gender) AND " +
            "(i.minIncome IS NULL OR i.minIncome <= :income)")
    List<Insurance> findCuratedInsurances(
            Integer age,
            String gender,
            BigDecimal income
    );
}
