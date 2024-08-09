package com.example.project_1200308_1201738.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.project_1200308_1201738.R;

public class PizzaDetailsFragment extends Fragment {

    private static final String ARG_ID = "id";
    private static final String ARG_NAME = "name";
    private static final String ARG_DESCRIPTION = "description";
    private static final String ARG_CATEGORY = "category";
    private static final String ARG_SIZES = "sizes";
    private static final String ARG_PRICES = "prices";

    private int id;
    private String name;
    private String description;
    private String category;
    private String[] sizes;
    private double[] prices;

    public PizzaDetailsFragment() {
        // Required empty public constructor
    }

    public static PizzaDetailsFragment newInstance(int id, String name, String description, String category, String[] sizes, double[] prices) {
        PizzaDetailsFragment fragment = new PizzaDetailsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_ID, id);
        args.putString(ARG_NAME, name);
        args.putString(ARG_DESCRIPTION, description);
        args.putString(ARG_CATEGORY, category);
        args.putStringArray(ARG_SIZES, sizes);
        args.putDoubleArray(ARG_PRICES, prices);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id = getArguments().getInt(ARG_ID);
            name = getArguments().getString(ARG_NAME);
            description = getArguments().getString(ARG_DESCRIPTION);
            category = getArguments().getString(ARG_CATEGORY);
            sizes = getArguments().getStringArray(ARG_SIZES);
            prices = getArguments().getDoubleArray(ARG_PRICES);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pizza_details_fragments, container, false);

        TextView nameTextView = view.findViewById(R.id.pizzaNameTextView);
        TextView descriptionTextView = view.findViewById(R.id.pizzaDescriptionTextView);
        TextView categoryTextView = view.findViewById(R.id.pizzaCategoryTextView);
        TextView sizesTextView = view.findViewById(R.id.pizzaSizesTextView);
        TextView pricesTextView = view.findViewById(R.id.pizzaPricesTextView);

        nameTextView.setText(name);
        descriptionTextView.setText(description);
        categoryTextView.setText(category);
        sizesTextView.setText("Sizes: " + String.join(", ", sizes));
        pricesTextView.setText("Prices: " + formatPrices(prices));

        return view;
    }

    private String formatPrices(double[] prices) {
        StringBuilder pricesStr = new StringBuilder();
        for (double price : prices) {
            pricesStr.append(price).append(" ");
        }
        return pricesStr.toString().trim();
    }
}
