package com.niagarakayak.niagarakayakapp.add_reservations;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import com.badoualy.stepperindicator.StepperIndicator;
import com.niagarakayak.niagarakayakapp.R;
import com.niagarakayak.niagarakayakapp.add_reservations.steps.Step1Fragment;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.Calendar;

public class AddReservationsViewFragment extends Fragment implements AddReservationsContract.View, View.OnClickListener {

    private AddReservationsContract.Presenter mPresenter;
    private StepperIndicator indicator;
    private StepViewPager stepPager;
    private Button continueOrDoneButton;
    private StepPagerAdapter stepPagerAdapter;
    private int currentPage;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_add_reservations, container, false);
        indicator = (StepperIndicator) root.findViewById(R.id.stepper);
        stepPager = (StepViewPager) root.findViewById(R.id.step_viewpager);
        continueOrDoneButton = (Button) root.findViewById(R.id.continue_done_btn);
        continueOrDoneButton.setOnClickListener(this);
        stepPagerAdapter = new StepPagerAdapter(getChildFragmentManager());
        stepPager.setAdapter(stepPagerAdapter);
        indicator.setViewPager(stepPager, true);
        indicator.setStepCount(3);
        currentPage = 0;
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void setPresenter(AddReservationsContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void showNextPage() {
        if (currentPage < 3) {
            currentPage++;
            // Fake a drag in the x direction by 100 pixels to the right
            stepPager.setEnabled(true);
            stepPager.beginFakeDrag();
            stepPager.fakeDragBy(-1000);
            stepPager.endFakeDrag();
            stepPager.setEnabled(false);
        }
    }

    @Override
    public String getDateText() {
        return stepPagerAdapter.getDateText();
    }

    @Override
    public String getTimeText() {
        return stepPagerAdapter.getTimeText();
    }

    @Override
    public String getHoursText() {
        return stepPagerAdapter.getHoursText();
    }

    @Override
    public void showToastWithMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    public static AddReservationsViewFragment newInstance() {
        Bundle args = new Bundle();
        AddReservationsViewFragment fragment = new AddReservationsViewFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.date_text:
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                        ((StepPagerAdapter) stepPager.getAdapter()).setDateText(year + "-" + monthOfYear + "-" + dayOfMonth);
                    }
                }, now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));
                dpd.show(getChildFragmentManager(), "datePicker");
                break;
            case R.id.time_text:
                TimePickerDialog tpd = TimePickerDialog.newInstance(new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
                        String amOrpm = hourOfDay < 12 ? "AM" : "PM";
                        String paddedMinute = minute < 10 ? "0"+minute+" ": minute+" ";
                        ((StepPagerAdapter) stepPager.getAdapter()).setTimeText(hourOfDay-12 + ":" + paddedMinute + amOrpm);
                    }
                }, 12, 0, false);
                tpd.show(getChildFragmentManager(), "timePicker");
                break;
            case R.id.continue_done_btn:
                mPresenter.handleOnContinue(currentPage);
                break;
        }
    }


    private class StepPagerAdapter extends FragmentStatePagerAdapter {
        private int mPosition = 0;
        private Step1Fragment step1;

        public StepPagerAdapter(FragmentManager fm) {
            super(fm);
            step1 = new Step1Fragment(AddReservationsViewFragment.this);
        }

        @Override
        public Fragment getItem(int position) {
            mPosition = position;
            switch (position) {
                case 0: {
                    return step1;
                }

                case 1: {
                    return new Step1Fragment(AddReservationsViewFragment.this);
                }

                case 2: {
                    return new Step1Fragment(AddReservationsViewFragment.this);
                }

                default: {
                    return new Step1Fragment(AddReservationsViewFragment.this);
                }
            }
        }

        public String getDateText() {
            return step1.getDateText();
        }

        public void setDateText(String date) {
            step1.setDateText(date);
        }

        public String getTimeText() {
            return step1.getTimeText();
        }

        public void setTimeText(String time) {
            step1.setTimeText(time);
        }

        public String getHoursText() {
            return step1.getHoursOptionSelected();
        }

        @Override
        public int getCount() {
            return 3;
        }
    }
}
