package com.mahlafuna.model;

public class OnlineCustomer extends Customer implements Billable {
    private double baseOffer;
    private final String portalUsername;

    public static final double ONLINE_FEE_MULTIPLIER = 0.95;

    public OnlineCustomer(String firstName, String lastName, String email, String phoneNumber, String customerId, double baseOffer, String portalUsername) {
        super(firstName, lastName, email, phoneNumber, customerId);
        updateBaseOffer(baseOffer);
        this.portalUsername = portalUsername;
    }

    public double offerAmount() {
        return baseOffer * 0.95;
    }

    public void updateBaseOffer(double baseOffer) {
        if (baseOffer > 0)
            this.baseOffer = baseOffer;
        else throw new IllegalArgumentException();
    }

    public String portalUsername() {
        return portalUsername;
    }

    @Override
    public String customerType() {
        return "Online";
    }

    @Override
    public String role() {
        return "Online Customer";
    }

    @Override
    public String generateInvoice() {
        return String.format("Invoice for %s | Offer: R %f", fullName(), offerAmount());
    }
}
