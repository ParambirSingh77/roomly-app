package com.example.roomly;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

// Rooms tab: the employee picks a date and time and sees only the free rooms.
public class RoomsFragment extends Fragment {

    private String selectedDate = BookingStore.DATES[0];
    private String selectedTime = BookingStore.TIMES[1];

    private RoomAdapter adapter;
    private TextView txtEmpty;

    // Opens the request screen and waits for the result
    private final ActivityResultLauncher<Intent> requestLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    // Tell MainActivity to open My bookings
                    getParentFragmentManager().setFragmentResult("requestSent", new Bundle());
                }
            });

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rooms, container, false);

        // Keep the chosen date and time after the screen rotates
        if (savedInstanceState != null) {
            selectedDate = savedInstanceState.getString("date", selectedDate);
            selectedTime = savedInstanceState.getString("time", selectedTime);
        }

        AutoCompleteTextView dropDate = view.findViewById(R.id.dropDate);
        AutoCompleteTextView dropTime = view.findViewById(R.id.dropTime);
        RecyclerView listRooms = view.findViewById(R.id.listRooms);
        txtEmpty = view.findViewById(R.id.txtEmpty);

        dropDate.setAdapter(new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_list_item_1, BookingStore.DATES));
        dropTime.setAdapter(new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_list_item_1, BookingStore.TIMES));
        dropDate.setText(selectedDate, false);
        dropTime.setText(selectedTime, false);

        dropDate.setOnItemClickListener((parent, v, position, id) -> {
            selectedDate = BookingStore.DATES[position];
            refreshList();
        });
        dropTime.setOnItemClickListener((parent, v, position, id) -> {
            selectedTime = BookingStore.TIMES[position];
            refreshList();
        });

        adapter = new RoomAdapter(room -> openRequest(room));
        int columns = getResources().getInteger(R.integer.list_columns);
        listRooms.setLayoutManager(new GridLayoutManager(requireContext(), columns));
        listRooms.setAdapter(adapter);

        refreshList();
        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("date", selectedDate);
        outState.putString("time", selectedTime);
    }

    private void refreshList() {
        ArrayList<String> free = BookingStore.getFreeRooms(selectedDate, selectedTime);
        adapter.setRooms(free);
        if (free.isEmpty()) {
            txtEmpty.setVisibility(View.VISIBLE);
        } else {
            txtEmpty.setVisibility(View.GONE);
        }
    }

    private void openRequest(String room) {
        Intent intent = new Intent(requireContext(), RequestActivity.class);
        intent.putExtra(RequestActivity.EXTRA_ROOM, room);
        intent.putExtra(RequestActivity.EXTRA_DATE, selectedDate);
        intent.putExtra(RequestActivity.EXTRA_TIME, selectedTime);
        requestLauncher.launch(intent);
    }
}
