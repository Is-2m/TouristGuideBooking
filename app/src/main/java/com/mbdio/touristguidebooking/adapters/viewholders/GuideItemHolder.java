package com.mbdio.touristguidebooking.adapters.viewholders;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.imageview.ShapeableImageView;
import com.mbdio.touristguidebooking.R;

public class GuideItemHolder extends RecyclerView.ViewHolder {
    public ShapeableImageView pdp_img;
    public TextView  name_lbl, langs_lbl, city_lbl, bio_lbl;
    public LinearLayout book_btn, contact_btn,guide_item;

    public GuideItemHolder(@NonNull View itemView) {
        super(itemView);
        pdp_img = itemView.findViewById(R.id.item_pdp_img);
        langs_lbl = itemView.findViewById(R.id.item_langs_lbl);
        name_lbl = itemView.findViewById(R.id.item_name_lbl);
        bio_lbl = itemView.findViewById(R.id.item_bio_lbl);
        city_lbl = itemView.findViewById(R.id.item_city_lbl);
        book_btn = itemView.findViewById(R.id.item_book_btn);
        guide_item = itemView.findViewById(R.id.guide_item);
        contact_btn = itemView.findViewById(R.id.item_contact_btn);
    }
}

