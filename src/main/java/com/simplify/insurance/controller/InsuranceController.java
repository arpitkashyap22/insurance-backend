package com.simplify.insurance.controller;
import com.simplify.insurance.dto.InsuranceDto;
import com.simplify.insurance.dto.PurchaseReceiptDto;
import com.simplify.insurance.dto.PurchaseRequestDto;
import com.simplify.insurance.services.InsuranceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/insurance")
@RequiredArgsConstructor
@Tag(name = "Insurance Management", description = "APIs for Insurance Operations")
public class InsuranceController {
    @Autowired
    private InsuranceService insuranceService;

    @GetMapping("/list")
    @Operation(summary = "Get all available insurances")
    public ResponseEntity<List<InsuranceDto>> getAllInsurances() {
        return ResponseEntity.ok(insuranceService.getAllInsurances());
    }

    @GetMapping("/curated")
    @Operation(summary = "Get curated insurances based on user details")
    public ResponseEntity<List<InsuranceDto>> getCuratedInsurances(
            @RequestParam Integer age,
            @RequestParam String gender,
            @RequestParam BigDecimal income
    ) {
        return ResponseEntity.ok(
                insuranceService.getCuratedInsurances(age, gender, income)
        );
    }

    @PostMapping("/purchase")
    @Operation(summary = "Purchase an insurance")
    public ResponseEntity<PurchaseReceiptDto> purchaseInsurance(
            @Valid @RequestBody PurchaseRequestDto purchaseRequest
    ) {
        return ResponseEntity.ok(
                insuranceService.purchaseInsurance(purchaseRequest)
        );
    }

    @GetMapping("/download/{transactionId}")
    @Operation(summary = "Download policy document")
    public ResponseEntity<Resource> downloadPolicy(
            @PathVariable String transactionId
    ) {
        Resource resource = insuranceService.downloadPolicy(transactionId);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"policy_" + transactionId + ".pdf\"")
                .body(resource);
    }
}
