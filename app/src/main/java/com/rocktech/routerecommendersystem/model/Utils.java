package com.rocktech.routerecommendersystem.model;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rocktech.routerecommendersystem.Building;
import com.rocktech.routerecommendersystem.Language;
import com.rocktech.routerecommendersystem.R;
import com.rocktech.routerecommendersystem.util.LatLong;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Utils {
    private static final String TAG = "Utils";
    public static final String DATABASE_NAME = "database";
     Context context;
     Activity activity;


    public Utils(Context context) {
        this.context = context;
    }

    public Utils(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;
    }

    private void initAllItems() {
        Log.d(TAG, "initAllItems: started");
        SharedPreferences sharedPreferences = context.getSharedPreferences(DATABASE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();

        List<Building> buildings = new ArrayList<>();

        buildings.add(new Building(1, R.drawable.admin, activity.getResources().
                getString(R.string.admin), activity.getResources().getString(R.string.admin_desc), LatLong.ADMIN_LATITUDE_1,LatLong.ADMIN_LONGITUDE_1));
        buildings.add(new Building(2, R.drawable.auditorium, activity.getResources().
                getString(R.string.auditorium), activity.getResources().getString(R.string.auditorium_desc), LatLong.AUDITORIUM_LATITUDE_1,LatLong.AUDITORIUM_LONGITUDE_1 ));
        buildings.add(new Building(3, R.drawable.engineering, activity.getResources().
                getString(R.string.engineering), activity.getResources().getString(R.string.engineering_desc),LatLong.ENGINEERING_LATITUDE_1,LatLong.ENGINEERING_LONGITUDE_1));
        buildings.add(new Building(4, R.drawable.entrepreneur, activity.getResources().
                getString(R.string.entrepreneur), activity.getResources().getString(R.string.entrepreneur_desc),LatLong.ENTREPRENEUR_LATITUDE_1,LatLong.ENTREPRENEUR_LONGITUDE_1));
        buildings.add(new Building(5, R.drawable.gate, activity.getResources().
                getString(R.string.main_gate), activity.getResources().getString(R.string.main_gate_desc),LatLong.GATE_LATITUDE_1,LatLong.GATE_LONGITUDE_1));
        buildings.add(new Building(6, R.drawable.health_centre, activity.getResources().
                getString(R.string.health_centre), activity.getResources().getString(R.string.health_centre_desc),LatLong.HEALTH_CENTRE_LATITUDE_1,LatLong.HEALTH_CENTRE_LONGITUDE_1));
        buildings.add(new Building(7, R.drawable.health_scientist, activity.getResources().
                getString(R.string.health_scientist), activity.getResources().getString(R.string.health_scientist_desc),LatLong.HEALTH_SCIENCES_LATITUDE_1,LatLong.HEALTH_SCIENCES_LONGITUDE_1));
        buildings.add(new Building(8, R.drawable.library, activity.getResources().
                getString(R.string.library), activity.getResources().getString(R.string.library_desc),LatLong.LIBRARY_LATITUDE_1,LatLong.LIBRARY_LONGITUDE_1));
        buildings.add(new Building(9, R.drawable.multi_purpose_complex, activity.getResources().
                getString(R.string.multi_purpose), activity.getResources().getString(R.string.multi_purpose_desc),LatLong.MULTIPURPOSE_LATITUDE_1,LatLong.MULTIPURPOSE_LONGITUDE_1));
        buildings.add(new Building(10, R.drawable.set_pathway, activity.getResources().
                getString(R.string.set), activity.getResources().getString(R.string.set_desc),LatLong.SET_LATITUDE_1,LatLong.SET_LONGITUDE_1 ));
        buildings.add(new Building(11, R.drawable.tetfund, activity.getResources().
                getString(R.string.lecture_theatre), activity.getResources().getString(R.string.lecture_theatre_desc),LatLong.TETFUND_LATITUDE_1,LatLong.TETFUND_LONGITUDE_1));
        buildings.add(new Building(12, R.drawable.urp, activity.getResources().
                getString(R.string.urp), activity.getResources().getString(R.string.urp_desc),LatLong.URP_LATITUDE_1,LatLong.URP_LONGITUDE_1));
        buildings.add(new Building(13, R.drawable.water, activity.getResources().
                getString(R.string.water), activity.getResources().getString(R.string.water_desc),LatLong.WATER_FACTORY_LATITUDE_1,LatLong.WATER_FACTORY_LONGITUDE_1));

        String finalList = gson.toJson(buildings);
        editor.putString("allItems", finalList);
        editor.apply();
    }

    public void initDatabase() {
        Log.d(TAG, "initDatabase: Stored");
        SharedPreferences sharedPreferences = context.getSharedPreferences(DATABASE_NAME,
                Context.MODE_PRIVATE);

        Gson gson = new Gson();

        Type type = new TypeToken<List<Building>>() {
        }.getType();

        List<Building> possibleItems = gson.fromJson(
                sharedPreferences.getString("allItems", ""), type);

        if (possibleItems == null) {
            initAllItems();
        }

    }

    public List<Building> searchForItem(String text) {
        Log.d(TAG, "searchForItem: ");

        SharedPreferences sharedPreferences = context.getSharedPreferences(DATABASE_NAME,
                Context.MODE_PRIVATE);
        Gson gson = new Gson();
        Type type = new TypeToken<List<Building>>() {
        }.getType();
        List<Building> items = gson.fromJson(sharedPreferences.getString("allItems", null)
                , type);

        List<Building> searchItems = new ArrayList<>();

        if (null != items) {
            for (Building item : items) {
                if (item.getName().equalsIgnoreCase(text)) {
                    searchItems.add(item);
                }

                String[] splittedString = item.getName().split(" ");
                for (int i = 0; i < splittedString.length; i++) {
                    if (splittedString[i].equalsIgnoreCase(text)) {
                        boolean doesExist = false;
                        for (Building searchItem : searchItems) {
                            if (searchItem.equals(item)) {
                                doesExist = true;
                            }
                        }
                        if (!doesExist) {
                            searchItems.add(item);
                        }
                    }
                }
            }
        }
        return searchItems;
    }
}
