package com.example.androiduidesignlab;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CustomerReservationsAdapter extends RecyclerView.Adapter<CustomerReservationsAdapter.ReservationViewHolder> {

    private List<Reservation> reservations;
    private final OnReservationListener onReservationListener;

    public interface OnReservationListener {
        void onEditClick(long id, String date, String time, int guests, String details);
        void onCancelClick(long id);
    }

    public CustomerReservationsAdapter(List<Reservation> reservations, OnReservationListener onReservationListener) {
        this.reservations = reservations;
        this.onReservationListener = onReservationListener;
    }

    @NonNull
    @Override
    public ReservationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.customer_reservation_card_item, parent, false);
        return new ReservationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReservationViewHolder holder, int position) {
        Reservation reservation = reservations.get(position);
        
        holder.tvReservationDetails.setText(reservation.getFullDescription());

        holder.btnEditReservation.setOnClickListener(v -> {
            if (onReservationListener != null) {
                onReservationListener.onEditClick(reservation.getId(), reservation.getDate(), reservation.getTime(), reservation.getGuests(), reservation.getReservationDetails());
            }
        });

        holder.btnCancelReservation.setOnClickListener(v -> {
            if (onReservationListener != null) {
                onReservationListener.onCancelClick(reservation.getId());
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
        Button btnCancelReservation;

        public ReservationViewHolder(@NonNull View itemView) {
            super(itemView);
            tvReservationDetails = itemView.findViewById(R.id.tvReservationDetails);
            btnEditReservation = itemView.findViewById(R.id.btnEditReservation);
            btnCancelReservation = itemView.findViewById(R.id.btnCancelReservation);
        }
    }
}
