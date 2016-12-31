package com.niagarakayak.niagarakayakapp.reservations;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.niagarakayak.niagarakayakapp.R;
import com.niagarakayak.niagarakayakapp.model.Reservation;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ReservationsViewFragment extends Fragment implements ReservationsContract.View {

    private RecyclerView reservationList;
    private ReservationsContract.Presenter mPresenter;
    private Bundle mBundle;
    private SwipeRefreshLayout swipeToRefreshLayout;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root  = inflater.inflate(R.layout.fragment_reservation, container, false);

        if (savedInstanceState != null) {
            mBundle = savedInstanceState;
        }

        reservationList = (RecyclerView) root.findViewById(R.id.reservation_list);

        swipeToRefreshLayout = (SwipeRefreshLayout) root.findViewById(R.id.swipe_refresh_reservations_layout);
        int primaryColor = ContextCompat.getColor(getContext(), R.color.colorPrimary);
        int primaryDarkColor = ContextCompat.getColor(getContext(), R.color.colorPrimaryDark);
        swipeToRefreshLayout.setColorSchemeColors(primaryColor, primaryDarkColor);

        reservationList.setLayoutManager(new LinearLayoutManager(root.getContext()));
        reservationList.setAdapter(new ReservationCardAdapter(new ArrayList<Reservation>()));

        swipeToRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.loadReservations();
            }
        });

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mBundle == null) {
            mPresenter.start();
        } else {
            showReservations((ArrayList<Reservation>) mBundle.getSerializable("reservations"));
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        ReservationCardAdapter adapter = (ReservationCardAdapter) reservationList.getAdapter();
        outState.putSerializable("reservations", adapter.getReservations());
    }

    @Override
    public void setPresenter(ReservationsContract.Presenter presenter) {
        mPresenter = presenter;
    }


    @Override
    public void showReservations(ArrayList<Reservation> reservations) {
       reservationList.setAdapter(new ReservationCardAdapter(reservations));
    }

    @Override
    public void setRefreshing(boolean refresh) {
        swipeToRefreshLayout.setRefreshing(refresh);
    }

    public static ReservationsViewFragment newInstance() {
        Bundle args = new Bundle();
        ReservationsViewFragment fragment = new ReservationsViewFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
