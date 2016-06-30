package com.example.q.myapplication;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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
                //int permissionCheck = ContextCompat.checkSelfPermission(this,Manifest.permission.CALL_PHONE);
                int  MY_PERMISSIONS_REQUEST_READ_CONTACTS = 201;

                // Here, thisActivity is the current activity
                if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.CALL_PHONE)
                        != PackageManager.PERMISSION_GRANTED) {

                    // Should we show an explanation?
                    if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                            Manifest.permission.CALL_PHONE)) {

                        // Show an expanation to the user *asynchronously* -- don't block
                        // this thread waiting for the user's response! After the user
                        // sees the explanation, try again to request the permission.


                    } else {

                        // No explanation needed, we can request the permission.

                        ActivityCompat.requestPermissions(this,
                                new String[]{Manifest.permission.CALL_PHONE},
                                MY_PERMISSIONS_REQUEST_READ_CONTACTS);

                        // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                        // app-defined int constant. The callback method gets the
                        // result of the request.
                    }
                }
                startActivity(intent);
                break;
        }
    }
}
