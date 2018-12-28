package com.informatika.parentcare.psikolog;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.informatika.parentcare.R;
import com.informatika.parentcare.ResultConsultationActivity;
import com.informatika.parentcare.adapter.GejalaAdapter;
import com.informatika.parentcare.model.Gejala;
import com.informatika.parentcare.model.Kasus;

import java.util.ArrayList;

public class AddCasebaseActivity extends AppCompatActivity {

    public ArrayList<Gejala> gejalaArrayList = new ArrayList<>();
    private GejalaAdapter adapter;
    private ListView listView;
    private DatabaseReference dbGejala, dbKasus;
    private EditText solusiBaru;
    private long counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_casebase);

        dbGejala = FirebaseDatabase.getInstance().getReference();
        dbKasus = FirebaseDatabase.getInstance().getReference();
        listView = (ListView) findViewById(R.id.listView);
        solusiBaru = (EditText) findViewById(R.id.inputSolusi);

        dbKasus.child("kasus").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                counter = dataSnapshot.getChildrenCount();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        dbGejala.child("gejala").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Gejala gejala = ds.getValue(Gejala.class);
                    gejala.setSelected(false);
                    gejala.setKode(ds.getKey());
                    gejalaArrayList.add(gejala);
                }
                adapter = new GejalaAdapter(gejalaArrayList, AddCasebaseActivity.this);
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(AddCasebaseActivity.this, "Data tidak ditemukan atau kosong", Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.btn_add_case).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ArrayList<String> kodeGejala = new ArrayList<>();
                final ArrayList<String> deskGejala = new ArrayList<>();
                for (Gejala d : adapter.dataGejala) {
                    kodeGejala.add(d.getKode());
                    deskGejala.add(d.getDesk());
                }

                Kasus kasus = new Kasus(
                        kodeGejala,
                        "K" + (counter + 1),
                        solusiBaru.getText().toString()
                );

                dbKasus.child("kasus")
                        .child("K" + (counter + 1))
                        .setValue(kasus)
                        .addOnSuccessListener(AddCasebaseActivity.this, new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(AddCasebaseActivity.this,
                                        "Data Berhasil ditambahkan",
                                        Toast.LENGTH_SHORT).show();

                                startActivity(new Intent(AddCasebaseActivity.this, ResultNewCaseActivity.class)
                                        .putStringArrayListExtra("gejalaBaru", kodeGejala)
                                        .putExtra("solusi", solusiBaru.getText().toString())
                                        .putExtra("kodeKasusBaru", "K" + counter)
                                        .putStringArrayListExtra("deskGejala", deskGejala));
                            }
                        });

            }
        });
    }
}