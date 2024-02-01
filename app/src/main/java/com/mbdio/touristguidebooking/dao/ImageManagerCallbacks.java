package com.mbdio.touristguidebooking.dao;

import kotlin.NotImplementedError;

public interface ImageManagerCallbacks {
    default void onImageUploaded(boolean success, String message, String url) {
        throw new NotImplementedError();
    }
}
