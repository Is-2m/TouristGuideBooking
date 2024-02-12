package com.mbdio.touristguidebooking.dao;

import android.util.Log;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.mbdio.touristguidebooking.models.Booking;
import com.mbdio.touristguidebooking.models.Guide;
import com.mbdio.touristguidebooking.models.Tourist;
import com.mbdio.touristguidebooking.models.User;
import com.mbdio.touristguidebooking.utils.AppStateManager;
import com.mbdio.touristguidebooking.utils.TouristExclusionStrategy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class BookingDAO {
    private static final String TAG = "BookingDAO";
    private static final String USERS_COLLECTION = "users"; // Change this to your collection name

    private static final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static CollectionReference usersCollection = db.collection(USERS_COLLECTION);

    public static void insertBooking(Booking booking, String guideID, BookingCallbacks callback) {
        String collectionPath = "users/" + guideID + "/bookings";

        TouristExclusionStrategy strategy = new TouristExclusionStrategy();
        Gson gson = new GsonBuilder().setExclusionStrategies(strategy).create();
        String bookingJson = gson.toJson(booking);
        Map<String, Object> bookingMap = gson.fromJson(bookingJson, new TypeToken<Map<String, Object>>() {
        }.getType());


        db.collection(collectionPath)
                .add(bookingMap)
                .addOnSuccessListener(documentReference -> {
                    callback.onBookingInserted(true, "users/" +
                                    guideID + "/bookings/" + documentReference.getId(),
                            "success");
                }).addOnFailureListener(e -> {
                    callback.onBookingInserted(false, null, e.getMessage());
                });
    }

    public static void updateBooking(String guideID, Booking booking, BookingCallbacks callback) {
        String collectionPath = "users/" + guideID + "/bookings";

        TouristExclusionStrategy strategy = new TouristExclusionStrategy();
        Gson gson = new GsonBuilder().setExclusionStrategies(strategy).create();
        String bookingJson = gson.toJson(booking);
        Map<String, Object> bookingMap = gson.fromJson(bookingJson, new TypeToken<Map<String, Object>>() {
        }.getType());

        db.collection(collectionPath)
                .document(booking.getId())
                .set(bookingMap)
                .addOnSuccessListener(aVoid -> {
                    callback.onBookingUpdated(true, "Booking updated successfully");
                })
                .addOnFailureListener(e -> {
                    callback.onBookingUpdated(false, "Error updating booking: " + e.getMessage());

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

    public static void getBooking(String path, BookingCallbacks callbacks) {
        String[] parts = path.split("/");
        String docId = parts[3];
        String collection = String.join("/", Arrays.copyOf(parts, 3));
        db.collection(collection).document(docId).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Booking book = task.getResult().toObject(Booking.class);
                callbacks.onGetBooking(book);
            } else {
                callbacks.onGetBooking(null);
            }
        });
    }


}
