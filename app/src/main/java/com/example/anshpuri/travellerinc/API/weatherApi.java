package com.example.anshpuri.travellerinc.API;

import com.example.anshpuri.travellerinc.models.weather;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by anshpuri on 7/7/17.
 */

public interface weatherApi {
    @GET("/data/2.5/weather")
    Call<weather> getweather(@Query("q") String t, @Query("appid") String id);
}
