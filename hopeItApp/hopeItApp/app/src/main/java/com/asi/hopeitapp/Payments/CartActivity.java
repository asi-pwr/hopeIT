package com.asi.hopeitapp.Payments;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;

import com.asi.hopeitapp.R;
import com.payu.android.sdk.payment.PaymentEventBus;
import com.payu.android.sdk.payment.PaymentService;
import com.payu.android.sdk.payment.event.PaymentSuccessEvent;
import com.payu.android.sdk.payment.widget.PaymentMethodWidget;

public class CartActivity extends Activity {
    private PaymentService mPaymentService;
    private PaymentEventBus mPaymentEventBus = new PaymentEventBus();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.money_bitch_layout);
        startPayment();
    }

    private void startPayment() {
        PaymentMethodWidget paymentMethodWidget =
        (PaymentMethodWidget) findViewById(R.id.pay_widget);

        if (paymentMethodWidget.isPaymentMethodPresent()) {
            DonationMaker payment = new DonationMaker(20000, 1);
            payment.make();
        }
    }

    protected void onPause() {
        mPaymentEventBus.unregister(this);
        super.onPause();
    }

    protected void onResume() {
        super.onResume();
        mPaymentEventBus.register(this);
    }

    public void onPaymentProcessEventMainThread(PaymentSuccessEvent event) {

    }
}