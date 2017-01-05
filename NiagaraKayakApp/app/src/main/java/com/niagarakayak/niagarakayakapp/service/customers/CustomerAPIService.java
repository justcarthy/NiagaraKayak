package com.niagarakayak.niagarakayakapp.service.customers;

import android.os.AsyncTask;

import com.niagarakayak.niagarakayakapp.model.Customer;
import com.niagarakayak.niagarakayakapp.service.reservation.UrlContainer;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by bala on 1/1/17.
 */

public class CustomerAPIService implements CustomerService {

    private String APIKey;
    private String URL;
    private String URLparam;

    public CustomerAPIService(String APIKey) {
        this.APIKey = APIKey;
    }

    @Override
    public void postCustomer(String name, String email, String phone,CustomerCallback callback){
        this.URL = UrlContainer.getPostCustomerURL();
        String urlParameters = UrlContainer.getPostCustomerURLparam(); //api,email,name,phone
        this.URLparam = String.format(urlParameters,this.APIKey,email,name,phone);
        new CustomerBackgroundTask().execute(callback); //task to post customer info
    }

    @Override
    public void updateCustomer(String emailID, String newName, String newPhone,CustomerCallback callback){
        this.URL = UrlContainer.getUpdateCustomerURL();
        String urlparam = UrlContainer.getUpdateCustomerURLparam();
        this.URLparam = String.format(urlparam,this.APIKey,emailID,newName,newPhone);
        new CustomerBackgroundTask().execute(callback); //task to updateCustomer info
    }

    @Override
    public void checkEmailFree(String email, CustomerCallback callback){
        this.URL = UrlContainer.getCheckCustomerEmailURL();
        String urlparam = UrlContainer.getUpdateCustomerURLparam();
        this.URLparam = String.format(urlparam,this.APIKey,email);
        new CustomerBackgroundTask().execute(callback); //task to updateCustomer info
    }

    @Override
    public void sendVerificationEmail(String email,CustomerCallback callback){
        this.URL = UrlContainer.getSendVerificationURL();
        String urlparam = UrlContainer.getSendVerificationURLparam();
        this.URLparam = String.format(urlparam,this.APIKey,email);
        new CustomerBackgroundTask().execute(callback); //task to updateCustomer info
    }

    @Override
    public void verify(String email, String verificationCode, CustomerCallback callback){
        this.URL = UrlContainer.getVerificationURL();
        String urlparam = UrlContainer.getVerificationURLparam();
        this.URLparam = String.format(urlparam,this.APIKey,email,verificationCode);
        new CustomerBackgroundTask().execute(callback); //task to updateCustomer info
    }

    public void getCusomter(String email,CustomerCallback callback){
        this.URL = UrlContainer.getCustomerInfoURL();
        String urlparam = UrlContainer.getCustomerInfoURLparam();
        this.URLparam = String.format(urlparam,this.APIKey,email);
        new GetCustomerDataTask().execute(callback); //task to updateCustomer info
    }



    private void sendData(String url, String urlParameters) throws CustomerExistsException,InvalidValidationCode,Exception {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");
        con.setUseCaches(false);
        con.setDoOutput(true);

        //write parameters to body
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode(); //connection
        switch(responseCode){
            case HttpURLConnection.HTTP_OK:  //successfully inserted into the customer table
                break;
            case HttpURLConnection.HTTP_INTERNAL_ERROR:
                throw new Exception("Internal server error");
            case HttpURLConnection.HTTP_UNAUTHORIZED:
                throw new Exception("Invalid API key");
            case HttpURLConnection.HTTP_BAD_REQUEST:
                throw new Exception("Bad request");
            case HttpURLConnection.HTTP_CONFLICT:
                throw new CustomerExistsException(); //customer email already exists
            case HttpURLConnection.HTTP_FORBIDDEN:
                throw new InvalidValidationCode();  //Invalid code given
        }
        //close resources
        con.getInputStream().close();
        con.disconnect();
    }

    /**
     * @throws NoCustomerExists   If no Customer record exists for given Email
     * @throws Exception          Server and other network errors . Invalid JSON treated as server error
     */
    private ArrayList<Customer> getCustomerData(String url, String urlParameters) throws NoCustomerExists,Exception{
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");
        con.setUseCaches(false);
        con.setDoOutput(true);

        //write parameters to body
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode(); //connection
        switch(responseCode){
            case HttpURLConnection.HTTP_OK:  //successfully inserted into the customer table
                return (CustomerJSONParser.getCustomers(getJSONString(con)));
            case HttpURLConnection.HTTP_INTERNAL_ERROR:
                throw new Exception("Internal server error");
            case HttpURLConnection.HTTP_UNAUTHORIZED:
                throw new Exception("Invalid API key");
            case HttpURLConnection.HTTP_BAD_REQUEST:
                throw new NoCustomerExists(); //customer record does not exist
        }
        return null;
    }

    private String getJSONString(HttpURLConnection httpConnection) throws IOException {
        StringBuffer buffer = new StringBuffer();
        BufferedReader in = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));
        String line = null;

        while ((line = in.readLine()) != null ) {
            buffer.append(line + "\r\n");
        }

        in.close();
        httpConnection.disconnect();
        return buffer.toString();
    }


    /**
     * Class used to schedule Background task for Posting and updating Customer information
     * Sending and verifying codes
     */
    private class CustomerBackgroundTask extends AsyncTask<CustomerCallback,Void,Void>{
        private CustomerCallback callback;
        private Exception exception;

        @Override
        protected Void doInBackground(CustomerCallback... params) {
            callback = params[0];
            try{
                CustomerAPIService.this.sendData(CustomerAPIService.this.URL,CustomerAPIService.this.URLparam);
            }catch (Exception ex){
                this.exception = ex;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if(exception != null) {
                callback.onFailure(exception);
            } else {
                callback.onSuccess();
            }
        }
    }

    private class GetCustomerDataTask extends AsyncTask<CustomerCallback,Void,ArrayList<Customer>>{
        private CustomerCallback callback;
        private Exception exception;

        @Override
        protected ArrayList<Customer> doInBackground(CustomerCallback... params){
            callback = params[0];
            try{
                return getCustomerData(CustomerAPIService.this.URL,CustomerAPIService.this.URLparam);
            }catch (Exception ex){
                this.exception = ex;
            }
            return new ArrayList<>();
        }

        @Override
        protected void onPostExecute(ArrayList<Customer> customers) {
            if(exception != null  || customers == null) {
                callback.onFailure(exception);
            } else {
                callback.onSuccess(customers);
            }
        }
    }
}
