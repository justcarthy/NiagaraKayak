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
import com.niagarakayak.niagarakayakapp.R;
import com.niagarakayak.niagarakayakapp.util.SnackbarUtils;

import java.util.Objects;

public class HomeViewFragment extends Fragment implements HomeContract.View {

    private HomeContract.Presenter mPresenter;

    private TextView tweetLabel;
    private TextView tweetHandle;
    private TextView tweetDesc;
    private ImageView tweetProfileImage;

    private CardView mapsCard;
    private TextView mapsLabel;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root  = inflater.inflate(R.layout.fragment_home, container, false);
        tweetLabel = (TextView) root.findViewById(R.id.tweet_label_intro);
        tweetDesc = (TextView) root.findViewById(R.id.tweet_desc);
        tweetHandle = (TextView) root.findViewById(R.id.tweet_handle);
        tweetProfileImage = (ImageView) root.findViewById(R.id.tweet_pic);
        mapsCard = (CardView) root.findViewById(R.id.map_card);
        mapsLabel = (TextView) root.findViewById(R.id.map_card_label);
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        setTweetImage(ContextCompat.getDrawable(getActivity(), R.drawable.nk_twitter_logo));
        mPresenter.start();
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
    public void showMapsCard() {
        if (mapsCard != null) {
            mapsCard.setVisibility(View.VISIBLE);
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
