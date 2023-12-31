package com.mbdio.touristguidebooking.dao;

import android.util.Log;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;
import com.mbdio.touristguidebooking.models.Tourist;
import com.mbdio.touristguidebooking.models.User;
import com.mbdio.touristguidebooking.models.UserType;

public class UserDAO {
    private static final String TAG = "UserDAO";
    private static final String USERS_COLLECTION = "users"; // Change this to your collection name

    private static final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final CollectionReference usersCollection = db.collection(USERS_COLLECTION);


    public static void getUser(String uid, UserCallbacks callback) {
        Gson gson = new Gson();
        DocumentReference userRef = usersCollection.document(uid);
        userRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.get("userType").toString().contentEquals(UserType.TOURIST.name())) {
                    User user = document.toObject(Tourist.class);
                    callback.onGetUser(user);
                }
            } else {
                callback.onGetUser(null);
            }
        });
    }
}
