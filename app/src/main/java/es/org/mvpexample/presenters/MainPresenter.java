package es.org.mvpexample.presenters;

/**
 * Created by oskarko on 4/11/16.
 */

public interface MainPresenter {

    void onResume();

    void onItemClicked(int position);

    void onClick(int id);

    void onDestroy();

}
