package com.informatika.parentcare;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.informatika.parentcare.adapter.GejalaAdapter;
import com.informatika.parentcare.model.Gejala;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ConsultationActivity extends AppCompatActivity {

    private ArrayList<Gejala> gejalaArrayList = new ArrayList<>();
    private GejalaAdapter adapter;
    private ListView listView;

    private DatabaseReference dbGejala;

    private float min = 0;
    private String kode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultation);

        dbGejala = FirebaseDatabase.getInstance().getReference();

        listView = (ListView) findViewById(R.id.gejala_listView);

        findViewById(R.id.show_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(ConsultationActivity.this,ConsultationActivity.class);
//                startActivity(intent);
                kemiripanKonsultasi(v);
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
                adapter = new GejalaAdapter(gejalaArrayList, ConsultationActivity.this);
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ConsultationActivity.this, "Data tidak ditemukan atau kosong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void kemiripanKonsultasi(View view) {
        if (adapter.dataGejala.size() > 0) {
            final ArrayList<String> kodeGejala = new ArrayList<>();
            final HashMap<String, Float> hasilKemiripan = new HashMap<>();

            for (Gejala gejala : adapter.dataGejala) {
                kodeGejala.add(gejala.getKode());
            }
            Toast.makeText(ConsultationActivity.this,String.valueOf(kodeGejala.size()),Toast.LENGTH_LONG).show();

//            FirebaseDatabase.getInstance().getReference()
//                    .child("kasus")
//                    .addValueEventListener(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(DataSnapshot dataSnapshot) {
//                            for (DataSnapshot dataKasus : dataSnapshot.getChildren()) {
//                                float kemiripan = 0;
//                                for (DataSnapshot gejalaKasus : dataKasus.child("gejala").getChildren()) {
//                                    if (kodeGejala.contains(gejalaKasus.getKey())) {
////                                          kemiripan++;
//                                        Toast.makeText(ConsultationActivity.this, String.valueOf(min), Toast.LENGTH_SHORT).show();
//
//                                    }
//                                }
//
////                        float pembagi = Math.max((float) (dataKasus.child("gejala").getChildrenCount()), kodeGejala.size());
////                        float hasil_bagi = kemiripan/pembagi;
////
////                        hasilKemiripan.put(dataKasus.getKey(), hasil_bagi);
//                            }
//
////                    for (Map.Entry<String, Float> entry : hasilKemiripan.entrySet()){
////                        if (entry.getValue() > min){
////                            min = entry.getValue();
////                            kode = entry.getKey();
////                        }
////                    }
////
////                    if(kode != null){
////                        System.out.println(kode+" - "+min);
////                        Toast.makeText(ConsultationActivity.this, String.valueOf(min), Toast.LENGTH_SHORT).show();
//////                        solusi(kode,min);
////                    }else{
////                        Toast.makeText(ConsultationActivity.this, "Kasus yang mirip tidak ditemukan", Toast.LENGTH_SHORT).show();
////                    }
//                        }
//
//                        @Override
//                        public void onCancelled(DatabaseError databaseError) {
//                            Toast.makeText(ConsultationActivity.this, "Upss... Gagal mencari data", Toast.LENGTH_SHORT).show();
//                        }
//                    });
        } else {
            Toast.makeText(ConsultationActivity.this, "Anda belum mengisi gejala yang anda rasakan", Toast.LENGTH_SHORT).show();
        }
    }

    private void solusi(String kode, final float min) {
        final String kode_kasus = kode;

        if (min >= 0.75) {
            FirebaseDatabase.getInstance().getReference()
                    .child("kasus")
                    .child(kode)
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            String solusiKasus = dataSnapshot.child("solusi").getValue().toString();
                            Toast.makeText(ConsultationActivity.this, solusiKasus, Toast.LENGTH_SHORT).show();

//                            FirebaseDatabase.getInstance().getReference().child("anak").child()
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Toast.makeText(ConsultationActivity.this, "Upss... data tidak ditemukan.", Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            Toast.makeText(ConsultationActivity.this, "Solusi tidak ditemukan. Silahkan menghubungi psikiater pada kontak dibawah ini", Toast.LENGTH_SHORT).show();
        }
    }
}
