package com.mbdio.touristguidebooking.dao;

import kotlin.NotImplementedError;

public interface GuideCallbacks {

    default void onGuideInserted(boolean success, String message) {
        throw new NotImplementedError();
    }
    default void onGuideUpdated(boolean success, String message) {
        throw new NotImplementedError();
    }
}
