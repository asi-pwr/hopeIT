package com.asi.hopeitapp.Payments;

import com.payu.android.sdk.payment.PaymentService;
import com.payu.android.sdk.payment.model.Currency;
import com.payu.android.sdk.payment.model.Order;

import java.util.UUID;

/**
 * Created by sgorawski on 27.10.17.
 */

public class Donation {
    private Integer amount;
    private String extOrderId;
    private PaymentService mPaymentService;

    public Donation(Integer amount) {
        this.amount = amount;
        this.extOrderId = UUID.randomUUID().toString();
    }

    public void startPayment() {
        // TODO send extOrderId to backend

        mPaymentService.pay(new Order.Builder()
        .withNotifyUrl("link")
        .withAmount(amount)
        .withCurrency(Currency.PLN)
        .withExtOrderId(extOrderId)
        .withDescription("exapmle-donation")
        .build());
    }
}
