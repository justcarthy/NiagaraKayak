package com.niagarakayak.niagarakayakapp.service.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class ReservationReaderHelper extends SQLiteOpenHelper {


    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "ReservationReader.db";


    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + ReservationReaderContract.ReservationEntry.RESERVATION_TABLE + " (" +
                    ReservationReaderContract.ReservationEntry.RESERVATION_ID + " TEXT PRIMARY KEY," +
                    ReservationReaderContract.ReservationEntry.RESERVATION_DATE + " TEXT," +
                    ReservationReaderContract.ReservationEntry.RESERVATION_LOCATION + " TEXT," +
                    ReservationReaderContract.ReservationEntry.RESERVATION_EMAIL + " TEXT," +
                    ReservationReaderContract.ReservationEntry.RESERVATION_ADULTS + " INTEGER," +
                    ReservationReaderContract.ReservationEntry.RESERVATION_CHILDREN + " INTEGER," +
                    ReservationReaderContract.ReservationEntry.RESERVATION_HOURS + " INTEGER," +
                    ReservationReaderContract.ReservationEntry.RESERVATION_SINGLE + " INTEGER," +
                    ReservationReaderContract.ReservationEntry.RESERVATION_TANDEM + " INTEGER," +
                    ReservationReaderContract.ReservationEntry.RESERVATION_CONFIRMED + " INTEGER" +
                    " )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + ReservationReaderContract.ReservationEntry.RESERVATION_TABLE;


    public ReservationReaderHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
