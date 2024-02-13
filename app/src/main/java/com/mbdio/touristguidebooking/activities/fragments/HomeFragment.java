package com.mbdio.touristguidebooking.activities.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.mbdio.touristguidebooking.R;
import com.mbdio.touristguidebooking.activities.EmergencyNumberActivity;
import com.mbdio.touristguidebooking.activities.MonumentsActivity;
import com.mbdio.touristguidebooking.adapters.myadapter;
import com.mbdio.touristguidebooking.models.Monument;
import com.mbdio.touristguidebooking.utils.AppStateManager;

import java.util.ArrayList;


public class HomeFragment extends Fragment {

    ImageView sos;
    RecyclerView rview;
    myadapter adapter;
    TextView user_name_lbl;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        rview = v.findViewById(R.id.rview);
        rview.setLayoutManager(new LinearLayoutManager(getContext()));
        sos = v.findViewById(R.id.imageSOS);
        user_name_lbl = v.findViewById(R.id.user_name_lbl);
        user_name_lbl.setText(AppStateManager.getCurrentUser().getFirstName());
        sos.setOnClickListener(v1 -> {
            Intent intent = new Intent(getContext(), EmergencyNumberActivity.class);
            startActivity(intent);
        });

        getAll();
        SearchView searchView = v.findViewById(R.id.search);

        assert searchView != null;
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                processSearch(getContext(), s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                processSearch(getContext(), s);
                return false;
            }
        });
        // Inflate the layout for this fragment
        return v;
    }

    void getAll() {

        ArrayList<Monument> pojoList = new ArrayList<>();

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("monuments").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                QuerySnapshot querySnapshot = task.getResult();
                if (querySnapshot != null) {
                    for (QueryDocumentSnapshot document : querySnapshot) {
                        // Convert each document to model
                        Monument pojo = document.toObject(Monument.class);
                        pojoList.add(pojo);
                    }
                    show(pojoList);
                }
            }
        });

    }

    private void processSearch(Context ctx, String s) {
        ArrayList<Monument> lst;
        if (s.isEmpty()) {
            getAll();

        } else {
            lst = new ArrayList<>();
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("monuments").whereGreaterThanOrEqualTo("name", s)
                    .whereLessThanOrEqualTo("name", s + '\uf8ff').get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            QuerySnapshot querySnapshot = task.getResult();
                            if (querySnapshot != null) {
                                for (QueryDocumentSnapshot document : querySnapshot) {
                                    // Convert each document to model
                                    Monument pojo = document.toObject(Monument.class);
                                    lst.add(pojo);
                                }
                                show(lst);
                            }
                        }
                    });
        }

    }

    void show(ArrayList<Monument> lst) {
        if (lst != null && !lst.isEmpty()) {

            adapter = new myadapter(lst);
            rview.setAdapter(adapter);

        }
    }

   /* public void openEmergencyNumbersActivity(View view) {
        // Start the EmergencyNumbersActivity
        Intent intent = new Intent(getContext(), EmergencyNumberActivity.class);
        startActivity(intent);*/

}