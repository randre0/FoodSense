package com.cs125.foodsense;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.arch.lifecycle.ViewModelProviders;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cs125.foodsense.data.entity.FoodJournal;
import com.cs125.foodsense.data.view_model.FoodJournalViewModel;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
import java.util.List;

public class ViewFoodJournalFragment extends Fragment {
    private FoodJournalViewModel vm_foodJournal;
    private String USER_EMAIL = "default@uci.edu";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View v = inflater.inflate(R.layout.fragment_view_journal, container, false);
        vm_foodJournal = ViewModelProviders.of(this).get(FoodJournalViewModel.class);

        LiveData<List<FoodJournal>> foodJournal = vm_foodJournal.getMyFoodJournal("default@uci.edu");


        RecyclerView recyclerView = v.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);

        final FoodJournalAdapter adapter = new FoodJournalAdapter();
        recyclerView.setAdapter(adapter);

        final Observer<List<FoodJournal>> fJObserver = new Observer<List<FoodJournal>>() {
            @Override
            public void onChanged(@Nullable List<FoodJournal> fjList) {
                Toast.makeText(getActivity(), "onChanged", Toast.LENGTH_SHORT).show();
                adapter.setFoodJournal(fjList);
            }
        };

        foodJournal.observe(this,fJObserver);

        return v;
    }

}
