package com.rocktech.routerecommendersystem;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {
    Context context;
    Activity activity;
    List<Building> buildings;

    public SearchAdapter(Context context, Activity activity, List<Building> buildings) {
        this.context = context;
        this.activity = activity;
        this.buildings = buildings;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.building, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name.setText(buildings.get(position).getName());
        Glide.with(context).asBitmap()
                .load(buildings.get(position).getImage_url())
                .centerCrop()
                .into(holder.image);
        holder.build.setOnClickListener(view -> {
            Intent intent = new Intent(context, DisplayActivity.class);
            intent.putExtra("name", buildings.get(position).getName());
            intent.putExtra("description", buildings.get(position).getDescription());
            intent.putExtra("longitude", buildings.get(position).getLongitude());
            intent.putExtra("latitude", buildings.get(position).getLatitude());
            intent.putExtra("image_url", buildings.get(position).getImage_url());
            activity.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return buildings.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView image;
        private TextView name;
        private CardView build;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            name = itemView.findViewById(R.id.name);
            build = itemView.findViewById(R.id.building);
        }
    }
}
