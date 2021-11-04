package com.rocktech.routerecommendersystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.rocktech.routerecommendersystem.model.Utils;
import com.rocktech.routerecommendersystem.util.LatLong;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView recycler;
    BuildingAdapter adapter;
    List<Building> buildings;
    private Utils utils;
    Language language;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        language = new Language(this, this);
        language.loadLocale();
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(getResources().getString(R.string.app_name));
        utils = new Utils(getApplicationContext());

        recycler = findViewById(R.id.recycler);
        buildings = new ArrayList<>();
        recycler.setLayoutManager(new LinearLayoutManager(this));

        buildings.add(new Building(1, R.drawable.admin, getString(R.string.admin), getString(R.string.admin_desc), LatLong.ADMIN_LATITUDE_1,LatLong.ADMIN_LONGITUDE_1));
        buildings.add(new Building(2, R.drawable.auditorium, getString(R.string.auditorium), getString(R.string.auditorium_desc), LatLong.AUDITORIUM_LATITUDE_1,LatLong.AUDITORIUM_LONGITUDE_1 ));
        buildings.add(new Building(3, R.drawable.engineering, getString(R.string.engineering), getString(R.string.engineering_desc), LatLong.ENGINEERING_LATITUDE_1,LatLong.ENGINEERING_LONGITUDE_1));
        buildings.add(new Building(4, R.drawable.entrepreneur, getString(R.string.entrepreneur), getString(R.string.entrepreneur_desc), LatLong.ENTREPRENEUR_LATITUDE_1,LatLong.ENTREPRENEUR_LONGITUDE_1));
        buildings.add(new Building(5, R.drawable.gate, getString(R.string.main_gate), getString(R.string.main_gate_desc), LatLong.GATE_LATITUDE_1,LatLong.GATE_LONGITUDE_1));
        buildings.add(new Building(6, R.drawable.health_centre, getString(R.string.health_centre), getString(R.string.health_centre_desc), LatLong.HEALTH_CENTRE_LATITUDE_1,LatLong.HEALTH_CENTRE_LONGITUDE_1));
        buildings.add(new Building(7, R.drawable.health_scientist, getString(R.string.health_scientist), getString(R.string.health_scientist_desc), LatLong.HEALTH_SCIENCES_LATITUDE_1,LatLong.HEALTH_SCIENCES_LONGITUDE_1));
        buildings.add(new Building(8, R.drawable.library, getString(R.string.library), getString(R.string.library_desc), LatLong.LIBRARY_LATITUDE_1,LatLong.LIBRARY_LONGITUDE_1));
        buildings.add(new Building(9, R.drawable.multi_purpose_complex, getString(R.string.multi_purpose), getString(R.string.multi_purpose_desc), LatLong.MULTIPURPOSE_LATITUDE_1,LatLong.MULTIPURPOSE_LONGITUDE_1));
        buildings.add(new Building(10, R.drawable.set_pathway, getString(R.string.set), getString(R.string.set_desc), LatLong.SET_LATITUDE_1,LatLong.SET_LONGITUDE_1));
        buildings.add(new Building(11, R.drawable.tetfund, getString(R.string.lecture_theatre), getString(R.string.lecture_theatre_desc), LatLong.TETFUND_LATITUDE_1,LatLong.TETFUND_LONGITUDE_1));
        buildings.add(new Building(12, R.drawable.urp, getString(R.string.urp), getString(R.string.urp_desc), LatLong.URP_LATITUDE_1,LatLong.URP_LONGITUDE_1));
        buildings.add(new Building(13, R.drawable.water, getString(R.string.water), getString(R.string.water_desc), LatLong.WATER_FACTORY_LATITUDE_1,LatLong.WATER_FACTORY_LONGITUDE_1));

        adapter = new BuildingAdapter(this, this, buildings);
        recycler.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        exitApp();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.change_lang:
                language.showChangeLanguageDialog();
                break;
            case R.id.search_menu:
                this.getSharedPreferences("database", Context.MODE_PRIVATE).edit().remove("allItems").apply();
               // recreate();
                language.loadLocale();
                utils.initDatabase();
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intent);
                break;
            case R.id.exit_menu:
                exitApp();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }


    public void exitApp(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.exit);
        builder.setMessage(R.string.exit_title);
        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
                finishAffinity();
                System.exit(0);
            }
        });
        builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // TODO: 9/11/2020 Cancel submission
            }
        });
        builder.show();
    }
}