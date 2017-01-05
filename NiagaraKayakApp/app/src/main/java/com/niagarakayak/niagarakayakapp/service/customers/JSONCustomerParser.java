package com.niagarakayak.niagarakayakapp.service.customers;

import com.niagarakayak.niagarakayakapp.model.Customer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by bala on 4/1/17.
 */

public class JSONCustomerParser {

    public static ArrayList<Customer> getCustomers(String json) throws JSONException {
        ArrayList<Customer> customers = new ArrayList<>();
        JSONArray jsonArray = new JSONArray(json);
        JSONObject jsonObject;
        Customer customer;

        for(int i = 0; i < jsonArray.length(); i++) {
            jsonObject = jsonArray.getJSONObject(i);
            customer = new Customer();
            customer.setEmail(jsonObject.getString("Email"));
            customer.setName(jsonObject.getString("Name"));
            customer.setPhone(jsonObject.getString("Phone"));
            customers.add(customer);
        }

        return customers;
    }
}
