package com.rocktech.routerecommendersystem;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;

import androidx.appcompat.app.AlertDialog;

import java.util.Locale;

public class Language {
    Activity activity;
    Context context;

    public Language(Activity activity, Context context) {
        this.activity = activity;
        this.context = context;
    }

    public void showChangeLanguageDialog() {
        final String [] listItems = {"Yoruba", "English"};
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Choose Language...");
        builder.setSingleChoiceItems(listItems, -1, (dialogInterface, i) -> {
            if (i == 0) {
                setLocale("yo");
                activity.recreate();
            }
            else if (i == 1) {
                setLocale("en");
                activity.recreate();
            }
            dialogInterface.dismiss();
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void setLocale(String lang) {
        Locale locale = new Locale(lang);
        Configuration configuration = new Configuration();
        configuration.locale = locale;
        context.getResources().updateConfiguration(configuration,
                context.getResources().getDisplayMetrics());

        SharedPreferences.Editor editor = activity.getSharedPreferences("Settings",
                Context.MODE_PRIVATE).edit();
        editor.putString("my_lang", lang);
        editor.apply();
    }

    public void loadLocale() {
        SharedPreferences preferences = activity.getSharedPreferences("Settings", Context.MODE_PRIVATE);
        String language = preferences.getString("my_lang","");
        setLocale(language);
    }

}
