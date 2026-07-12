package com.mahlafuna.model;

public class TradeInCustomer extends Customer implements Billable {
    private double tradeInValue;
    private double outstandingFinance;

    public TradeInCustomer(String firstName, String lastName, String email, String phoneNumber, String customerId, double tradeInValue, double outstandingFinance) {
        super(firstName, lastName, email, phoneNumber, customerId);
        updateTradeInValue(tradeInValue);
        updateOutstandingFinance(outstandingFinance);
    }

    public double offerAmount() {
        double output = tradeInValue - outstandingFinance;
        return Math.max(output, 0.0);
    }

    public void updateTradeInValue(double tradeInValue) {
        if (tradeInValue > 0)
            this.tradeInValue = tradeInValue;
        else throw new IllegalArgumentException();
    }

    public void updateOutstandingFinance(double outstandingFinance) {
        if (outstandingFinance > 0)
            this.outstandingFinance = outstandingFinance;
        else throw new IllegalArgumentException();
    }

    @Override
    public String customerType() {
        return "Trade-In";
    }

    @Override
    public String role() {
        return "Trade-In Customer";
    }

    @Override
    public String generateInvoice() {
        return String.format("Invoice for %s | Offer: R %f", fullName(), offerAmount());
    }
}
