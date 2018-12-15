package com.informatika.parentcare;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.informatika.parentcare.model.Pengguna;
import com.nightonke.blurlockview.BlurLockView;
import com.nightonke.blurlockview.Password;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "messege";
    private BlurLockView blurLockView;
    private ArrayList<Pengguna> dfPengguna;

    private FirebaseAuth mAuth;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        blurLockView = (BlurLockView) findViewById(R.id.blurLockView);
        updateUI();

        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setMessage("Data Anda sedang di proses"); // Setting Message
        progressDialog.setTitle("Tunggu Sebentar"); // Setting Title
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
        progressDialog.show(); // Display Progress Dialog
        progressDialog.setCancelable(false);
        new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(3500);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                progressDialog.dismiss();
            }
        }).start();

    }

    private void updateUI() {
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

    public void selectedUser(ArrayList<Pengguna> pengguna, String email) {
        int i = 0;
        while (!pengguna.get(i).getEmail().equals(email)){
            i++;
        }
        Toast.makeText(LoginActivity.this, pengguna.get(i).getEmail(), Toast.LENGTH_LONG).show();

        blurLockView.setCorrectPassword(pengguna.get(i).getPassword());
        blurLockView.setLeftButton("Batal");
        blurLockView.setRightButton("Hapus");
        blurLockView.setTypeface(Typeface.DEFAULT);
        blurLockView.setType(Password.NUMBER, false);

        blurLockView.setOnLeftButtonClickListener(new BlurLockView.OnLeftButtonClickListener() {
            @Override
            public void onClick() {
                finish();
                System.exit(0);
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        //@Sari Kurnia : Fungsi Autentifikasi Pengguna
        blurLockView.setOnPasswordInputListener(new BlurLockView.OnPasswordInputListener() {
            @Override
            public void correct(String inputPassword) {
                Toast.makeText(LoginActivity.this, "Selamat Datang", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(intent);
            }

            @Override
            public void incorrect(String inputPassword) {
                int count = 0;
                if (count < 3) {
                    Toast.makeText(LoginActivity.this, "Password Salah", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LoginActivity.this, "Coba Lain Kali", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void input(String inputPassword) {

            }
        });
    }

}
