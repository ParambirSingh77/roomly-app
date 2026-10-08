package com.example.roomly;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

// Shows the free rooms in a list. Each row has a Request button.
public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.RoomHolder> {

    public interface OnRequestClickListener {
        void onRequestClick(String room);
    }

    private final ArrayList<String> rooms = new ArrayList<>();
    private final OnRequestClickListener listener;

    public RoomAdapter(OnRequestClickListener listener) {
        this.listener = listener;
    }

    public void setRooms(List<String> newRooms) {
        rooms.clear();
        rooms.addAll(newRooms);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RoomHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_room, parent, false);
        return new RoomHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomHolder holder, int position) {
        final String room = rooms.get(position);
        holder.txtRoomName.setText(room);
        holder.btnRequest.setContentDescription(
                holder.itemView.getContext().getString(R.string.desc_request, room));
        holder.btnRequest.setOnClickListener(v -> listener.onRequestClick(room));
    }

    @Override
    public int getItemCount() {
        return rooms.size();
    }

    static class RoomHolder extends RecyclerView.ViewHolder {
        TextView txtRoomName;
        MaterialButton btnRequest;

        RoomHolder(@NonNull View itemView) {
            super(itemView);
            txtRoomName = itemView.findViewById(R.id.txtRoomName);
            btnRequest = itemView.findViewById(R.id.btnRequest);
        }
    }
}
