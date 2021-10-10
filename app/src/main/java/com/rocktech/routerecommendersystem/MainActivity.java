package com.rocktech.routerecommendersystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.rocktech.routerecommendersystem.model.Utils;

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

        buildings.add(new Building(1, R.drawable.admin, getString(R.string.admin), "description", 7.7615777,4.53122));
        buildings.add(new Building(2, R.drawable.auditorium, getString(R.string.auditorium), "description", 7.7628282,4.5299585 ));
        buildings.add(new Building(3, R.drawable.engineering, getString(R.string.engineering), "description", 7.7589025,4.5319887));
        buildings.add(new Building(4, R.drawable.entrepreneur, getString(R.string.entrepreneur), "description", 7.7616921,4.5336781));
        buildings.add(new Building(5, R.drawable.gate, getString(R.string.main_gate), "description", 7.7674823,4.5329037));
        buildings.add(new Building(6, R.drawable.health_centre, getString(R.string.health_centre), "description", 7.7616921,4.532392));
        buildings.add(new Building(7, R.drawable.health_scientist, getString(R.string.health_scientist), "description", 7.7561739,4.5342213));
        buildings.add(new Building(8, R.drawable.library, getString(R.string.library), "description", 7.7612279,4.5339493));
        buildings.add(new Building(9, R.drawable.multi_purpose_complex, getString(R.string.multi_purpose), "description", 7.7585265,4.5336781));
        buildings.add(new Building(10, R.drawable.set_pathway, getString(R.string.set), "description", 7.7611055,4.5318274 ));
        buildings.add(new Building(11, R.drawable.tetfund, getString(R.string.lecture_theatre), "description", 7.7616921,4.532392));
        buildings.add(new Building(12, R.drawable.urp, getString(R.string.urp), "description", 7.7443465,4.5078785));
        buildings.add(new Building(13, R.drawable.water, getString(R.string.water), "description", 7.7616921,4.532392));

        adapter = new BuildingAdapter(this, this, buildings);
        recycler.setAdapter(adapter);
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
        builder.setTitle("EXIT");
        builder.setMessage("Are you sure you want to exit?");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
                finishAffinity();
                System.exit(0);
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // TODO: 9/11/2020 Cancel submission
            }
        });
        builder.show();
    }
}