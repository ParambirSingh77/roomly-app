package com.example.roomly;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

// My bookings tab: lists the user's bookings and lets them cancel.
public class MyBookingsFragment extends Fragment {

    private BookingAdapter adapter;
    private TextView txtEmpty;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_bookings, container, false);

        RecyclerView listBookings = view.findViewById(R.id.listBookings);
        txtEmpty = view.findViewById(R.id.txtEmpty);

        adapter = new BookingAdapter(booking -> askToCancel(booking));
        int columns = getResources().getInteger(R.integer.list_columns);
        listBookings.setLayoutManager(new GridLayoutManager(requireContext(), columns));
        listBookings.setAdapter(adapter);

        refreshList();
        return view;
    }

    private void refreshList() {
        ArrayList<Booking> mine = BookingStore.getMyBookings();
        adapter.setBookings(mine);
        if (mine.isEmpty()) {
            txtEmpty.setVisibility(View.VISIBLE);
        } else {
            txtEmpty.setVisibility(View.GONE);
        }
    }

    // Ask first, so a booking is not cancelled by a wrong tap
    private void askToCancel(final Booking booking) {
        String details = booking.getRoom() + ", "
                + getString(R.string.when_format, booking.getDate(), booking.getTime());
        new MaterialAlertDialogBuilder(requireContext())
                .setTitle(R.string.dialog_cancel_title)
                .setMessage(details)
                .setPositiveButton(R.string.dialog_cancel_yes, (dialog, which) -> doCancel(booking))
                .setNegativeButton(R.string.dialog_keep, null)
                .show();
    }

    private void doCancel(Booking booking) {
        boolean wasConfirmed = booking.getStatus().equals(Booking.CONFIRMED);
        BookingStore.cancel(booking);
        refreshList();
        if (wasConfirmed) {
            Snackbar.make(requireView(), R.string.msg_cancel_requested, Snackbar.LENGTH_LONG).show();
        } else {
            Snackbar.make(requireView(), R.string.msg_cancelled, Snackbar.LENGTH_LONG).show();
        }
    }
}
