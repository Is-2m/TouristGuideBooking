package com.mbdio.touristguidebooking.utils;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

public class TouristExclusionStrategy implements ExclusionStrategy {

    @Override
    public boolean shouldSkipField(FieldAttributes fieldAttributes) {
        return fieldAttributes.getName().equals("tourist");
    }

    @Override
    public boolean shouldSkipClass(Class<?> clazz) {
        return false; // Don't exclude any other classes
    }
}
