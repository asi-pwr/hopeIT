package com.asi.hopeitapp.Network;

import com.asi.hopeitapp.Model.DonationWrapper;
import com.asi.hopeitapp.Model.MessageList;
import com.asi.hopeitapp.Model.PatientList;
import com.asi.hopeitapp.Model.PaymentList;
import com.asi.hopeitapp.Model.TokenWrapper;
import com.asi.hopeitapp.Model.PayuWrapper;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface HopeService {
    //@GET("app_data")
    //Call<PatientList> getAppData();

    @GET("v1/last_update")
    Call<String> getLastUpdateId();

    @GET("v1/patients")
    Call<PatientList> getPatients();

    @GET("v1/payments")
    Call<PaymentList> getPayments();

    @GET("v1/messages")
    Call<MessageList> getMessages();

    @POST("v1/payu/token")
    Call<TokenWrapper> getToken(@Body PayuWrapper payuWrapper);

    @POST("v1/donations")
    Call<DonationWrapper> postDonation(@Body DonationWrapper donationWrapper);
}
