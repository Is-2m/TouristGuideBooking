package com.mbdio.touristguidebooking.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.widget.SearchView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import com.mbdio.touristguidebooking.R;
import com.mbdio.touristguidebooking.adapters.myadapter;
import com.mbdio.touristguidebooking.models.Monument;

public class MonumentsActivity extends AppCompatActivity {

    RecyclerView rview;
    myadapter adapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monuments);


        rview = findViewById(R.id.rview);
        rview.setLayoutManager(new LinearLayoutManager(this));


        getAll();
        SearchView searchView = findViewById(R.id.search);

        assert searchView != null;
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                processSearch(MonumentsActivity.this, s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                processSearch(MonumentsActivity.this, s);
                return false;
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

    void show(ArrayList<Monument> lst) {
        adapter = new myadapter(lst);
        rview.setAdapter(adapter);
    }


}