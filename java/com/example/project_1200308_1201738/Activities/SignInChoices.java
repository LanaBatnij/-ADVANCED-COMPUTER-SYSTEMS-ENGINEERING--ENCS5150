package com.example.project_1200308_1201738.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.project_1200308_1201738.Activities.User.LogIn;
import com.example.project_1200308_1201738.R;
import com.example.project_1200308_1201738.Activities.Admin.SignInAsAdmin;

public class SignInChoices extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_choices);

        // Load the animation
        Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);

        // Find the ImageView and start the animation
        ImageView imageView = findViewById(R.id.imageView);
        imageView.startAnimation(fadeIn);

        Button signAsAdmin = findViewById(R.id.SignAsAdmin);
        Button signAsCustomer = findViewById(R.id.SignAsCustomer);

        signAsCustomer.setOnClickListener(v -> {
            Intent intent = new Intent(SignInChoices.this, LogIn_SignUpActivity.class);
            startActivity(intent);
            finish();
            Toast.makeText(SignInChoices.this, "Welcome Our Pretty Customer", Toast.LENGTH_SHORT).show();
        });

        signAsAdmin.setOnClickListener(v -> {
            Intent intent = new Intent(SignInChoices.this, SignInAsAdmin.class);
            startActivity(intent);
            finish();
            Toast.makeText(SignInChoices.this, "Welcome Our Pretty Admin", Toast.LENGTH_SHORT).show();
        });
    }
}
