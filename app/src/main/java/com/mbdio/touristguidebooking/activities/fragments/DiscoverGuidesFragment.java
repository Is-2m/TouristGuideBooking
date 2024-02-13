package com.mbdio.touristguidebooking.activities.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mbdio.touristguidebooking.R;
import com.mbdio.touristguidebooking.activities.BookingHistoryActivity;
import com.mbdio.touristguidebooking.adapters.DiscoverGuideAdapter;
import com.mbdio.touristguidebooking.dao.GuideCallbacks;
import com.mbdio.touristguidebooking.dao.GuideDAO;
import com.mbdio.touristguidebooking.models.Guide;
import com.mbdio.touristguidebooking.utils.MailSender;

import java.util.ArrayList;

public class DiscoverGuidesFragment extends Fragment {
    RecyclerView recyclerView;
    ImageView history_btn;

    public DiscoverGuidesFragment() {
        // Required empty public constructor
    }


    ArrayList<Guide> guideList = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_discover_guides, container, false);
        recyclerView = v.findViewById(R.id.recycle_guide);
        history_btn = v.findViewById(R.id.guide_history_btn);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        GuideDAO.getAllGuides(new GuideCallbacks() {
            @Override
            public void onGetAllGuides(ArrayList<Guide> lst) {
                showData(lst);
            }
        });

        history_btn.setOnClickListener(v1 -> {
            Intent intent = new Intent(getContext(), BookingHistoryActivity.class);
            getActivity().startActivity(intent);
        });

        return v;
    }


    void showData(ArrayList<Guide> lst) {

        if (lst != null && !lst.isEmpty()) {
            DiscoverGuideAdapter adapter = new DiscoverGuideAdapter(getContext(), lst);
            recyclerView.setAdapter(adapter);
        }
    }
}