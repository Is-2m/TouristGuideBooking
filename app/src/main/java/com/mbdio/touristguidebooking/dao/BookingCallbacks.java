package com.mbdio.touristguidebooking.dao;

import com.mbdio.touristguidebooking.models.Booking;
import com.mbdio.touristguidebooking.models.Guide;

import java.util.ArrayList;

import kotlin.NotImplementedError;

public interface BookingCallbacks {

    default void onBookingInserted(boolean success,  String message) {
        throw new NotImplementedError();
    }

    default void onBookingUpdated(boolean success, String message) {
        throw new NotImplementedError();
    }

    default void onGetAllBookings(ArrayList<Booking> lst) {
        throw new NotImplementedError();
    }
}
