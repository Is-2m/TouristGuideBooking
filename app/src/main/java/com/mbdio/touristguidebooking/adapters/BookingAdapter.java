package com.mbdio.touristguidebooking.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mbdio.touristguidebooking.R;
import com.mbdio.touristguidebooking.activities.ViewProfileActivity;
import com.mbdio.touristguidebooking.adapters.viewholders.BookItemHolder;
import com.mbdio.touristguidebooking.dao.BookingCallbacks;
import com.mbdio.touristguidebooking.dao.BookingDAO;
import com.mbdio.touristguidebooking.models.Booking;
import com.mbdio.touristguidebooking.models.BookingStatus;
import com.mbdio.touristguidebooking.utils.AppStateManager;

import java.util.ArrayList;
import java.util.List;

public class BookingAdapter extends RecyclerView.Adapter<BookItemHolder> {
    private List<Booking> lst = new ArrayList<>();
    private Context ctx;

    public BookingAdapter(Context ctx, ArrayList<Booking> books) {
        this.ctx = ctx;
        lst = books;
    }

    @NonNull
    @Override
    public BookItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(ctx).inflate(R.layout.booking_item_layout, parent, false);
        return new BookItemHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BookItemHolder holder, int position) {
        Booking curntBooking = lst.get(position);

        String name = curntBooking.getTourist().getFirstName() + " " + curntBooking.getTourist().getLastName();

        holder.date_lbl.setText(curntBooking.getDate().toString());
        holder.name_lbl.setText(name);
        holder.nation_lbl.setText(curntBooking.getTourist().getNationality());
        holder.phone_lbl.setText(curntBooking.getTourist().getPhone());
//        holder.city_lbl.setText(curntBooking.getLocation());

        Glide.with(ctx).load(curntBooking.getTourist()
                .getProfilePicture()).error(R.drawable.traveler_colored).into(holder.pdp_img);

        holder.book_item.setOnClickListener(v -> {
            AppStateManager.setClickedTourist(curntBooking.getTourist());
            Intent intent = new Intent(ctx, ViewProfileActivity.class);
            ctx.startActivity(intent);
        });
        holder.accept_btn.setOnClickListener(v -> {
            Booking booking = new Booking(curntBooking);
            booking.setStatus(BookingStatus.APPROVED);
            BookingDAO.updateBooking(AppStateManager
                    .getCurrentUser().getUserID(), booking, new BookingCallbacks() {
                @Override
                public void onBookingUpdated(boolean success, String message) {
                    if (success) {
                        curntBooking.setStatus(BookingStatus.APPROVED);
                    }
                }
            });
        });
        holder.deny_btn.setOnClickListener(v -> {
            Booking booking = new Booking(curntBooking);
            booking.setStatus(BookingStatus.DENIED);
            BookingDAO.updateBooking(AppStateManager
                    .getCurrentUser().getUserID(), booking, new BookingCallbacks() {
                @Override
                public void onBookingUpdated(boolean success, String message) {
                    if (success) {
                        curntBooking.setStatus(BookingStatus.DENIED);
                    }
                }
            });
        });

//        String phone = curntBooking.getTourist().getPhone().replace(" ", "");
//        String url = "https://api.whatsapp.com/send?phone=" + phone;
//
//        holder.contact_btn.setOnClickListener(v -> {
//            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            ctx.startActivity(intent);
//        });


    }

    @Override
    public int getItemCount() {
        return lst == null ? 0 : lst.size();
    }
}


