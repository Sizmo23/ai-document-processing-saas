package com.docautomation.backend.service;

import com.docautomation.backend.dto.*;
import com.docautomation.backend.entity.*;
import com.docautomation.backend.exception.InvoiceNotFoundException;
import com.docautomation.backend.repository.InvoiceRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class InvoiceService {

    private final InvoiceRepository repo;

    public InvoiceService(InvoiceRepository repo) {
        this.repo = repo;
    }

    public InvoiceResponse create(InvoiceRequest req) {
        if (repo.existsByInvoiceNumber(req.invoiceNumber())) {
            throw new IllegalArgumentException("Invoice number already exists: " + req.invoiceNumber());
        }
        Invoice inv = new Invoice();
        apply(inv, req);
        return InvoiceResponse.from(repo.save(inv));
    }

    @Transactional(readOnly = true)
    public InvoiceResponse get(UUID id) {
        return InvoiceResponse.from(find(id));
    }

    @Transactional(readOnly = true)
    public Page<InvoiceResponse> list(Pageable pageable) {
        return repo.findAll(pageable).map(InvoiceResponse::from);
    }

    public InvoiceResponse update(UUID id, InvoiceRequest req) {
        Invoice inv = find(id);
        apply(inv, req);
        return InvoiceResponse.from(inv);
    }

    public InvoiceResponse patch(UUID id, InvoicePatchRequest r) {
        Invoice inv = find(id);

        if (r.invoiceNumber() != null && !r.invoiceNumber().equals(inv.getInvoiceNumber())) {
            if (repo.existsByInvoiceNumber(r.invoiceNumber())) {
                throw new IllegalArgumentException("Invoice number already exists: " + r.invoiceNumber());
            }
            inv.setInvoiceNumber(r.invoiceNumber());
        }
        if (r.documentId() != null)   inv.setDocumentId(r.documentId());
        if (r.vendorName() != null)   inv.setVendorName(r.vendorName());
        if (r.customerName() != null) inv.setCustomerName(r.customerName());
        if (r.invoiceDate() != null)  inv.setInvoiceDate(r.invoiceDate());
        if (r.dueDate() != null)      inv.setDueDate(r.dueDate());
        if (r.currency() != null)     inv.setCurrency(r.currency().toUpperCase());
        if (r.subtotal() != null)     inv.setSubtotal(r.subtotal());
        if (r.taxAmount() != null)    inv.setTaxAmount(r.taxAmount());
        if (r.totalAmount() != null)  inv.setTotalAmount(r.totalAmount());
        if (r.status() != null)       inv.setStatus(r.status());

        return InvoiceResponse.from(inv);
    }

    public void delete(UUID id) {
        repo.delete(find(id));
    }

    private Invoice find(UUID id) {
        return repo.findById(id).orElseThrow(() -> new InvoiceNotFoundException(id));
    }

    private void apply(Invoice inv, InvoiceRequest r) {
        inv.setDocumentId(r.documentId());
        inv.setInvoiceNumber(r.invoiceNumber());
        inv.setVendorName(r.vendorName());
        inv.setCustomerName(r.customerName());
        inv.setInvoiceDate(r.invoiceDate());
        inv.setDueDate(r.dueDate());
        inv.setCurrency(r.currency().toUpperCase());
        inv.setSubtotal(r.subtotal());
        inv.setTaxAmount(r.taxAmount());
        inv.setTotalAmount(r.totalAmount());
        if (r.status() != null) inv.setStatus(r.status());
    }
}