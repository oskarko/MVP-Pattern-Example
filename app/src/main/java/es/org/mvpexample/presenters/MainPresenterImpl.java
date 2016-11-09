package es.org.mvpexample.presenters;

import java.util.List;

import es.org.mvpexample.models.FindItemsInteractor;
import es.org.mvpexample.models.RetrofitCallBack;
import es.org.mvpexample.rest.NetworkService;
import es.org.mvpexample.views.MainView;


/**
 * Created by oskarko on 4/11/16.
 */

public class MainPresenterImpl implements MainPresenter, RetrofitCallBack ,FindItemsInteractor.OnFinishedListener {

    private MainView mainView;
    private FindItemsInteractor findItemsInteractor;
    private NetworkService service;
    private int result;

    public MainPresenterImpl(MainView mainView, FindItemsInteractor findItemsInteractor, NetworkService service) {
        this.mainView = mainView;
        this.findItemsInteractor = findItemsInteractor;
        this.service = service;
    }

    @Override
    public void onResume() {}

    @Override
    public void onItemClicked(int position) {
        if (mainView != null) {
            mainView.showMessage(String.format("Position %d clicked", position + 1));
        }
    }

    @Override
    public void onClick(int id) {
        mainView.showProgress();
        if (id == 0){
            loadRetrofitData();
        }
        else {
            findItemsInteractor.findItems(this);
        }
    }

    @Override
    public void onDestroy() {
        mainView = null;
    }

    @Override
    public void onFinished(List<String> items) {
        if (mainView != null) {
            mainView.setItems(items);
            mainView.hideProgress();
        }
    }

    public MainView getMainView() {
        return mainView;
    }

    public int getResult(){
        return result;
    }


    public void loadRetrofitData(){
        result = 0;
        // llamada asíncrona
        service.makeCall(this);
    }

    @Override
    public void onSuccess(int userId) {
        result = userId;
        mainView.hideProgress();
        mainView.showMessage(String.format("Respuesta del webService -> userId: %d", userId));
    }

    @Override
    public void onFail(String msg) {
        mainView.hideProgress();
        mainView.showMessage(msg);
    }
}
