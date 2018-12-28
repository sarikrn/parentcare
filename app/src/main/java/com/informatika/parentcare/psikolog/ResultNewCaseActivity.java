package com.informatika.parentcare.psikolog;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.informatika.parentcare.R;
import com.informatika.parentcare.ResultConsultationActivity;
import com.informatika.parentcare.adapter.GejalaKasusBaruAdapter;
import com.informatika.parentcare.model.Gejala;
import com.informatika.parentcare.model.Kasus;

import java.util.ArrayList;


public class ResultNewCaseActivity extends AppCompatActivity {

    private GejalaKasusBaruAdapter adapter;
    private RecyclerView rc_list_newcase;
    private ArrayList<Gejala> gejalaArrayList = new ArrayList<>();
    private DatabaseReference dbKasus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_new_case);
        dbKasus = FirebaseDatabase.getInstance().getReference();

        rc_list_newcase = (RecyclerView) findViewById(R.id.rc_list_newcase);
    }

    @Override
    protected void onStart() {
        super.onStart();

        ArrayList<String> kodeGejala = getIntent().getStringArrayListExtra("gejalaBaru");
        ArrayList<String> deskGejala = getIntent().getStringArrayListExtra("deskGejala");

        for(int i=0;i<kodeGejala.size();i++){
            Gejala gejala = new Gejala();
            gejala.setKode(kodeGejala.get(i));
            gejala.setDesk(deskGejala.get(i));
            gejalaArrayList.add(gejala);
        }
        adapter = new GejalaKasusBaruAdapter(gejalaArrayList, ResultNewCaseActivity.this);
        rc_list_newcase.setAdapter(adapter);
    }
}
