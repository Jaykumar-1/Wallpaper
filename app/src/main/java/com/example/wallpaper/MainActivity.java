package com.example.wallpaper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements CategoryRVAdapter.CategoryClickInterface{

    private EditText searchTxt;
    private ImageView searchIV;
    private RecyclerView categoryRV,WallpaperRV;
    private ProgressBar loadingPB;
    private ArrayList<String> wallpaperArrayList;
    private ArrayList<CategoryRVModel> categoryRVModelsArrayList;
    private CategoryRVAdapter categoryRVAdapter;
    private WallpaperRVAdapter wallpaperRVAdapter;

    // api key - 1vSz9Doi5GtEiza8V8LAsCPeci64H5UM2PiprW9h2K2o9iZXHAt0xJR5


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchTxt = findViewById(R.id.EditTxt);
        searchIV = findViewById(R.id.searchIV);
        categoryRV = findViewById(R.id.RLCategory);
        WallpaperRV = findViewById(R.id.RLWallpaper);
        loadingPB = findViewById(R.id.RlLoading);

        wallpaperArrayList = new ArrayList<>();
        categoryRVModelsArrayList = new ArrayList<>();

        LinearLayoutManager linearLayoutManager =new LinearLayoutManager(MainActivity.this,RecyclerView.HORIZONTAL,false);
        categoryRV.setLayoutManager(linearLayoutManager);
        categoryRVAdapter = new CategoryRVAdapter(categoryRVModelsArrayList, this,this::onCategoryClick);
        categoryRV.setAdapter(categoryRVAdapter);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        WallpaperRV.setLayoutManager(gridLayoutManager);
        wallpaperRVAdapter = new WallpaperRVAdapter(wallpaperArrayList,this);
        WallpaperRV.setAdapter(wallpaperRVAdapter);

        getCategories();
        getWallPapers();

        searchIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String searchStr = searchTxt.getText().toString();
                if(searchStr.isEmpty()){
                    Toast.makeText(MainActivity.this, "Please enter your search Query", Toast.LENGTH_SHORT).show();
                }
                else{
                    getWallpaperByCategory(searchStr);
                }
            }
        });
    }

    private void getWallpaperByCategory(String category){
        wallpaperArrayList.clear();
        loadingPB.setVisibility(View.VISIBLE);
        String url="https://api.pexels.com/v1/search?query="+category+"&per_page=200&page=1";
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        JsonObjectRequest jsonObjectRequest =   new JsonObjectRequest(Request.Method.GET,url,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONArray photoarray = null;
                try {
                     photoarray = response.getJSONArray("photos");
                    for(int i=0;i<photoarray.length();i++){
                        JSONObject photoOBJ = photoarray.getJSONObject(i);
                        String imgUrl = photoOBJ.getJSONObject("src").getString("portrait");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Fail to load wallpapers...", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String,String> headers = new HashMap<>();
                headers.put("","");
                return headers;
            }
        };
        requestQueue.add(jsonObjectRequest);
    }

    private void getCategories(){
        categoryRVModelsArrayList.add(new CategoryRVModel("Wallpaper","https://images.pexels.com/photos/14668285/pexels-photo-14668285.jpeg?auto=compress&cs=tinysrgb&w=600&lazy=load"));
        categoryRVModelsArrayList.add(new CategoryRVModel("Nature","https://images.pexels.com/photos/3573351/pexels-photo-3573351.png?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2"));
        categoryRVModelsArrayList.add(new CategoryRVModel("Galaxy","https://images.pexels.com/photos/1252890/pexels-photo-1252890.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2"));
        categoryRVModelsArrayList.add(new CategoryRVModel("4K","https://images.pexels.com/photos/1402787/pexels-photo-1402787.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2"));
        categoryRVModelsArrayList.add(new CategoryRVModel("Mobile","https://images.pexels.com/photos/799443/pexels-photo-799443.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2/"));
        categoryRVModelsArrayList.add(new CategoryRVModel("Desktop","https://images.pexels.com/photos/1933239/pexels-photo-1933239.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2"));
        categoryRVModelsArrayList.add(new CategoryRVModel("Programming","https://images.pexels.com/photos/2004161/pexels-photo-2004161.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2"));
        categoryRVModelsArrayList.add(new CategoryRVModel("Black Background","https://images.pexels.com/photos/396547/pexels-photo-396547.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2"));
        categoryRVModelsArrayList.add(new CategoryRVModel("Photoshop","https://images.pexels.com/photos/2244330/pexels-photo-2244330.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2"));

        categoryRVAdapter.notifyDataSetChanged();

    }

    private void getWallPapers(){
        wallpaperArrayList.clear();
        loadingPB.setVisibility(View.VISIBLE);
        String url ="https://api.pexels.com/v1/curated?per_page=200&page=1";
        RequestQueue requestQueue  = Volley.newRequestQueue(MainActivity.this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Method.GET,url,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                loadingPB.setVisibility(View.GONE);
                try {
                    JSONArray photoArray  = response.getJSONArray("photos");
                    for(int i=0;i<photoArray.length();i++){
                        JSONObject photoObj  =photoArray.getJSONObject(i);
                        String imgUrl = photoObj.getJSONObject("src").getString("portrait");
                        wallpaperArrayList.add(imgUrl);
                    }
                    wallpaperRVAdapter.notifyDataSetChanged();
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Fail to load wallpapers...", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String,String> headers = new HashMap<>();
                headers.put("Authorization","1vSz9Doi5GtEiza8V8LAsCPeci64H5UM2PiprW9h2K2o9iZXHAt0xJR5");
                return headers;
            }
        };
        requestQueue.add(jsonObjectRequest);
    }

    @Override
    public void onCategoryClick(int position) {
        String category = categoryRVModelsArrayList.get(position).getCategory();
        getWallpaperByCategory(category);
    }
}