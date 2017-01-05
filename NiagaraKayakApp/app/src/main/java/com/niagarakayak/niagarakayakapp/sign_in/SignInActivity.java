package com.niagarakayak.niagarakayakapp.sign_in;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.niagarakayak.niagarakayakapp.R;
import com.niagarakayak.niagarakayakapp.model.Customer;
import com.niagarakayak.niagarakayakapp.service.customers.CustomerAPIService;
import com.niagarakayak.niagarakayakapp.service.customers.CustomerService;
import com.niagarakayak.niagarakayakapp.util.ActivityUtils;
import com.niagarakayak.niagarakayakapp.util.SnackbarUtils.SnackbarColor;

import java.util.ArrayList;

import static com.niagarakayak.niagarakayakapp.util.SnackbarUtils.LENGTH_LONGER;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnSignIn;
    private EditText editTextEmail;
    private CustomerAPIService customerAPIService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        btnSignIn = (Button) findViewById(R.id.btn_sign_in);
        editTextEmail = (EditText) findViewById(R.id.text_email_sign_in);
        btnSignIn.setOnClickListener(this);
        customerAPIService = new CustomerAPIService(getString(R.string.NK_API_KEY));
    }

    private boolean isEmailEmpty() {
        return TextUtils.isEmpty(editTextEmail.getText());
    }

    private String getEmailText() {
        return editTextEmail.getText().toString();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_sign_in: {
                if (!isEmailEmpty()) {
                    customerAPIService.checkEmailFree(getEmailText(), new CustomerService.CustomerCallback() {
                        @Override
                        public void onFailure(Exception ex) {
                            // If a customer exists.
                            if (ex instanceof CustomerService.CustomerExistsException) {
                                // Send the verification email here.
                                customerAPIService.sendVerificationEmail(getEmailText(), new CustomerService.CustomerCallback() {
                                    @Override
                                    public void onFailure(Exception ex) {
                                        ActivityUtils.showSnackbarWithMessage(findViewById(android.R.id.content),
                                                "Couldn't send verification email, server issues. Try again later.",
                                                LENGTH_LONGER, SnackbarColor.ERROR_COLOR);
                                    }

                                    @Override
                                    public void onSuccess() {
                                        // If the email went through, show the verification screen.
                                        Intent i = new Intent(SignInActivity.this, VerifyActivity.class);
                                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        i.putExtra("email", getEmailText());
                                        startActivity(i);
                                    }

                                    @Override
                                    public void onSuccess(ArrayList<Customer> customers){}
                                });
                            } else {
                                // It failed to check the email, server issues.
                                ActivityUtils.showSnackbarWithMessage(findViewById(android.R.id.content),
                                        "Failed to reach server. Try again later.",
                                        LENGTH_LONGER, SnackbarColor.ERROR_COLOR);
                            }
                        }

                        @Override
                        public void onSuccess() {
                            ActivityUtils.showSnackbarWithMessage(findViewById(android.R.id.content),
                                    "No account with that email. Sign up!",
                                    LENGTH_LONGER, SnackbarColor.ERROR_COLOR);
                        }

                        @Override
                        public void onSuccess(ArrayList<Customer> customers) {}
                    });
                }
                break;
            }
        }
    }
}
