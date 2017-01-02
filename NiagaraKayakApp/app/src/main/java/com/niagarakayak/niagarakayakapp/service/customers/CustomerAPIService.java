package com.niagarakayak.niagarakayakapp.service.customers;

import com.niagarakayak.niagarakayakapp.service.reservation.UrlContainer;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by bala on 1/1/17.
 */

public class CustomerAPIService implements CustomerService {

    private String APIKey;

    public CustomerAPIService(String APIKey) {
        this.APIKey = APIKey;
    }

    @Override
    public void postCustomer(String name, String email, String phone) throws CustomerExistsException, Exception {
        String url = UrlContainer.getPostCustomerURL();
        String urlParameters = UrlContainer.getPostCustomerURLparam(); //api,email,name,phone
        urlParameters = String.format(urlParameters,this.APIKey,email,name,phone);
        this.sendData(url,urlParameters);
    }

    @Override
    public void updateCustomer(String emailID, String newName, String newPhone) throws Exception {
        String url = UrlContainer.getUpdateCustomerURL();
        String urlparam = UrlContainer.getUpdateCustomerURLparam();
        urlparam = String.format(urlparam,this.APIKey,emailID,newName,newPhone);
        this.sendData(url,urlparam);
    }

    @Override
    public void sendVerificationEmail(String email) throws Exception {

    }

    @Override
    public void verify(String verificationCode) {

    }

    private void sendData(String url,String urlParameters) throws CustomerExistsException,Exception {
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
                throw new CustomerExistsException();
        }
        //close resources
        con.getInputStream().close();
        con.disconnect();
    }
}
