package com.cs125.foodsense;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
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
import android.widget.TextView;

import com.cs125.foodsense.data.entity.FoodJournal;
import com.cs125.foodsense.data.entity.HeartRate;
import com.cs125.foodsense.data.entity.User;
import com.cs125.foodsense.data.view_model.FoodJournalViewModel;
import com.cs125.foodsense.data.view_model.HeartRateViewModel;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class HealthState extends Fragment {

    private LineGraphSeries<DataPoint> series;
    private List<HeartRate> samples = new ArrayList<>();
    private View v;
    BarChart barchart;
    private Button food_rec;
    private TextView mTextBody;
    String body;
    private HeartRateViewModel vm_heartRate;
    private FoodJournalViewModel vm_foodJournal;
    LiveData<List<HeartRate>> hr;
    LiveData<List<FoodJournal>> hrDiff;
    private String USER_EMAIL = "default@uci.edu";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //food_rec = (Button) v.findViewById(R.id.button);

        vm_heartRate = ViewModelProviders.of(this).get(HeartRateViewModel.class);
        vm_foodJournal = ViewModelProviders.of(this).get(FoodJournalViewModel.class);
        v = inflater.inflate(R.layout.health_state, container, false);

        barchart = (BarChart) v.findViewById(R.id.bargraph);

        String duration = "-3 day";
        hr = vm_heartRate.getAllHeartRateByUserDuration(USER_EMAIL, duration);
        hrDiff = vm_foodJournal.getMyFoodJournalByDuration(USER_EMAIL, duration);
        final Observer<List<HeartRate>> hrObserver =  new Observer<List<HeartRate>>() {
            @Override
            public void onChanged(@Nullable List<HeartRate> list) {

                int x, y;
                GraphView graph = (GraphView) v.findViewById(R.id.graph);
                series = new LineGraphSeries<DataPoint>();
                for(int i = 0; i < list.size(); i++) {
                    x = i;
                    y = list.get(i).getHeartRate();
                    series.appendData(new DataPoint(x,y), true, list.size()+1);
                }
                //x = 1;
                //y = 61;
                //series.appendData(new DataPoint(1,61), true, list.size() + 1);
                graph.addSeries(series);
            }

        };

        final Observer<List<FoodJournal>> fjObserver =  new Observer<List<FoodJournal>>() {
            @Override
            public void onChanged(@Nullable List<FoodJournal> journal) {

                ArrayList<BarEntry> barEntries = new ArrayList<>();
                for(int i = 0; i < journal.size(); i++) {
                    float y = (float)(journal.get(i).getHrDiff());
                    float x = (float)(i);
                    barEntries.add(new BarEntry(x,y));
                }
                BarDataSet barDataSet = new BarDataSet(barEntries, "Meals");

                BarData theData = new BarData(barDataSet);
                barchart.setData(theData);
            }

        };

        hr.observe(this, hrObserver);
        hrDiff.observe(this, fjObserver);

        /*food_rec.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                goToFoodRec(view);
            }
        });*/

        //Attach observer to LiveData<User>


        return v;
    }


    public void goToFoodRec(View view) {
        Intent recActivity = new Intent(getActivity(), FoodRecActivity.class);
        startActivity(recActivity);
    }

    /*private void readData() {
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

    }*/
}
