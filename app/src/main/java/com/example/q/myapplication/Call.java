package com.example.q.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class Call extends AppCompatActivity {
    EditText mNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);
        Intent i = getIntent();
        String pn = i.getStringExtra("phoneNumber");
        mNumber = (EditText)findViewById(R.id.number);
        mNumber.setText(pn);
    }

    public void mOnClick(View v){
        Uri number;
        Intent intent;
        switch (v.getId()){
            case R.id.calldialer :
                number = Uri.parse("tel: "+mNumber.getText());
                intent = new Intent(Intent.ACTION_DIAL,number);
                startActivity(intent);
                break;
            case R.id.calldirect :
                number = Uri.parse("tel: "+ mNumber.getText());
                intent = new Intent(Intent.ACTION_CALL,number);
                break;
        }
    }
}
