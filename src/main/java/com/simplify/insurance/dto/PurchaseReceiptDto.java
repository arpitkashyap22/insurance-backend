package com.simplify.insurance.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseReceiptDto {
    private String transactionId;
    private String insuranceName;
    private LocalDate purchaseDate;
    private BigDecimal totalPremium;
    private String customerName;
}
