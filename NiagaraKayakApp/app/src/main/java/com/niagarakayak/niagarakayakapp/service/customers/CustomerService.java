package com.niagarakayak.niagarakayakapp.service.customers;

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
     * @throws Exception    If any networking issues, it throws an exception.
     */
    void updateCustomer(String emailID, String newName, String newPhone,CustomerCallback callback);

    /**
     * Sends a verification email to a customers email.
     * @param email         The email of the customer.
     * @throws Exception    If any errors are found, an exception should be thrown.
     */
    void sendVerificationEmail(String email,CustomerCallback callback) throws Exception;

    /**
     * Verifies from the server if the code was valid or not.
     * @param email                 Email of client whose code is being verified
     * @param verificationCode      Verification code input by the user to check.
     * @throws InvalidValidationCode wrong or expired validation code
     * @throws  Exception Connection or server related errors
     */
    void verify(String email,String verificationCode,CustomerCallback callback) throws InvalidValidationCode,Exception;

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
    class InvalidValidationCode extends Exception{
        public InvalidValidationCode() {
            super("The validation code provided by the user is expired or wrong");
        }
    }

    /**
     * Interface for all callbacks related to service
     *  Check the instance of Exception to display appropriate error message
     *  CustomerExistsException if customer record already exists
     *  InvalidValidationCode if customer entered wrong or expired code
     */
    interface CustomerCallback{
        void onFailure(Exception ex);
        void onSuccess();
    }

}
