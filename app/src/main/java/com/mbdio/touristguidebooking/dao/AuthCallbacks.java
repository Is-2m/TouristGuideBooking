package com.mbdio.touristguidebooking.dao;

import com.google.firebase.auth.FirebaseUser;

import kotlin.NotImplementedError;

public interface AuthCallbacks {
    default void onLoginComplete(FirebaseUser user) {
        throw new NotImplementedError();
    }

    default void onRegisterComplete(FirebaseUser user) {
        throw new NotImplementedError();
    }
}
