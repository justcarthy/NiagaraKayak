package com.niagarakayak.niagarakayakapp.service.customers;

public interface CustomerService {

    /**
     * Method to post specific customers information to the customers table.
     * Called if the verification went through.
     * It should throw an CustomerExistsException if a customer exists.
     * It should throw a general exception for any networking related exceptions.
     */
    void postCustomer(String name, String email, String phone) throws CustomerExistsException, Exception;

    /**
     * To update a row in the customers table.
     * Updates their name, or phone if any changes from the PreferencesActivity.
     * @param emailID       The email ID of the customer
     * @param newName       The desired name
     * @param newPhone      The desired phone
     * @throws Exception    If any networking issues, it throws an exception.
     */
    void updateCustomer(String emailID, String newName, String newPhone) throws Exception;

    /**
     * Sends a verification email to a customers email.
     * @param email         The email of the customer.
     * @throws Exception    If any errors are found, an exception should be thrown.
     */
    void sendVerificationEmail(String email) throws Exception;

    /**
     * Verifies from the server if the code was valid or not.
     * @param verificationCode      Verification code input by the user to check.
     */
    void verify(String verificationCode);

    /**
     * This exception should be thrown if a customer exists
     */
    class CustomerExistsException extends Exception {
        public CustomerExistsException() {
            super("A user with this email already exists. Please log in if you already have an account.");
        }
    }
}
