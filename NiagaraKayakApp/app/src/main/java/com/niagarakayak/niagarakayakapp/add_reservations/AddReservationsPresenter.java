package com.niagarakayak.niagarakayakapp.add_reservations;

public class AddReservationsPresenter implements AddReservationsContract.Presenter {

    private AddReservationsContract.View mAddReservationsView;

    private static final int STEP_ONE = 0;
    private static final int STEP_TWO = 1;
    private static final int STEP_THREE = 2;

    public AddReservationsPresenter(AddReservationsContract.View mAddReservationsView) {
        this.mAddReservationsView = mAddReservationsView;
        this.mAddReservationsView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void handleOnContinue(int page) {
        // Depending on what page we are on..
        switch (page) {
            case STEP_ONE:
                // We need to verify correct input at each page
                String dateText = mAddReservationsView.getDateText();
                String timeText = mAddReservationsView.getTimeText();
                String hoursText = mAddReservationsView.getHoursText();

                if (inputValid(dateText, timeText, hoursText)) {
                    mAddReservationsView.showToastWithMessage("yay, moving to next page");
                    mAddReservationsView.showNextPage();
                } else {
                    mAddReservationsView.showToastWithMessage("One or more fields left blank!");
                }

                break;
            case STEP_TWO:
                // Validate stuff
                break;
            case STEP_THREE:
                // Show the done page, send the request here
                break;
        }
    }

    private boolean inputValid(String input1, String input2, String input3) {
        return !input1.isEmpty() && !input2.isEmpty() && !input3.isEmpty();
    }
}
