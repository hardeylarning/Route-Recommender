package com.rocktech.routerecommendersystem.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rocktech.routerecommendersystem.Building;
import com.rocktech.routerecommendersystem.R;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Utils {
    private static final String TAG = "Utils";
    public static final String DATABASE_NAME = "database";
    private final Context context;

    public Utils(Context context) {
        this.context = context;
    }

    private void initAllItems() {
        Log.d(TAG, "initAllItems: started");
        SharedPreferences sharedPreferences = context.getSharedPreferences(DATABASE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();

        List<Building> buildings = new ArrayList<>();

        buildings.add(new Building(1, R.drawable.admin, context.
                getResources().getString(R.string.admin), "description",7.7615777,4.53122));
        buildings.add(new Building(2, R.drawable.auditorium, context.getResources().
                getString(R.string.auditorium), "description",7.7628282,4.5299585 ));
        buildings.add(new Building(3, R.drawable.engineering, context.getResources().
                getString(R.string.engineering), "description",7.7589025,4.5319887));
        buildings.add(new Building(4, R.drawable.entrepreneur, context.getResources().
                getString(R.string.entrepreneur), "description",7.7616921,4.5336781));
        buildings.add(new Building(5, R.drawable.gate, context.getResources().
                getString(R.string.main_gate), "description",7.7674823,4.5329037));
        buildings.add(new Building(6, R.drawable.health_centre, context.getResources().
                getString(R.string.health_centre), "description",7.7616921,4.532392));
        buildings.add(new Building(7, R.drawable.health_scientist, context.getResources().
                getString(R.string.health_scientist), "description",7.7561739,4.5342213));
        buildings.add(new Building(8, R.drawable.library, context.getResources().
                getString(R.string.library), "description",7.7612279,4.5339493));
        buildings.add(new Building(9, R.drawable.multi_purpose_complex, context.getResources().
                getString(R.string.multi_purpose), "description",7.7585265,4.5336781));
        buildings.add(new Building(10, R.drawable.set_pathway, context.getResources().
                getString(R.string.set), "description",7.7611055,4.5318274 ));
        buildings.add(new Building(11, R.drawable.tetfund, context.getResources().
                getString(R.string.lecture_theatre), "description",7.7616921,4.532392));
        buildings.add(new Building(12, R.drawable.urp, context.getResources().
                getString(R.string.urp), "description",7.7443465,4.5078785));
        buildings.add(new Building(13, R.drawable.water, context.getResources().
                getString(R.string.water), "description",7.7616921,4.532392));

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

    public List<Building> getAllItems() {
        Log.d(TAG, "allItems: started");
        Gson gson = new Gson();
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                DATABASE_NAME, Context.MODE_PRIVATE);
        Type type = new TypeToken<List<Building>>() {
        }.getType();
        return gson.fromJson(sharedPreferences.getString("allItems", null), type);
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
