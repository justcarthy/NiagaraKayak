package com.niagarakayak.niagarakayakapp.service.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import com.niagarakayak.niagarakayakapp.model.Reservation;
import java.util.ArrayList;


/**
 * Created by Justin on 2016-12-29.
 */

/**
 *
 */
public class ReservationLocalDataService implements DataService{

    private ReservationReaderHelper dbHelp;
    private Reservation reservation;
    private String reservationID;
    private ContentValues resForSQL;

    public ReservationLocalDataService (Context context){
        dbHelp = new ReservationReaderHelper(context);
    }

    /**
     * @param callback
     * @param reservation, Reservation to be added to SQLite
     */
    @Override
    public void addReservationLocal(InsertCallback callback, Reservation reservation) {
        this.reservation = reservation;
        new InsertLocalDataTask().execute(callback);
    }

    private class InsertLocalDataTask extends AsyncTask<InsertCallback, Void, Void>{
        private InsertCallback callback;
        private Exception exception;
        @Override
        protected Void doInBackground(InsertCallback... params) {
            this.callback = params[0];
            try {
                executeInsertReservation();
            } catch (Exception e) {
                this.exception = e;
            }
            return null;
        }
    }

    private void executeInsertReservation() throws Exception{
        SQLiteDatabase db = dbHelp.getWritableDatabase();
        resForSQL = new ContentValues();
        String dateTime = reservation.getDate() + " " + reservation.getTime();
        resForSQL.put(ReservationReaderContract.ReservationEntry.RESERVATION_ID, reservation.getReservationID());
        resForSQL.put(ReservationReaderContract.ReservationEntry.RESERVATION_DATE, dateTime);
        resForSQL.put(ReservationReaderContract.ReservationEntry.RESERVATION_LOCATION, reservation.getLocation());
        resForSQL.put(ReservationReaderContract.ReservationEntry.RESERVATION_EMAIL, reservation.getEmail());
        resForSQL.put(ReservationReaderContract.ReservationEntry.RESERVATION_HOURS, reservation.getHours());
        resForSQL.put(ReservationReaderContract.ReservationEntry.RESERVATION_ADULTS, reservation.getAdults());
        resForSQL.put(ReservationReaderContract.ReservationEntry.RESERVATION_CHILDREN, reservation.getChildren());
        resForSQL.put(ReservationReaderContract.ReservationEntry.RESERVATION_SINGLE, reservation.getSingleKayaks());
        resForSQL.put(ReservationReaderContract.ReservationEntry.RESERVATION_TANDEM, reservation.getTandemKayaks());
        resForSQL.put(ReservationReaderContract.ReservationEntry.RESERVATION_CONFIRMED, reservation.isConfirmed() ? 1 : 0);
        db.insert(ReservationReaderContract.ReservationEntry.RESERVATION_TABLE, null, resForSQL);
        db.close();
    }



    /**
     *
     * @param callback
     */
    @Override
    public void readLocalReservations(ReadCallback callback) {
        new ReadReservationsTask().execute(callback);
    }

    private class ReadReservationsTask extends AsyncTask<ReadCallback, Void, ArrayList<Reservation>>{
        private ReadCallback callback;
        private Exception exception;

        @Override
        protected ArrayList<Reservation> doInBackground(ReadCallback... params) {
            this.callback = params[0];
            try {
                return executeReadReservations();
            } catch (Exception e){
                this.exception = e;
            }
            return new ArrayList<>();
        }

        @Override
        protected void onPostExecute(ArrayList<Reservation> reservations) {
            if (exception != null) {
                callback.onFailure(exception);
            } else {
                callback.onSuccess(reservations);
            }
        }
    }


    private ArrayList<Reservation> executeReadReservations() {
        SQLiteDatabase db = dbHelp.getReadableDatabase();
        ArrayList<Reservation> list = new ArrayList<>();

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
        db.close();
        return list;
    }

    public void confirmReservationLocal(String reservationID){
        this.reservationID = reservationID;
        new ConfirmLocalReservation().execute();
    }

    private class ConfirmLocalReservation extends AsyncTask<Void, Void, Void>{
        private Exception exception;
        @Override
        protected Void doInBackground(Void... params) {
            try{
                executeConfirmReservations();
            } catch (Exception e){
                this.exception = e;
            }
            return null;
        }
    }

    private void executeConfirmReservations() throws Exception{
        SQLiteDatabase db = dbHelp.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ReservationReaderContract.ReservationEntry.RESERVATION_CONFIRMED, true);
        String updateQuery = ReservationReaderContract.ReservationEntry.RESERVATION_ID + " LIKE ?";
        String[] updateArgs = {this.reservationID};
        db.update(ReservationReaderContract.ReservationEntry.RESERVATION_TABLE, values, updateQuery, updateArgs);
        db.close();
    }
}
