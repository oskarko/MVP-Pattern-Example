package es.org.mvpexample;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowToast;

import java.util.Arrays;
import java.util.List;

import es.org.mvpexample.models.FindItemsInteractor;
import es.org.mvpexample.models.RetrofitCallBack;
import es.org.mvpexample.presenters.MainPresenterImpl;
import es.org.mvpexample.rest.NetworkService;
import es.org.mvpexample.rest.models.DataResponse;
import es.org.mvpexample.views.MainView;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;
import static org.mockito.ArgumentCaptor.forClass;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Unit Tests
 */
@RunWith(MockitoJUnitRunner.class)
@PowerMockRunnerDelegate(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 24)
public class ExampleUnitTest {

    @Mock
    MainView view;
    @Mock
    FindItemsInteractor interactor;
    @Mock
    NetworkService service;
    @Captor
    private ArgumentCaptor<RetrofitCallBack> callbackAC;

    private MainPresenterImpl presenter;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        presenter = new MainPresenterImpl(view, interactor, service);
    }

    @Test
    public void checkIfShowsMessageOnItemClick() {
        presenter.onItemClicked(1);
        verify(view, times(1)).showMessage(anyString());
    }

    @Test
    public void checkIfRightMessageIsDisplayed() {
        ArgumentCaptor<String> captor = forClass(String.class);
        presenter.onItemClicked(1);
        verify(view, times(1)).showMessage(captor.capture());
        assertThat(captor.getValue(), is("Position 2 clicked"));
    }

    @Test
    public void checkIfViewIsReleasedOnDestroy() {
        presenter.onDestroy();
        assertNull(presenter.getMainView());
    }

    @Test
    public void checkIfItemsArePassedToView() {
        List<String> items = Arrays.asList("Model", "View", "Presenter");
        presenter.onFinished(items);
        verify(view, times(1)).setItems(items);
        verify(view, times(1)).hideProgress();
    }

    @Test
    public void checkRetrofitCallIsCorrect() throws Exception {

        ArgumentCaptor<String> captor = forClass(String.class);
        final int result = 1;

        presenter.loadRetrofitData();
        verify(service, times(1)).makeCall(callbackAC.capture());

        // comprobación previa
        assertThat(presenter.getResult(), is(equalTo(0)));

        callbackAC.getValue().onSuccess(result);

        // comprobaciones finales
        assertThat(presenter.getResult(), is(equalTo(result)));
        verify(view, times(1)).hideProgress();
        verify(view, times(1)).showMessage(captor.capture());
        assertThat(captor.getValue(), is(String.format("Respuesta del webService -> userId: %d", result)));

    }

    @Test
    public void checkRetrofitCallIsIncorrect() throws Exception {

        ArgumentCaptor<String> captor = forClass(String.class);
        final String result = "Ups! algó falló X(";

        presenter.loadRetrofitData();
        verify(service, times(1)).makeCall(callbackAC.capture());

        callbackAC.getValue().onFail(result);

        verify(view, times(1)).hideProgress();
        verify(view, times(1)).showMessage(captor.capture());
        assertThat(captor.getValue(), is(result));

    }
}