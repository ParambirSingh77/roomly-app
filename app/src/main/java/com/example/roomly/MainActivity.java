package com.example.roomly;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;

// Holds the toolbar, the bottom navigation and the two tabs (Rooms and My bookings).
public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setSubtitle(BookingStore.getCurrentUser());

        bottomNav = findViewById(R.id.bottomNav);
        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_rooms) {
                showFragment(new RoomsFragment());
                return true;
            } else if (id == R.id.nav_bookings) {
                showFragment(new MyBookingsFragment());
                return true;
            } else if (id == R.id.nav_logout) {
                askToLogout();
                return false;
            }
            return false;
        });

        // After a request is sent, show the My bookings tab with a message
        getSupportFragmentManager().setFragmentResultListener("requestSent", this,
                (requestKey, result) -> {
                    bottomNav.setSelectedItemId(R.id.nav_bookings);
                    Snackbar.make(findViewById(R.id.fragmentContainer),
                            R.string.request_sent, Snackbar.LENGTH_LONG).show();
                });

        // Start on the Rooms tab (not after a rotation, because the fragment is restored)
        if (savedInstanceState == null) {
            showFragment(new RoomsFragment());
        }
    }

    private void showFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .commit();
    }

    private void askToLogout() {
        new MaterialAlertDialogBuilder(this)
                .setTitle(R.string.dialog_logout_title)
                .setPositiveButton(R.string.dialog_logout_yes, (dialog, which) -> {
                    BookingStore.logout();
                    Intent intent = new Intent(this, LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                })
                .setNegativeButton(R.string.dialog_keep, null)
                .show();
    }
}
