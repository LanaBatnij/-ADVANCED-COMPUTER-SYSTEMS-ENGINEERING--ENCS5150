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

import com.example.project_1200308_1201738.DataBases.AdminDataBase;
import com.example.project_1200308_1201738.R;
import com.example.project_1200308_1201738.Shared.SharedPrefManagerAdmin;
import com.example.project_1200308_1201738.Taskes.AESUtil;

import java.util.HashMap;

public class SignUpAsAdmin extends AppCompatActivity {
    private static final String TOAST_TEXT = "Sign up is successful";
    public static String adminName = "";
    private static final String TAG = "SignUpAsAdmin";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_as_admin);

        AdminDataBase adminDataBase = new AdminDataBase(this,"admin_db",null,1);
        Spinner genderSpinner = findViewById(R.id.genderforadmin);
        Spinner countrySpinner = findViewById(R.id.countryforadmin);
        Spinner citySpinner = findViewById(R.id.cityforadmin);
        EditText email2 = findViewById(R.id.emailforadmin);
        EditText firstName = findViewById(R.id.firstNameforadmin);
        EditText lastName = findViewById(R.id.lastNameforadmin);
        EditText phoneNumber = findViewById(R.id.phoneNumberforadmin);
        EditText password2 = findViewById(R.id.passwordforadmin);
        EditText ConfirmPassword = findViewById(R.id.confirmPasswordforadmin);
        Button createAccount = findViewById(R.id.createaccountforadmin);
        Button back = findViewById(R.id.back);

        back.setOnClickListener(view -> {
            Intent intent = new Intent(SignUpAsAdmin.this, SignInAsAdmin.class);
            startActivity(intent);
            finish();
        });

        HashMap<String, String> areaCodes = new HashMap<>();
        areaCodes.put("Palestine", "00970");
        areaCodes.put("Jordan", "00962");
        areaCodes.put("USA", "001");
        areaCodes.put("Egypt", "0020");

        String[] options = {"Male", "Female"};
        ArrayAdapter<String> objGenderArr = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, options);
        genderSpinner.setAdapter(objGenderArr);

        String[] options2 = {"Palestine", "Jordan"};
        ArrayAdapter<String> objCountryArr = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, options2);
        countrySpinner.setAdapter(objCountryArr);

        String[] options5 = {"Jerusalem", "Ramallah", "Hebron", "Nablus"};
        ArrayAdapter<String> objCityArrforPalestine = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, options5);

        String[] options6 = {"Amman", "Irbid", "Zarqa", "Aqaba"};
        ArrayAdapter<String> objCityArrforJordan = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, options6);

        countrySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedCountry = countrySpinner.getSelectedItem().toString();
                String areaCode = areaCodes.get(selectedCountry);
                phoneNumber.setText(areaCode);
                switch (selectedCountry) {
                    case "Palestine":
                        citySpinner.setAdapter(objCityArrforPalestine);
                        break;
                    case "Jordan":
                        citySpinner.setAdapter(objCityArrforJordan);
                        break;
                    default:
                        // Handle default case or do nothing
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Handle no selection if needed
            }
        });

        createAccount.setOnClickListener(v -> {
            // Validate all fields
            boolean isValid = true;

            if (firstName.getText().toString().length() < 3 || !firstName.getText().toString().matches("[a-zA-Z]+")) {
                firstName.setError("First name must be at least 3 characters and only letters");
                isValid = false;
            }

            if (lastName.getText().toString().length() < 3 || !lastName.getText().toString().matches("[a-zA-Z]+")) {
                lastName.setError("Last name must be at least 3 characters and only letters");
                isValid = false;
            }

            if (password2.getText().toString().length() < 5 ||
                    !password2.getText().toString().matches(".*[a-zA-Z]+.*") ||
                    !password2.getText().toString().matches(".*[0-9]+.*") ||
                    !password2.getText().toString().matches(".*[!@#$%^&*()_+]+.*")) {
                password2.setError("Password must be at least 5 characters and include at least 1 character, 1 number, and 1 special character");
                isValid = false;
            }

            if (!password2.getText().toString().equals(ConfirmPassword.getText().toString())) {
                ConfirmPassword.setError("Password and confirm password must be the same");
                isValid = false;
            }

            if (!phoneNumber.getText().toString().matches("[0-9]+") || phoneNumber.getText().toString().length() <= 9) {
                phoneNumber.setError("Phone number must be only numbers and more than 9 digits");
                isValid = false;
            }

            if (email2.getText().toString().isEmpty() || !email2.getText().toString().contains("@")) {
                email2.setError("Email must not be empty and include @");
                isValid = false;
            } else {
                Cursor cursor = adminDataBase.getAdmin(email2.getText().toString());
                if (cursor.getCount() != 0) {
                    email2.setError("Email is already exist");
                    isValid = false;
                }
            }

            if (isValid) {
                try {
                    // Removed encryption for testing
                    String password = password2.getText().toString();
                    adminDataBase.insertAdmin(
                            email2.getText().toString(),
                            password,
                            firstName.getText().toString(),
                            lastName.getText().toString(),
                            phoneNumber.getText().toString(),
                            genderSpinner.getSelectedItem().toString(),
                            countrySpinner.getSelectedItem().toString(),
                            citySpinner.getSelectedItem().toString()
                    );

                    adminName = firstName.getText().toString();
                    Toast.makeText(SignUpAsAdmin.this, TOAST_TEXT, Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "Sign up successful");

                    Intent intent = new Intent(SignUpAsAdmin.this, SignInAsAdmin.class);
                    startActivity(intent);
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(SignUpAsAdmin.this, "Error during sign up", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "Error during sign up", e);
                }
            }
        });
    }
}
