package com.rocktech.routerecommendersystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class DisplayActivity extends AppCompatActivity {

    private String name, description;
    private double latitude, longitude;
    private ImageView image;
    private TextView txtName, txtDescription;
    private Button getDirection;
    Language language;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        language = new Language(this, this);
        language.loadLocale();

        setContentView(R.layout.activity_display);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(getResources().getString(R.string.app_name));
        image = findViewById(R.id.image);
        txtName = findViewById(R.id.name);
        txtDescription = findViewById(R.id.description);
        getDirection = findViewById(R.id.btnGetDirection);

        getIntentData();

        getDirection.setOnClickListener(view ->{
            Intent intent = new Intent(DisplayActivity.this, DetailActivity.class);
            intent.putExtra("name", name);
            intent.putExtra("latitude", latitude);
            intent.putExtra("longitude", longitude);
            startActivity(intent);
        } );

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
                break;
            case R.id.search_menu:
                Intent intent1 = new Intent(this, SearchActivity.class);
                startActivity(intent1);
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

    private void getIntentData() {
        if (getIntent().hasExtra("latitude"))
        {
            name = getIntent().getStringExtra("name");
            description = getIntent().getStringExtra("description");

            txtName.setText(name);
            txtDescription.setText(description);

            latitude = getIntent().getDoubleExtra("latitude", 0);
            longitude = getIntent().getDoubleExtra("longitude", 0);

            Glide.with(this).asBitmap()
                    .load(getIntent().getIntExtra("image_url", -1))
                    .centerCrop()
                    .into(image);
        }
        else {
            Toast.makeText(this, "No Intent Data Found", Toast.LENGTH_SHORT).show();
        }
    }
}