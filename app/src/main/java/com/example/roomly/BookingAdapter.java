package com.example.roomly;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

// Shows the user's bookings. The status tag changes colour and text with the status.
public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.BookingHolder> {

    public interface OnCancelClickListener {
        void onCancelClick(Booking booking);
    }

    private final ArrayList<Booking> bookings = new ArrayList<>();
    private final OnCancelClickListener listener;

    public BookingAdapter(OnCancelClickListener listener) {
        this.listener = listener;
    }

    public void setBookings(List<Booking> newBookings) {
        bookings.clear();
        bookings.addAll(newBookings);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BookingHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_booking, parent, false);
        return new BookingHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookingHolder holder, int position) {
        final Booking booking = bookings.get(position);
        Context context = holder.itemView.getContext();

        holder.txtRoom.setText(booking.getRoom());
        holder.txtWhen.setText(context.getString(R.string.when_format, booking.getDate(), booking.getTime()));

        if (booking.getStatus().equals(Booking.CONFIRMED)) {
            holder.txtStatus.setText(R.string.status_confirmed);
            holder.txtStatus.setBackgroundResource(R.drawable.tag_free);
            holder.txtStatus.setTextColor(ContextCompat.getColor(context, R.color.free_text));
        } else if (booking.getStatus().equals(Booking.PENDING)) {
            holder.txtStatus.setText(R.string.status_pending);
            holder.txtStatus.setBackgroundResource(R.drawable.tag_wait);
            holder.txtStatus.setTextColor(ContextCompat.getColor(context, R.color.wait_text));
        } else {
            holder.txtStatus.setText(R.string.status_cancel);
            holder.txtStatus.setBackgroundResource(R.drawable.tag_wait);
            holder.txtStatus.setTextColor(ContextCompat.getColor(context, R.color.wait_text));
        }

        // The note under a pending request explains who reviews it
        if (booking.getStatus().equals(Booking.PENDING)) {
            holder.txtNote.setVisibility(View.VISIBLE);
        } else {
            holder.txtNote.setVisibility(View.GONE);
        }

        // No Cancel button once a cancellation has been requested
        if (booking.getStatus().equals(Booking.CANCEL_REQUESTED)) {
            holder.btnCancel.setVisibility(View.GONE);
        } else {
            holder.btnCancel.setVisibility(View.VISIBLE);
            holder.btnCancel.setContentDescription(context.getString(R.string.desc_cancel, booking.getRoom()));
            holder.btnCancel.setOnClickListener(v -> listener.onCancelClick(booking));
        }
    }

    @Override
    public int getItemCount() {
        return bookings.size();
    }

    static class BookingHolder extends RecyclerView.ViewHolder {
        TextView txtRoom;
        TextView txtStatus;
        TextView txtWhen;
        TextView txtNote;
        MaterialButton btnCancel;

        BookingHolder(@NonNull View itemView) {
            super(itemView);
            txtRoom = itemView.findViewById(R.id.txtRoom);
            txtStatus = itemView.findViewById(R.id.txtStatus);
            txtWhen = itemView.findViewById(R.id.txtWhen);
            txtNote = itemView.findViewById(R.id.txtNote);
            btnCancel = itemView.findViewById(R.id.btnCancel);
        }
    }
}
