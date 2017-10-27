package com.asi.hopeitapp.Network;

import com.asi.hopeitapp.Model.PatientList;

import retrofit2.Call;
import retrofit2.http.GET;

interface HopeService {
    //@GET("app_data")
    //Call<PatientList> getAppData();

    @GET("v1/last_update")
    Call<String> getLastUpdateId();

    @GET("v1/last_update")
    Call<PatientList> getPatients();
}
