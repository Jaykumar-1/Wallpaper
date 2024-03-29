package com.example.wallpaper;

import android.content.Context;
import android.content.Intent;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class WallpaperRVAdapter extends RecyclerView.Adapter<WallpaperRVAdapter.Viewholder> {

    private ArrayList<String> wallpaperArrayList;
    private Context context;

    public WallpaperRVAdapter(ArrayList<String> wallpaperArrayList, Context context) {
        this.wallpaperArrayList = wallpaperArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(context).inflate(R.layout.wallpaper_rv_item,parent,false);
        return new WallpaperRVAdapter.Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        Glide.with(context).load(wallpaperArrayList.get(position)).into(holder.WallpaperIV);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,WallpaperActivity.class);
                intent.putExtra("imgURL", wallpaperArrayList.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return wallpaperArrayList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        private ImageView WallpaperIV;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            WallpaperIV = itemView.findViewById(R.id.wallpaperDisplay);
        }
    }
}
