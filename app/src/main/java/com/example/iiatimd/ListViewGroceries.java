package com.example.iiatimd;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class ListViewGroceries extends RecyclerView.Adapter<ListViewStorage.ProductViewHolder> {

    private Product[] products;

    public ListViewGroceries(Product[] villagers){
        this.products = villagers;
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder{
        public TextView name;
        public TextView category;
        public TextView date;
        public TextView barcode;
        public TextView note;
        public ProductViewHolder(View v){
            super(v);
            name = v.findViewById(R.id.recyclerName);
            category = v.findViewById(R.id.recyclerCategory);
            date = v.findViewById(R.id.recyclerDate);
            barcode = v.findViewById(R.id.recyclerBarcode);
            note = v.findViewById(R.id.recyclerNote);
        }
    }

    @NonNull
    @Override
    public ListViewStorage.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.product_card, parent, false);
        ListViewStorage.ProductViewHolder productViewHolder = new ListViewStorage.ProductViewHolder(v);
        return productViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewStorage.ProductViewHolder holder, int position) {
        holder.name.setText(products[position].getNaam());
        holder.category.setText(products[position].getSoort());
        holder.barcode.setText(products[position].getBarcode());
        holder.note.setText(products[position].getNotitie());
    }

    @Override
    public int getItemCount() {
        return products.length;
    }
}
