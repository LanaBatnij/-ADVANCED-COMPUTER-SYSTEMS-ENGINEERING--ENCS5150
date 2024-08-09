// src/main/java/com/example/project_1200308_1201738/Activities/ContactUsActivity.java
package com.example.project_1200308_1201738.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project_1200308_1201738.R;

public class ContactUsActivity extends AppCompatActivity {

    private static final String PHONE_NUMBER = "0599000000";
    private static final String MAPS_URI = "geo:31.961013,35.190483?q=31.961013,35.190483(Advanced Pizza)";
    private static final String EMAIL_ADDRESS = "AdvancePizza@Pizza.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        Button callButton = findViewById(R.id.callButton);
        Button findUsButton = findViewById(R.id.findUsButton);
        Button emailButton = findViewById(R.id.emailButton);

        callButton.setOnClickListener(v -> {
            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            callIntent.setData(Uri.parse("tel:" + PHONE_NUMBER));
            startActivity(callIntent);
        });

        findUsButton.setOnClickListener(v -> {
            Intent mapIntent = new Intent(Intent.ACTION_VIEW);
            mapIntent.setData(Uri.parse(MAPS_URI));
            if (mapIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(mapIntent);
            }
        });

        emailButton.setOnClickListener(v -> {
            Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
            emailIntent.setData(Uri.parse("mailto:" + EMAIL_ADDRESS));
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Inquiry");
            if (emailIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(emailIntent);
            }
        });
    }
}
