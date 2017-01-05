package com.niagarakayak.niagarakayakapp.sign_up;

import android.support.annotation.ColorInt;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import com.github.paolorotolo.appintro.ISlideBackgroundColorHolder;
import com.github.paolorotolo.appintro.ISlidePolicy;

public abstract class SignUpSlide extends Fragment implements ISlideBackgroundColorHolder, ISlidePolicy {
    View mRoot;
    EditText input;

    private boolean allowedToContinue;

    boolean isValid() {
        return input != null && !TextUtils.isEmpty(input.getText());
    }

    String getInput() {
        if (input != null) {
            return input.getText().toString();
        }

        return "";
    }

    void setIsAllowedToContinue(boolean allowedToContinue) {
        this.allowedToContinue = allowedToContinue;
    }


    abstract void onIllegalyRequestedContinue();

    @Override
    public boolean isPolicyRespected() {
        return allowedToContinue;
    }

    @Override
    public void onUserIllegallyRequestedNextPage() {
        onIllegalyRequestedContinue();
    }

    abstract void handleContinue(final GoToNextPageCallback callback);

    void handleDone(final OnDoneCallback callback) {
        if (isValid()) {
            callback.onDone();
        }
    }

    @Override
    public void setBackgroundColor(@ColorInt int backgroundColor) {
        if (mRoot != null) {
            mRoot.setBackgroundColor(backgroundColor);
        }
    }

    interface GoToNextPageCallback {
        void nextPage();
    }

    interface OnDoneCallback {
        void onDone();
    }
}
