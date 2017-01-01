package com.niagarakayak.niagarakayakapp.add_reservations.steps;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.niagarakayak.niagarakayakapp.R;
import com.niagarakayak.niagarakayakapp.util.MapUtils;

public class Step2Fragment extends Fragment {

    private MapFragment mapFragment;
    private GoogleMap map;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_step2, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (map == null) {
            // Try to obtain the map from the MapFragment.
            mapFragment = (MapFragment) getChildFragmentManager().findFragmentById(R.id.map_fragment);
            mapFragment.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap googleMap) {
                    map = googleMap;
                    setUpMap();
                }
            });
        }
    }

    private void setUpMap() {
        LatLng charlesDaleyPark = MapUtils.getLocation("charles daley park");
        LatLng queenston = MapUtils.getLocation("queenston");
        map.addMarker(new MarkerOptions().position(charlesDaleyPark));
        map.addMarker(new MarkerOptions().position(queenston));
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(charlesDaleyPark, 4));
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    public static Step2Fragment newInstance() {
        Bundle args = new Bundle();
        Step2Fragment fragment = new Step2Fragment();
        fragment.setArguments(args);
        return fragment;
    }
}
