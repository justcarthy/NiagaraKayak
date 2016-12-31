package com.niagarakayak.niagarakayakapp.service.database;

import android.provider.BaseColumns;

/**
 * Inner class that defines the table contents
 */

public final class ReservationReaderContract {

    public static class ReservationEntry implements BaseColumns {
        public static final String RESERVATION_TABLE = "reservations";
        public static final String RESERVATION_ID = "reservationID";
        public static final String RESERVATION_DATE = "datetime";
        public static final String RESERVATION_LOCATION = "location";
        public static final String RESERVATION_EMAIL = "email";
        public static final String RESERVATION_ADULTS = "adults";
        public static final String RESERVATION_CHILDREN = "children";
        public static final String RESERVATION_HOURS = "hours";
        public static final String RESERVATION_SINGLE = "singleKayaks";
        public static final String RESERVATION_TANDEM = "tandemKayaks";
        public static final String RESERVATION_CONFIRMED = "confirmed";
    }
}
