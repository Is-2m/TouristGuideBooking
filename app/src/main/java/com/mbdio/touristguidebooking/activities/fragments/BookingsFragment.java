package com.mbdio.touristguidebooking.activities.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mbdio.touristguidebooking.R;
import com.mbdio.touristguidebooking.adapters.BookingAdapter;
import com.mbdio.touristguidebooking.adapters.DiscoverGuideAdapter;
import com.mbdio.touristguidebooking.dao.GuideCallbacks;
import com.mbdio.touristguidebooking.dao.GuideDAO;
import com.mbdio.touristguidebooking.models.Booking;
import com.mbdio.touristguidebooking.models.Guide;
import com.mbdio.touristguidebooking.utils.AppStateManager;

import java.util.ArrayList;

public class BookingsFragment extends Fragment {


    RecyclerView recyclerView;
    ArrayList<Booking> bookLst = new ArrayList<>();


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
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        showData(AppStateManager.getBookingHistory());

        return v;
    }

    void showData(ArrayList<Booking> lst) {
        BookingAdapter adapter = new BookingAdapter(getContext(), lst);
        recyclerView.setAdapter(adapter);
    }
}