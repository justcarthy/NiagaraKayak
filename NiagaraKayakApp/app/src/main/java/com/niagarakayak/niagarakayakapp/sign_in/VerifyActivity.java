package com.niagarakayak.niagarakayakapp.sign_in;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.niagarakayak.niagarakayakapp.R;
import com.niagarakayak.niagarakayakapp.home.HomeActivity;
import com.niagarakayak.niagarakayakapp.model.Customer;
import com.niagarakayak.niagarakayakapp.service.customers.CustomerAPIService;
import com.niagarakayak.niagarakayakapp.util.ActivityUtils;
import com.niagarakayak.niagarakayakapp.util.SnackbarUtils.SnackbarColor;

import java.util.ArrayList;

import static android.support.design.widget.Snackbar.LENGTH_SHORT;
import static com.niagarakayak.niagarakayakapp.service.customers.CustomerService.*;
import static com.niagarakayak.niagarakayakapp.util.SnackbarUtils.LENGTH_LONGER;

public class VerifyActivity extends AppCompatActivity {
    private View root;
    private Button btnVerify;
    private EditText editTextVerify;

    private CustomerAPIService customerAPIService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_verify);
        root = findViewById(android.R.id.content);

        Intent i = getIntent();
        final String email = i.getStringExtra("email");

        if (savedInstanceState == null) {
            // Show a snackbar to let the user know we sent an email.
            // We want to show this only once.
            showEmailSuccessSnackbar();
        }

        customerAPIService = new CustomerAPIService(getString(R.string.NK_API_KEY));

        btnVerify = (Button) findViewById(R.id.btn_verify);
        editTextVerify = (EditText) findViewById(R.id.text_verify_sign_in);


        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isVerificationCodeEmpty()) {
                    customerAPIService.verify(email, getVerifyText(), new CustomerCallback() {
                        @Override
                        public void onFailure(Exception ex) {
                            if (ex instanceof InvalidValidationCode) {
                                showInvalidErrorSnackbar("Verification code is invalid!");
                            } else {
                                showInvalidErrorSnackbar("Couldn't validate code due to server issues. Try again later.");
                            }
                        }

                        @Override
                        public void onSuccess() {
                            // Next we need to get their customer information.
                            customerAPIService.getCustomer(email, new CustomerCallback() {
                                @Override
                                public void onFailure(Exception ex) {
                                    showInvalidErrorSnackbar("Failed to login.");
                                }

                                @Override
                                public void onSuccess() {

                                }

                                @Override
                                public void onSuccess(ArrayList<Customer> customers) {
                                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(VerifyActivity.this);
                                    SharedPreferences.Editor editor = prefs.edit();

                                    // This is just dummy data for now.
                                    editor.putString("name", customers.get(0).getName());
                                    editor.putString("email", email);
                                    editor.putString("phone", customers.get(0).getPhone());
                                    editor.commit();

                                    Intent i = new Intent(VerifyActivity.this, HomeActivity.class);
                                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(i);

                                }
                            });
                        }

                        @Override
                        public void onSuccess(ArrayList<Customer> customers){}
                    });
                } else {
                    showInvalidErrorSnackbar("Verification code can't be empty!");
                }
            }
        });
    }

    private boolean isVerificationCodeEmpty() {
        return TextUtils.isEmpty(editTextVerify.getText());
    }

    private String getVerifyText() {
        return editTextVerify.getText().toString();
    }

    private void showEmailSuccessSnackbar() {
        ActivityUtils.showSnackbarWithMessage(root,
                "We've sent an email to your inbox",
                LENGTH_LONGER, SnackbarColor.SUCCESS_COLOR);
    }

    private void showInvalidErrorSnackbar(String errorMessage) {
        ActivityUtils.showSnackbarWithMessage(root,
                errorMessage,
                LENGTH_SHORT, SnackbarColor.ERROR_COLOR);
    }

}
