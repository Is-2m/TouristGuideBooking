package com.mbdio.touristguidebooking.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.PopupMenu;

import com.mbdio.touristguidebooking.R;
import com.mbdio.touristguidebooking.adapters.BookingAdapter;
import com.mbdio.touristguidebooking.adapters.BookingHistoryAdapter;
import com.mbdio.touristguidebooking.dao.BookingCallbacks;
import com.mbdio.touristguidebooking.dao.BookingDAO;
import com.mbdio.touristguidebooking.models.Booking;
import com.mbdio.touristguidebooking.models.BookingStatus;
import com.mbdio.touristguidebooking.models.Tourist;
import com.mbdio.touristguidebooking.utils.AppStateManager;

import java.util.ArrayList;

public class BookingHistoryActivity extends AppCompatActivity {


    RecyclerView recyclerView;
    ImageView filter_btn;
    ArrayList<Booking> bookLst = new ArrayList<>();
    SwipeRefreshLayout book_refresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_history);

        recyclerView = findViewById(R.id.recycle_bookings_hist);
        filter_btn = findViewById(R.id.book_hist_filter_btn);
        book_refresh = findViewById(R.id.book_hist_refresh);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        showData(AppStateManager.getBookingHistory());

        filter_btn.setOnClickListener(v1 -> {
            PopupMenu popup = new PopupMenu(this, filter_btn);
            popup.getMenuInflater().inflate(R.menu.filter, popup.getMenu());
            popup.setOnMenuItemClickListener(item -> {

                int id = item.getItemId();
                if (id == R.id.opt_all) {
                    showData(filterBy(null));
                } else if (id == R.id.opt_approve) {
                    showData(filterBy(BookingStatus.APPROVED));
                } else if (id == R.id.opt_deny) {
                    showData(filterBy(BookingStatus.DENIED));
                } else if (id == R.id.opt_pending) {
                    showData(filterBy(BookingStatus.PENDING));
                }
                return false;
            });
            popup.show(); // Showing the popup menu
        });
        book_refresh.setOnRefreshListener(() -> {
            Tourist t = (Tourist) AppStateManager.getCurrentUser();
            ArrayList<Booking> lst = new ArrayList<>();

            if (t.getListBookings() != null && !t.getListBookings().isEmpty())
                for (String s : t.getListBookings()) {
                    BookingDAO.getBooking(s, new BookingCallbacks() {
                        @Override
                        public void onGetBooking(Booking book) {
                            lst.add(book);
                        }
                    });
                }
            AppStateManager.setBookingHistory(lst);
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    showData(lst);

                    book_refresh.setRefreshing(false);
                }
            }, 750);
        });
    }

    void showData(ArrayList<Booking> lst) {
        BookingHistoryAdapter adapter = new BookingHistoryAdapter(BookingHistoryActivity.this, lst);
        recyclerView.setAdapter(adapter);
    }

    ArrayList<Booking> filterBy(BookingStatus bookStat) {
        ArrayList<Booking> lst = new ArrayList<>();
        if (bookStat == null) {
            lst = AppStateManager.getBookingHistory();
        } else {
            for (Booking book : AppStateManager.getBookingHistory()) {
                if (book.getStatus() == bookStat) {
                    lst.add(book);
                }
            }
        }
        return lst;
    }

}