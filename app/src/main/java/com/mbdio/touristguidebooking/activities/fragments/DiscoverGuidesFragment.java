package com.mbdio.touristguidebooking.activities.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mbdio.touristguidebooking.R;
import com.mbdio.touristguidebooking.adapters.DiscoverGuideAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DiscoverGuidesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DiscoverGuidesFragment extends Fragment {



    public DiscoverGuidesFragment() {
        // Required empty public constructor
    }


    public static DiscoverGuidesFragment newInstance(String param1, String param2) {
        DiscoverGuidesFragment fragment = new DiscoverGuidesFragment();

        return fragment;
    }

    RecyclerView recyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_discover_guides, container, false);
        recyclerView = v.findViewById(R.id.recycle_guide);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity().getBaseContext());
        recyclerView.setLayoutManager(layoutManager);

//        List<Map<String, Object>> guidesData = getTouristGuidesData();  // Use your data source method here
        DiscoverGuideAdapter adapter = new DiscoverGuideAdapter(getActivity().getBaseContext());
        recyclerView.setAdapter(adapter);

        return v;
    }

}