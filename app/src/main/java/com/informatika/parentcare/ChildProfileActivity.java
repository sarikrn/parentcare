package com.informatika.parentcare;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ChildProfileActivity extends AppCompatActivity {

    private TextView setKode, setNama, setUrutan;
    private String kodeAnak, namaAnak, urutanAnak;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_profile);

        setNama = (TextView) findViewById(R.id.namaAnak);
        setUrutan = (TextView) findViewById(R.id.urutanAnak);

    }

    @Override
    protected void onStart() {
        super.onStart();

        kodeAnak = getIntent().getStringExtra("id");
        namaAnak = getIntent().getStringExtra("nama");
        urutanAnak = getIntent().getStringExtra("urutan");

        setNama.setText(namaAnak.toUpperCase());
        setUrutan.setText("Anak " + urutanAnak);

        findViewById(R.id.lakukanTest).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChildProfileActivity.this, ConsultationActivity.class));
            }
        });
    }
}
