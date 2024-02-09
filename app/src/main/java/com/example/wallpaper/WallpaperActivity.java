package com.example.wallpaper;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.WallpaperManager;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.io.IOException;

public class WallpaperActivity extends AppCompatActivity {
        ImageView wallpaperIV;
        Button setBtn;
        String imgUrl;
        WallpaperManager wallpaperManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallpaper);

        wallpaperIV = findViewById(R.id.idWallpaper);
        setBtn = findViewById(R.id.BtnSetWall);
        imgUrl = getIntent().getStringExtra("imgURL");
        Glide.with(this).load(imgUrl).into(wallpaperIV);

        wallpaperManager = WallpaperManager.getInstance(getApplicationContext());
        setBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Glide.with(WallpaperActivity.this).asBitmap().load(imgUrl).listener(new RequestListener<Bitmap>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                        Toast.makeText(WallpaperActivity.this, "Fail to load Image...", Toast.LENGTH_SHORT).show();
                        return false;
                    }


                    @Override
                    public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                        try {
                            wallpaperManager.setBitmap(resource);
                        }catch (IOException e){
                            e.printStackTrace();
                            Toast.makeText(WallpaperActivity.this, "Failed to load wallpaper...", Toast.LENGTH_SHORT).show();
                        }
                        return false;
                    }
                }).submit();
                Toast.makeText(WallpaperActivity.this, "Wallpaper set successfully", Toast.LENGTH_LONG).show();
            }
        });
    }
}