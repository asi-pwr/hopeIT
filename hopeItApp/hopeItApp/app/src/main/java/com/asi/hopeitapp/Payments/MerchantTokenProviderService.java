package com.asi.hopeitapp.Payments;

import android.content.Context;

import com.asi.hopeitapp.Model.Payu;
import com.asi.hopeitapp.Model.PayuWrapper;
import com.asi.hopeitapp.Model.TokenWraper;
import com.asi.hopeitapp.Network.NetworkManager;
import com.payu.android.sdk.payment.model.MerchantOAuthAccessToken;
import com.payu.android.sdk.payment.service.TokenProviderService;
import com.payu.android.sdk.payment.service.exception.ExternalRequestError;

/**
 * Created by sgorawski on 27.10.17.
 */

public class MerchantTokenProviderService extends TokenProviderService {
    private TokenWraper token;

    public MerchantTokenProviderService(Context context) {
        super(context);
    }

    @Override
    public MerchantOAuthAccessToken provideAccessToken() throws ExternalRequestError {

        new Thread(() -> {
            NetworkManager networkManager = NetworkManager.getInstance();
            networkManager.retrieveToken(new PayuWrapper(new Payu("donor10@example.com"))); // TODO get email adress

            synchronized (this) {
                while(networkManager.getToken() == null) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            token = networkManager.getToken();
            networkManager.setToken(null);
        }).start();

        return new MerchantOAuthAccessToken(token.getToken().get(0).toString());
    }
}


