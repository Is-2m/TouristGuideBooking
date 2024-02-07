package com.mbdio.touristguidebooking.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mbdio.touristguidebooking.activities.ItemActivity;
import com.mbdio.touristguidebooking.R;
import com.mbdio.touristguidebooking.models.Monument;

import java.util.ArrayList;

public class myadapter extends RecyclerView.Adapter< myadapter.myviewholder> {

    ArrayList<Monument> lst;
    public myadapter(ArrayList<Monument> list) {
        super();
        lst=list;
    }


    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerow, parent, false);
        return new myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myviewholder holder, int position) {
        Monument clickedModel = lst.get(position);

        if (holder.name != null) {
            holder.name.setText(clickedModel.getName());
        }

        if (holder.ville != null) {
            holder.ville.setText(clickedModel.getVille());
        }

        if (holder.address != null) {
            holder.address.setText(clickedModel.getAdresse());
        }

        if (holder.history != null) {
            holder.history.setText(clickedModel.getHistory());
        }
        Glide.with(holder.img.getContext()).load(clickedModel.getImage()).into(holder.img);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the model at the clicked position
                Monument clickedModel = lst.get(holder.getAdapterPosition());
                // Create an Intent to start the ItemActivity

                Intent intent = new Intent(holder.itemView.getContext(), ItemActivity.class);

                // Pass any necessary data to the ItemActivity using Intent extras
                intent.putExtra("name", clickedModel.getName());
                intent.putExtra("ville", clickedModel.getVille());
                intent.putExtra("address", clickedModel.getAdresse());
                intent.putExtra("image", clickedModel.getImage());
                intent.putExtra("history", clickedModel.getHistory());

                // Start the ItemActivity
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return lst==null?0:lst.size();
    }

    static class myviewholder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView name, address, ville, history;

        public myviewholder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.imageview);
            name = itemView.findViewById(R.id.textName);
            ville = itemView.findViewById(R.id.textville);
            address = itemView.findViewById(R.id.textaddress);
            history = itemView.findViewById(R.id.history);
        }
    }
}
