package com.docautomation.backend.dto;

import com.docautomation.backend.entity.Invoice;
import com.docautomation.backend.entity.InvoiceStatus;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

public record InvoiceResponse(
        UUID id, UUID documentId, String invoiceNumber, String vendorName,
        String customerName, LocalDate invoiceDate, LocalDate dueDate,
        String currency, BigDecimal subtotal, BigDecimal taxAmount,
        BigDecimal totalAmount, InvoiceStatus status,
        Instant createdAt, Instant updatedAt
) {
    public static InvoiceResponse from(Invoice i) {
        return new InvoiceResponse(i.getId(), i.getDocumentId(), i.getInvoiceNumber(),
                i.getVendorName(), i.getCustomerName(), i.getInvoiceDate(), i.getDueDate(),
                i.getCurrency(), i.getSubtotal(), i.getTaxAmount(), i.getTotalAmount(),
                i.getStatus(), i.getCreatedAt(), i.getUpdatedAt());
    }
}