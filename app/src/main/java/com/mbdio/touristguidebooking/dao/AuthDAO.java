package com.mbdio.touristguidebooking.dao;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mbdio.touristguidebooking.MainActivity;
import com.mbdio.touristguidebooking.activities.LoginActivity;

public class AuthDAO {
    private static FirebaseAuth fAuth;

    //    public static Async
    public static void login(Activity activity, String email, String password, AuthCallbacks callback) {
        fAuth = FirebaseAuth.getInstance();
        fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(activity, task -> {
            FirebaseUser user = task.isSuccessful() ? fAuth.getCurrentUser() : null;
            callback.onLoginComplete(user);
        });
    }

    public static void register(Activity activity, String email, String password, AuthCallbacks callback) {
        fAuth = FirebaseAuth.getInstance();
        System.out.println("AuthDAO.register");
        System.out.println(email + "  " + password);
        fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(activity, task -> {
            FirebaseUser user = task.isSuccessful() ? fAuth.getCurrentUser() : null;
            System.out.println("AuthDAO.register.addOnCompleteListener");
            System.out.println(task.getException());
            callback.onRegisterComplete(user);
        });
    }
}
