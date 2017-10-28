package com.asi.hopeitapp.Payments;

import android.content.Context;
import android.util.Log;

import com.asi.hopeitapp.Model.Payu;
import com.asi.hopeitapp.Model.PayuWrapper;
import com.asi.hopeitapp.Model.Token;
import com.asi.hopeitapp.Model.TokenWraper;
import com.asi.hopeitapp.Network.NetworkManager;
import com.payu.android.sdk.payment.model.MerchantOAuthAccessToken;
import com.payu.android.sdk.payment.service.TokenProviderService;
import com.payu.android.sdk.payment.service.exception.ExternalRequestError;

/**
 * Created by sgorawski on 27.10.17.
 */

public class MerchantTokenProviderService extends TokenProviderService {
    private TokenWraper tokenWraper;

    public MerchantTokenProviderService(Context context) {
        super(context);
    }

    @Override
    public MerchantOAuthAccessToken provideAccessToken() throws ExternalRequestError {

        NetworkManager networkManager = NetworkManager.getInstance();

        synchronized (this)  {
            networkManager.retrieveToken(new PayuWrapper(new Payu("donor10@example.com"))); // TODO get email adress
        }

        try{
            Thread.sleep(5000);
        }
        catch (Exception e){
            Log.e("dvv", "fdgd");
        }


        tokenWraper = networkManager.getToken();
        //networkManager.setToken(null);

        return new MerchantOAuthAccessToken(tokenWraper.getToken().get(0).getAccessToken());
    }
}


