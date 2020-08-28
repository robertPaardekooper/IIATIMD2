package com.example.iiatimd;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.common.util.Strings;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class ListView extends RecyclerView.Adapter<ListView.ProductViewHolder> {

    private ProductInRecycler[] products;

    public ListView(ProductInRecycler[] products){
        this.products = products;
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
            date = v.findViewById(R.id.recyclerDate);
            barcode = v.findViewById(R.id.recyclerBarcode);
            note = v.findViewById(R.id.recyclerNote);
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
        holder.name.setText(products[position].getName());
        holder.date.setText(products[position].getExpirationDate());
        holder.barcode.setText(products[position].getBarcode());

        // Notitie wordt niet weergeven als deze null is
        if (products[position].getNote() != null &&
                !products[position].getNote().isEmpty() &&
                !products[position].getNote().equals("null")) {

            holder.note.setText(products[position].getNote());
        } else {
            holder.note.setText("");
        }

        // Als product over de datum is dan wordt het rood gekleurd
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date currentDate = new Date();

        try {
            Date expirationDate = sdf.parse(products[position].getExpirationDate());

            if(currentDate.after(expirationDate)) {
                holder.date.setTextColor(Color.parseColor("#FA5858"));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return products.length;
    }
}

