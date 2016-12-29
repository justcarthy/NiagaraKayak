package com.niagarakayak.niagarakayakapp.add_reservations;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;
import com.badoualy.stepperindicator.StepperIndicator;
import com.niagarakayak.niagarakayakapp.R;
import com.niagarakayak.niagarakayakapp.add_reservations.steps.VerifyDialog;

public class AddReservationsActivity extends AppCompatActivity implements View.OnClickListener {

    private StepperIndicator indicator;
    private StepViewPager stepPager;
    private Button submitButton;
    private ImageButton continueOrDoneButton;
    private ImageButton backButton;
    private StepPagerAdapter stepPagerAdapter;
    private int currentStep;
    private Bundle mBundle;
    private Toolbar mToolbar;

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
                    new VerifyDialog.Builder(this)
                            .title("Thank you!")
                            .customView(R.layout.dialog_verify, true)
                            .positiveText("Got it")
                            .dismissListener(new DialogInterface.OnDismissListener() {
                                @Override
                                public void onDismiss(DialogInterface dialog) {
                                    // TODO: Send a request here
                                    AddReservationsActivity.this.onBackPressed();
                                }
                            })
                            .show();
                } else {
                    showToastWithMessage("One or more fields are blank!");
                }
            }
        }
    }

    private boolean isValid(int page) {
        switch (page) {
            case StepPagerAdapter.STEP_ONE:
                String dateText = ((TextInputEditText) findViewById(R.id.date_text)).getText().toString();
                String timeText = ((TextInputEditText) findViewById(R.id.time_text)).getText().toString();
                String hourText = ((AutoCompleteTextView) findViewById(R.id.hours_text)).getText().toString();
                return !(dateText.isEmpty() || timeText.isEmpty() || hourText.isEmpty());
            case StepPagerAdapter.STEP_TWO:
                String adultText = ((TextInputEditText) findViewById(R.id.adult_text)).getText().toString();
                String childText = ((TextInputEditText) findViewById(R.id.child_text)).getText().toString();
                String launchPointText = ((AutoCompleteTextView) findViewById(R.id.launch_text)).getText().toString();
                return !(adultText.isEmpty() || childText.isEmpty() || launchPointText.isEmpty());
            default:
                return false;
        }
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
