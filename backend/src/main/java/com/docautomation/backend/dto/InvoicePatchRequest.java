package com.docautomation.backend.dto;

import com.docautomation.backend.entity.InvoiceStatus;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record InvoicePatchRequest(
        UUID documentId,
        @Pattern(regexp = ".*\\S.*", message = "must not be blank") String invoiceNumber,
        @Pattern(regexp = ".*\\S.*", message = "must not be blank") String vendorName,
        String customerName,
        LocalDate invoiceDate,
        LocalDate dueDate,
        @Size(min = 3, max = 3) String currency,
        @PositiveOrZero BigDecimal subtotal,
        @PositiveOrZero BigDecimal taxAmount,
        @PositiveOrZero BigDecimal totalAmount,
        InvoiceStatus status
) {}