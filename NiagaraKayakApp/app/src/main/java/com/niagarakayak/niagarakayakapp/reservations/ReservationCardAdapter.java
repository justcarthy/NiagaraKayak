package com.niagarakayak.niagarakayakapp.reservations;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import com.niagarakayak.niagarakayakapp.R;
import com.niagarakayak.niagarakayakapp.model.Reservation;
import com.niagarakayak.niagarakayakapp.util.TimeUtils;

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

    public void updateData(ArrayList<Reservation> reservations) {
        this.reservations = reservations;
        notifyDataSetChanged();
    }

    public ArrayList<Reservation> getReservations() {
        return reservations;
    }

    @Override
    public void onBindViewHolder(ReservationViewHolder holder, int position) {
        holder.bindViews(reservations.get(position), position);
    }

    @Override
    public int getItemCount() {
        return reservations.size();
    }

    class ReservationViewHolder extends RecyclerView.ViewHolder {
        private ImageButton confirmedIndicator;
        private TextView confirmedText;
        private TextView reservationDate;
        private TextView reservationNumberLocations;
        private TextView reservationGroupDetails;
        private View itemView;

        public ReservationViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            confirmedIndicator = (ImageButton) itemView.findViewById(R.id.confirmed_indicator);
            confirmedText = (TextView) itemView.findViewById(R.id.confirmed_text);
            reservationDate = (TextView) itemView.findViewById(R.id.reservation_date);
            reservationNumberLocations = (TextView) itemView.findViewById(R.id.reservation_number_location);
            reservationGroupDetails = (TextView) itemView.findViewById(R.id.reservation_group_details);
        }

        public void bindViews(Reservation reservation, int position) {
            String twelveHrtime = TimeUtils.get12HrTime(reservation.getReservationTime());
            reservationDate.setText(reservation.getDate() + " @ " + twelveHrtime);
            reservationNumberLocations.setText("@ " + reservation.getLocation());
            int count = reservation.getAdults() + reservation.getChildren();

            String adultMsg = reservation.getAdults() > 1 ? " adults " : " adult ";
            String childrenMsg = reservation.getChildren() > 1 ? " children" : " child";
            String reservationGroupMsg =
                    "Group of " + count + "; "
                            + reservation.getAdults() + adultMsg
                            + reservation.getChildren() + childrenMsg;


            if (reservation.isConfirmed()) {
                // Light green
                int CONFIRMED_COLOR = Color.argb(255, 112, 171, 43);
                confirmedIndicator.setColorFilter(CONFIRMED_COLOR);
                confirmedText.setText("CONFIRMED");
                confirmedText.setTextColor(CONFIRMED_COLOR);
            } else {
                // Red
                int UNCONFIRMED_COLOR = Color.argb(255, 179, 62, 62);
                confirmedIndicator.setColorFilter(UNCONFIRMED_COLOR);
                confirmedText.setText("UNCONFIRMED");
                confirmedText.setTextColor(UNCONFIRMED_COLOR);
                // Light grey
                itemView.setBackgroundColor(Color.argb(255, 238, 238, 238));
            }

            reservationGroupDetails.setText(reservationGroupMsg);
        }

    }

}