package com.cs125.foodsense;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;


public class HealthState extends Fragment {

    private LineGraphSeries<DataPoint> series;
    private List<HRVData> samples = new ArrayList<>();
    private View v;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.health_state, container, false);
        readData();
        return v;
    }



    private void readData() {
       InputStream is = getResources().openRawResource(R.raw.practice_data);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is,
                Charset.forName("UTF-8")));
        String line = "";
        try {
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(",");
                HRVData sample = new HRVData();
                sample.setDay(Integer.parseInt(tokens[0]));
                sample.setHrv(Integer.parseInt(tokens[1]));
                samples.add(sample);

                Log.d("MyActivity", "Just created: " + sample);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        int x, y;
        GraphView graph = (GraphView) v.findViewById(R.id.graph);
        series = new LineGraphSeries<DataPoint>();
        for(int i = 0; i < samples.size(); i++) {
            x = samples.get(i).getDay();
            y = samples.get(i).getHrv();
            series.appendData(new DataPoint(x,y), true, samples.size());
        }
        graph.addSeries(series);

    }
}
