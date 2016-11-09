package es.org.mvpexample.views;

import java.util.List;

/**
 * Created by oskarko on 4/11/16.
 */

public interface MainView {

    void showProgress();

    void hideProgress();

    void setItems(List<String> items);

    void showMessage(String msg);
}
