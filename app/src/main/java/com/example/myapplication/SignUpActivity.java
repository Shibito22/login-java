package com.example.myapplication;

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

public class SignUpActivity extends AppCompatActivity {

    private EditText emailEditText, usernameEditText, passwordEditText, confirmPasswordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.signUpMain), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        emailEditText = findViewById(R.id.emailEditText);
        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText);
        Button registerButton = findViewById(R.id.registerButton);

        if (registerButton != null) {
            registerButton.setOnClickListener(v -> registerUser());
        }

        TextView loginLinkText = findViewById(R.id.loginLinkText);
        if (loginLinkText != null) {
            loginLinkText.setOnClickListener(v -> finish());
        }
    }

    private void registerUser() {
        String email = emailEditText.getText() != null ? emailEditText.getText().toString().trim() : "";
        String username = usernameEditText.getText() != null ? usernameEditText.getText().toString().trim() : "";
        String password = passwordEditText.getText() != null ? passwordEditText.getText().toString() : "";
        String confirmPassword = confirmPasswordEditText.getText() != null ? confirmPasswordEditText.getText().toString() : "";

        if (TextUtils.isEmpty(email)) {
            emailEditText.setError("Please enter your email");
            emailEditText.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(username)) {
            usernameEditText.setError("Please enter your username");
            usernameEditText.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            passwordEditText.setError("Please enter a password");
            passwordEditText.requestFocus();
            return;
        }

        if (!password.equals(confirmPassword)) {
            confirmPasswordEditText.setError("Passwords do not match");
            confirmPasswordEditText.requestFocus();
            return;
        }

        // Save credentials to SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("email", email);
        editor.putString("username", username);
        editor.putString("password", password);
        editor.apply();

        Toast.makeText(this, "Registration Successful! Please Log In.", Toast.LENGTH_SHORT).show();
        finish();
    }
}