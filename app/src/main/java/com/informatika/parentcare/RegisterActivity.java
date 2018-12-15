package com.informatika.parentcare;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.informatika.parentcare.model.Pengguna;

import java.util.ArrayList;

import static android.provider.Telephony.Carriers.PASSWORD;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "messege";
    private EditText fnama, fconfirm, femail, fpassword;
    private FirebaseAuth mAuth;

    private ArrayList<Pengguna> dfPengguna;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        fnama = (EditText) findViewById(R.id.nama);
        femail = (EditText) findViewById(R.id.email);
        fpassword = (EditText) findViewById(R.id.password);
        fconfirm = (EditText) findViewById(R.id.confirm_password);

        mAuth = FirebaseAuth.getInstance();

        findViewById(R.id.btn_register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view == findViewById(R.id.btn_register)) {
                    registerUser();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            // Check if user is signed in (non-null) and update UI accordingly.
//            updateUI(currentUser);
            Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(RegisterActivity.this, "Silahkan Register Terlebih Dahulu", Toast.LENGTH_LONG).show();
        }
    }

    private void updateUI(FirebaseUser currentUser) {
        dfPengguna = new ArrayList<>();

        FirebaseDatabase.getInstance().getReference()
                .child("pengguna")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            Pengguna pengguna = ds.getValue(Pengguna.class);
                            dfPengguna.add(pengguna);
                        }
                        selectedUser(dfPengguna, mAuth.getCurrentUser().getEmail());
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // Getting Post failed, log a message
                        Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                        // ...
                    }
                });

    }

    public void registerUser() {
        final String nama, password, confirm, email;

        nama = fnama.getText().toString();
        email = femail.getText().toString();
        password = fpassword.getText().toString();
        confirm = fconfirm.getText().toString();

        if (password.equals(confirm)) {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // store the additional fields in firebase database
                                Pengguna pengguna = new Pengguna(
                                        nama,
                                        password,
                                        "Ibu",
                                        email
                                );

                                FirebaseDatabase.getInstance().getReference("pengguna")
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .setValue(pengguna).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(RegisterActivity.this, "Registration Sucess", Toast.LENGTH_LONG).show();
                                            Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
                                            startActivity(intent);

                                        } else {
                                            //display failure
                                        }
                                    }
                                });

                            } else {
                                Toast.makeText(RegisterActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }

    }

    public void selectedUser(ArrayList<Pengguna> pengguna, String email) {
        int i = 0;
        while (!pengguna.get(i).getEmail().equals(email)){
            i++;
        }
        Toast.makeText(RegisterActivity.this, pengguna.get(i).getEmail(), Toast.LENGTH_LONG).show();
        Intent sendPassword = new Intent(RegisterActivity.this, LoginActivity.class);
        sendPassword.putExtra(PASSWORD, pengguna.get(i).getPassword());
        startActivity(sendPassword);

    }
}
