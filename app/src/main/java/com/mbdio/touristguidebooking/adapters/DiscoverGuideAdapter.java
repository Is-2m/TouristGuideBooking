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

import com.bumptech.glide.Glide;
import com.mbdio.touristguidebooking.R;
import com.mbdio.touristguidebooking.activities.ViewProfileActivity;
import com.mbdio.touristguidebooking.activities.fragments.ProfileFragment;
import com.mbdio.touristguidebooking.adapters.viewholders.GuideItemHolder;
import com.mbdio.touristguidebooking.models.Guide;
import com.mbdio.touristguidebooking.utils.AppStateManager;
import com.mbdio.touristguidebooking.utils.DemoData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DiscoverGuideAdapter extends RecyclerView.Adapter<GuideItemHolder> {
    private List<Guide> lst = new ArrayList<>();
    private Context ctx;

    public DiscoverGuideAdapter(Context ctx, ArrayList<Guide> guides) {
        this.ctx = ctx;
        lst = guides;
    }

    @NonNull
    @Override
    public GuideItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(ctx).inflate(R.layout.guide_item_layout, parent, false);
        return new GuideItemHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull GuideItemHolder holder, int position) {
        Guide curntGuide = lst.get(position);
        String name = curntGuide.getFirstName() + " " + curntGuide.getLastName();
        holder.bio_lbl.setText(curntGuide.getBio());
        holder.name_lbl.setText(name);
        holder.langs_lbl.setText(curntGuide.getLanguages());
        holder.city_lbl.setText(curntGuide.getLocation());

        Glide.with(ctx).load(curntGuide.getProfilePicture()).error(R.drawable.guide).into(holder.pdp_img);

        holder.guide_item.setOnClickListener(v -> {
            AppStateManager.setClickedGuide(curntGuide);
            Intent intent = new Intent(ctx, ViewProfileActivity.class);
            ctx.startActivity(intent);
        });

        String phone = curntGuide.getPhone().replace(" ", "");
        String url = "https://api.whatsapp.com/send?phone=" + phone;

        holder.contact_btn.setOnClickListener(v -> {
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


