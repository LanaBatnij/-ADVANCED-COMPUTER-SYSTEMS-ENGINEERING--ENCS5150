package com.example.project_1200308_1201738.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project_1200308_1201738.DataBases.PizzaDatabaseHelper;
import com.example.project_1200308_1201738.Models.Pizza;
import com.example.project_1200308_1201738.R;
import com.example.project_1200308_1201738.Taskes.ConnectionAsyncTask;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private PizzaDatabaseHelper pizzaDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pizzaDatabaseHelper = new PizzaDatabaseHelper(this);

        Button get_started = findViewById(R.id.button_getstarted);
        get_started.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectionAsyncTask connectionAsyncTask = new ConnectionAsyncTask(MainActivity.this);
                connectionAsyncTask.setResultListener(new ConnectionAsyncTask.ConnectionResultListener() {
                    @Override
                    public void onConnectionResult(boolean successful, List<Pizza> pizzaList) {
                        if (successful) {
                            for (Pizza pizza : pizzaList) {
                                pizzaDatabaseHelper.insertPizza(pizza);
                            }
                            Toast.makeText(MainActivity.this, "Connection successful", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainActivity.this, SignInChoices.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(MainActivity.this, "Connection unsuccessful", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                connectionAsyncTask.execute("https://18fbea62d74a40eab49f72e12163fe6c.api.mockbin.io/");
            }
        });
    }
}
