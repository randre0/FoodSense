package com.cs125.foodsense;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cs125.foodsense.data.entity.FoodJournal;

import java.util.ArrayList;
import java.util.List;

import static com.cs125.foodsense.R.id.text_view_food;
import static com.cs125.foodsense.R.id.text_view_food_class;

public class FoodJournalAdapter extends RecyclerView.Adapter<FoodJournalAdapter.FoodJournalHolder> {
    private List<FoodJournal> foodJournal = new ArrayList<>();

    @NonNull
    @Override
    public FoodJournalHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.food_item, parent, false);
        return new FoodJournalHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodJournalHolder holder, int position) {
        FoodJournal currentEntry = foodJournal.get(position);
        holder.textViewFood.setText(currentEntry.getFood());
        holder.textViewFoodClass.setText(currentEntry.getFoodClassification());
    }

    @Override
    public int getItemCount() {
        return foodJournal.size();
    }

    public void setFoodJournal(List<FoodJournal> foodJournal) {
        this.foodJournal = foodJournal;
        notifyDataSetChanged();
    }

    class FoodJournalHolder extends RecyclerView.ViewHolder {
        private TextView textViewFood;
        private TextView textViewFoodClass;

        public FoodJournalHolder(View itemView) {
            super(itemView);
            textViewFood = itemView.findViewById(R.id.text_view_food);
            textViewFoodClass = itemView.findViewById(R.id.text_view_food_class);
        }
    }
}
