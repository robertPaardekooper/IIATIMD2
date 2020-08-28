package com.example.iiatimd;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.common.util.Strings;

import org.json.JSONObject;

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
        public TextView date;
        public TextView barcode;
        public TextView note;
        public ImageButton deleteButton;

        public ProductViewHolder(View v){
            super(v);
            name = v.findViewById(R.id.recyclerName);
            date = v.findViewById(R.id.recyclerDate);
            barcode = v.findViewById(R.id.recyclerBarcode);
            note = v.findViewById(R.id.recyclerNote);
            deleteButton = v.findViewById(R.id.deleteButton);
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
    public void onBindViewHolder(@NonNull final ProductViewHolder holder, final int position) {
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
                //holder.date.setTextColor(Color.parseColor("#FA5858"));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String barcode = products[holder.getAdapterPosition()].getBarcode();

                AppDatabase db = AppDatabase.getInstance(holder.itemView.getContext());

                GetLoggedInUserEmailTask getLoggedInUserEmailTask = new GetLoggedInUserEmailTask(db);
                Thread thread = new Thread(getLoggedInUserEmailTask);
                thread.start();
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                String email = getLoggedInUserEmailTask.getEmail();

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                        "http://142.93.235.231/api/deleteProductInList/" + barcode + "/"
                                + email, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });

                VolleySingleton.getInstance(holder.itemView.getContext())
                        .addToRequestQueue(jsonObjectRequest);

                // TODO: refresh pagina
                Intent intent = new Intent (v.getContext(), List.class);
                holder.itemView.getContext().startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return products.length;
    }
}

