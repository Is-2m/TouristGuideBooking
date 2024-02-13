package com.mbdio.touristguidebooking.activities.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;

import com.mbdio.touristguidebooking.R;
import com.mbdio.touristguidebooking.adapters.BookingAdapter;
import com.mbdio.touristguidebooking.adapters.DiscoverGuideAdapter;
import com.mbdio.touristguidebooking.dao.BookingCallbacks;
import com.mbdio.touristguidebooking.dao.BookingDAO;
import com.mbdio.touristguidebooking.dao.GuideCallbacks;
import com.mbdio.touristguidebooking.dao.GuideDAO;
import com.mbdio.touristguidebooking.models.Booking;
import com.mbdio.touristguidebooking.models.BookingStatus;
import com.mbdio.touristguidebooking.models.Guide;
import com.mbdio.touristguidebooking.utils.AppStateManager;

import java.util.ArrayList;

public class BookingsFragment extends Fragment {


    RecyclerView recyclerView;
    ImageView filter_btn;
    ArrayList<Booking> bookLst = new ArrayList<>();
    SwipeRefreshLayout book_refresh;


    public BookingsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_bookings, container, false);
        recyclerView = v.findViewById(R.id.recycle_bookings);
        filter_btn = v.findViewById(R.id.filter_btn);
        book_refresh = v.findViewById(R.id.book_refresh);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        showData(AppStateManager.getBookingHistory());

        filter_btn.setOnClickListener(v1 -> {
            PopupMenu popup = new PopupMenu(getContext(), filter_btn);
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
            BookingDAO.getAllBookings(AppStateManager.getCurrentUser().getUserID(), new BookingCallbacks() {
                @Override
                public void onGetAllBookings(ArrayList<Booking> lst) {
                    AppStateManager.setBookingHistory(lst);
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            showData(lst);

                            book_refresh.setRefreshing(false);
                        }
                    }, 750);
                }
            });
        });

        return v;
    }

    void showData(ArrayList<Booking> lst) {
        if (lst != null && !lst.isEmpty()) {
            BookingAdapter adapter = new BookingAdapter(getContext(), lst);
            recyclerView.setAdapter(adapter);
        } else {
            lst = new ArrayList<>();
            BookingAdapter adapter = new BookingAdapter(getContext(), lst);
            recyclerView.setAdapter(adapter);
        }
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