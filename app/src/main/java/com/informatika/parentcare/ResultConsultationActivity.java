package com.informatika.parentcare;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ResultConsultationActivity extends AppCompatActivity {

    private String solusi, kodeAnak;
    private TextView solusiHasil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_consultation);

        solusi = getIntent().getStringExtra("solusi");
        kodeAnak = getIntent().getStringExtra("kodeAnak");

        solusiHasil = (TextView) findViewById(R.id.deskSolusi);
        solusiHasil.setText(kodeAnak + " - " + solusi);

        findViewById(R.id.btn_kembaliProfilAnak).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ResultConsultationActivity.this, ChildProfileActivity.class));
            }
        });
    }
}
