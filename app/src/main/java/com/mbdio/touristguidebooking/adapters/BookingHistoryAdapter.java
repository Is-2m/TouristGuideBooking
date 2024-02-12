package com.mbdio.touristguidebooking.adapters;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mbdio.touristguidebooking.R;
import com.mbdio.touristguidebooking.activities.ViewProfileActivity;
import com.mbdio.touristguidebooking.adapters.viewholders.BookItemHolder;
import com.mbdio.touristguidebooking.adapters.viewholders.GuideItemHolder;
import com.mbdio.touristguidebooking.dao.BookingCallbacks;
import com.mbdio.touristguidebooking.dao.BookingDAO;
import com.mbdio.touristguidebooking.dao.TouristCallbacks;
import com.mbdio.touristguidebooking.dao.TouristDAO;
import com.mbdio.touristguidebooking.models.Booking;
import com.mbdio.touristguidebooking.models.BookingStatus;
import com.mbdio.touristguidebooking.models.Guide;
import com.mbdio.touristguidebooking.models.Tourist;
import com.mbdio.touristguidebooking.utils.AppStateManager;
import com.mbdio.touristguidebooking.utils.MailSender;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class BookingHistoryAdapter extends RecyclerView.Adapter<BookItemHolder> {
    private ArrayList<Booking> lst = new ArrayList<>();
    private Context ctx;

    public BookingHistoryAdapter(Context ctx, ArrayList<Booking> books) {
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

        String name = curntBooking.getGuide().getFirstName() + " " + curntBooking.getGuide().getLastName();

        holder.nation_lbl.setText("Morocco");
        holder.date_lbl.setText(curntBooking.getDate().toString());
        holder.phone_lbl.setText(curntBooking.getGuide().getPhone());
        holder.name_lbl.setText(name);

        refreshState(holder, curntBooking.getStatus());

        Glide.with(ctx).load(curntBooking.getGuide()
                .getProfilePicture()).error(R.drawable.traveler_colored).into(holder.pdp_img);

        holder.book_item.setOnClickListener(v -> {
            AppStateManager.setClickedGuide(curntBooking.getGuide());
            Intent intent = new Intent(ctx, ViewProfileActivity.class);
            ctx.startActivity(intent);
        });

        holder.phone_btn.setOnClickListener(v -> {
            String phone = curntBooking.getTourist().getPhone().replace(" ", "");
            String url = "https://api.whatsapp.com/send?phone=" + phone;
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ctx.startActivity(intent);
        });

    }


    void refreshState(BookItemHolder holder, BookingStatus bookStat) {
        holder.state_lbl.setText(bookStat.name());

        holder.options_container.setVisibility(View.GONE);
        holder.denied_container.setVisibility(View.GONE);
        holder.approved_container.setVisibility(View.GONE);
        holder.hist_stat_container.setVisibility(View.VISIBLE);

        if (bookStat == BookingStatus.PENDING) {
            holder.book_hist_denied.setVisibility(View.GONE);
            holder.book_hist_approved.setVisibility(View.GONE);
            holder.book_hist_pending.setVisibility(View.VISIBLE);
        } else if (BookingStatus.APPROVED == bookStat) {
            holder.book_hist_denied.setVisibility(View.GONE);
            holder.book_hist_approved.setVisibility(View.VISIBLE);
            holder.book_hist_pending.setVisibility(View.GONE);
        } else if (bookStat == BookingStatus.DENIED) {
            holder.book_hist_denied.setVisibility(View.VISIBLE);
            holder.book_hist_approved.setVisibility(View.GONE);
            holder.book_hist_pending.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return lst == null ? 0 : lst.size();
    }

}
