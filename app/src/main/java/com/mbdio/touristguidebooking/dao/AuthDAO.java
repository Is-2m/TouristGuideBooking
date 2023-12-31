package com.mbdio.touristguidebooking.dao;

import android.app.Activity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;
import com.mbdio.touristguidebooking.utils.AppStateManager;

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
        fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(activity, task -> {
            FirebaseUser user = task.isSuccessful() ? fAuth.getCurrentUser() : null;
            callback.onRegisterComplete(user);
        });
    }

    public static void confirmEmail(Activity activity, AuthCallbacks callback) {
        fAuth = FirebaseAuth.getInstance();
        FirebaseUser user = fAuth.getCurrentUser();
        user.sendEmailVerification().addOnCompleteListener(activity, task -> {

        });
    }

    // Checks if user is already in app's cache,
    // if not checks user already connected based on Firebase's cache
    public static void checkIsLogged(AuthCallbacks callback) {
        FirebaseUser user = AppStateManager.getCurrentFireUser();

        if (user != null) {
            callback.onCheckLoggedComplete(user);
        } else {
            fAuth = FirebaseAuth.getInstance();
            user = fAuth.getCurrentUser();
            callback.onCheckLoggedComplete(user);
        }
    }

    public static void logout() {
        fAuth = FirebaseAuth.getInstance();
        fAuth.signOut();
        AppStateManager.clearCache();
    }

}
