package com.mbdio.touristguidebooking.dao;

import kotlin.NotImplementedError;

public interface TouristCallbacks {
    default void onTouristInserted(boolean success, String message) {
        throw new NotImplementedError();
    }

    default void onTouristUpdated(boolean success, String message) {
        throw new NotImplementedError();
    }

    default void onProfilePictureUpdated(boolean success) {
        throw new NotImplementedError();
    }
}
