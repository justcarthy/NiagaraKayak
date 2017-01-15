package com.niagarakayak.niagarakayakapp.reservation_detail;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.niagarakayak.niagarakayakapp.R;

public class ReservationDetailActivity extends AppCompatActivity {

    private ImageView mLocationImageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_detail);
        CoordinatorLayout root = (CoordinatorLayout) findViewById(R.id.root);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView timeText = (TextView) findViewById(R.id.time_text);
        TextView dateText = (TextView) findViewById(R.id.date_text);
        Button directionBtn = (Button) findViewById(R.id.btn_directions);
        RelativeLayout adultContainer = (RelativeLayout) findViewById(R.id.adult_container);
        RelativeLayout childContainer = (RelativeLayout) findViewById(R.id.child_container);
        TextView adultText = (TextView) adultContainer.findViewById(R.id.adult_text);
        TextView childText = (TextView) childContainer.findViewById(R.id.child_text);
        ImageView statusImage = (ImageView) findViewById(R.id.status_icon);
        TextView statusText = (TextView) findViewById(R.id.status_text);
        TextView statusLabel = (TextView) findViewById(R.id.status_label);
        mLocationImageView = (ImageView) findViewById(R.id.img_location);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
        Intent i = getIntent();
        Bundle intentBundle = i.getBundleExtra("reservation");
        final String location = intentBundle.getString("location");
        String time = intentBundle.getString("time");
        String date = intentBundle.getString("date");
        int adults = intentBundle.getInt("adults");
        int children = intentBundle.getInt("children");
        boolean confirmed = intentBundle.getBoolean("confirmedStatus");

        // Assume confirmed by default.
        if (!confirmed) {
            int UNCONFIRMED_COLOR = Color.argb(255, 179, 62, 62);
            statusImage.setColorFilter(UNCONFIRMED_COLOR);
            statusText.setText(R.string.status_text);
            statusText.setTextColor(UNCONFIRMED_COLOR);
            statusLabel.setTextColor(UNCONFIRMED_COLOR);
        }

        if (adults == 0) {
            root.removeView(adultContainer);
        } else {
            adultContainer.setVisibility(View.VISIBLE);
            adultText.setText(String.valueOf(adults));
        }

        if (children == 0) {
            root.removeView(childContainer);
        } else {
            childContainer.setVisibility(View.VISIBLE);
            childText.setText(String.valueOf(children));
        }

        timeText.setText(time);
        dateText.setText(date);


        directionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri gmmIntentUri = Uri.parse("google.navigation:q=" + location + ",+Ontario+Canada");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });


        setTitle(location);
        setToolbarImage(location);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.reservation_detail_menu, menu);
        MenuItem deleteReservationItem = menu.getItem(0);
        deleteReservationItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                showDeleteDialog();
                return true;
            }
        });
        return true;
    }

    private void showDeleteDialog() {
        new MaterialDialog.Builder(this)
                .title("Please call us to cancel!")
                .content(R.string.delete_reservation_dialog)
                .positiveText("Call")
                .negativeText("Nevermind")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        String number = "289-654-2562";
                        Intent dialIntent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", number, null));
                        startActivity(dialIntent);
                    }
                })
                .show();
    }

    private void setToolbarImage(String location) {
        location = location.toLowerCase();
        switch (location) {
            case "charles daley park": {
                mLocationImageView.setImageDrawable(getDrawable(R.drawable.charles_daley_park));
                break;
            }

            case "queenston": {
                mLocationImageView.setImageDrawable(getDrawable(R.drawable.queenston));
                break;
            }

            case "niagara-on-the-lake": {
                mLocationImageView.setImageDrawable(getDrawable(R.drawable.notl));
                break;
            }
        }
    }
}
