package com.niagarakayak.niagarakayakapp.reservations;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import com.niagarakayak.niagarakayakapp.R;
import com.niagarakayak.niagarakayakapp.model.Reservation;

import java.util.ArrayList;

public class ReservationCardAdapter extends RecyclerView.Adapter<ReservationCardAdapter.ReservationViewHolder> {

    private ArrayList<Reservation> reservations;

    public ReservationCardAdapter(ArrayList<Reservation> reservations) {
        this.reservations = reservations;
    }

    @Override
    public ReservationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_reservation_card, parent, false);
        return new ReservationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReservationViewHolder holder, int position) {
        holder.bindViews(reservations.get(position), position);
    }

    @Override
    public int getItemCount() {
        return reservations.size();
    }

    public void updateReservationDate(ArrayList<Reservation> reservationData) {
        this.reservations = reservationData;
        notifyDataSetChanged();
    }

    class ReservationViewHolder extends RecyclerView.ViewHolder {
        private ImageButton confirmedIndicator;
        private TextView reservationDate;
        private TextView reservationNumberLocations;
        private TextView reservationGroupDetails;

        public ReservationViewHolder(View itemView) {
            super(itemView);
            confirmedIndicator = (ImageButton) itemView.findViewById(R.id.confirmed_indicator);
            reservationDate = (TextView) itemView.findViewById(R.id.reservation_date);
            reservationNumberLocations = (TextView) itemView.findViewById(R.id.reservation_number_location);
            reservationGroupDetails = (TextView) itemView.findViewById(R.id.reservation_group_details);
        }

        public void bindViews(Reservation reservation, int position) {
            reservationDate.setText(reservation.getDate());
            reservationNumberLocations.setText(position + " - " + reservation.getLocation());
            int count = reservation.getAdults() + reservation.getChildren();
            String adultMsg = reservation.getAdults() > 1 ? " adults " : " adult ";
            String childrenMsg = reservation.getChildren() > 1 ? " children" : " child";
            String reservationGroupMsg =
                    "Group of " + count + "; "
                            + reservation.getAdults() + adultMsg
                            + reservation.getChildren() + childrenMsg;


            if (reservation.isConfirmed()) {
                confirmedIndicator.setColorFilter(Color.argb(255, 165, 223, 131));
            } else {
                confirmedIndicator.setColorFilter(Color.argb(255, 179, 62, 62));
            }

            reservationGroupDetails.setText(reservationGroupMsg);
        }

    }

}