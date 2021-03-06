package com.niagarakayak.niagarakayakapp.service.reservation;

import com.niagarakayak.niagarakayakapp.model.Reservation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 *  Class is a parser to build reservation object from json
 */

public class JSONReservationParser {

   // [{"ReservationID":"bala@bala.com1","Email":"bala@bala.com","Date":"2016-12-29","Time":"11:20:00","Hours":"2","Single":"3","Tandem":"0"}]

    public static ArrayList<Reservation> getReservations(String json) throws JSONException {
        ArrayList<Reservation> reservations = new ArrayList<>();
        JSONArray jsonArray = new JSONArray(json);
        JSONObject jsonObject;
        Reservation reservation;

        for(int i = 0; i < jsonArray.length(); i++) {
            jsonObject = jsonArray.getJSONObject(i);
            reservation = new Reservation();
            reservation.setReservationID(jsonObject.getString("ReservationID"));
            reservation.setEmail(jsonObject.getString("Email"));
            reservation.setDate(jsonObject.getString("Date"));
            reservation.setReservationTime(jsonObject.getString("Time"));
            reservation.setReservationHours(jsonObject.getInt("Hours"));
            reservation.setSingleKayaks(jsonObject.getInt("Single"));
            reservation.setTandemKayaks(jsonObject.getInt("Tandem"));
            reservation.setAdults(jsonObject.getInt("adults"));
            reservation.setChildren(jsonObject.getInt("children"));
            reservation.setLocation(jsonObject.getString("location"));
            reservation.setConfirmed(true);
            reservations.add(reservation);
        }

        return reservations;
    }
}
