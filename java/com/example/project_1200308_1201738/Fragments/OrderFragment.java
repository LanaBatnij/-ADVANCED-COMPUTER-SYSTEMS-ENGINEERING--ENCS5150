package com.example.project_1200308_1201738.Fragments;

import android.annotation.SuppressLint;
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
import com.example.project_1200308_1201738.Models.OrderDetails;
import com.example.project_1200308_1201738.R;
import com.example.project_1200308_1201738.Taskes.OrderAdapter;
import java.util.ArrayList;
import java.util.List;

public class OrderFragment extends Fragment {
    private RecyclerView orderListView;
    private OrderAdapter orderAdapter;
    private List<OrderDetails> orderList;
    private PizzaDatabaseHelper pizzaDatabaseHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order, container, false);
        orderListView = view.findViewById(R.id.orderRecyclerView);
        orderListView.setLayoutManager(new LinearLayoutManager(getContext())); // Ensure LayoutManager is set
        pizzaDatabaseHelper = new PizzaDatabaseHelper(getContext());
        loadOrders();
        return view;
    }

    private void loadOrders() {
        orderList = new ArrayList<>();
        Cursor cursor = pizzaDatabaseHelper.getAllOrders();
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("id"));
                @SuppressLint("Range") int pizzaId = cursor.getInt(cursor.getColumnIndex("pizza_id"));
                @SuppressLint("Range") String size = cursor.getString(cursor.getColumnIndex("size"));
                @SuppressLint("Range") double price = cursor.getDouble(cursor.getColumnIndex("price"));
                @SuppressLint("Range") String dateTime = cursor.getString(cursor.getColumnIndex("date_time"));
                @SuppressLint("Range") String pizzaName = cursor.getString(cursor.getColumnIndex("pizza_name"));

                OrderDetails order = new OrderDetails(id, pizzaId, pizzaName, size, price, dateTime);
                orderList.add(order);
            } while (cursor.moveToNext());
        } else {
            Log.d("OrderFragment", "No orders found");
        }
        cursor.close();
        orderAdapter = new OrderAdapter(orderList);
        orderListView.setAdapter(orderAdapter);
    }
}
