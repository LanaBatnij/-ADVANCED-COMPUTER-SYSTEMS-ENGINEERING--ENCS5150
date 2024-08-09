package com.example.project_1200308_1201738.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_1200308_1201738.DataBases.PizzaDatabaseHelper;
import com.example.project_1200308_1201738.Models.PizzaDetails;
import com.example.project_1200308_1201738.R;
import com.example.project_1200308_1201738.Taskes.PizzaAdapter;

import java.util.ArrayList;
import java.util.List;

public class PizzaMenuFragment extends Fragment {

    private RecyclerView pizzaRecyclerView;
    private PizzaAdapter pizzaAdapter;
    private PizzaDatabaseHelper pizzaDatabaseHelper;

    @SuppressLint("WrongViewCast")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pizza_menu, container, false);
        pizzaRecyclerView = view.findViewById(R.id.pizzaListView);
        pizzaRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        pizzaDatabaseHelper = new PizzaDatabaseHelper(getContext());
        pizzaDatabaseHelper.UpdateInitialPizzas(); // Ensure initial update
        loadPizzaData();
        return view;
    }

    private void loadPizzaData() {
        List<PizzaDetails> pizzaList = new ArrayList<>();
        Cursor cursor = pizzaDatabaseHelper.getAllPizzas();

        if (cursor != null && cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("name"));
                @SuppressLint("Range") String description = cursor.getString(cursor.getColumnIndex("description"));
                @SuppressLint("Range") String category = cursor.getString(cursor.getColumnIndex("category"));
                @SuppressLint("Range") String sizesString = cursor.getString(cursor.getColumnIndex("sizes"));
                @SuppressLint("Range") String pricesString = cursor.getString(cursor.getColumnIndex("prices"));

                String[] sizes = sizesString.split(",");
                double[] prices = stringToDoubleArray(pricesString);

                pizzaList.add(new PizzaDetails(name, description, category, sizes, prices));
            } while (cursor.moveToNext());

            cursor.close();
        }

        pizzaAdapter = new PizzaAdapter(getContext(), pizzaList);
        pizzaRecyclerView.setAdapter(pizzaAdapter);
    }

    private double[] stringToDoubleArray(String str) {
        String[] split = str.split(",");
        double[] result = new double[split.length];
        for (int i = 0; i < split.length; i++) {
            result[i] = Double.parseDouble(split[i]);
        }
        return result;
    }
}
