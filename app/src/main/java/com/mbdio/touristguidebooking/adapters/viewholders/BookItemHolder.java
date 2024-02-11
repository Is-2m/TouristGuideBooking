package com.mbdio.touristguidebooking.adapters.viewholders;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.imageview.ShapeableImageView;
import com.mbdio.touristguidebooking.R;

public class BookItemHolder extends RecyclerView.ViewHolder {
    public ShapeableImageView pdp_img;
    public TextView name_lbl, date_lbl, nation_lbl, phone_lbl;
    public LinearLayout accept_btn, deny_btn, book_item, phone_btn;

    public BookItemHolder(@NonNull View itemView) {
        super(itemView);
        pdp_img = itemView.findViewById(R.id.item_book_pdp_img);
        date_lbl = itemView.findViewById(R.id.item_book_date_lbl);
        name_lbl = itemView.findViewById(R.id.item_book_name_lbl);
        nation_lbl = itemView.findViewById(R.id.item_book_nation_lbl);
        accept_btn = itemView.findViewById(R.id.item_book_accept_btn);
        deny_btn = itemView.findViewById(R.id.item_book_deny_btn);
        book_item = itemView.findViewById(R.id.book_item);
        phone_btn = itemView.findViewById(R.id.item_book_phone);
        phone_lbl = itemView.findViewById(R.id.item_book_phone_lbl);
    }
}

