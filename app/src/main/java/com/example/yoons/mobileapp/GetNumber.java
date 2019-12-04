package com.example.yoons.mobileapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import androidx.appcompat.app.AppCompatActivity;


public class GetNumber extends AppCompatActivity {
    private IntentIntegrator qrScan;
    private EditText editdt;
    private String msg;
    private String qr_id;
    DatabaseReference root = FirebaseDatabase.getInstance().getReference().getRoot();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.getnumber);

    }
    public void getQR(View v){

        qrScan = new IntentIntegrator(this);
        qrScan.setBeepEnabled(false);//바코드 인식시 소리
        qrScan.setOrientationLocked(false); // default가 세로모드인데 휴대폰 방향에 따라 가로, 세로로 자동 변경됩니다.
        qrScan.setPrompt("QR 코드를 인식해주세요");
        qrScan.initiateScan();

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                //Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
                Toast.makeText(this, "번호표를 발급받으세요", Toast.LENGTH_LONG).show();
            }
            qr_id=result.getContents().toString();
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void sendMsg(View v){

        editdt = (EditText) findViewById(R.id.sendmsg);
        if(editdt ==null){
            Toast.makeText(this, "발급자 정보를 입력해주세요", Toast.LENGTH_LONG).show();
        }
        else {
            msg = editdt.getText().toString();
            root.child(qr_id).push().setValue(msg);
            //root.child("밍").push().setValue(msg);
            Intent intent = new Intent(getApplicationContext(), Number.class);
            intent.putExtra("qrID", qr_id);
            startActivity(intent);
        }
    }




    public void showNumber(){
          }

}

