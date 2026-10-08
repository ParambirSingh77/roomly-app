package com.example.roomly;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;

// Shows the booking details and lets the employee send the request.
public class RequestActivity extends AppCompatActivity {

    public static final String EXTRA_ROOM = "room";
    public static final String EXTRA_DATE = "date";
    public static final String EXTRA_TIME = "time";

    private String room;
    private String date;
    private String time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        TextView txtRoom = findViewById(R.id.txtRoom);
        TextView txtWhen = findViewById(R.id.txtWhen);
        MaterialButton btnSend = findViewById(R.id.btnSend);
        MaterialButton btnBack = findViewById(R.id.btnBack);

        room = getIntent().getStringExtra(EXTRA_ROOM);
        date = getIntent().getStringExtra(EXTRA_DATE);
        time = getIntent().getStringExtra(EXTRA_TIME);

        txtRoom.setText(room);
        txtWhen.setText(getString(R.string.when_format, date, time));

        toolbar.setNavigationOnClickListener(v -> finish());
        btnBack.setOnClickListener(v -> finish());
        btnSend.setOnClickListener(v -> sendRequest());
    }

    private void sendRequest() {
        // Stop two bookings for the same room and time
        if (BookingStore.isTaken(room, date, time)) {
            Snackbar.make(findViewById(R.id.txtRoom), R.string.request_taken, Snackbar.LENGTH_LONG).show();
            return;
        }
        BookingStore.addRequest(room, date, time);
        setResult(RESULT_OK);
        finish();
    }
}
