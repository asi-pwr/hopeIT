package com.asi.hopeitapp.Network;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NetworkManager {

    private final String CLASS_TAG = "NetworkManager";

    private HopeService hopeService;

    private int dbState = 0;
    private int apiLastUpdateId;
    private int localLastUpdateId;

    NetworkManager(){
        hopeService = HopeApi.getClient().create(HopeService.class);
    }

    //retrofit api calls

    private Call<String> callLastUpdate() {
        return hopeService.getLastUpdateId();
    }

    //json parsing



    private boolean checkNetworkStatus(final Context context){
        ConnectivityManager connectivityManager =
                (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo == null){
            Log.e(CLASS_TAG, "Internet status: no service!");
            return false;
        }
        else {
            Log.i(CLASS_TAG, "Internet status: OK");
            return true;
        }
    }

    public void checkForUpdate(final Context context){
        dbState = 0; //no db access

        SharedPreferences settings = context.getSharedPreferences("prefs", Context.MODE_PRIVATE);
        localLastUpdateId = settings.getInt("dbLastUpdate", 0);
        Log.i(CLASS_TAG, "Local lastUpdateID pre update: " + Integer.toString(localLastUpdateId));

        if(checkNetworkStatus(context)){
            checkDbTimestamp(context);
        }
        else {
            if(localLastUpdateId == 0){
                dbState = 3; //get init data state
            }
            else {
                dbState = 2; //get
            }
        }
    }

    private void updateCheck(final Context context) {
        if (apiLastUpdateId != localLastUpdateId) {
            loadAppData(context);
        } else {
            Log.i(CLASS_TAG, "Local db up-to-date");
            dbState = 1;
        }
    }

    private void connectionProblem(Throwable throwable){
        Log.e(CLASS_TAG, "connection problem: " + throwable.getMessage());
        if(localLastUpdateId == 0){
            dbState = 3;
        }
        else {
            dbState = 2;
        }
    }

    private void checkDbTimestamp(final Context context){
        callLastUpdate().enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.body() == null){
                    connectionProblem(new Throwable("Server returned null"));
                    return;
                }

                Log.i(CLASS_TAG, "Last db update ID: " + response.body());

                apiLastUpdateId = Integer.parseInt(response.body());
                updateCheck(context);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                connectionProblem(t);
            }
        });
    }

    private void loadAppData(final Context context){

    }

    private void postUpdate(final Context context) {
        localLastUpdateId = apiLastUpdateId;

        Log.i(CLASS_TAG, "Local lastUpdateID post update: " + Integer.toString(localLastUpdateId));

        SharedPreferences settings = context.getSharedPreferences("prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("dbLastUpdate", localLastUpdateId);
        editor.apply();

        dbState = 1; //db ready
    }

    public int getDbState() {
        return dbState;
    }
}
