package com.example.project_1200308_1201738.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.project_1200308_1201738.Activities.Admin.AddAdminActivity;
import com.example.project_1200308_1201738.Activities.Admin.AddSpecialOffersActivity;
import com.example.project_1200308_1201738.Activities.Admin.LogoutActivity;
import com.example.project_1200308_1201738.DataBases.AdminDataBase;
import com.example.project_1200308_1201738.R;
import com.example.project_1200308_1201738.Shared.SharedPrefManagerAdmin;
import com.example.project_1200308_1201738.Taskes.AESUtil;

public class HomeAdmin extends AppCompatActivity {

    private AdminDataBase adminDataBase;
    private EditText firstName, lastName, phoneNumber, email;
    private String adminEmail;
    private String encryptionKey;
    private SharedPrefManagerAdmin sharedPrefManagerAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_admin);

        sharedPrefManagerAdmin = SharedPrefManagerAdmin.getInstance(this);
        encryptionKey = sharedPrefManagerAdmin.readString("encryption_key", null);

        if (encryptionKey == null) {
            Toast.makeText(this, "Encryption key not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        adminDataBase = new AdminDataBase(this,"admin_db",null,1);

        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        phoneNumber = findViewById(R.id.phoneNumber);
        email = findViewById(R.id.email);
        Button updateProfileButton = findViewById(R.id.updateProfileButton);
        Button addAdminButton = findViewById(R.id.addAdminButton);
        Button addSpecialOffersButton = findViewById(R.id.addSpecialOffersButton);
        Button logoutButton = findViewById(R.id.logoutButton);

        // Assuming admin email is retrieved from shared preferences or passed through intent
        adminEmail = sharedPrefManagerAdmin.readString("admin_email", "lana.batnij@example.com"); // Default to Lana Batnij's email

        loadAdminProfile();

        updateProfileButton.setOnClickListener(v -> updateProfile());

        addAdminButton.setOnClickListener(v -> {
            Intent intent = new Intent(HomeAdmin.this, AddAdminActivity.class);
            startActivity(intent);
        });
        addSpecialOffersButton.setOnClickListener(v -> {
            Intent intent = new Intent(HomeAdmin.this, AddSpecialOffersActivity.class);
            startActivity(intent);
        });

        logoutButton.setOnClickListener(v -> {
            Intent intent = new Intent(HomeAdmin.this, LogoutActivity.class);
            startActivity(intent);
            finish();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Reload the admin profile when the activity is resumed
        loadAdminProfile();
    }

    @SuppressLint("Range")
    private void loadAdminProfile() {
        Cursor cursor = adminDataBase.getAdmin(adminEmail);
        if (cursor.moveToFirst()) {
            firstName.setText(cursor.getString(cursor.getColumnIndex("FirstName")));
            lastName.setText(cursor.getString(cursor.getColumnIndex("LastName")));
            phoneNumber.setText(cursor.getString(cursor.getColumnIndex("PhoneNumber")));
            email.setText(cursor.getString(cursor.getColumnIndex("email")));
        } else {
            Toast.makeText(this, "Failed to load admin profile", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateProfile() {
        String firstNameStr = firstName.getText().toString();
        String lastNameStr = lastName.getText().toString();
        String phoneNumberStr = phoneNumber.getText().toString();
        String emailStr = email.getText().toString();

        if (validateFields(firstNameStr, lastNameStr, phoneNumberStr, emailStr)) {
            adminDataBase.updateAdmin(adminEmail, firstNameStr, lastNameStr, phoneNumberStr, emailStr);
            Toast.makeText(this, "Profile updated successfully", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean validateFields(String firstName, String lastName, String phoneNumber, String email) {
        boolean isValid = true;

        if (firstName.length() < 3) {
            isValid = false;
            this.firstName.setError("First name must be at least 3 characters");
        }

        if (!firstName.matches("[a-zA-Z]+")) {
            isValid = false;
            this.firstName.setError("First name must be only letters");
        }

        if (lastName.length() < 3) {
            isValid = false;
            this.lastName.setError("Last name must be at least 3 characters");
        }

        if (!lastName.matches("[a-zA-Z]+")) {
            isValid = false;
            this.lastName.setError("Last name must be only letters");
        }

        if (!phoneNumber.matches("[0-9]+") || phoneNumber.length() <= 9) {
            isValid = false;
            this.phoneNumber.setError("Phone number must be only numbers and more than 9 digits");
        }

        if (email.length() == 0 || !email.contains("@")) {
            isValid = false;
            this.email.setError("Email must not be empty and include @");
        }

        return isValid;
    }
}

