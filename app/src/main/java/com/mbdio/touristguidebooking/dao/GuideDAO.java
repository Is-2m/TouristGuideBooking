package com.mbdio.touristguidebooking.dao;

import android.util.Log;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.mbdio.touristguidebooking.models.Guide;
import com.mbdio.touristguidebooking.models.Monument;
import com.mbdio.touristguidebooking.models.Tourist;
import com.mbdio.touristguidebooking.models.UserType;

import java.util.ArrayList;

public class GuideDAO {
    private static final String TAG = "GuideDAO";
    private static final String USERS_COLLECTION = "users"; // Change this to your collection name

    private static final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static CollectionReference usersCollection = db.collection(USERS_COLLECTION);


    public static void insertGuide(Guide guide, GuideCallbacks callback) {
        DocumentReference newTouristRef = usersCollection.document(guide.getUserID());

        newTouristRef.set(guide)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "Tourist inserted successfully");
                        callback.onGuideInserted(true, "guide inserted successfully");
                    } else {
                        Log.w(TAG, "Error inserting guide", task.getException());
                        callback.onGuideInserted(false, "Error inserting guide");
                    }
                });
    }

    public static void update(Guide guide, GuideCallbacks callback) {
        DocumentReference touristRef = usersCollection.document(guide.getUserID());
        touristRef.set(guide).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Log.d(TAG, "Tourist updated successfully");
                callback.onGuideUpdated(true, "Profile inserted successfully");
            } else {
                Log.w(TAG, "Error updating tourist", task.getException());
                callback.onGuideUpdated(false, "Error updating profile");
            }
        });
    }

    public static void getAllGuides(GuideCallbacks callbacks) {
        ArrayList<Guide> lst = new ArrayList<>();
        usersCollection.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                QuerySnapshot querySnapshot = task.getResult();
                if (querySnapshot != null) {
                    for (QueryDocumentSnapshot document : querySnapshot) {
                        if (document.get("userType").toString().contentEquals(UserType.GUIDE.name())) {
                            Guide pojo = document.toObject(Guide.class);
                            lst.add(pojo);
                        }
                    }
                    callbacks.onGetAllGuides(lst);
                }
            }
        });
    }

}
