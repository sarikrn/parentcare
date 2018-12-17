package com.informatika.parentcare;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.informatika.parentcare.model.Pengguna;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "messege";
    private EditText fnama, fconfirm, femail, fpassword;
    private RadioGroup statusGroup;
    private RadioButton statusOrangtua;
    private FirebaseAuth mAuth;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        fnama = (EditText) findViewById(R.id.nama);
        femail = (EditText) findViewById(R.id.email);
        fpassword = (EditText) findViewById(R.id.password);
        fconfirm = (EditText) findViewById(R.id.confirm_password);

        statusGroup = (RadioGroup) findViewById(R.id.statusGroup);

        mAuth = FirebaseAuth.getInstance();

        findViewById(R.id.btn_register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view == findViewById(R.id.btn_register)) {
                    registerUser();

                    progressDialog = new ProgressDialog(RegisterActivity.this);
                    progressDialog.setMessage("Loading..."); // Setting Message
                    progressDialog.setTitle("Proses Registrasi"); // Setting Title
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
                    progressDialog.show(); // Display Progress Dialog
                    progressDialog.setCancelable(false);
                    new Thread(new Runnable() {
                        public void run() {
                            try {
                                Thread.sleep(5000);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            progressDialog.dismiss();
                        }
                    }).start();
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
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(RegisterActivity.this, "Silahkan Register Terlebih Dahulu", Toast.LENGTH_LONG).show();
        }
    }

    public void registerUser() {
        final String nama, password, confirm, email, status;

        nama = fnama.getText().toString();
        email = femail.getText().toString();
        password = fpassword.getText().toString();
        confirm = fconfirm.getText().toString();

        int selectedID = statusGroup.getCheckedRadioButtonId();
        statusOrangtua = (RadioButton) findViewById(selectedID);
        status = statusOrangtua.getText().toString();

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
                                        status,
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
}
