package com.mahlafuna.model;

@FunctionalInterface
public interface Billable {
    String generateInvoice();
}
