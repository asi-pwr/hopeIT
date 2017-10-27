package com.asi.hopeitapp.Network;

import retrofit2.Call;
import retrofit2.http.GET;

interface HopeService {
    //@GET("app_data")
    //Call<AppData> getAppData();

    @GET("last_update")
    Call<String> getLastUpdateId();
}
