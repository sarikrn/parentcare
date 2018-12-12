package com.informatika.parentcare;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    public void DetailChildInfo(View view) {
        Intent intent = new Intent(HomeActivity.this, ChildProfileActivity.class);
        startActivity(intent);
    }
}
