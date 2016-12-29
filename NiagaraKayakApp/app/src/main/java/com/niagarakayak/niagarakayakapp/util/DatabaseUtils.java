package com.niagarakayak.niagarakayakapp.util;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.niagarakayak.niagarakayakapp.model.Reservation;
import com.niagarakayak.niagarakayakapp.service.database.ReservationReaderContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Justin on 2016-12-29.
 */

public class DatabaseUtils {

    public List getAllMeets(SQLiteDatabase db) {
        List<Reservation> list = new ArrayList<>();
        //SharedPreferences
        String query = "SELECT * FROM reservations ";

        Cursor c = db.rawQuery(query, null);

        while(c.moveToNext()) {
            int index0 = c.getColumnIndex("reservationID");
            int index1 = c.getColumnIndex("datetime");
            int index2 = c.getColumnIndex("location");
            int index3 = c.getColumnIndex("email");
            int index4 = c.getColumnIndex("adults");
            int index5 = c.getColumnIndex("children");
            int index6 = c.getColumnIndex("hours");
            int index7 = c.getColumnIndex("singleKayaks");
            int index8 = c.getColumnIndex("tandemKayaks");
            int index9 = c.getColumnIndex("confirmed");
            String reservationID = c.getString(index0);
            String datetime = c.getString(index1);
            String location = c.getString(index2);
            String email = c.getString(index3);
            int adults = c.getInt(index4);
            int children = c.getInt(index5);
            int hours = c.getInt(index6);
            int singleKayaks = c.getInt(index7);
            int tandemKayaks = c.getInt(index8);
            boolean confirmed = c.getInt(index9) == 1 ? true : false;


            String date = datetime.split(" ")[0];
            String time = datetime.split(" ")[1];

            list.add(new Reservation(reservationID, email, date, time, hours, singleKayaks, tandemKayaks, location, adults, children, confirmed));
        }

        return list;
    }
}
