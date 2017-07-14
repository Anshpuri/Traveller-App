package com.example.anshpuri.travellerinc;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by anshpuri on 7/7/17.
 */

public class WorldClock extends Fragment{

    Calendar current;
    Spinner spinner;
    TextView tvTimeZone , tvCurrentTime , tvTimeZoneTime;
    long milliseconds;
    ArrayAdapter<String> idAdapter;
    SimpleDateFormat adf;
    Date resultDate;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.worldclock, container, false);

        spinner = (Spinner) rootView.findViewById(R.id.cities_spinner);
        tvTimeZone= (TextView) rootView.findViewById(R.id.tvTimeZone);
        tvCurrentTime = (TextView) rootView.findViewById(R.id.tvCurrentTime);
        tvTimeZoneTime = (TextView) rootView.findViewById(R.id.tvTimeZoneTime);

        String[] idArray = TimeZone.getAvailableIDs();
        adf = new SimpleDateFormat("EEEE , dd MMMM yyyy HH:mm:ss"  );

        idAdapter = new ArrayAdapter<String>(getActivity() ,android.R.layout.simple_spinner_item,
                android.R.id.text1,
                idArray);

        idAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(idAdapter);
        getGmtTime();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                getGmtTime();
                String  selected = (String)(parent.getItemAtPosition(position));
                TimeZone timeZone= TimeZone.getTimeZone(selected);
                String TimeZoneName = timeZone.getDisplayName();

                int TimeZoneOffset = timeZone.getRawOffset()/(60*1000);
                int  hrs = TimeZoneOffset / 60;
                int mins = TimeZoneOffset% 60;
                milliseconds = milliseconds+ timeZone.getRawOffset();
                resultDate = new Date(milliseconds);
                tvTimeZone.setText(TimeZoneName + ":GMT" + hrs + " : " + mins );
                tvTimeZoneTime.setText("" + adf.format(resultDate));
                milliseconds= 0;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        return rootView;

    }
    private void getGmtTime(){
        current = Calendar.getInstance();
        tvCurrentTime.setText("" + current.getTime());
        milliseconds = current.getTimeInMillis();
        TimeZone trCurrent = current.getTimeZone();
        int offset = trCurrent.getRawOffset();

        if(trCurrent.inDaylightTime(new Date())){
            offset = offset + trCurrent.getDSTSavings();
        }
        milliseconds=milliseconds- offset;
    }
}
