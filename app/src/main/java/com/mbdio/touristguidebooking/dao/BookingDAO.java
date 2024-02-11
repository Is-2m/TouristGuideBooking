package com.mbdio.touristguidebooking.dao;

import android.util.Log;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.mbdio.touristguidebooking.models.Booking;
import com.mbdio.touristguidebooking.models.Guide;
import com.mbdio.touristguidebooking.models.Tourist;
import com.mbdio.touristguidebooking.models.User;
import com.mbdio.touristguidebooking.utils.AppStateManager;

import java.util.ArrayList;

public class BookingDAO {
    private static final String TAG = "BookingDAO";
    private static final String USERS_COLLECTION = "users"; // Change this to your collection name

    private static final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static CollectionReference usersCollection = db.collection(USERS_COLLECTION);

    public static void insertBooking(Booking booking, String guideID, BookingCallbacks callback) {
        db.collection("user/" + guideID + "/bookings")
                .add(booking)
                .addOnSuccessListener(documentReference -> {
                    callback.onBookingInserted(true, "Success");
                }).addOnFailureListener(e -> {
                    callback.onBookingInserted(false, e.getMessage());
                });
    }

    public static void updateBooking(String guideID, Booking booking, BookingCallbacks callback) {
        db.collection("user/" + guideID + "/bookings")
                .document(booking.getId())
                .set(booking)
                .addOnSuccessListener(aVoid -> {
                    callback.onBookingUpdated(true, "Booking updated successfully");
                })
                .addOnFailureListener(e -> {
                    callback.onBookingUpdated(false, e.getMessage());
                });
    }

    public static void getAllBookings(String guideID, BookingCallbacks callback) {
        String collectionPath = "users/" + guideID + "/bookings";

        db.collection(collectionPath)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    ArrayList<Booking> bookings = new ArrayList<>();
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        Booking booking = document.toObject(Booking.class);
                        booking.setId(document.getId());
                        UserDAO.getUser(booking.getTouristId(), new UserCallbacks() {
                            @Override
                            public void onGetUser(User user) {
                                booking.setTourist((Tourist) user);
                            }
                        });
                        bookings.add(booking);
                    }
                    callback.onGetAllBookings(bookings);
                })
                .addOnFailureListener(e -> {
                    callback.onGetAllBookings(new ArrayList<>());
                });
    }


}
