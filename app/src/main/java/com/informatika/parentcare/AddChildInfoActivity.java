package com.informatika.parentcare;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.informatika.parentcare.adapter.ChildAdapter;
import com.informatika.parentcare.model.Anak;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;


public class AddChildInfoActivity extends AppCompatActivity {
    private EditText fnama, fstatus_anak, ttl;
    private RadioGroup genderGroup;
    private RadioButton genderStatus;
    private TextView tketanak;
    private ArrayList<Anak> dfAnak = new ArrayList<>();
    private DatabaseReference dbAnak;
    private FirebaseAuth mAuth;
    private ProgressDialog loading;
    final Calendar myCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_child_info);

        dbAnak = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        fnama = (EditText) findViewById(R.id.nama);
        fstatus_anak = (EditText) findViewById(R.id.statusAnak);
        ttl = (EditText) findViewById(R.id.ttl);
        tketanak = (TextView) findViewById(R.id.keterangan_anak);

        genderGroup = (RadioGroup) findViewById(R.id.statusGroup);

        dbAnak.child("anak").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int size = (int) dataSnapshot.getChildrenCount();
                tketanak.setText(String.valueOf(size + 1));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        findViewById(R.id.btn_register_anak).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nama = fnama.getText().toString().toUpperCase();
                String status = fstatus_anak.getText().toString();
                String tanggal_lahir = ttl.getText().toString();

                int selectedId = genderGroup.getCheckedRadioButtonId();
                genderStatus = (RadioButton) findViewById(selectedId);
                String gender = genderStatus.getText().toString().toLowerCase();

                addChild(new Anak(nama, gender, tanggal_lahir, mAuth.getCurrentUser().getUid(), status));
                loading = ProgressDialog.show(AddChildInfoActivity.this,
                        null,
                        "Mohon tunggu sebentar...",
                        true,
                        false);
            }
        });

        ttl = (EditText) findViewById(R.id.ttl);
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        ttl.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(AddChildInfoActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }

    private void updateLabel() {
        String myFormat = "dd-MM-yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        ttl.setText(sdf.format(myCalendar.getTime()));
    }

    private void addChild(Anak anak) {
        String getCount = tketanak.getText().toString();

        dbAnak.child("anak")
                .child("A" + getCount)
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

}
