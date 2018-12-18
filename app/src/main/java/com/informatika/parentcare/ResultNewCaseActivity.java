package com.informatika.parentcare;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.informatika.parentcare.adapter.GejalaAdapter;
import com.informatika.parentcare.model.Gejala;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

public class ResultNewCaseActivity extends AppCompatActivity {

    private GejalaAdapter adapter;
    private DatabaseReference dbTambahKasus;
    private TextView solusiHasil;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_new_case);

        dbTambahKasus = FirebaseDatabase.getInstance().getReference();
        solusiHasil = (TextView) findViewById(R.id.solusiHasil);

        List<String> kodeGejala = new ArrayList<>();
//        for (Gejala d : adapter.dataGejala) {
//            kodeGejala.add(d.getKode());
//        }
        System.out.println("kode Gejala : " + adapter.dataGejala);

    }

    @Override
    protected void onStart() {
        super.onStart();

        String solusi = getIntent().getStringExtra("solusi");
        solusiHasil.setText(solusi);


    }
}
