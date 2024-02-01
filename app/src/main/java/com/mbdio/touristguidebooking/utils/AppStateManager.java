package com.mbdio.touristguidebooking.utils;


import android.content.Context;
import android.content.SharedPreferences;


import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mbdio.touristguidebooking.models.Tourist;
import com.mbdio.touristguidebooking.models.User;

import org.json.JSONObject;

public class AppStateManager {
    private static final String currentUser_CacheName = "currentUser";

    private static final String CACHE_NAME = "app_state_cache";
    static SharedPreferences cache;
    static FirebaseUser currentFireUser;
    static User currentUser;

    public static void initialize(Context ctx) {
        // Cache initialisation
        if (cache == null) {
            cache = ctx.getSharedPreferences(CACHE_NAME, ctx.MODE_PRIVATE);
        }

        Gson gson = new Gson();

        // checking if user is already logged and stored in cache
        String jsonUser = cache.getString(currentUser_CacheName, null);
        if (jsonUser != null) {
            if ((gson.fromJson(jsonUser, JsonObject.class))
                    .get("userType").toString().contains("TOURIST")) {
                System.out.println("AppStateManager.initialize");
                System.out.println(jsonUser);
                currentUser = gson.fromJson(jsonUser, Tourist.class);

            } else if ((gson.fromJson(jsonUser, JsonObject.class))
                    .get("userType").toString().contains("GUIDE")) {
                // cast to GUIDE class. something like 👇👇
//                 currentUser = gson.fromJson(jsonUser, Guide.class);
            }
        }


    }

    public static void clearCache() {
        cache.edit().clear().apply();
        AppStateManager.currentUser = null;
        AppStateManager.currentFireUser = null;
    }

    public static SharedPreferences getCache() {
        return cache;
    }


    public static FirebaseUser getCurrentFireUser() {
        return currentFireUser;
    }

    public static void setCurrentFireUser(FirebaseUser currentFireUser) {
        AppStateManager.currentFireUser = currentFireUser;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User currentUser) { //Update currentUSer and save it to cache
        AppStateManager.currentUser = currentUser;

        Gson gson = new Gson();
        cache.edit().putString(currentUser_CacheName, gson.toJson(currentUser)).apply();
    }
}
