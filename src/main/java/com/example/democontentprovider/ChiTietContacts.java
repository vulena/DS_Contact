package com.example.democontentprovider;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class ChiTietContacts extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_contacts);
        Intent intent = getIntent();
        TextView tv = (TextView) findViewById(R.id.thongtin);
        tv.setText(intent.getStringExtra("Dulieu"));
    }
}