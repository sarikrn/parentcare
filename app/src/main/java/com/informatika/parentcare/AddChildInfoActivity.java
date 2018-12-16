package com.informatika.parentcare;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.informatika.parentcare.adapter.ChildAdapter;
import com.informatika.parentcare.model.Anak;

import java.util.ArrayList;


public class AddChildInfoActivity extends AppCompatActivity {
    private EditText fnama, fstatus_anak;
    private TextView tketanak;
    private ArrayList<Anak> dfAnak = new ArrayList<>();

    private ChildAdapter childAdapter;
    private DatabaseReference dbAnak ;
    private ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_child_info);

        dbAnak = FirebaseDatabase.getInstance().getReference();

        fnama = (EditText) findViewById(R.id.nama);
        fstatus_anak = (EditText) findViewById(R.id.status_anak);
        tketanak = (TextView) findViewById(R.id.keterangan_anak);

        dbAnak.child("anak").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int size = (int) dataSnapshot.getChildrenCount();
                tketanak.setText(String.valueOf(size));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        findViewById(R.id.btn_add_child).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nama = fnama.getText().toString();
                String status = fstatus_anak.getText().toString();

                addChild(new Anak(nama, "perempuan", status));
                loading = ProgressDialog.show(AddChildInfoActivity.this,
                        null,
                        "Mohon tunggu sebentar...",
                        true,
                        false);
            }
        });
    }

    private void addChild(Anak anak){
        String getCount = tketanak.getText().toString();

        Toast.makeText(AddChildInfoActivity.this,
                String.valueOf(getCount),
                Toast.LENGTH_SHORT).show();

        dbAnak.child("anak")
                .child("__anak" + getCount)
                .setValue(anak)
                .addOnSuccessListener(AddChildInfoActivity.this, new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(AddChildInfoActivity.this,
                                "Data Berhasil ditambahkan",
                                Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(AddChildInfoActivity.this, HomeActivity.class);
                        startActivity(intent);
                    }
                });

    }

//    private void addConsultation(Anak anak){
//        dbAnak.child("anak").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for (DataSnapshot ds : dataSnapshot.getChildren()){
//                    Anak listAnak = ds.getValue(Anak.class);
//                    listAnak.setKey(ds.getKey());
//                    dfAnak.add(listAnak);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//
//        dbAnak.child("anak")
//                .child(anak.getKey())
//                .setValue(anak)
//                .addOnSuccessListener(AddChildInfoActivity.this, new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//                        Toast.makeText(AddChildInfoActivity.this,"Data berhasil diubah", Toast.LENGTH_LONG).show();
//                    }
//                });
//
//    }

}
