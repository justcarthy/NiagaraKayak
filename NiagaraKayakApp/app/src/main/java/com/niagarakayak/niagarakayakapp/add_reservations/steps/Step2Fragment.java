package com.niagarakayak.niagarakayakapp.add_reservations.steps;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.internal.zzf;
import com.niagarakayak.niagarakayakapp.R;
import com.niagarakayak.niagarakayakapp.util.MapUtils;

import java.util.ArrayList;

public class Step2Fragment extends Fragment implements GoogleMap.OnMarkerClickListener {

    private MapFragment mapFragment;
    private GoogleMap map;
    private Marker selectedLaunchPoint;
    private Bundle mBundle;
    private ArrayList<Marker> allLaunchPoints;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mBundle = savedInstanceState;
        }

        return inflater.inflate(R.layout.fragment_step2, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (selectedLaunchPoint != null) {
            outState.putString("selectedID", selectedLaunchPoint.getId());
        }
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

    public String getLaunchPointText() {
        return selectedLaunchPoint == null ? "" : selectedLaunchPoint.getTitle();
    }

    private void setUpMap() {
        LatLng charlesDaleyPark = MapUtils.getLocation("charles daley park");
        LatLng queenston = MapUtils.getLocation("queenston");
        LatLng niagaraOnTheLake = MapUtils.getLocation("niagara on the lake");

        Marker charlesDaleyMarker = MapUtils.addMarkerToMap(map, charlesDaleyPark, "Charles Daley Park",
                "Start kayaking near Charles Daley Park!");
        Marker queenstonMarker = MapUtils.addMarkerToMap(map, queenston, "Queenston",
                "Start kayaking near Queenston!");
        Marker niagaraOnTheLakeMarker = MapUtils.addMarkerToMap(map, niagaraOnTheLake, "Niagara-on-the-lake",
                "Start kayaking near Niagara-on-the-lake!");

        map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(43.157376, -79.242507), 9));
        map.setOnMarkerClickListener(this);

        allLaunchPoints = new ArrayList<>();
        allLaunchPoints.add(charlesDaleyMarker);
        allLaunchPoints.add(queenstonMarker);
        allLaunchPoints.add(niagaraOnTheLakeMarker);

        if (mBundle != null && !mBundle.getString("selectedID", "").isEmpty()) {
            restoreSelectedLaunchPoint(mBundle.getString("selectedID", ""));
        }
    }

    public static Step2Fragment newInstance() {
        Bundle args = new Bundle();
        Step2Fragment fragment = new Step2Fragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        selectedLaunchPoint = marker;

        for (Marker launchPoint : allLaunchPoints) {
            if (launchPoint.getId().equals(selectedLaunchPoint.getId())) {
                launchPoint.setIcon((BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
            } else {
                launchPoint.setIcon((BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
            }
        }

        return false;
    }

    private void restoreSelectedLaunchPoint(String selectedMarkerId) {
        for (Marker launchPoint : allLaunchPoints) {
            if (selectedMarkerId.equals(launchPoint.getId())) {
                selectedLaunchPoint = launchPoint;
                selectedLaunchPoint.setIcon((BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
            } else {
                launchPoint.setIcon((BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
            }
        }
    }
}
