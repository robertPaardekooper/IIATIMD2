package com.example.iiatimd;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ListViewStorage extends RecyclerView.Adapter<ListViewStorage.ProductViewHolder> {

    private Product[] products;

    public ListViewStorage(Product[] villagers){
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
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.product_card, parent, false);
        ProductViewHolder productViewHolder = new ProductViewHolder(v);
        return productViewHolder;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        holder.name.setText(products[position].getNaam());
        holder.category.setText(products[position].getSoort());
        holder.date.setText(products[position].getHoudbaarheidsdatum());
        holder.barcode.setText(products[position].getBarcode());
        holder.note.setText(products[position].getNotitie());

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();

        if(products[position].getHoudbaarheidsdatum().equals(dateFormat.format(date).toString())) {
            Log.d("hoi", products[position].getHoudbaarheidsdatum());
            holder.date.setTextColor(R.color.dateRedColor);
        }
    }

    @Override
    public int getItemCount() {
        return products.length;
    }
}

