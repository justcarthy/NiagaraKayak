package com.niagarakayak.niagarakayakapp.add_reservations.steps;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.niagarakayak.niagarakayakapp.R;
import pl.droidsonroids.gif.GifImageView;


public class VerifyDialog extends MaterialDialog {

    protected VerifyDialog(Builder builder) {
        super(builder);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GifImageView drawable = (GifImageView) findViewById(R.id.kayak_gif);
        Animation a = drawable.getAnimation();
        a.start();
    }

    public void setPositiveButtonClickListener(View.OnClickListener listener) {
        getActionButton(DialogAction.POSITIVE).setOnClickListener(listener);
    }

}
