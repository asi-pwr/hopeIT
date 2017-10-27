package com.asi.hopeitapp.Payments;

import android.content.Context;

import com.asi.hopeitapp.Model.Token;
import com.asi.hopeitapp.Network.NetworkManager;
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
        NetworkManager networkManager = NetworkManager.getInstance();
        networkManager.retrieveToken("asjdioj@auishd.saoij"); // TODO get email adress
        Token token = networkManager.getToken();
        networkManager.setToken(null);

        return new MerchantOAuthAccessToken(token.getAccessToken());
    }
}


