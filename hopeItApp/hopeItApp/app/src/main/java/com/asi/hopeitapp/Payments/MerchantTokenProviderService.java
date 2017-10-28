package com.asi.hopeitapp.Payments;

import android.content.Context;
import android.util.Log;

import com.asi.hopeitapp.Model.Token;
import com.asi.hopeitapp.Network.HopeApi;

import com.asi.hopeitapp.Model.Payu;
import com.asi.hopeitapp.Model.PayuWrapper;
import com.asi.hopeitapp.Model.TokenWrapper;
import com.asi.hopeitapp.Network.HopeService;
import com.asi.hopeitapp.Network.NetworkManager;
import com.payu.android.sdk.payment.model.MerchantOAuthAccessToken;
import com.payu.android.sdk.payment.service.TokenProviderService;
import com.payu.android.sdk.payment.service.exception.ExternalRequestError;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by sgorawski on 27.10.17.
 */

public class MerchantTokenProviderService extends TokenProviderService {

    private String access_token;

    public MerchantTokenProviderService(Context context) {
        super(context);
    }

    @Override
    public MerchantOAuthAccessToken provideAccessToken() throws ExternalRequestError {
        Retrofit retrofit = HopeApi.getClient();
        HopeService apiService = retrofit.create(HopeService.class);
        String email = "donor10@example.com";
        Call<TokenWrapper> tokenWrapperCall = apiService.getToken(new PayuWrapper(new Payu("donor1@example.com")));
        try {
            Response<TokenWrapper> tw = tokenWrapperCall.execute();
            access_token = tw.body().getToken().getAccessToken();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new MerchantOAuthAccessToken(access_token);
    }
}


