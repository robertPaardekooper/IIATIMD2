package com.example.iiatimd;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class ListView extends RecyclerView.Adapter<ListView.ProductViewHolder> {

    private Product[] products;

    public ListView(Product[] villagers){
        this.products = villagers;
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder{
        public TextView name;
        public TextView category;
        public ProductViewHolder(View v){
            super(v);
            name = v.findViewById(R.id.recyclerName);
            category = v.findViewById(R.id.recyclerCategory);
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
        holder.name.setText(products[position].getNaam());
        holder.category.setText(products[position].getSoort());
    }

    @Override
    public int getItemCount() {
        return products.length;
    }
}
