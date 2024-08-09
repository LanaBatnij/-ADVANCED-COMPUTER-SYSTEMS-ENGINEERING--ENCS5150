package com.example.project_1200308_1201738.Fragments;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.project_1200308_1201738.DataBases.AdminDataBase;
import com.example.project_1200308_1201738.Models.SpecialOffer;
import com.example.project_1200308_1201738.R;
import com.example.project_1200308_1201738.Taskes.SpecialOfferAdapter;

import java.util.ArrayList;
import java.util.List;

public class SpecialsFragment extends Fragment {

    private RecyclerView specialsRecyclerView;
    private SpecialOfferAdapter specialOfferAdapter;
    private AdminDataBase adminDataBase;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_specials, container, false);
        specialsRecyclerView = view.findViewById(R.id.specialsRecyclerView);
        specialsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adminDataBase = new AdminDataBase(getContext(),"admin_db",null,3);
        loadSpecialOffers();

        return view;
    }

    private void loadSpecialOffers() {
        List<SpecialOffer> specialOfferList = new ArrayList<>();
        Cursor cursor = adminDataBase.getAllSpecialOffers();

        if (cursor != null && cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String pizzaTypes = cursor.getString(cursor.getColumnIndex("pizza_types"));
                @SuppressLint("Range") String sizes = cursor.getString(cursor.getColumnIndex("sizes"));
                @SuppressLint("Range") String offerPeriod = cursor.getString(cursor.getColumnIndex("offer_period"));
                @SuppressLint("Range") double totalPrice = cursor.getDouble(cursor.getColumnIndex("total_price"));

                specialOfferList.add(new SpecialOffer(pizzaTypes, sizes, offerPeriod, totalPrice));
            } while (cursor.moveToNext());

            cursor.close();
        }

        specialOfferAdapter = new SpecialOfferAdapter(getContext(), specialOfferList);
        specialsRecyclerView.setAdapter(specialOfferAdapter);
    }
}
