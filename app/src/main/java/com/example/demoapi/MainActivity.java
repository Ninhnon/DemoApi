package com.example.demoapi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.demoapi.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    private RegistrationApiClient registrationApiClient;
    private AuthenticationApiClient authenticationApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        registrationApiClient = new RegistrationApiClient();

        // Register button click listener
        binding.registerButton.setOnClickListener(v -> {
            if(validateData(binding.emailRegister.getText().toString(),binding.usernameRegister.getText().toString(), binding.passwordRegister.getText().toString())) {
                // Call the registerUser method
                registrationApiClient.registerUser(binding.emailRegister.getText().toString(), binding.usernameRegister.getText().toString(), binding.passwordRegister.getText().toString(), MainActivity.this);
            }
        });
        authenticationApiClient = new AuthenticationApiClient();
        binding.authenticateButton.setOnClickListener(v -> {
            if(validateData("demo123@gmail.com",binding.usernameAuthentication.getText().toString(), binding.passwordAuthentication.getText().toString())) {
                // Call the registerUser method
                authenticationApiClient.authenticateUser(binding.usernameAuthentication.getText().toString(), binding.passwordAuthentication.getText().toString(), MainActivity.this);
            }
        });
    }
    boolean validateData(String email,String userName, String password) {

        /* Check empty */
        if (email.isEmpty()) {
            Toast.makeText(MainActivity.this, "You have not entered email", Toast.LENGTH_LONG).show();
            return false;
        }
        if (userName.isEmpty()) {
            Toast.makeText(MainActivity.this, "You have not entered username", Toast.LENGTH_LONG).show();
            return false;
        }
        if (password.isEmpty()) {
            Toast.makeText(MainActivity.this, "You have not entered password", Toast.LENGTH_LONG).show();
            return false;
        }

        /* Check valid */
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(MainActivity.this, "Email is invalid", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}