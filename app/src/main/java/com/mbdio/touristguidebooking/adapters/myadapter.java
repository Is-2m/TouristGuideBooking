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

public class myadapter extends RecyclerView.Adapter<myadapter.myviewholder> {

    ArrayList<Monument> lst;

    public myadapter(ArrayList<Monument> list) {
        super();
        lst = list;
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
                intent.putExtra("longitude", clickedModel.getLongitude());
                intent.putExtra("latitude", clickedModel.getLatitude());

                // Start the ItemActivity
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return lst == null ? 0 : lst.size();
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


    // private void getCurrentLocation(Context ctx) {
    // if (ActivityCompat.checkSelfPermission(ctx,
    // Manifest.permission.ACCESS_FINE_LOCATION) !=
    // PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(ctx,
    // Manifest.permission.ACCESS_COARSE_LOCATION) !=
    // PackageManager.PERMISSION_GRANTED) {
    // // TODO: Consider calling
    // // ActivityCompat#requestPermissions
    // // here to request the missing permissions, and then overriding
    // // public void onRequestPermissionsResult(int requestCode, String[]
    // permissions,
    // // int[] grantResults)
    // // to handle the case where the user grants the permission. See the
    // documentation
    // // for ActivityCompat#requestPermissions for more details.
    // return;
    // }
    // LocationManager locationManager = (LocationManager)
    // ctx.getSystemService(Context.LOCATION_SERVICE);
    // boolean isNetworkEnabled =
    // locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    // if (isNetworkEnabled) {

    // }

    // Location location =
    // locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
    // if (location != null) {
    // double latitude = location.getLatitude();
    // double longitude = location.getLongitude();

    // // Assuming you have another set of coordinates to compare against
    // double otherLatitude = 34.0522; // Example coordinates
    // double otherLongitude = -118.2437;

    // float[] results = new float[1];
    // Location.distanceBetween(latitude, longitude, otherLatitude, otherLongitude,
    // results);
    // float distanceInMeters = results[0];

    // // Do something with the distance
    // System.out.println("Distance to other location: " + distanceInMeters + "
    // meters.");
    // } else {
    // // Handle location not available
    // System.out.println("Location not available.");
    // }
    // }
}
