package com.example.project_1200308_1201738.Activities.User;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.project_1200308_1201738.Taskes.AESUtil;
import com.example.project_1200308_1201738.Activities.HomeActivity;
import com.example.project_1200308_1201738.DataBases.DataBaseHelper;
import com.example.project_1200308_1201738.R;
import com.example.project_1200308_1201738.Shared.SharedPrefManager;

public class LogIn extends AppCompatActivity {

    private EditText passwordEditText;
    private EditText emailEditText;
    private CheckBox rememberMeCheckBox;
    private Button LButton;
    private SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        passwordEditText = findViewById(R.id.editTextPassword);
        emailEditText = findViewById(R.id.editTextEmail);
        rememberMeCheckBox = findViewById(R.id.checkBox_remember);
        LButton = findViewById(R.id.buttonL);

        sharedPrefManager = SharedPrefManager.getInstance(this);

        String savedEmail = sharedPrefManager.readString("email", "");
        if (!savedEmail.isEmpty()) {
            emailEditText.setText(savedEmail);
            rememberMeCheckBox.setChecked(true);
        }

        LButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();
                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LogIn.this, "Please enter email and password", Toast.LENGTH_SHORT).show();
                    return;
                }

                DataBaseHelper dbHelper = new DataBaseHelper(LogIn.this);
                String storedEncryptedPassword = dbHelper.getPasswordHash(email);

                if (storedEncryptedPassword != null) {
                    String aesKey = sharedPrefManager.readString("aesKey", "");
                    String decryptedPassword;
                    try {
                        decryptedPassword = AESUtil.decrypt(aesKey, storedEncryptedPassword);
                    } catch (Exception e) {
                        Toast.makeText(LogIn.this, "Failed to decrypt password", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (password.equals(decryptedPassword)) {
                        if (rememberMeCheckBox.isChecked()) {
                            sharedPrefManager.writeString("email", email);
                        } else {
                            sharedPrefManager.removeKey("email");
                        }
                        Intent intent = new Intent(LogIn.this, HomeActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(LogIn.this, "Invalid email or password", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LogIn.this, "Invalid email or password", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
