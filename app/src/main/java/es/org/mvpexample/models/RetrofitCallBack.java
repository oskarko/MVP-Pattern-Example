package es.org.mvpexample.models;

/**
 * Created by oskarko on 9/11/16.
 */

public interface RetrofitCallBack {

    void onSuccess(int userId);
    void onFail(String msg);
}
