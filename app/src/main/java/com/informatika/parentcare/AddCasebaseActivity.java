package com.informatika.parentcare;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.informatika.parentcare.adapter.GejalaAdapter;
import com.informatika.parentcare.model.Gejala;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddCasebaseActivity extends AppCompatActivity {

    private ArrayList<Gejala> gejalaArrayList = new ArrayList<>();
    private GejalaAdapter adapter;
    private ListView listView;
    private DatabaseReference dbGejala;
    private EditText solusiBaru;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_casebase);

        dbGejala = FirebaseDatabase.getInstance().getReference();
        listView = (ListView) findViewById(R.id.listView);
        solusiBaru = (EditText) findViewById(R.id.inputSolusi);

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
                System.out.println("splusi : " + solusiBaru.getText().toString());
                startActivity(new Intent(AddCasebaseActivity.this, ResultNewCaseActivity.class)
                        .putExtra("solusi", solusiBaru.getText().toString()));
            }
        });
    }
}