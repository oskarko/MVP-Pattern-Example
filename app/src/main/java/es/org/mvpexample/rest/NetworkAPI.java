package es.org.mvpexample.rest;

import es.org.mvpexample.rest.models.DataResponse;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by oskarko on 5/11/16.
 */

public interface NetworkAPI {

    @GET("posts/1")
    Call<DataResponse> getData();
}
