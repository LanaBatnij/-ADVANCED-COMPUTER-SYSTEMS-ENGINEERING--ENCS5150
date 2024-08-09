package com.example.project_1200308_1201738.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.project_1200308_1201738.Fragments.FavoritesFragment;
import com.example.project_1200308_1201738.Fragments.OrderFragment;
import com.example.project_1200308_1201738.Fragments.PizzaMenuFragment;
import com.example.project_1200308_1201738.Fragments.ProfileFragment;
import com.example.project_1200308_1201738.Fragments.SpecialsFragment;
import com.example.project_1200308_1201738.R;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Button buttonMenuPizza = findViewById(R.id.buttonmenuPizza);
        Button buttonSpecial = findViewById(R.id.buttonSpecial);
        Button buttonProfile = findViewById(R.id.buttonProfile);
        Button buttonYourOrders = findViewById(R.id.buttoYourOrders);
        Button buttonFavorites = findViewById(R.id.buttonFav);
        Button buttonContactUs = findViewById(R.id.buttonContactUs);

        buttonMenuPizza.setOnClickListener(v -> replaceFragment(new PizzaMenuFragment()));
        buttonSpecial.setOnClickListener(v -> replaceFragment(new SpecialsFragment()));
        buttonProfile.setOnClickListener(v -> replaceFragment(new ProfileFragment()));
        buttonYourOrders.setOnClickListener(v -> replaceFragment(new OrderFragment()));
        buttonFavorites.setOnClickListener(v -> replaceFragment(new FavoritesFragment()));
        buttonContactUs.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, ContactUsActivity.class);
            startActivity(intent);
        });
    }

    private void replaceFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainerView, fragment)
                .addToBackStack(null)
                .commit();
    }
}
