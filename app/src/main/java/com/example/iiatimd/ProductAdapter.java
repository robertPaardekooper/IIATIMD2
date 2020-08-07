package com.example.iiatimd;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private Product[] products;

    public ProductAdapter(Product[] products){
        this.products = products;
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder{
        public TextView naamProduct;
        public TextView datumProduct;
        public ProductViewHolder(View v){
            super(v);
            naamProduct = v.findViewById(R.id.textView2);
            datumProduct = v.findViewById(R.id.textView3);
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
        holder.naamProduct.setText(products[position].getNaam());
        holder.datumProduct.setText(products[position].getNotitie());
    }

    @Override
    public int getItemCount() {
        return products.length;
    }
}
