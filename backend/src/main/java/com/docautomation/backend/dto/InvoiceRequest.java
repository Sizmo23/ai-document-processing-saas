package com.docautomation.backend.dto;

import com.docautomation.backend.entity.InvoiceStatus;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record InvoiceRequest(
        UUID documentId,
        @NotBlank String invoiceNumber,
        @NotBlank String vendorName,
        String customerName,
        LocalDate invoiceDate,
        LocalDate dueDate,
        @NotBlank @Size(min = 3, max = 3) String currency,
        @PositiveOrZero BigDecimal subtotal,
        @PositiveOrZero BigDecimal taxAmount,
        @NotNull @PositiveOrZero BigDecimal totalAmount,
        InvoiceStatus status
) {}