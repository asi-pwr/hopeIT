package com.asi.hopeitapp.Payments;

import android.content.Context;

import com.payu.android.sdk.payment.model.MerchantOAuthAccessToken;
import com.payu.android.sdk.payment.service.TokenProviderService;
import com.payu.android.sdk.payment.service.exception.ExternalRequestError;

/**
 * Created by sgorawski on 27.10.17.
 */

public class MerchantTokenProviderService extends TokenProviderService {

    public MerchantTokenProviderService(Context context) {
        super(context);
    }

    @Override
    public MerchantOAuthAccessToken provideAccessToken() throws ExternalRequestError {
        // TODO call to backend for token

        return new MerchantOAuthAccessToken("xd");
    }
}
