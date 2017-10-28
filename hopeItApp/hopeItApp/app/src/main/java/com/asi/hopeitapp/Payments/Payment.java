package com.asi.hopeitapp.Payments;

import com.asi.hopeitapp.Model.Donation;
import com.asi.hopeitapp.Model.DonationWrapper;
import com.asi.hopeitapp.Network.NetworkManager;
import com.payu.android.sdk.payment.PaymentService;
import com.payu.android.sdk.payment.model.Currency;
import com.payu.android.sdk.payment.model.Order;

import java.util.UUID;

/**
 * Created by sgorawski on 27.10.17.
 */

public class Payment {
    private Integer amount;
    private Integer donationTypeId;
    private String extOrderId;
    private Integer patientId;
    private PaymentService mPaymentService;

    public Payment(Integer amount, Integer patientId) {
        this.amount = amount;
        this.patientId = patientId;

        this.donationTypeId = 1;
        this.extOrderId = UUID.randomUUID().toString();
    }

    public void make() {
        DonationWrapper donation = new DonationWrapper(new Donation(amount, donationTypeId, patientId, extOrderId));
        NetworkManager networkManager = NetworkManager.getInstance();
        networkManager.postDonation(donation);

        startPayment();
    }

    private void startPayment() {
        mPaymentService.pay(new Order.Builder()
                .withNotifyUrl("link")
                .withAmount(amount)
                .withCurrency(Currency.PLN)
                .withExtOrderId(extOrderId)
                .withDescription("exapmle-donation")
                .build());
    }
}
