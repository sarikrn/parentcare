package com.informatika.parentcare;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ConsultationActivity extends AppCompatActivity {

    private TextView tv_judul, tv_gejala;
    private Button ya, tidak;
    private int counter = 1;
    private DatabaseReference dbGejala;

    private ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultation);

        dbGejala = FirebaseDatabase.getInstance().getReference("gejala");

        tv_judul = (TextView) findViewById(R.id.title);
        tv_gejala = (TextView) findViewById(R.id.gejala);

        updateGejala();

        findViewById(R.id.Ya).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateGejala();
            }
        });

        findViewById(R.id.Tidak).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateGejala();
            }
        });


    }

    public void updateGejala(){
        loading = new ProgressDialog(ConsultationActivity.this);
        loading.setMessage("Tunggu sebentar..."); // Setting Message
        loading.setTitle(null); // Setting Title
        loading.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
        loading.show(); // Display Progress Dialog
        loading.setCancelable(false);
        new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(3000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                loading.dismiss();
            }
        }).start();

        final String kodeGejala = "G" + String.valueOf(counter);
        dbGejala.child(kodeGejala).child("desk").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String gejala = dataSnapshot.getValue(String.class);
                tv_judul.setText(kodeGejala);
                tv_gejala.setText(gejala);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        counter++;
    }


}
