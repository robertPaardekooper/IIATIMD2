package com.example.iiatimd;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class ListView extends RecyclerView.Adapter<ListView.ProductViewHolder> {

    private String[] products;

    public ListView(String[] villagers){
        this.products = villagers;
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder{
        public TextView textView;
        public ProductViewHolder(View v){
            super(v);
            textView = v.findViewById(R.id.textView2);
        }
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.product_card, parent, false);
        ProductViewHolder productViewHolder = new ProductViewHolder(v);
        return productViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        holder.textView.setText(products[position]);
    }

    @Override
    public int getItemCount() {
        return products.length;
    }
}
