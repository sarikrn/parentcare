package com.informatika.parentcare;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

    }
}
