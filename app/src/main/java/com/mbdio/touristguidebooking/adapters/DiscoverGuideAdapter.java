package com.mbdio.touristguidebooking.adapters;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
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
import com.mbdio.touristguidebooking.utils.DemoData;
import com.mbdio.touristguidebooking.utils.MailSender;

import java.util.ArrayList;
import java.util.Calendar;
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
        String guideName = curntGuide.getFirstName() + " " + curntGuide.getLastName();
        holder.bio_lbl.setText(curntGuide.getBio());
        holder.name_lbl.setText(guideName);
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

        holder.book_btn.setOnClickListener(v -> {
            final Calendar c = Calendar.getInstance();

            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    v.getContext(),
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {
                            // on below line we are setting date to our text view.
                            String date = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                            Booking book = new Booking();
                            book.setStatus(BookingStatus.PENDING);
                            book.setTouristId(AppStateManager.getCurrentUser().getUserID());
                            book.setDate(date);
                            BookingDAO.insertBooking(book, curntGuide.getUserID(), new BookingCallbacks() {
                                @Override
                                public void onBookingInserted(boolean success, String path, String message) {
                                    Tourist t = new Tourist((Tourist) AppStateManager.getCurrentUser());
                                    if (t.getListBookings() == null) {
                                        t.setListBookings(new ArrayList<>());
                                    }
                                    t.getListBookings().add(path);

                                    TouristDAO.update(t, new TouristCallbacks() {
                                        @Override
                                        public void onTouristUpdated(boolean success, String message) {
                                            AppStateManager.setCurrentUser(t);
                                        }
                                    });
                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            try {

                                                String touristName = t.getFirstName() + " " + t.getLastName();
                                                MailSender.sendEmail(curntGuide.getEmail(),
                                                        "Booking Request",
                                                        MailSender.bookRequestTemplate(guideName, touristName,
                                                                date, t.getEmail(), t.getPhone()));
                                            } catch (final Exception e) {
                                                // Handle failures, such as updating the UI to show an error message
                                                e.printStackTrace();
                                            }
                                        }
                                    }).start();
                                }
                            });

                        }
                    },
                    year, month, day);
            datePickerDialog.show();
        });


    }

    @Override
    public int getItemCount() {
        return lst.size();
    }
}


