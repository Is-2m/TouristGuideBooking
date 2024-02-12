package com.mbdio.touristguidebooking.adapters;

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
import com.mbdio.touristguidebooking.adapters.viewholders.BookItemHolder;
import com.mbdio.touristguidebooking.dao.BookingCallbacks;
import com.mbdio.touristguidebooking.dao.BookingDAO;
import com.mbdio.touristguidebooking.models.Booking;
import com.mbdio.touristguidebooking.models.BookingStatus;
import com.mbdio.touristguidebooking.utils.AppStateManager;
import com.mbdio.touristguidebooking.utils.MailSender;

import java.util.ArrayList;
import java.util.List;

public class BookingAdapter extends RecyclerView.Adapter<BookItemHolder> {
    private ArrayList<Booking> lst = new ArrayList<>();
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
        refreshState(holder, curntBooking.getStatus());

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
                        refreshState(holder, curntBooking.getStatus());

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {

                                    String guideName = AppStateManager.getCurrentUser().getFirstName()
                                            + " " + AppStateManager.getCurrentUser().getLastName();
                                    String touristName = curntBooking.getTourist().getFirstName()
                                            + " " + curntBooking.getTourist().getLastName();

                                    MailSender.sendEmail(curntBooking.getTourist().getEmail(), "Booking Request Accepted",
                                            MailSender.bookingApprovedTemplate(guideName, touristName, booking.getDate(),
                                                    curntBooking.getId(), AppStateManager.getCurrentUser().getEmail()
                                                    , AppStateManager.getCurrentUser().getPhone()));
                                } catch (final Exception e) {
                                    // Handle failures, such as updating the UI to show an error message
                                    e.printStackTrace();
                                }
                            }
                        }).start();
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
                        refreshState(holder, curntBooking.getStatus());
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {

                                    String guideName = AppStateManager.getCurrentUser().getFirstName()
                                            + " " + AppStateManager.getCurrentUser().getLastName();
                                    String touristName = curntBooking.getTourist().getFirstName()
                                            + " " + curntBooking.getTourist().getLastName();

                                    MailSender.sendEmail(curntBooking.getTourist().getEmail(), "Booking Request Accepted",
                                            MailSender.bookingDeniedTemplate(guideName, touristName, booking.getDate(),
                                                    curntBooking.getId(), AppStateManager.getCurrentUser().getEmail()
                                                    , AppStateManager.getCurrentUser().getPhone()));
                                } catch (final Exception e) {
                                    // Handle failures, such as updating the UI to show an error message
                                    e.printStackTrace();
                                }
                            }
                        }).start();

                    }
                }
            });
        });
        holder.phone_btn.setOnClickListener(v -> {
            String phone = curntBooking.getTourist().getPhone().replace(" ", "");
            String url = "https://api.whatsapp.com/send?phone=" + phone;
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ctx.startActivity(intent);
        });
//        holder.phone_btn.setOnClickListener(v -> {
//            String phoneNumber = curntBooking.getTourist().getPhone().replace(" ", "");
//
//            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:" + phoneNumber));
//
//            // WhatsApp Intent (if installed)
//            Intent whatsappIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://wa.me/" + phoneNumber));
//            if (isAppInstalled(ctx, "com.whatsapp")) {
//                intent.setPackage("com.whatsapp");
//            }
//            intent.putExtra(Intent.EXTRA_SPLIT_INTENT, whatsappIntent);
//
//            // Telegram Intent (if installed)
//            Intent telegramIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/" + phoneNumber));
//            if (isAppInstalled(ctx, "org.telegram.messenger")) {
//                intent.setPackage("org.telegram.messenger");
//            }
//            intent.putExtra(Intent.EXTRA_SPLIT_INTENT, telegramIntent);
//
//            // Create chooser
//            Intent chooserIntent = Intent.createChooser(intent, "Choose an app to contact:");
//
//            // Start chooser
//            if (intent.resolveActivity(ctx.getPackageManager()) != null) {
//                ctx.startActivity(chooserIntent);
//            } else {
//                // Handle no suitable app found
//                // You can display a toast message or provide alternative options
//                Toast.makeText(ctx, "No suitable app found to contact", Toast.LENGTH_SHORT).show();
//            }
//        });


    }

    // Helper method to check app installation
    private boolean isAppInstalled(Context context, String packageName) {
        PackageManager pm = context.getPackageManager();
        try {
            pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    void refreshState(BookItemHolder holder, BookingStatus bookStat) {
        holder.state_lbl.setText(bookStat.name());
        if (bookStat == BookingStatus.PENDING) {
            holder.options_container.setVisibility(View.VISIBLE);
            holder.denied_container.setVisibility(View.GONE);
            holder.approved_container.setVisibility(View.GONE);
        } else if (BookingStatus.APPROVED == bookStat) {
            holder.options_container.setVisibility(View.GONE);
            holder.denied_container.setVisibility(View.GONE);
            holder.approved_container.setVisibility(View.VISIBLE);
        } else if (bookStat == BookingStatus.DENIED) {
            holder.options_container.setVisibility(View.GONE);
            holder.denied_container.setVisibility(View.VISIBLE);
            holder.approved_container.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return lst == null ? 0 : lst.size();
    }

    void setData(ArrayList<Booking> newLst) {
        lst = newLst;
        notifyDataSetChanged();
    }
}


