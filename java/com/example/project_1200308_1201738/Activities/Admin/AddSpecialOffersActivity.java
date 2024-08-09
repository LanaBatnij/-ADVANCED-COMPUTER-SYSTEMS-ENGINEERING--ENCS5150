package com.example.project_1200308_1201738.Activities.Admin;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.example.project_1200308_1201738.DataBases.AdminDataBase;
import com.example.project_1200308_1201738.R;

public class AddSpecialOffersActivity extends AppCompatActivity {

    private EditText editTextPizzaTypes, editTextSizes, editTextOfferPeriod, editTextTotalPrice;
    private AdminDataBase adminDataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_special_offers);

        editTextPizzaTypes = findViewById(R.id.editTextPizzaTypes);
        editTextSizes = findViewById(R.id.editTextSizes);
        editTextOfferPeriod = findViewById(R.id.editTextOfferPeriod);
        editTextTotalPrice = findViewById(R.id.editTextTotalPrice);
        Button buttonAddOffer = findViewById(R.id.buttonAddOffer);

        adminDataBase = new AdminDataBase(this,"admin_db",null,1);

        buttonAddOffer.setOnClickListener(v -> addSpecialOffer());
    }

    private void addSpecialOffer() {
        String pizzaTypes = editTextPizzaTypes.getText().toString().trim();
        String sizes = editTextSizes.getText().toString().trim();
        String offerPeriod = editTextOfferPeriod.getText().toString().trim();
        String totalPriceStr = editTextTotalPrice.getText().toString().trim();

        if (pizzaTypes.isEmpty() || sizes.isEmpty() || offerPeriod.isEmpty() || totalPriceStr.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        double totalPrice;
        try {
            totalPrice = Double.parseDouble(totalPriceStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid total price", Toast.LENGTH_SHORT).show();
            return;
        }

        adminDataBase.addSpecialOffer(pizzaTypes, sizes, offerPeriod, totalPrice);
        Toast.makeText(this, "Special offer added successfully", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void sendNewOfferNotification(String offerDetails) {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        String channelId = "special_offers_channel";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, "Special Offers", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.baseline_bakery_dining_24)
                .setContentTitle("New Special Offer")
                .setContentText(offerDetails)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        notificationManager.notify(1, builder.build());
    }
}
