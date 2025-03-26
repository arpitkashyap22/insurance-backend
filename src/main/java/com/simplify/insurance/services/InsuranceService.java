package com.simplify.insurance.services;



import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.simplify.insurance.dto.InsuranceDto;
import com.simplify.insurance.dto.PurchaseReceiptDto;
import com.simplify.insurance.dto.PurchaseRequestDto;
import com.simplify.insurance.model.Insurance;
import com.simplify.insurance.model.Purchase;
import com.simplify.insurance.repository.InsuranceRepository;
import com.simplify.insurance.repository.PurchaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InsuranceService {
    private final InsuranceRepository insuranceRepository;
    private final PurchaseRepository purchaseRepository;

    public List<InsuranceDto> getAllInsurances() {
        return insuranceRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<InsuranceDto> getCuratedInsurances(
            Integer age, String gender, BigDecimal income) {
        return insuranceRepository.findCuratedInsurances(age, gender, income)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public PurchaseReceiptDto purchaseInsurance(PurchaseRequestDto request) {
        // Find insurance
        Insurance insurance = insuranceRepository.findById(request.getInsuranceId())
                .orElseThrow(() -> new RuntimeException("Insurance not found"));

        // Validate eligibility (simplified)
        validateEligibility(insurance, request);

        // Create purchase
        Purchase purchase = Purchase.builder()
                .transactionId(generateTransactionId())
                .insurance(insurance)
                .purchaseDate(LocalDate.now())
                .totalPremium(insurance.getPremium())
                .customerName(request.getCustomerName())
                .customerEmail(request.getCustomerEmail())
                .paymentStatus("COMPLETED")
                .build();

        purchaseRepository.save(purchase);

        return convertToPurchaseReceiptDto(purchase);
    }

    public Resource downloadPolicy(String transactionId) {
        Purchase purchase = purchaseRepository.findByTransactionId(transactionId);
        if (purchase == null) {
            throw new RuntimeException("Purchase not found");
        }

        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            Document document = new Document();
            PdfWriter.getInstance(document, baos);

            document.open();
            document.add(new Paragraph("Insurance Policy Document"));
            document.add(new Paragraph("Transaction ID: " + transactionId));
            document.add(new Paragraph("Insurance: " + purchase.getInsurance().getName()));
            document.add(new Paragraph("Customer: " + purchase.getCustomerName()));
            document.add(new Paragraph("Purchase Date: " + purchase.getPurchaseDate()));
            document.close();

            return new ByteArrayResource(baos.toByteArray());
        } catch (Exception e) {
            throw new RuntimeException("Error generating policy document", e);
        }
    }

    private void validateEligibility(Insurance insurance, PurchaseRequestDto request) {
        if (insurance.getMinAge() != null &&
                (request.getAge() < insurance.getMinAge())) {
            throw new RuntimeException("Age does not meet insurance requirements");
        }

        if (insurance.getGender() != null &&
                !insurance.getGender().equals(request.getGender())) {
            throw new RuntimeException("Gender does not meet insurance requirements");
        }

        if (insurance.getMinIncome() != null &&
                (request.getIncome().compareTo(insurance.getMinIncome()) < 0)) {
            throw new RuntimeException("Income does not meet insurance requirements");
        }
    }

    private String generateTransactionId() {
        return UUID.randomUUID().toString();
    }

    private InsuranceDto convertToDto(Insurance insurance) {
        return InsuranceDto.builder()
                .id(insurance.getId())
                .name(insurance.getName())
                .description(insurance.getDescription())
                .premium(insurance.getPremium())
                .coverageAmount(insurance.getCoverageAmount())
                .build();
    }

    private PurchaseReceiptDto convertToPurchaseReceiptDto(Purchase purchase) {
        return PurchaseReceiptDto.builder()
                .transactionId(purchase.getTransactionId())
                .insuranceName(purchase.getInsurance().getName())
                .purchaseDate(purchase.getPurchaseDate())
                .totalPremium(purchase.getTotalPremium())
                .customerName(purchase.getCustomerName())
                .build();
    }
}
