package com.informatika.parentcare;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.informatika.parentcare.adapter.ChildAdapter;
import com.informatika.parentcare.model.Anak;
import com.informatika.parentcare.model.Pengguna;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    private DatabaseReference database;
    private FirebaseAuth mAuth;
    private ArrayList<Anak> dfAnak;
    private ChildAdapter requestChildAdapter;
    private RecyclerView rc_list_request;
    private ProgressDialog loading;
    private TextView orangTua;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        database = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        rc_list_request = findViewById(R.id.rc_list_request);
        orangTua = (TextView) findViewById(R.id.orangTua);

        findViewById(R.id.fab_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, AddChildInfoActivity.class));
//                        .putExtra("id", "")
//                        .putExtra("title", ""));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        database.child("pengguna")
                .child(mAuth.getCurrentUser().getUid())
                .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Pengguna pengguna = dataSnapshot.getValue(Pengguna.class);
                orangTua.setText(pengguna.getStatus() + " " + pengguna.getNama());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println(databaseError.getDetails() + " " + databaseError.getMessage());
            }
        });

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
                    if (requests.getKode_orangtua().equals(mAuth.getCurrentUser().getUid())) {
                        requests.setKey(noteDataSnapshot.getKey());
                        dfAnak.add(requests);
                    }
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
    }

    @Override
    public void onBackPressed() {
        finish();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
