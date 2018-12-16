package com.informatika.parentcare;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.informatika.parentcare.adapter.ChildAdapter;
import com.informatika.parentcare.model.Anak;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    private DatabaseReference database;

    private ArrayList<Anak> dfAnak;
    private ChildAdapter requestChildAdapter;

    private RecyclerView rc_list_request;
    private ProgressDialog loading;
    private FloatingActionButton fab_add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        database = FirebaseDatabase.getInstance().getReference();

        rc_list_request = findViewById(R.id.rc_list_request);
        fab_add = findViewById(R.id.fab_add);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rc_list_request.setLayoutManager(mLayoutManager);
        rc_list_request.setItemAnimator(new DefaultItemAnimator());

        loading = ProgressDialog.show(HomeActivity.this,
                null,
                "Please wait...",
                true,
                false);

        database.child("anak").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dfAnak = new ArrayList<>();
                for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
                    Anak requests = noteDataSnapshot.getValue(Anak.class);
                    requests.setKey(noteDataSnapshot.getKey());
                    dfAnak.add(requests);
                }
                requestChildAdapter = new ChildAdapter(dfAnak, HomeActivity.this);
                rc_list_request.setAdapter(requestChildAdapter);
                loading.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println(databaseError.getDetails() + " " + databaseError.getMessage());
                loading.dismiss();
            }
        });

        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, AddChildInfoActivity.class)
                        .putExtra("id", "")
                        .putExtra("title", ""));
            }
        });
    }
}
