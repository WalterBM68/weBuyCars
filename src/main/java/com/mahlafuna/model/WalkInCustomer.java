package com.mahlafuna.model;

public class WalkInCustomer extends Customer implements Billable {
    private double offeredPrice;

    public WalkInCustomer(String firstName, String lastName, String email, String phoneNumber, String customerId, double offeredPrice) {
        super(firstName, lastName, email, phoneNumber, customerId);
        updateOfferedPrice(offeredPrice);
    }

    public double offerAmount() {
        return offeredPrice;
    }

    public void updateOfferedPrice(double offeredPrice) {
        if (offeredPrice > 0)
            this.offeredPrice = offeredPrice;
        else throw new IllegalArgumentException();
    }

    @Override
    public String customerType() {
        return "Walk-In";
    }

    @Override
    public String role() {
        return "Walk-In Customer";
    }

    @Override
    public String generateInvoice() {
        return String.format("Invoice for %s | Offer: R %f", fullName(), offerAmount());
    }
}
