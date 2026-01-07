package com.example.androiduidesignlab;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ReservationsAdapter extends RecyclerView.Adapter<ReservationsAdapter.ReservationViewHolder> {

    private List<Reservation> reservations;
    private final OnReservationListener onReservationListener;

    public interface OnReservationListener {
        void onEditClick(long id, String details);
        void onRemoveClick(long id);
    }

    public ReservationsAdapter(List<Reservation> reservations, OnReservationListener onReservationListener) {
        this.reservations = reservations;
        this.onReservationListener = onReservationListener;
    }

    @NonNull
    @Override
    public ReservationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reservation_card_item, parent, false);
        return new ReservationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReservationViewHolder holder, int position) {
        Reservation reservation = reservations.get(position);
        holder.tvReservationDetails.setText(reservation.getReservationDetails());

        holder.btnEditReservation.setOnClickListener(v -> {
            if (onReservationListener != null) {
                onReservationListener.onEditClick(reservation.getId(), reservation.getReservationDetails());
            }
        });

        holder.btnRemoveReservation.setOnClickListener(v -> {
            if (onReservationListener != null) {
                onReservationListener.onRemoveClick(reservation.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return reservations.size();
    }

    public static class ReservationViewHolder extends RecyclerView.ViewHolder {
        TextView tvReservationDetails;
        Button btnEditReservation;
        Button btnRemoveReservation;

        public ReservationViewHolder(@NonNull View itemView) {
            super(itemView);
            tvReservationDetails = itemView.findViewById(R.id.tvReservationDetails);
            btnEditReservation = itemView.findViewById(R.id.btnEditReservation);
            btnRemoveReservation = itemView.findViewById(R.id.btnRemoveReservation);
        }
    }
}
