package com.informatika.parentcare;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class LoginPsikologActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_psikolog);
    }

    @Override
    protected void onStart() {
        super.onStart();

        findViewById(R.id.btn_login_psikolog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginPsikologActivity.this, AddCasebaseActivity.class));
            }
        });
    }
}
