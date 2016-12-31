package com.niagarakayak.niagarakayakapp.add_reservations;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.badoualy.stepperindicator.StepperIndicator;
import com.niagarakayak.niagarakayakapp.R;
import com.niagarakayak.niagarakayakapp.add_reservations.steps.VerifyDialog;
import com.niagarakayak.niagarakayakapp.model.Reservation;
import com.niagarakayak.niagarakayakapp.reservations.ReservationActivity;
import com.niagarakayak.niagarakayakapp.service.database.DataService;
import com.niagarakayak.niagarakayakapp.service.database.ReservationLocalDataService;
import com.niagarakayak.niagarakayakapp.service.reservation.ReservationAPIService;
import com.niagarakayak.niagarakayakapp.service.reservation.ReservationService;
import com.niagarakayak.niagarakayakapp.util.ActivityUtils;
import com.niagarakayak.niagarakayakapp.util.TimeUtils;

import static com.niagarakayak.niagarakayakapp.util.SnackbarUtils.*;

public class AddReservationsActivity extends AppCompatActivity implements View.OnClickListener {
    private View root;
    private StepperIndicator indicator;
    private StepViewPager stepPager;
    private Button submitButton;
    private ImageButton continueOrDoneButton;
    private ImageButton backButton;
    private StepPagerAdapter stepPagerAdapter;
    private Bundle mBundle;
    private Toolbar mToolbar;
    private ReservationAPIService reservationAPIService;
    private ReservationLocalDataService reservationLocalService;

    private String userEmail;
    private int currentStep;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mBundle = savedInstanceState;
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reservations);
        indicator = (StepperIndicator) findViewById(R.id.stepper);
        stepPager = (StepViewPager) findViewById(R.id.step_viewpager);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        continueOrDoneButton = (ImageButton) findViewById(R.id.continue_done_btn);
        submitButton = (Button) findViewById(R.id.submit_btn);
        backButton = (ImageButton) findViewById(R.id.back_btn);
        stepPagerAdapter = new StepPagerAdapter(getFragmentManager());
        stepPager.setAdapter(stepPagerAdapter);
        indicator.setViewPager(stepPager, true);
        indicator.setStepCount(2);
        continueOrDoneButton.setOnClickListener(this);
        backButton.setOnClickListener(this);
        submitButton.setOnClickListener(this);
        currentStep = 0;
        root = findViewById(android.R.id.content);
        reservationAPIService = new ReservationAPIService(getString(R.string.NK_API_KEY));
        reservationLocalService = new ReservationLocalDataService(this);
        this.userEmail = PreferenceManager.getDefaultSharedPreferences(this).getString("email", "");
        setToolbarTitle("Add Reservation");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("currentStep", currentStep);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (mBundle != null) {
            currentStep = mBundle.getInt("currentStep");
            if (currentStep > 0) {
                backButton.setVisibility(View.VISIBLE);
            }

            if (currentStep == 1) {
                continueOrDoneButton.setVisibility(View.INVISIBLE);
                submitButton.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.continue_done_btn: {
                // Validate here
                if (isValid(currentStep)) {
                    showNextPage();
                } else {
                    showToastWithMessage("One or more fields are blank!");
                }
                break;
            }

            case R.id.back_btn: {
                showPrevPage();
                break;
            }

            case R.id.submit_btn: {
                if (isValid(currentStep)) {
                    showVerifyDialog();
                } else {
                    showToastWithMessage("One or more fields are blank!");
                }
            }
        }
    }

    private void showVerifyDialog() {
        new VerifyDialog.Builder(this)
                .title("Thank you!")
                .customView(R.layout.dialog_verify, true)
                .positiveText("Got it")
                .dismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        sendEmailForReservation(getReservationFromFields());
                    }
                })
                .show();
    }

    private boolean isValid(int page) {
        switch (page) {
            case StepPagerAdapter.STEP_ONE:
                return !(getDateText().isEmpty() || getTimeText().isEmpty() || getHourText().isEmpty());
            case StepPagerAdapter.STEP_TWO:
                return !(getAdultText().isEmpty() || getChildText().isEmpty() || getLaunchText().isEmpty());
            default:
                return false;
        }
    }

    private Reservation getReservationFromFields() {
        return new Reservation(
                userEmail+System.currentTimeMillis(),
                userEmail,
                getDateText(),
                getTimeText(),
                convertHourText(getHourText()),
                Integer.parseInt(getSingleText()),
                Integer.parseInt(getTandemText()),
                getLaunchText(),
                Integer.parseInt(getAdultText()),
                Integer.parseInt(getChildText()),
                false
        );
    }

    private void sendEmailForReservation(final Reservation reservation) {
        reservationAPIService.postReservation(new ReservationService.PostCallback() {
            @Override
            public void onFailure(Exception e) {
                ActivityUtils.showSnackbarWithMessage(root, "Couldn't send reservation email.", LENGTH_LONGER, SnackbarColor.ERROR_COLOR);
            }

            @Override
            public void onSuccess() {
                // Save locally to the database
                saveToLocal(reservation);
                Intent i = new Intent(AddReservationsActivity.this, ReservationActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
            }
        }, reservation);
    }

    private int convertHourText(String hourText) {
        int toHours = 0;
        int unit = Integer.parseInt(hourText.split(" ")[0]);
        // Convert days to hours
        return hourText.contains("day") ? unit * 24 : unit;
    }

    private void saveToLocal(Reservation reservation) {
        reservationLocalService.addReservationLocal(new DataService.InsertCallback() {
            @Override
            public void onFailure(Exception e) {
                Log.d("ADD LOCAL", "onFailure: Failed to write to database");
            }

            @Override
            public void onSuccess() {
                Log.d("ADD LOCAL", "onSuccess: Successfully wrote to database");
            }
        }, reservation);
    }

    private String getDateText() {
        return ((TextInputEditText) findViewById(R.id.date_text)).getText().toString();
    }

    private String getTimeText() {
        String timeText = ((TextInputEditText) findViewById(R.id.time_text)).getText().toString();
        String result = TimeUtils.get24HrTime(timeText.split(" ")) + ":00";
        return result;
    }

    private String getHourText() {
        return ((AutoCompleteTextView) findViewById(R.id.hours_text)).getText().toString();
    }


    private String getAdultText() {
        return ((TextInputEditText) findViewById(R.id.adult_text)).getText().toString();
    }

    private String getChildText() {
        return ((TextInputEditText) findViewById(R.id.child_text)).getText().toString();
    }

    private String getLaunchText() {
        return ((AutoCompleteTextView) findViewById(R.id.launch_text)).getText().toString();
    }

    private String getSingleText() {
        return ((TextInputEditText) findViewById(R.id.single_text)).getText().toString();
    }

    private String getTandemText() {
        return ((TextInputEditText) findViewById(R.id.tandem_text)).getText().toString();
    }


    private void showToastWithMessage(String message) {
        Toast.makeText(AddReservationsActivity.this, message, Toast.LENGTH_SHORT).show();
    }


    private void showNextPage() {
        if (currentStep < 1) {
            currentStep++;
            // Fake a drag in the x direction by 1000 pixels to the right
            fakeDrag(-1000);
        }

        if (currentStep > 0) {
            backButton.setVisibility(View.VISIBLE);
            continueOrDoneButton.setVisibility(View.INVISIBLE);
            submitButton.setVisibility(View.VISIBLE);
        }
    }

    private void showPrevPage() {
        if (currentStep > 0) {
            currentStep--;
            // Fake a drag in the x direction by 1000 pixels to the left
            fakeDrag(1000);
        }

        if (currentStep == 0) {
            backButton.setVisibility(View.INVISIBLE);
            continueOrDoneButton.setVisibility(View.VISIBLE);
            submitButton.setVisibility(View.INVISIBLE);
        }
    }

    private void fakeDrag(float amount) {
        stepPager.setEnabled(true);
        stepPager.beginFakeDrag();
        stepPager.fakeDragBy(amount);
        stepPager.endFakeDrag();
        stepPager.setEnabled(false);
    }

    private void setToolbarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }
}
