package com.mbdio.touristguidebooking.dao;

import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.mbdio.touristguidebooking.models.Tourist;
import com.mbdio.touristguidebooking.models.UserType;

public class TouristDAO {
    private static final String TAG = "TouristDAO";
    private static final String USERS_COLLECTION = "users"; // Change this to your collection name

    private static final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static CollectionReference usersCollection = db.collection(USERS_COLLECTION);


    public static void insertTourist(Tourist tourist, TouristCallbacks callback) {
        DocumentReference touristRef = usersCollection.document(tourist.getUserID());

        touristRef.set(tourist)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "Tourist inserted successfully");
                        callback.onTouristInserted(true, "Tourist inserted successfully");
                    } else {
                        Log.w(TAG, "Error inserting tourist", task.getException());
                        callback.onTouristInserted(false, "Error inserting tourist");
                    }
                });
    }

    public static void update(Tourist tourist, TouristCallbacks callback) {
        DocumentReference touristRef = usersCollection.document(tourist.getUserID());
        touristRef.set(tourist).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Log.d(TAG, "Tourist updated successfully");
                callback.onTouristUpdated(true, "Profile inserted successfully");
            } else {
                Log.w(TAG, "Error updating tourist", task.getException());
                callback.onTouristUpdated(false, "Error updating profile");
            }
        });
    }

    public static void uploadProfilePicture(String uuid, String url, TouristCallbacks callback) {
        DocumentReference ref = usersCollection.document(uuid);
        ref.update("profilePicture", url).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                callback.onProfilePictureUpdated(true);
            } else {
                callback.onProfilePictureUpdated(false);
            }
        });
    }
}
