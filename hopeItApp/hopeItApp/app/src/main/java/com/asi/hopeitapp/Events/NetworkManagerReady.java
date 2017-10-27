package com.asi.hopeitapp.Events;

public class NetworkManagerReady {

    private final boolean networkManagerFinished;

    public NetworkManagerReady(boolean networkManagerFinished){
        this.networkManagerFinished = networkManagerFinished;
    }

    public boolean isNetworkManagerFinished() {
        return networkManagerFinished;
    }
}
