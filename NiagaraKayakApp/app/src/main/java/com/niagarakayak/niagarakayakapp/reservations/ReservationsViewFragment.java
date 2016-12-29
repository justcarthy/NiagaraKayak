package com.niagarakayak.niagarakayakapp.reservations;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.niagarakayak.niagarakayakapp.R;
import com.niagarakayak.niagarakayakapp.model.Reservation;

import java.util.ArrayList;

public class ReservationsViewFragment extends Fragment implements ReservationsContract.View {

    private ReservationsContract.Presenter mPresenter;
    private RecyclerView reservationList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root  = inflater.inflate(R.layout.fragment_reservation, container, false);
        reservationList = (RecyclerView) root.findViewById(R.id.reservation_list);
        reservationList.setLayoutManager(new LinearLayoutManager(root.getContext()));
        reservationList.setAdapter(new RecyclerViewAdapter(new ArrayList<Reservation>())); // MARK
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void setPresenter(ReservationsContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showReservations(ArrayList<Reservation> reservations) {
       reservationList.setAdapter(new RecyclerViewAdapter(reservations));
    }

    public static ReservationsViewFragment newInstance() {
        Bundle args = new Bundle();
        ReservationsViewFragment fragment = new ReservationsViewFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
