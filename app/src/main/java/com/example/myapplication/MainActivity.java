package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private EditText emailOrUsernameEditText, passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            float density = getResources().getDisplayMetrics().density;
            int padding24 = (int) (24 * density);
            v.setPadding(systemBars.left + padding24, systemBars.top + padding24, systemBars.right + padding24, systemBars.bottom + padding24);
            return insets;
        });

        emailOrUsernameEditText = findViewById(R.id.emailOrUsernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        Button loginButton = findViewById(R.id.loginButton);

        if (loginButton != null) {
            loginButton.setOnClickListener(v -> loginUser());
        }

        TextView signUpLinkText = findViewById(R.id.signUpLinkText);
        if (signUpLinkText != null) {
            signUpLinkText.setOnClickListener(v -> {
                Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(intent);
            });
        }
    }

    private void loginUser() {
        String inputIdentifier = emailOrUsernameEditText.getText() != null ? emailOrUsernameEditText.getText().toString().trim() : "";
        String inputPassword = passwordEditText.getText() != null ? passwordEditText.getText().toString() : "";

        if (TextUtils.isEmpty(inputIdentifier)) {
            emailOrUsernameEditText.setError("Please enter email or username");
            emailOrUsernameEditText.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(inputPassword)) {
            passwordEditText.setError("Please enter password");
            passwordEditText.requestFocus();
            return;
        }

        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String savedEmail = sharedPreferences.getString("email", "");
        String savedUsername = sharedPreferences.getString("username", "");
        String savedPassword = sharedPreferences.getString("password", "");

        if (TextUtils.isEmpty(savedEmail) && TextUtils.isEmpty(savedUsername)) {
            Toast.makeText(this, "No registered account found. Please sign up first.", Toast.LENGTH_LONG).show();
            return;
        }

        boolean isIdentifierValid = inputIdentifier.equalsIgnoreCase(savedEmail) || inputIdentifier.equalsIgnoreCase(savedUsername);
        boolean isPasswordValid = Objects.equals(inputPassword, savedPassword);

        if (isIdentifierValid && isPasswordValid) {
            Toast.makeText(this, "Login Successful!", Toast.LENGTH_SHORT).show();
            passwordEditText.setText("");
            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Invalid Email/Username or Password", Toast.LENGTH_SHORT).show();
        }
    }
}