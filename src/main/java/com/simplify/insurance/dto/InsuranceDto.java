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
public class InsuranceDto {
    private Long id;
    private String name;
    private String description;
    private BigDecimal premium;
    private BigDecimal coverageAmount;
}
