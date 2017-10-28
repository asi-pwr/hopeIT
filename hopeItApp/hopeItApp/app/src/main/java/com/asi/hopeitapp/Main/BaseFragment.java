package com.asi.hopeitapp.Main;

import android.os.Handler;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.asi.hopeitapp.Network.NetworkManager;
import com.asi.hopeitapp.R;

public abstract class BaseFragment extends Fragment {

    private final String CLASS_TAG = "BaseFragment";

    private NetworkManager networkManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        networkManager = NetworkManager.getInstance();
        networkHandler.postDelayed(networkStatusCheck, 50);

    }

    @Override
    public void onResume() {
        super.onResume();
        if(MainActivity.appOnRestartCalled) {
            loadingUpdate();
            networkManager.checkForUpdate(getContext());
            networkHandler.postDelayed(networkStatusCheck, 10);
        }
    }

    private Handler networkHandler = new Handler();
    private Runnable networkStatusCheck = new Runnable() {
        @Override
        public void run() {
            switch (networkManager.getDbState()) {
                case 1:
                    loadContent();
                    networkHandler.removeCallbacks(this);
                    break;
                case 2:
                    loadContent();
                    if (getContext() != null) {
                        Toast.makeText(getContext(), R.string.no_server_connection,
                                Toast.LENGTH_SHORT).show();
                    }
                    networkManager.networkProblemInfoDisplayed();
                    networkHandler.removeCallbacks(this);
                    break;
                case 3:
                    firstLoadFailure();
                    networkHandler.removeCallbacks(this);
                    break;
                default:
                    networkHandler.postDelayed(this, 10);
            }

        }
    };

    @Override
    public void onPause() {
        super.onPause();
        networkHandler.removeCallbacks(networkStatusCheck);
    }

    protected void firstLoadFailure(){
        Log.e(CLASS_TAG, "Db no init data!, displaying warning...");
        Toast.makeText(getContext(), "Brak połączenia z internetem\n -brak zapisanych danych",
                Toast.LENGTH_LONG).show();
    }

    protected void loadingUpdate(){
        Log.i(CLASS_TAG, "Db check...");
    }

    protected void loadContent(){
        Log.i(CLASS_TAG, "Db ready, loading content...");
    }

}
