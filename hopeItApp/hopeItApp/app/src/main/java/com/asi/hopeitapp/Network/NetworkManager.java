package com.asi.hopeitapp.Network;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.asi.hopeitapp.Model.DonationWrapper;
import com.asi.hopeitapp.Model.Message;
import com.asi.hopeitapp.Model.MessageList;
import com.asi.hopeitapp.Model.Patient;
import com.asi.hopeitapp.Model.PatientList;
import com.asi.hopeitapp.Model.Payment;
import com.asi.hopeitapp.Model.PaymentList;
import com.asi.hopeitapp.Model.PayuWrapper;
import com.asi.hopeitapp.Model.Token;
import com.asi.hopeitapp.Model.TokenWraper;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NetworkManager {

    private final String CLASS_TAG = "NetworkManager";

    private HopeService hopeService;

    private int dbState = 0;
    private int apiLastUpdateId;
    private int localLastUpdateId;

    private static NetworkManager instance = null;

    //yeah I know...
    public static NetworkManager getInstance(){
        if(instance == null){
            instance = new NetworkManager();
        }

        return instance;
    }

    private NetworkManager(){
        hopeService = HopeApi.getClient().create(HopeService.class);
    }

    //retrofit api calls

    private Call<String> callLastUpdate() {
        return hopeService.getLastUpdateId();
    }

    private Call<PatientList> patientCall() {
        return hopeService.getPatients();
    }

    private Call<PaymentList> paymentCall() {
        return hopeService.getPayments();
    }

    private Call<MessageList> messageCall() {
        return hopeService.getMessages();
    }

    private Call<TokenWraper> tokenCall(PayuWrapper payuWrapper) {
        return hopeService.getToken(payuWrapper);
    }

    private Call<DonationWrapper> donationCall(DonationWrapper donationWrapper) {
        return hopeService.postDonation(donationWrapper);
    }

    //json parsing

    private List<Patient> fetchPatients(Response<PatientList> response) {
        PatientList data = response.body();
        return data.getPatients();
    }

    private List<Payment> fetchPayments(Response<PaymentList> response) {
        PaymentList data = response.body();
        return data.getPayments();
    }

    private List<Message> fetchMessages(Response<MessageList> response) {
        MessageList data = response.body();
        return data.getMessages();
    }

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
        loadAppData(context); //temporary no lastUpdate implemented on backend yet

        /*
        dbState = 0; //no db access

        SharedPreferences settings = context.getSharedPreferences("prefs", Context.MODE_PRIVATE);
        localLastUpdateId = settings.getInt("dbLastUpdate", 0);
        Log.i(CLASS_TAG, "Local lastUpdateID pre update: " + Integer.toString(localLastUpdateId));

        if(checkNetworkStatus(context)){
            checkDbTimestamp(context);
        }
        else {
            if(localLastUpdateId == 0){
                dbState = 3; //get init data
            }
            else {
                dbState = 2; //get update
            }
        }
        */
    }

    private void updateCheck(final Context context) {
        if (apiLastUpdateId != localLastUpdateId) {
            loadAppData(context);
        } else {
            Log.i(CLASS_TAG, "Local db up-to-date");
            dbState = 1; //db ready
        }
    }

    private void connectionProblem(Throwable throwable){
        Log.e(CLASS_TAG, "connection problem: " + throwable.getMessage());
        if(localLastUpdateId == 0){
            dbState = 3; //get init data
        }
        else {
            dbState = 2; //get update
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
        new Thread(() -> { //GET Patients
            patientCall().enqueue(new Callback<PatientList>() {
                @Override
                public void onResponse(Call<PatientList> call, Response<PatientList> response) {
                    if (response.body() == null) {
                        connectionProblem(new Throwable("Server returned null"));
                        return;
                    }

                    List<Patient> patients = fetchPatients(response);

                    if(patients.size()>0) {
                        Log.i(CLASS_TAG, "Api Test: " + patients.get(0).getName());
                    }

                    boolean stopLoad = false;

                    try { //delete entries, reset id autoincrement
                        Patient.deleteAll(Patient.class);
                        Patient.executeQuery("DELETE FROM SQLITE_SEQUENCE WHERE NAME = 'PATIENT'");
                    } catch (Exception e) {
                        Log.e(CLASS_TAG, "local db critical error: " + e.getMessage());
                        stopLoad = true;
                    }

                    if (!stopLoad) {
                        for (Patient patient : patients) {
                            patient.setId(null);
                            patient.save();
                        }
                    }
                }

                @Override
                public void onFailure(Call<PatientList> call, Throwable t) {
                    connectionProblem(t);
                }
            });
        }).start();

        new Thread(() -> { //GET Payments
            paymentCall().enqueue(new Callback<PaymentList>() {
                @Override
                public void onResponse(Call<PaymentList> call, Response<PaymentList> response) {
                    if (response.body() == null) {
                        connectionProblem(new Throwable("Server returned null"));
                        return;
                    }

                    List<Payment> payments = fetchPayments(response);

                    if(payments.size()>0) {
                        Log.i(CLASS_TAG, "Api Test: " + payments.get(0).getStatus());
                    }

                    boolean stopLoad = false;

                    try { //delete entries, reset id autoincrement
                        Payment.deleteAll(Payment.class);
                        Payment.executeQuery("DELETE FROM SQLITE_SEQUENCE WHERE NAME = 'PAYMENT'");
                    } catch (Exception e) {
                        Log.e(CLASS_TAG, "local db critical error: " + e.getMessage());
                        stopLoad = true;
                    }

                    if (!stopLoad) {
                        for (Payment payment : payments) {
                            payment.setId(null);
                            payment.save();
                        }
                    }
                }

                @Override
                public void onFailure(Call<PaymentList> call, Throwable t) {
                    connectionProblem(t);
                }
            });
        }).start();

        new Thread(() -> { //GET Payments
            messageCall().enqueue(new Callback<MessageList>() {
                @Override
                public void onResponse(Call<MessageList> call, Response<MessageList> response) {
                    if (response.body() == null) {
                        connectionProblem(new Throwable("Server returned null"));
                        return;
                    }

                    List<Message> messages = fetchMessages(response);

                    if(messages.size()>0) {
                        Log.i(CLASS_TAG, "Api Test: " + messages.get(0).getTitle());
                    }

                    boolean stopLoad = false;


                    try { //delete entries, reset id autoincrement
                        Message.deleteAll(Message.class);
                        Message.executeQuery("DELETE FROM SQLITE_SEQUENCE WHERE NAME = 'MESSAGE'");
                    } catch (Exception e) {
                        Log.e(CLASS_TAG, "local db critical error: " + e.getMessage());
                        stopLoad = true;
                    }

                    if (!stopLoad) {
                        for (Message message : messages) {
                            message.setId(null);
                            message.save();
                        }
                    }
                }

                @Override
                public void onFailure(Call<MessageList> call, Throwable t) {
                    connectionProblem(t);
                }
            });
        }).start();

        postUpdate(context);
    }

    private TokenWraper tokenWraper = null;

    public void retrieveToken(PayuWrapper payuWrapper) {
        tokenCall(payuWrapper).enqueue(new Callback<TokenWraper>() {
            @Override
            public void onResponse(Call<TokenWraper> call, Response<TokenWraper> response) {
                if (response.body() == null) {
                    connectionProblem(new Throwable("Server returned null"));
                    return;
                }

                synchronized (this) {
                    setToken(response.body());
                    notifyAll();
                }

            }

            @Override
            public void onFailure(Call<TokenWraper> call, Throwable t) {
                connectionProblem(t);
            }
        });
    }

    private Boolean confirmation = null;

    public void postDonation(DonationWrapper donationWrapper) {
        hopeService.postDonation(donationWrapper).enqueue(new Callback<DonationWrapper>(){
            @Override
            public void onResponse(Call<DonationWrapper> call, Response<DonationWrapper> response) {
                if (response.body() == null) {
                    connectionProblem(new Throwable("Server returned null"));
                    return;
                }

                synchronized (this) {
                    setConfirmation(true);
                    notifyAll();
                }
            }

            @Override
            public void onFailure(Call<DonationWrapper> call, Throwable t) {
                connectionProblem(t);
            }
        });
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

    public void networkProblemInfoDisplayed(){
        dbState = 1;
    }

    public TokenWraper getToken() {
        return tokenWraper;
    }

    public void setToken(TokenWraper tokenWraper) {
        this.tokenWraper = tokenWraper;
    }

    public Boolean getConfirmation() {
        return confirmation;
    }

    public void setConfirmation(Boolean confirmation) {
        this.confirmation = confirmation;
    }
}
