package com.niagarakayak.niagarakayakapp.service.customers;

import com.niagarakayak.niagarakayakapp.model.Customer;

import java.util.ArrayList;

public interface CustomerService {

    /**
     * Method to post specific customers information to the customers table.
     * Called if the verification went through.
     * It should throw an CustomerExistsException if a customer exists.
     * It should throw a general exception for any networking related exceptions.
     */
    void postCustomer(String name, String email, String phone,CustomerCallback callback);

    /**
     * To update a row in the customers table.
     * Updates their name, or phone if any changes from the PreferencesActivity.
     * @param emailID       The email ID of the customer
     * @param newName       The desired name
     * @param newPhone      The desired phone
     */
    void updateCustomer(String emailID, String newName, String newPhone,CustomerCallback callback);

    /**
     *
     * @param email                 Email to check for availability
     * @param callback              Callback to pass to async task
     * @param email      Email to check for availability
     * @param callback   Callback to pass to async task
     */
    void checkEmailFree(String email,CustomerCallback callback);

    /**
     * Sends a verification email to a customers email.
     * @param email         The email of the customer.
     */
    void sendVerificationEmail(String email,CustomerCallback callback);

    /**
     * Verifies from the server if the code was valid or not.
     * @param email                 Email of client whose code is being verified
     * @param verificationCode      Verification code input by the user to check.
     */
    void verify(String email,String verificationCode,CustomerCallback callback);

    /**
     * @param email         Email of client to fetch inforamtion for
     */
    void getCustomer(String email, CustomerCallback callback);

    /**
     * This exception should be thrown if a customer exists
     */
    class CustomerExistsException extends Exception {
        public CustomerExistsException() {
            super("A user with this email already exists. Please log in if you already have an account.");
        }
    }

    /**
     *  Exception thrown when customer enters wrong validation code or
     *  when the validation code is expired
     */

    class InvalidValidationCode extends Exception {
        public InvalidValidationCode() {
            super("The validation code provided by the user is expired or wrong");
        }
    }

    /**
     *  Exception thrown when no such customer record exists for the given email
     */
    class NoCustomerExists extends Exception{
        public NoCustomerExists() {
            super("No such customer record");
        }
    }

    /**
     * Interface for all callbacks related to service
     *  Check the instance of Exception to display appropriate error message
     *  CustomerExistsException if customer record already exists
     *  InvalidValidationCode if customer entered wrong or expired code
     *  NoCustomerExists if customer record does not exist
     */
    interface CustomerCallback{
        void onFailure(Exception ex);
        void onSuccess();
        /**
         *
         * @param customers list containing customer records,get the customer at index 0
         */
        void onSuccess(ArrayList<Customer> customers);
    }

}
