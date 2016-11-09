package es.org.mvpexample.models;

import java.util.List;

/**
 * Created by oskarko on 4/11/16.
 */

public interface FindItemsInteractor {

    interface OnFinishedListener {
        void onFinished(List<String> items);
    }

    void findItems(OnFinishedListener listener);
}
