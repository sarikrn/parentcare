package com.informatika.parentcare;

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
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "messege";
    private FirebaseAuth mAuth;
    private BlurLockView blurLockView;

    private List<Pengguna> dfPengguna = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        blurLockView = (BlurLockView) findViewById(R.id.blurLockView);

        FirebaseDatabase.getInstance().getReference()
                .child("pengguna")
                .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds: dataSnapshot.getChildren()) {
                    Pengguna pengguna = ds.getValue(Pengguna.class);
                    dfPengguna.add(pengguna);
                }

                pinCodeLogin(dfPengguna);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        });
    }

    private void pinCodeLogin(List<Pengguna> dfPengguna){
        String password = "";

        for (int i=0;i<dfPengguna.size();i++){
            if(dfPengguna.get(i).getEmail().equals(mAuth.getCurrentUser().getEmail())){
                password = dfPengguna.get(i).getPassword();
                break;
            }
        }

        blurLockView.setCorrectPassword(password);
        blurLockView.setLeftButton("Batal");
        blurLockView.setRightButton("Hapus");
        blurLockView.setTypeface(Typeface.DEFAULT);
        blurLockView.setType(Password.NUMBER, false);

        blurLockView.setOnLeftButtonClickListener(new BlurLockView.OnLeftButtonClickListener() {
            @Override
            public void onClick() {
//                Toast.makeText(LoginActivity.this, "LEFT CLICKED", Toast.LENGTH_SHORT).show();
                finish();
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
                if(count < 3){
                    Toast.makeText(LoginActivity.this, "Password Salah", Toast.LENGTH_SHORT).show();
                }else{
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
