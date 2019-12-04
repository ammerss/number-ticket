package com.example.yoons.mobileapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;



public class CreateQR extends AppCompatActivity {
    private ImageView iv;
    private String itemname;
    private ListView listView;
    private ArrayAdapter<String> adapter;
    List<Object> Array = new ArrayList<Object>();

    final ArrayList<String> keyList = new ArrayList<>();
    final ArrayList<String> items = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_qr);
        iv = (ImageView) findViewById(R.id.qrcode);
        Intent intent = getIntent();
        final String ID = intent.getExtras().getString("giverID");
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(ID, BarcodeFormat.QR_CODE, 200, 200);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            iv.setImageBitmap(bitmap);
        } catch (Exception e){
        }

        listView = (ListView) findViewById(R.id.listviewmsg);
        initDatabase();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, new ArrayList<String>());
        listView.setAdapter(adapter);
        DatabaseReference mReference = FirebaseDatabase.getInstance().getReference(ID); // 변경값을 확인할 child 이름
        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                adapter.clear();//리스트뷰 초기화
                for (DataSnapshot messageData : dataSnapshot.getChildren()) {
                    // child 내에 있는 데이터만큼 반복합니다.
                    String msg2 = messageData.getValue().toString();
                    Array.add(msg2);
                    adapter.add(msg2);
                }
                adapter.notifyDataSetChanged();
                listView.setSelection(adapter.getCount() - 1);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        final DatabaseReference MyRef = FirebaseDatabase.getInstance().getReference().getRoot();
        final Query applesQuery = mReference.child(ID).equalTo(itemname);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?>adapter,View v, int position,long id){
                String item = (String)adapter.getItemAtPosition(position);
                //Log.d("ITEMNAME!!!",item.toString());
                itemname=item;
                MyRef.getRoot().child(ID).child(keyList.get(position)).removeValue();
                keyList.remove(position);
            }
        });
        MyRef.getRoot().child(ID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                    //Log.d("whhtwhthtw",appleSnapshot.toString());
                    keyList.add(appleSnapshot.getKey());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("tag", "onCancelled", databaseError.toException());
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
        //mReference.addChildEventListener(mChild);

    }
        @Override
        protected void onDestroy () {
            super.onDestroy();
           // mReference.removeEventListener(mChild);
        }


}

