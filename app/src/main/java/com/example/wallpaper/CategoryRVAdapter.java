package com.example.wallpaper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.ArrayList;

public class CategoryRVAdapter extends RecyclerView.Adapter<CategoryRVAdapter.ViewHolder> {
    private ArrayList<CategoryRVModel> categoryRVModelsArrayList;
    private Context context;
    private CategoryClickInterface categoryClickInterface;

    public CategoryRVAdapter(ArrayList<CategoryRVModel> categoryRVModelsArrayList, Context context, CategoryClickInterface categoryClickInterface) {
        this.categoryRVModelsArrayList = categoryRVModelsArrayList;
        this.context = context;
        this.categoryClickInterface = categoryClickInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.category_rv_item, parent,false);
        return new CategoryRVAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CategoryRVModel categoryRVModel = categoryRVModelsArrayList.get(position);
        holder.categoryTV_txt.setText(categoryRVModel.getCategory());
        Glide.with(context).load(categoryRVModel.getCategoryIVURL()).into(holder.categoryIV_img);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
             public void onClick(View view) {
                categoryClickInterface.onCategoryClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryRVModelsArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView categoryTV_txt;
        private final ImageView categoryIV_img;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryTV_txt = itemView.findViewById(R.id.TVCategory_txt);
            categoryIV_img = itemView.findViewById(R.id.IVCategory_img);

        }
    }
    public interface CategoryClickInterface{
        void onCategoryClick(int position);
    }
}
