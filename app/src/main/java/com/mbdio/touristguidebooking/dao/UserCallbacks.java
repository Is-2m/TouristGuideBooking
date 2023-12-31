package com.mbdio.touristguidebooking.dao;

import com.mbdio.touristguidebooking.models.User;

import kotlin.NotImplementedError;

public interface UserCallbacks {
    default void onGetUser(User user) {
        throw new NotImplementedError();
    }
}
