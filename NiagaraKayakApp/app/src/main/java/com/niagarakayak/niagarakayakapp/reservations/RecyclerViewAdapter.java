package com.niagarakayak.niagarakayakapp.reservations;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.niagarakayak.niagarakayakapp.R;
import com.niagarakayak.niagarakayakapp.model.Reservation;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ReservationViewHolder> {

    private ArrayList<Reservation> reservations;

    public RecyclerViewAdapter(ArrayList<Reservation> reservations) {
        this.reservations = reservations;
    }

    @Override
    public ReservationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reservation_card, parent, false);
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
        private ImageView weatherPic;
        private TextView weatherDetails;
        private TextView reservationDate;
        private TextView reservationWeatherDetails;
        private TextView reservationNumberLocations;
        private TextView reservationGroupDetails;

        public ReservationViewHolder(View itemView) {
            super(itemView);
            weatherPic = (ImageView) itemView.findViewById(R.id.reservation_weather_pic);
            weatherDetails = (TextView) itemView.findViewById(R.id.reservation_weather_details);
            reservationDate = (TextView) itemView.findViewById(R.id.reservation_date);
            reservationWeatherDetails = (TextView) itemView.findViewById(R.id.reservation_weather_details);
            reservationNumberLocations = (TextView) itemView.findViewById(R.id.reservation_number_location);
            reservationGroupDetails = (TextView) itemView.findViewById(R.id.reservation_group_details);
        }

        public void bindViews(Reservation reservation, int position) {
            reservationDate.setText(reservation.getDate());
            reservationNumberLocations.setText(position + " - " + reservation.getLocation());
            int count = reservation.getAdults() + reservation.getChildren();
            reservationGroupDetails.setText("Group of " + count + "; " + reservation.getAdults() + " adults, " + reservation.getChildren() + " children");
        }

    }

}