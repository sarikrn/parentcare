package com.informatika.parentcare;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.informatika.parentcare.adapter.ChildAdapter;
import com.informatika.parentcare.adapter.GejalaAdapter;
import com.informatika.parentcare.model.Anak;
import com.informatika.parentcare.model.Gejala;

import java.util.ArrayList;
import java.util.HashMap;

public class Consultation_2Activity extends AppCompatActivity {

    private ListView lv;
    private ArrayList<Gejala> gejalaArrayList;
    private GejalaAdapter gejalaAdapter;

    private HashMap<String, Gejala> gejalaHashMap;

    private DatabaseReference dbGejala;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultation_2);

        dbGejala = FirebaseDatabase.getInstance().getReference();

        lv = (ListView) findViewById(R.id.gejala_listView);

        dbGejala.child("gejala").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                gejalaArrayList = new ArrayList<>();
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    Gejala gejala = ds.getValue(Gejala.class);
                    gejala.setKode(ds.getKey());
                    gejalaArrayList.add(gejala);
                }
                gejalaAdapter = new GejalaAdapter(Consultation_2Activity.this,gejalaArrayList);
                lv.setAdapter(gejalaAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void konsultasi(){
        }
}
