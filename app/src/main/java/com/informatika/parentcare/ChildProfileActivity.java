package com.informatika.parentcare;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.informatika.parentcare.adapter.ChildAdapter;
import com.informatika.parentcare.adapter.HistoryAdapter;
import com.informatika.parentcare.model.Konsultasi;

import java.util.ArrayList;

public class ChildProfileActivity extends AppCompatActivity {

    private TextView setKode, setNama, setUrutan;
    private String kodeAnak, namaAnak, urutanAnak;
    private ArrayList<Konsultasi> konsultasiArrayList = new ArrayList<>();
    private DatabaseReference dbAnak, dbKonsultasi;
    private RecyclerView rc_list_konsultasi;
    private HistoryAdapter historyAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_profile);

        dbAnak = FirebaseDatabase.getInstance().getReference();

        setNama = (TextView) findViewById(R.id.namaAnak);
        setUrutan = (TextView) findViewById(R.id.urutanAnak);
        rc_list_konsultasi = findViewById(R.id.rc_list_konsultasi);

        kodeAnak = getIntent().getStringExtra("kodeAnak");
        namaAnak = getIntent().getStringExtra("nama");
        urutanAnak = getIntent().getStringExtra("urutan");

    }

    @Override
    protected void onStart() {
        super.onStart();

        setNama.setText(namaAnak);
        setUrutan.setText("Anak Ke : " + urutanAnak);

        findViewById(R.id.lakukanTest).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChildProfileActivity.this, ConsultationActivity.class)
                        .putExtra("kodeAnak", kodeAnak)
                        .putExtra("nama", namaAnak));
            }
        });

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rc_list_konsultasi.setLayoutManager(mLayoutManager);
        rc_list_konsultasi.setItemAnimator(new DefaultItemAnimator());

        dbAnak.child("konsultasi")
                .orderByChild("id_anak")
                .equalTo(kodeAnak)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        konsultasiArrayList = new ArrayList<>();
                        for (DataSnapshot ds : dataSnapshot.getChildren()){
                            Konsultasi konsultasi = ds.getValue(Konsultasi.class);
                            konsultasiArrayList.add(konsultasi);
                        }
                        historyAdapter = new HistoryAdapter(konsultasiArrayList, ChildProfileActivity.this);
                        rc_list_konsultasi.setAdapter(historyAdapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

    }
}
