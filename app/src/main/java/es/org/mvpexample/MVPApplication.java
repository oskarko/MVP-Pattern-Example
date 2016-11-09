package es.org.mvpexample;

import android.app.Application;

import es.org.mvpexample.rest.NetworkService;

/**
 * Created by oskarko on 4/11/16.
 */

public class MVPApplication extends Application {

    private NetworkService networkService;

    @Override
    public void onCreate() {
        super.onCreate();
        networkService = new NetworkService();
    }

    public NetworkService getNetworkService(){
        return networkService;
    }
}
