package com.simplify.insurance.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseRequestDto {
    private Long insuranceId;
    private String customerName;
    private String customerEmail;
    private Integer age;
    private String gender;
    private BigDecimal income;
}
