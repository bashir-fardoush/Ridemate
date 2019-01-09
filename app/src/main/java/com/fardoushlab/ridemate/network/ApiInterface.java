package com.fardoushlab.ridemate.network;

import com.fardoushlab.ridemate.models.Response;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface ApiInterface {

    @Headers({
            "authorization:32DFCFD@#&DSFDSFSDF!L@?hh7@32DF",
            "Accept: application/json"
    })
    @GET("profile/api/v2/client")
    Call<Response> getClients();
}
