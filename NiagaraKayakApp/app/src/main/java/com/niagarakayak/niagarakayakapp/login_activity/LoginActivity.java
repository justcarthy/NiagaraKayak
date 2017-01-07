package com.niagarakayak.niagarakayakapp.login_activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import com.niagarakayak.niagarakayakapp.R;
import com.niagarakayak.niagarakayakapp.sign_in.SignInActivity;
import com.niagarakayak.niagarakayakapp.sign_up.SignUpActivity;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button signUp = (Button) findViewById(R.id.btn_sign_up);
        Button signIn = (Button) findViewById(R.id.btn_sign_in);
        signUp.setOnClickListener(this);
        signIn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_sign_in: {
                Intent i = new Intent(LoginActivity.this, SignInActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                break;
            }

            case R.id.btn_sign_up: {
                Intent i = new Intent(LoginActivity.this, SignUpActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                break;
            }
        }
    }
}
