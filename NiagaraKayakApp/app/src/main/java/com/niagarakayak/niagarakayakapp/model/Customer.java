package com.niagarakayak.niagarakayakapp.model;

import java.io.Serializable;

/**
 * Created by bala on 4/1/17.
 */

public class Customer implements Serializable {
    private String Email;
    private String Name;
    private String Phone;

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }
}
