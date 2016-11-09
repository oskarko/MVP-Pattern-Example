package es.org.mvpexample.views;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;

import es.org.mvpexample.MVPApplication;
import es.org.mvpexample.R;
import es.org.mvpexample.models.FindItemsInteractorImpl;
import es.org.mvpexample.presenters.MainPresenterImpl;
import es.org.mvpexample.rest.NetworkService;

public class MainActivity extends Activity implements MainView, View.OnClickListener, ListView.OnItemClickListener {

    private ListView listView;
    private Button btnRetrofit, btnFillListview;
    private ProgressBar progressBar;
    private MainPresenterImpl presenter;
    private NetworkService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listview);
        btnRetrofit = (Button) findViewById(R.id.buttonRetrofit);
        btnFillListview = (Button) findViewById(R.id.buttonList);
        progressBar = (ProgressBar) findViewById(R.id.progress);

        btnRetrofit.setOnClickListener(this);
        btnFillListview.setOnClickListener(this);
        listView.setOnItemClickListener(this);

        service = ((MVPApplication)getApplication()).getNetworkService();


        presenter = new MainPresenterImpl(this, new FindItemsInteractorImpl(), service);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonRetrofit:
                presenter.onClick(0);
                break;
            case R.id.buttonList:
                presenter.onClick(1);
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        presenter.onItemClicked(position);
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
        listView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.INVISIBLE);
        listView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void setItems(List<String> items) {
        listView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items));
    }
}
