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

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Utils {
    private static final String TAG = "Utils";
    public static final String DATABASE_NAME = "database";
    private final Context context;
    private Activity activity;
    Language language;


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

        buildings.add(new Building(1, R.drawable.admin, context.getResources().getString(R.string.admin), context.getResources().getString(R.string.admin_desc),7.761708,4.6014596));
        buildings.add(new Building(2, R.drawable.auditorium, context.getResources().
                getString(R.string.auditorium), context.getResources().getString(R.string.auditorium_desc),7.762993,4.5999549 ));
        buildings.add(new Building(3, R.drawable.engineering, context.getResources().
                getString(R.string.engineering), context.getResources().getString(R.string.engineering_desc),7.7590357,4.6019182));
        buildings.add(new Building(4, R.drawable.entrepreneur, context.getResources().
                getString(R.string.entrepreneur), context.getResources().getString(R.string.entrepreneur_desc),7.7628716,4.5964974));
        buildings.add(new Building(5, R.drawable.gate, context.getResources().
                getString(R.string.main_gate), context.getResources().getString(R.string.main_gate_desc),7.7675821,4.6029815));
        buildings.add(new Building(6, R.drawable.health_centre, context.getResources().
                getString(R.string.health_centre), context.getResources().getString(R.string.health_centre_desc),7.7648985,4.6011351));
        buildings.add(new Building(7, R.drawable.health_scientist, context.getResources().
                getString(R.string.health_scientist), context.getResources().getString(R.string.health_scientist_desc),7.7567254,4.6044771));
        buildings.add(new Building(8, R.drawable.library, context.getResources().
                getString(R.string.library), context.getResources().getString(R.string.library_desc),7.7612459,4.6040279));
        buildings.add(new Building(9, R.drawable.multi_purpose_complex, context.getResources().
                getString(R.string.multi_purpose), context.getResources().getString(R.string.multi_purpose_desc),7.7585322,4.6037187));
        buildings.add(new Building(10, R.drawable.set_pathway, context.getResources().
                getString(R.string.set), context.getResources().getString(R.string.set_desc),7.7609505,4.6017573 ));
        buildings.add(new Building(11, R.drawable.tetfund, context.getResources().
                getString(R.string.lecture_theatre), context.getResources().getString(R.string.lecture_theatre_desc),7.7587524,4.6031735));
        buildings.add(new Building(12, R.drawable.urp, context.getResources().
                getString(R.string.urp), context.getResources().getString(R.string.urp_desc),7.7564098,4.5990049));
        buildings.add(new Building(13, R.drawable.water, context.getResources().
                getString(R.string.water), context.getResources().getString(R.string.water_desc),7.7628716,4.5964974));

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
