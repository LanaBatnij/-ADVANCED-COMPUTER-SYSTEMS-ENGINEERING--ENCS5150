package com.example.project_1200308_1201738.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.project_1200308_1201738.Activities.User.LogIn;
import com.example.project_1200308_1201738.DataBases.DataBaseHelper;
import com.example.project_1200308_1201738.Models.User;
import com.example.project_1200308_1201738.R;

public class ProfileFragment extends Fragment {

    private EditText editTextFirstName, editTextLastName, editTextPhone, editTextPassword;
    private DataBaseHelper dataBaseHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        editTextFirstName = view.findViewById(R.id.editTextFirstName);
        editTextLastName = view.findViewById(R.id.editTextLastName);
        editTextPhone = view.findViewById(R.id.editTextPhone);
        editTextPassword = view.findViewById(R.id.editTextPassword);
        Button buttonSaveChanges = view.findViewById(R.id.buttonSaveChanges);
        Button buttonLogout = view.findViewById(R.id.buttonLogout);

        dataBaseHelper = new DataBaseHelper(getContext());

        // Load user information (assuming email is used to identify the user)
        String userEmail = "user@example.com"; // Replace with actual user email
        loadUserInfo(userEmail);

        buttonSaveChanges.setOnClickListener(v -> {
            String firstName = editTextFirstName.getText().toString().trim();
            String lastName = editTextLastName.getText().toString().trim();
            String phone = editTextPhone.getText().toString().trim();
            String password = editTextPassword.getText().toString().trim();

            if (validateInput(firstName, lastName, phone, password)) {
                updateUserInfo(userEmail, firstName, lastName, phone, password);
            }
        });

        buttonLogout.setOnClickListener(v -> logout());

        return view;
    }

    private void loadUserInfo(String email) {
        User user = dataBaseHelper.getUserByEmail(email);
        if (user != null) {
            editTextFirstName.setText(user.getFirstName());
            editTextLastName.setText(user.getLastName());
            editTextPhone.setText(user.getPhoneNumber());
            editTextPassword.setText(user.getPassword()); // Consider using a secure method to handle passwords
        } else {
            Toast.makeText(getContext(), "User not found", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean validateInput(String firstName, String lastName, String phone, String password) {
        if (TextUtils.isEmpty(firstName) || TextUtils.isEmpty(lastName) || TextUtils.isEmpty(phone) || TextUtils.isEmpty(password)) {
            Toast.makeText(getContext(), "All fields are required", Toast.LENGTH_SHORT).show();
            return false;
        }
        // Add more validation if needed (e.g., regex for phone, password strength)
        return true;
    }

    private void updateUserInfo(String email, String firstName, String lastName, String phone, String password) {
        boolean updated = dataBaseHelper.updateUser(email, firstName, lastName, phone, password);
        if (updated) {
            Toast.makeText(getContext(), "Profile updated successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "Failed to update profile", Toast.LENGTH_SHORT).show();
        }
    }

    private void logout() {
        Intent intent = new Intent(getActivity(), LogIn.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
