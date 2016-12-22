package com.niagarakayak.niagarakayakapp.home;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.niagarakayak.niagarakayakapp.R;
import com.niagarakayak.niagarakayakapp.util.SnackbarUtils;

import java.util.Objects;

public class HomeViewFragment extends Fragment implements HomeContract.View {

    private HomeContract.Presenter mPresenter;

    private TextView tweetLabel;
    private TextView tweetHandle;
    private TextView tweetDesc;
    private TextView tweetDate;
    private ImageView tweetProfileImage;

    private CardView mapsCard;
    private SupportMapFragment mapFragment;
    private TextView mapsLabel;
    private Bundle mBundle;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root  = inflater.inflate(R.layout.fragment_home, container, false);

        if (savedInstanceState != null) {
            mBundle = savedInstanceState;
        }

        tweetLabel = (TextView) root.findViewById(R.id.tweet_label_intro);
        tweetDesc = (TextView) root.findViewById(R.id.tweet_desc);
        tweetHandle = (TextView) root.findViewById(R.id.tweet_handle);
        tweetDate = (TextView) root.findViewById(R.id.tweet_date);
        tweetProfileImage = (ImageView) root.findViewById(R.id.tweet_pic);
        mapsCard = (CardView) root.findViewById(R.id.map_card);
        mapsLabel = (TextView) root.findViewById(R.id.map_card_label);
        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map_fragment);
        // Don't destroy the map fragment.
        mapFragment.setRetainInstance(true);
        return root;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putCharSequence("tweetLabel", tweetLabel.getText());
        outState.putCharSequence("tweetHandle", tweetHandle.getText());
        outState.putCharSequence("tweetDesc", tweetDesc.getText());
        outState.putCharSequence("tweetDate", tweetDate.getText());
        if (mapsCard.getVisibility() == View.VISIBLE) {
            outState.putBoolean("mapsIsVisible", true);
            outState.putCharSequence("mapsLabel", mapsLabel.getText());
        } else {
            outState.putBoolean("mapsIsVisible", false);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        setTweetImage(ContextCompat.getDrawable(getActivity(), R.drawable.nk_twitter_logo));

        if (mBundle == null) {
            mPresenter.start();
        } else {
            tweetLabel.setText(mBundle.getCharSequence("tweetLabel"));
            tweetDesc.setText(mBundle.getCharSequence("tweetDesc"));
            tweetHandle.setText(mBundle.getCharSequence("tweetHandle"));
            tweetDate.setText(mBundle.getCharSequence("tweetDate"));
            mapsLabel.setText(mBundle.getCharSequence("mapsLabel"));
            if (mBundle.getBoolean("mapsIsVisible")) {
                mapsCard.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void setPresenter(HomeContract.Presenter presenter) {
        mPresenter = Objects.requireNonNull(presenter);
    }

    @Override
    public void setTweetLabel(String label) {
        tweetLabel.setText(label);
    }

    @Override
    public void setTweetHandle(String handle) {
        tweetHandle.setText(handle);
    }

    @Override
    public void setTweetImage(Drawable image) {
        tweetProfileImage.setImageDrawable(image);
    }

    @Override
    public void setTweetDate(String stringDate) {
        tweetDate.setText(stringDate);
    }

    @Override
    public void setTweetDescription(String description) {
            tweetDesc.setText(description);
    }

    @Override
    public void showMapsLabel() {
        if (mapsLabel != null) {
            mapsLabel.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void setMapsLabel(String label) {
        mapsLabel.setText(label);
    }

    @Override
    public void showMapsCardWithCoords(final LatLng coords) {
        if (mapsCard != null) {
            mapsCard.setVisibility(View.VISIBLE);
            // Load the map asynchronously, fire the callback when ready.
            mapFragment.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap googleMap) {
                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(coords, 10));
                    MarkerOptions markerOptions = new MarkerOptions().position(coords);
                    googleMap.addMarker(markerOptions);
                    googleMap.getUiSettings().setScrollGesturesEnabled(false);
                }
            });
        }
    }

    @Override
    public void showSnackbarWithMessage(String message, int length, SnackbarUtils.SnackbarColor snackbarColor) {
        Snackbar sb = Snackbar.make(this.getView(), message, length);
        sb.getView().setBackgroundColor(SnackbarUtils.getBackgroundColor(getContext(), snackbarColor));
        sb.show();
    }

    public static HomeViewFragment newInstance() {
        Bundle args = new Bundle();
        HomeViewFragment homeViewFragment = new HomeViewFragment();
        homeViewFragment.setArguments(args);
        return homeViewFragment;
    }
}
