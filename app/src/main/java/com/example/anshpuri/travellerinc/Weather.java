package com.example.anshpuri.travellerinc;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anshpuri.travellerinc.API.weatherApi;
import com.example.anshpuri.travellerinc.models.temperature;
import com.example.anshpuri.travellerinc.models.weather;


import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by anshpuri on 7/7/17.
 */

public class Weather extends Fragment{

EditText etCity;
     TextView tvTemp , tvTemp_Max , tvTemp_Min;
    Button btn;
    String t;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {

        View rootView = inflater.inflate(R.layout.weather, container, false);
        etCity = (EditText) rootView.findViewById(R.id.etCity);
        tvTemp = (TextView) rootView.findViewById(R.id.tvTemp);
        tvTemp_Min = (TextView) rootView.findViewById(R.id.tvTemp_min);
        tvTemp_Max = (TextView) rootView.findViewById(R.id.tvTemp_max);
        btn = (Button) rootView.findViewById(R.id.btnweather);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                t = etCity.getText().toString();
                Log.d(" ", "onCreateViewhi: " + t);
                Toast.makeText(getActivity(), "" + t, Toast.LENGTH_SHORT).show();
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(" http://api.openweathermap.org")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                weatherApi weatherapi = retrofit.create(weatherApi.class);
                weatherapi.getweather(t,"4126359df82c135c666d58fc5eb40daf").enqueue(new Callback<weather>() {
                    @Override
                    public void onResponse(Call<weather> call, Response<weather> response) {

//                       Toast.makeText(getActivity() , " " + response.body().getMain().getTemp() , Toast.LENGTH_SHORT ).show();
                        Double d= (response.body().getMain().getTemp()-273);
                        Double min = response.body().getMain().getTemp_min()-273;
                        Double max = response.body().getMain().getTemp_max()-273 ;
                       float f = Float.valueOf(String.valueOf(d));
                        float minf = Float.valueOf(String.valueOf(min));
                        float maxf = Float.valueOf(String.valueOf(max));
                        tvTemp.setText("Avg Temp - " + String.valueOf(f) + "°C");
                        tvTemp_Max.setText("Max Temp - " + String.valueOf(maxf) + "°C");
                        tvTemp_Min.setText("Min Temp - " + String.valueOf(minf) + "°C");

                    }

                    @Override
                    public void onFailure(Call<weather> call, Throwable t) {

                    }
                });
            }
        });

        Log.d(" ", "onCreateView: " + etCity.getText().toString());


        return rootView;

    }
}
