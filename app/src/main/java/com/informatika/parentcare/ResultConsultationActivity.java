package com.informatika.parentcare;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ResultConsultationActivity extends AppCompatActivity {

    private String solusi, kodeAnak, namaAnak, urutanAnak, status;
    private TextView solusiHasil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_consultation);

        solusi = getIntent().getStringExtra("solusi");
        kodeAnak = getIntent().getStringExtra("kodeAnak");
        namaAnak = getIntent().getStringExtra("nama");
        urutanAnak = getIntent().getStringExtra("urutan");
        status = getIntent().getStringExtra("status");

        solusiHasil = (TextView) findViewById(R.id.deskSolusi);

        if (status.equals("ada")) {
            solusiHasil.setText(kodeAnak + " - " + solusi);
        }else{
            solusiHasil.setText(solusi);
        }

        findViewById(R.id.btn_kembaliProfilAnak).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ResultConsultationActivity.this, ChildProfileActivity.class)
                        .putExtra("kodeAnak", kodeAnak)
                        .putExtra("nama", namaAnak)
                        .putExtra("urutan", urutanAnak));
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(ResultConsultationActivity.this,ChildProfileActivity.class));
    }
}
