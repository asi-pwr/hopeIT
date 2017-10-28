package com.asi.hopeitapp.Network;

import com.asi.hopeitapp.Model.PatientList;
import com.asi.hopeitapp.Model.Token;
import com.asi.hopeitapp.Model.PayuWrapper;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

interface HopeService {
    //@GET("app_data")
    //Call<PatientList> getAppData();

    @GET("v1/last_update")
    Call<String> getLastUpdateId();

    @GET("v1/patients")
    Call<PatientList> getPatients();

    @POST("v1/payu/token")
    Call<Token> getToken(@Body PayuWrapper payuWrapper);
}
