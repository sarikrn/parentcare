package com.informatika.parentcare;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.informatika.parentcare.adapter.GejalaAdapter;
import com.informatika.parentcare.model.Anak;
import com.informatika.parentcare.model.Gejala;
import com.informatika.parentcare.model.Konsultasi;

import java.sql.SQLOutput;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConsultationActivity extends AppCompatActivity {

    private ArrayList<Gejala> gejalaArrayList = new ArrayList<>();
    private GejalaAdapter adapter;
    private ListView listView;
    private DatabaseReference dbGejala, dbKonsultasi;
    private float min = 0;
    private String kode;
    private String kodeAnak, namaAnak, urutanAnak, solusiKasus;
    private int counter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultation);

        dbGejala = FirebaseDatabase.getInstance().getReference();
        dbKonsultasi = FirebaseDatabase.getInstance().getReference();

        kodeAnak = getIntent().getStringExtra("kodeAnak");
        namaAnak = getIntent().getStringExtra("nama");
        urutanAnak = getIntent().getStringExtra("urutan");

        listView = (ListView) findViewById(R.id.gejala_listView);

        findViewById(R.id.show_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kemiripanKonsultasi(v);
            }
        });

        dbKonsultasi.child("konsultasi").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int size = (int) dataSnapshot.getChildrenCount();
                counter = size + 1;
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
            final List<String> kodeGejala = new ArrayList<>();
            final Map<String, Float> hasilKemiripan = new HashMap<>();

//            kodeGejala.clear();
            for (Gejala d : adapter.dataGejala) {
                kodeGejala.add(d.getKode());
            }

            DatabaseReference kasus = FirebaseDatabase.getInstance().getReference().child("kasus");
            kasus.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
//                    hasilKemiripan.clear();
                    for (DataSnapshot dataKasus : dataSnapshot.getChildren()) {
                        float kemiripan = 0;
                        for (DataSnapshot gejalaKasus : dataKasus.child("gejala").getChildren()) {
                            if (kodeGejala.contains(gejalaKasus.getValue().toString())) {
                                kemiripan++;
                            }
                        }

                        float pembagi = Math.max((float) (dataKasus.child("gejala").getChildrenCount()), Float.valueOf(kodeGejala.size()));
                        float hasil_bagi = kemiripan / pembagi;

                        System.out.println("data kasus : " + dataKasus.child("gejala").getChildrenCount());
                        System.out.println("kodeGejala : " + Float.valueOf(kodeGejala.size()));
                        System.out.println("kemiripan : " + kemiripan);
                        System.out.println("hasil bagi : " + String.valueOf(hasil_bagi));

                        hasilKemiripan.put(dataKasus.getKey(), hasil_bagi);
                    }

                    for (Map.Entry<String, Float> entry : hasilKemiripan.entrySet()) {
                        if (entry.getValue() > min) {
                            min = entry.getValue();
                            kode = entry.getKey();
                        }
                    }

                    if (kode != null) {
                        System.out.println(kode + " - " + min);
                        solusi(kode, min, kodeGejala);
                    } else {
                        Toast.makeText(ConsultationActivity.this, "Kasus yang mirip tidak ditemukan", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(ConsultationActivity.this, "Upss... Gagal mencari data", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(ConsultationActivity.this, "Anda belum mengisi gejala yang anda rasakan", Toast.LENGTH_SHORT).show();
        }
    }

    private void solusi(String kode, final float min, List<String> kodeGejala) {
        final String kode_kasus = kode;
        final List<String> gejalaAnak = kodeGejala;

        if (min >= 0.75) {
            FirebaseDatabase.getInstance().getReference()
                    .child("kasus")
                    .child(kode)
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            solusiKasus = dataSnapshot.child("solusi").getValue().toString();
//                            Toast.makeText(ConsultationActivity.this, solusiKasus, Toast.LENGTH_SHORT).show();



                            Konsultasi konsultasi = new Konsultasi("00-00-0000",
                                    solusiKasus,
                                    kodeAnak,
                                    gejalaAnak);

                            FirebaseDatabase.getInstance().getReference()
                                    .child("konsultasi")
                                    .child("Konsul" + String.valueOf(counter))
                                    .setValue(konsultasi).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        startActivity(new Intent(ConsultationActivity.this, ResultConsultationActivity.class)
                                                .putExtra("solusi", solusiKasus)
                                                .putExtra("kodeAnak", kodeAnak)
                                                .putExtra("nama", namaAnak)
                                                .putExtra("urutan", urutanAnak)
                                                .putExtra("status", "ada"));

                                    } else {
                                        //display failure
                                    }
                                }
                            });

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Toast.makeText(ConsultationActivity.this, "Upss... data tidak ditemukan.", Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            solusiKasus = "Solusi tidak ditemukan. Silahkan menghubungi psikiater / ahli";

            kodeAnak = getIntent().getStringExtra("kodeAnak");
            namaAnak = getIntent().getStringExtra("nama");
            urutanAnak = getIntent().getStringExtra("urutan");

            Konsultasi konsultasi = new Konsultasi("00-00-0000",
                    solusiKasus,
                    kodeAnak,
                    gejalaAnak);

            FirebaseDatabase.getInstance().getReference()
                    .child("konsultasi")
                    .child("Konsul" + String.valueOf(counter))
                    .setValue(konsultasi).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        startActivity(new Intent(ConsultationActivity.this, ResultConsultationActivity.class)
                                .putExtra("solusi", solusiKasus)
                                .putExtra("kodeAnak", kodeAnak)
                                .putExtra("nama", namaAnak)
                                .putExtra("urutan", urutanAnak)
                                .putExtra("status", "kosong"));
                    } else {
                        //display failure
                    }
                }
            });

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        String halaman = getIntent().getStringExtra("halaman");

        if(halaman.equals("home")){
            startActivity(new Intent(ConsultationActivity.this, HomeActivity.class)
                    .putExtra("kodeAnak", kodeAnak)
                    .putExtra("nama", namaAnak)
                    .putExtra("urutan", urutanAnak));
        }else{
            startActivity(new Intent(ConsultationActivity.this, ChildProfileActivity.class)
                    .putExtra("kodeAnak", kodeAnak)
                    .putExtra("nama", namaAnak)
                    .putExtra("urutan", urutanAnak));
        }
    }
}
