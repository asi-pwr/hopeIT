package com.asi.hopeitapp.Main;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.asi.hopeitapp.Events.NetworkManagerReady;
import com.asi.hopeitapp.Network.NetworkManager;
import com.asi.hopeitapp.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public abstract class BaseFragment extends Fragment {

    private final String CLASS_TAG = "BaseFragment";

    private NetworkManager networkManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        networkManager = NetworkManager.getInstance();
    }

    @Override
    public void onResume() {
        super.onResume();
        if(MainActivity.appOnRestartCalled) {
            loadingUpdate();
            networkManager.checkForUpdate(getContext());
        }
    }

    private void run(){
        switch (networkManager.getDbState()){
            case 1:
                loadContent();
                break;
            case 2:
                loadContent();
                if(getContext() != null) {
                    Toast.makeText(getContext(), R.string.no_server_connection,
                            Toast.LENGTH_SHORT).show();
                }
                break;
            case 3:
                firstLoadFailure();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    protected void firstLoadFailure(){
        Log.e(CLASS_TAG, "Db no init data!, displaying warning...");
    }

    protected void loadingUpdate(){
        Log.i(CLASS_TAG, "Db check...");
    }

    protected void loadContent(){
        Log.i(CLASS_TAG, "Db ready, loading content...");
    }

    @Subscribe
    public void networkEvent(NetworkManagerReady event) {
        EventBus.getDefault().removeStickyEvent(event);
        if (event.isNetworkManagerFinished()){
            run();
        }
    }
}
