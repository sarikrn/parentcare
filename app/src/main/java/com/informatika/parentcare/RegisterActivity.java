package com.informatika.parentcare;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.informatika.parentcare.model.Pengguna;

public class RegisterActivity extends AppCompatActivity{

    private EditText fnama, fconfirm, femail, fpassword;
    private FirebaseAuth mAuth;

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
                if(view == findViewById(R.id.btn_register)){
                    registerUser();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() != null) {
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
        }
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
                                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
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
}
