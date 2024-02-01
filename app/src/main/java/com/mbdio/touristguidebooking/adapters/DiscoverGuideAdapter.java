package com.mbdio.touristguidebooking.adapters;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mbdio.touristguidebooking.R;
import com.mbdio.touristguidebooking.adapters.viewholders.GuideItemHolder;
import com.mbdio.touristguidebooking.utils.DemoData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DiscoverGuideAdapter extends RecyclerView.Adapter<GuideItemHolder> {
    private List<Map<String, Object>> lst = DemoData.getTouristGuidesData();
    private Context ctx;

    public DiscoverGuideAdapter(Context ctx) {
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public GuideItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(ctx).inflate(R.layout.guide_item_layout, parent, false);
        return new GuideItemHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull GuideItemHolder holder, int position) {
        Map<String, Object> currentItem = lst.get(position);
        holder.bio_lbl.setText(currentItem.get("bio").toString());
        holder.name_lbl.setText(currentItem.get("name").toString());
        holder.langs_lbl.setText(String.join(", ", (String[]) currentItem.get("languages")));
        holder.rate_item.setText(currentItem.get("rating").toString());
        holder.city_lbl.setText(currentItem.get("city").toString());

        String url = "https://api.whatsapp.com/send?phone=" + currentItem.get("phone");

        holder.contact_btn.setOnClickListener(v -> {
            Toast.makeText(ctx, currentItem.get("phone").toString(), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ctx.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return lst.size();
    }
}


