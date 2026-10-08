package com.example.roomly;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

// Login screen. For now any username and password is accepted.
public class LoginActivity extends AppCompatActivity {

    private TextInputLayout layoutUsername;
    private TextInputLayout layoutPassword;
    private TextInputEditText etUsername;
    private TextInputEditText etPassword;
    private MaterialButtonToggleGroup toggleRole;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        layoutUsername = findViewById(R.id.layoutUsername);
        layoutPassword = findViewById(R.id.layoutPassword);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        toggleRole = findViewById(R.id.toggleRole);
        MaterialButton btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(v -> tryLogin());
    }

    private void tryLogin() {
        layoutUsername.setError(null);
        layoutPassword.setError(null);

        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString();

        // Check both fields so the user sees every problem at once
        boolean valid = true;
        if (username.isEmpty()) {
            layoutUsername.setError(getString(R.string.error_username));
            valid = false;
        }
        if (password.isEmpty()) {
            layoutPassword.setError(getString(R.string.error_password));
            valid = false;
        }
        if (!valid) {
            return;
        }

        // Only the Employee screens exist at this stage
        if (toggleRole.getCheckedButtonId() == R.id.btnManager) {
            Snackbar.make(findViewById(R.id.btnLogin), R.string.manager_later, Snackbar.LENGTH_LONG).show();
            return;
        }

        BookingStore.setCurrentUser(username);
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
