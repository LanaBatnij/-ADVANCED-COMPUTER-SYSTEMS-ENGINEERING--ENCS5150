package com.example.project_1200308_1201738.Activities.Admin;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.example.project_1200308_1201738.Activities.HomeAdmin;
import com.example.project_1200308_1201738.Activities.SignInChoices;
import com.example.project_1200308_1201738.DataBases.AdminDataBase;
import com.example.project_1200308_1201738.R;
import com.example.project_1200308_1201738.Shared.SharedPrefManagerAdmin;

public class SignInAsAdmin extends AppCompatActivity {
    SharedPrefManagerAdmin sharedPrefManagerAdmin;
    public static String adminEmail = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_as_admin);

        Button signAsAdmin = findViewById(R.id.signInAsAdmin);
        Button signUpAsAdmin = findViewById(R.id.signUpAdmin);
        CheckBox rememberMe = findViewById(R.id.rememberForAdmin);
        EditText email = findViewById(R.id.emailForAdmin);
        EditText password = findViewById(R.id.passwordForAdmin);
        Button forgetPassword = findViewById(R.id.forgetPassword2);

        AdminDataBase adminDataBase = new AdminDataBase(SignInAsAdmin.this,"admin_db",null,2);
        sharedPrefManagerAdmin = SharedPrefManagerAdmin.getInstance(this);

        // Read the email from the shared preferences
        email.setText(sharedPrefManagerAdmin.readString("Email", ""));
        password.setText(sharedPrefManagerAdmin.readString("Password", ""));

        forgetPassword.setOnClickListener(v -> {
            Toast.makeText(SignInAsAdmin.this, "Please contact the admin",
                    Toast.LENGTH_SHORT).show();
            AlertDialog.Builder builder = new AlertDialog.Builder(SignInAsAdmin.this);
            String message = "Please contact the admin to reset your password";
            builder.setMessage(message);
            builder.setIcon(R.drawable.baseline_security_update_warning_24);
            builder.setTitle("Forget Password");
            builder.setPositiveButton("OK", (dialog, which) -> {
                dialog.dismiss();
            });
            builder.show();
        });

        signUpAsAdmin.setOnClickListener(v -> {
            Intent intent = new Intent(SignInAsAdmin.this, SignUpAsAdmin.class);
            startActivity(intent);
            finish();
        });

        Button back = findViewById(R.id.back);
        back.setOnClickListener(v -> {
            Intent intent = new Intent(SignInAsAdmin.this, SignInChoices.class);
            SignInAsAdmin.this.startActivity(intent);
            finish();
        });

        signAsAdmin.setOnClickListener(v -> {
            // Read email and password entered
            String emailString = email.getText().toString();
            String passwordString = password.getText().toString();

            // Check if this email is in the database
            Cursor cursor = adminDataBase.getAdmin(emailString);
            if (cursor.getCount() == 0) {
                email.setError("Email not found");
            } else {
                cursor.moveToFirst();
                // Check if the password is correct
                @SuppressLint("Range") String storedPassword = cursor.getString(cursor.getColumnIndex("password"));
                Log.d("SignInAsAdmin", "Stored Password: " + storedPassword);

                if (passwordString.equals(storedPassword)) {
                    adminEmail = emailString;

                    // If remember me is checked
                    if (rememberMe.isChecked()) {
                        sharedPrefManagerAdmin.writeString("Email", emailString);
                        sharedPrefManagerAdmin.writeString("Password", passwordString);
                        Toast.makeText(SignInAsAdmin.this, "Sign In Successfully", Toast.LENGTH_SHORT).show();
                    }
                    Intent intent = new Intent(SignInAsAdmin.this, HomeAdmin.class);
                    SignInAsAdmin.this.startActivity(intent);
                    finish();
                } else {
                    password.setError("Password is incorrect");
                }
            }
        });
    }
}
