package com.example.project_1200308_1201738.Activities.Admin;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project_1200308_1201738.Activities.HomeAdmin;
import com.example.project_1200308_1201738.DataBases.AdminDataBase;
import com.example.project_1200308_1201738.R;
import com.example.project_1200308_1201738.Shared.SharedPrefManagerAdmin;

import java.util.HashMap;

public class AddAdminActivity extends AppCompatActivity {
    private static final String TAG = "AddAdminActivity";
    private AdminDataBase adminDataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_admin);

        adminDataBase = new AdminDataBase(this,"admin_db",null,1);

        Spinner genderSpinner = findViewById(R.id.genderforadmin);
        Spinner countrySpinner = findViewById(R.id.countryforadmin);
        Spinner citySpinner = findViewById(R.id.cityforadmin);
        EditText email = findViewById(R.id.emailforadmin);
        EditText firstName = findViewById(R.id.firstNameforadmin);
        EditText lastName = findViewById(R.id.lastNameforadmin);
        EditText phoneNumber = findViewById(R.id.phoneNumberforadmin);
        EditText password = findViewById(R.id.passwordforadmin);
        EditText confirmPassword = findViewById(R.id.confirmPasswordforadmin);
        Button createAccount = findViewById(R.id.createaccountforadmin);

        String[] genderOptions = {"Male", "Female"};
        ArrayAdapter<String> genderAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, genderOptions);
        genderSpinner.setAdapter(genderAdapter);

        String[] countryOptions = {"Palestine", "Jordan"};
        ArrayAdapter<String> countryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, countryOptions);
        countrySpinner.setAdapter(countryAdapter);

        String[] palestineCities = {"Jerusalem", "Ramallah", "Hebron", "Nablus"};
        ArrayAdapter<String> palestineCityAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, palestineCities);

        String[] jordanCities = {"Amman", "Irbid", "Zarqa", "Aqaba"};
        ArrayAdapter<String> jordanCityAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, jordanCities);

        countrySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedCountry = countrySpinner.getSelectedItem().toString();
                String areaCode = getAreaCode(selectedCountry);
                phoneNumber.setText(areaCode);
                switch (selectedCountry) {
                    case "Palestine":
                        citySpinner.setAdapter(palestineCityAdapter);
                        break;
                    case "Jordan":
                        citySpinner.setAdapter(jordanCityAdapter);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Handle no selection if needed
            }
        });

        createAccount.setOnClickListener(v -> {
            String emailStr = email.getText().toString();
            String firstNameStr = firstName.getText().toString();
            String lastNameStr = lastName.getText().toString();
            String phoneNumberStr = phoneNumber.getText().toString();
            String passwordStr = password.getText().toString();
            String confirmPasswordStr = confirmPassword.getText().toString();
            String genderStr = genderSpinner.getSelectedItem().toString();
            String countryStr = countrySpinner.getSelectedItem().toString();
            String cityStr = citySpinner.getSelectedItem().toString();

            if (validateFields(emailStr, firstNameStr, lastNameStr, phoneNumberStr, passwordStr, confirmPasswordStr)) {
                try {
                    adminDataBase.insertAdmin(emailStr, passwordStr, firstNameStr, lastNameStr, phoneNumberStr, genderStr, countryStr, cityStr);
                    Toast.makeText(AddAdminActivity.this, "Admin added successfully", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "Admin added successfully");

                    Intent intent = new Intent(AddAdminActivity.this, HomeAdmin.class);
                    startActivity(intent);
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(AddAdminActivity.this, "Error adding admin", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "Error adding admin", e);
                }
            }
        });
    }

    private boolean validateFields(String email, String firstName, String lastName, String phoneNumber, String password, String confirmPassword) {
        boolean isValid = true;

        if (firstName.length() < 3) {
            isValid = false;
            ((EditText) findViewById(R.id.firstNameforadmin)).setError("First name must be at least 3 characters");
        }

        if (!firstName.matches("[a-zA-Z]+")) {
            isValid = false;
            ((EditText) findViewById(R.id.firstNameforadmin)).setError("First name must be only letters");
        }

        if (lastName.length() < 3) {
            isValid = false;
            ((EditText) findViewById(R.id.lastNameforadmin)).setError("Last name must be at least 3 characters");
        }

        if (!lastName.matches("[a-zA-Z]+")) {
            isValid = false;
            ((EditText) findViewById(R.id.lastNameforadmin)).setError("Last name must be only letters");
        }

        if (password.length() < 5 || !password.matches(".*[a-zA-Z]+.*") || !password.matches(".*[0-9]+.*") || !password.matches(".*[!@#$%^&*()_+]+.*")) {
            isValid = false;
            ((EditText) findViewById(R.id.passwordforadmin)).setError("Password must be at least 5 characters, include at least 1 character, 1 number, and one special character");
        }

        if (!password.equals(confirmPassword)) {
            isValid = false;
            ((EditText) findViewById(R.id.confirmPasswordforadmin)).setError("Password and confirm password must be the same");
        }

        if (!phoneNumber.matches("[0-9]+") || phoneNumber.length() <= 9) {
            isValid = false;
            ((EditText) findViewById(R.id.phoneNumberforadmin)).setError("Phone number must be only numbers and more than 9 digits");
        }

        if (email.length() == 0 || !email.contains("@")) {
            isValid = false;
            ((EditText) findViewById(R.id.emailforadmin)).setError("Email must not be empty and include @");
        }

        Cursor cursor = adminDataBase.getAdmin(email);
        if (cursor.getCount() != 0) {
            isValid = false;
            ((EditText) findViewById(R.id.emailforadmin)).setError("Email is already exist");
        }

        return isValid;
    }

    private String getAreaCode(String country) {
        HashMap<String, String> areaCodes = new HashMap<>();
        areaCodes.put("Palestine", "00970");
        areaCodes.put("Jordan", "00962");
        return areaCodes.getOrDefault(country, "");
    }
}
