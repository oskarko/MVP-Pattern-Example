package es.org.mvpexample.rest;

import java.io.IOException;

import es.org.mvpexample.models.RetrofitCallBack;
import es.org.mvpexample.rest.models.DataResponse;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by oskarko on 4/11/16.
 */

public class NetworkService {

    private static String baseUrl = "https://jsonplaceholder.typicode.com/";
    private NetworkAPI networkAPI;
    private OkHttpClient okHttpClient;


    public NetworkService(){
        this(baseUrl);
    }

    public NetworkService(String baseUrl){
        okHttpClient = buildClient();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        networkAPI = retrofit.create(NetworkAPI.class);
    }


    public void makeCall(final RetrofitCallBack callBack){
        networkAPI.getData().enqueue(new Callback<DataResponse>() {
            @Override
            public void onResponse(Call<DataResponse> call, retrofit2.Response<DataResponse> response) {
                callBack.onSuccess( response.body().getUserId() );
            }

            @Override
            public void onFailure(Call<DataResponse> call, Throwable t) {
                callBack.onFail("Ups! algó falló X(");
            }
        });
    }


    /**
     * Método para construir un objeto OkHttpClient y poder añadir
     * cabeceras o guardar cookies de forma correcta.
     * @return
     */
    public OkHttpClient buildClient(){

        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        builder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Response response = chain.proceed(chain.request());
                // Do anything with response here
                //if we ant to grab a specific cookie or something.
                return response;
            }
        });

        builder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                //this is where we will add whatever we want to our request headers.
                Request request = chain.request().newBuilder().addHeader("Accept", "application/json").build();
                return chain.proceed(request);
            }
        });

        return  builder.build();
    }


}
