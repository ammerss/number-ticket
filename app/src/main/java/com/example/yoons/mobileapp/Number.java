package com.example.yoons.mobileapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;

public class Number extends AppCompatActivity {
    public long total_num;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.number);

        Intent intent = getIntent();
        String qr_id = intent.getExtras().getString("qrID");
        DatabaseReference mReference = FirebaseDatabase.getInstance().getReference("밍");

        initDatabase();

        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                total_num= dataSnapshot.getChildrenCount();
                Log.d("Total CoUNT0",""+total_num);
                String num=Long.toString(total_num);
                Log.d("what?",num);
                TextView textView = (TextView)findViewById(R.id.pretty);
                textView.setText(num+"번");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("tag", "getUser:onCancelled", databaseError.toException());
            }
        });
    }
    private void initDatabase() {

        ChildEventListener mChild = new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
    }
    @Override
    protected void onDestroy () {
        super.onDestroy();
    }
}
