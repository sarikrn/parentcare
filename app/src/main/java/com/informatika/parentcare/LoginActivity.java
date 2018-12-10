package com.informatika.parentcare;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.nightonke.blurlockview.BlurLockView;
import com.nightonke.blurlockview.Password;

public class LoginActivity extends AppCompatActivity {

    private BlurLockView blurLockView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        blurLockView = (BlurLockView) findViewById(R.id.blurLockView);

        blurLockView.setCorrectPassword("1234");
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
                Intent intent = new Intent(LoginActivity.this, ChildProfileActivity.class);
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
