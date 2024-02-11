package com.mbdio.touristguidebooking.dao;

import com.mbdio.touristguidebooking.models.Guide;

import java.util.ArrayList;

import kotlin.NotImplementedError;

public interface GuideCallbacks {

    default void onGuideInserted(boolean success, String message) {
        throw new NotImplementedError();
    }

    default void onGuideUpdated(boolean success, String message) {
        throw new NotImplementedError();
    }

    default void onGetAllGuides(ArrayList<Guide> lst) {
        throw new NotImplementedError();
    }
}
