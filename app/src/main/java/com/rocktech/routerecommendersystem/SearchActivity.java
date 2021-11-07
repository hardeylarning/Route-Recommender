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
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.rocktech.routerecommendersystem.model.Utils;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    RecyclerView recycler;
    TextView notFound;
    EditText searchText;
    Button searchButton;
    SearchAdapter adapter;
    List<Building> buildings;
    private Utils utils;
    Language language;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        language = new Language(this, this);
        language.loadLocale();
        setContentView(R.layout.activity_search);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(getResources().getString(R.string.app_name));

//        utils = new Utils(getApplicationContext(), this);
//        this.getSharedPreferences("database", Context.MODE_PRIVATE).edit().remove("allItems").apply();
//        utils.initDatabase();

        recycler = findViewById(R.id.searchReport);
        notFound = findViewById(R.id.notFound);
        searchText = findViewById(R.id.textSearch);
        searchButton = findViewById(R.id.btnSearch);

        recycler.setLayoutManager(new LinearLayoutManager(this));

        searchButton.setOnClickListener(view -> {
            utils = new Utils(getApplicationContext(), this);
            this.getSharedPreferences("database", Context.MODE_PRIVATE).edit().remove("allItems").apply();
            language.loadLocale();
            utils.initDatabase();
            String text = searchText.getText().toString();
            buildings = utils.searchForItem(text);
            if (buildings.size() < 1)
            {
                notFound.setVisibility(View.VISIBLE);
                notFound.setText(R.string.no_search_found);
                recycler.setVisibility(View.INVISIBLE);
            }
            else {
                notFound.setVisibility(View.GONE);
                recycler.setVisibility(View.VISIBLE);
                adapter = new SearchAdapter(this, this, buildings);
                recycler.setAdapter(adapter);
            }
        });



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
            case R.id.home:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.change_lang:
                language.showChangeLanguageDialog();
                language.loadLocale();
                new Handler().postDelayed(() -> {

                    Intent intentReturn = new Intent(this, MainActivity.class);
                    startActivity(intentReturn);
                }, 5000);
                break;
            case R.id.search_menu:
                Toast.makeText(this, "Already here",
                        Toast.LENGTH_SHORT).show();
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
        builder.setPositiveButton("YES", (dialogInterface, i) -> {
            finish();
            finishAffinity();
            System.exit(0);
        });
        builder.setNegativeButton("NO", (dialogInterface, i) -> {
            // TODO: 9/11/2020 Cancel submission
        });
        builder.show();
    }
}