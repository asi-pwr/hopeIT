package com.asi.hopeitapp.Payments;

import com.payu.android.sdk.payment.model.Currency;

/**
 * Created by sgorawski on 27.10.17.
 */

public class CurrencyFormatter {
    public static String format(Integer amount, Currency currency) {
        return String.format("%s %s", toDecimal(amount), currency.name());
    }

    private static float toDecimal(Integer num) {
        return num / 100f;
    }
}
