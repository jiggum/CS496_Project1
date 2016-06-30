package com.example.q.myapplication;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class Contacts extends AppCompatActivity {

    // Request code for READ_CONTACTS. It can be any number > 0.
     int PERMISSIONS_REQUEST_READ_CONTACTS = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

       // showContacts();
        // Check the SDK version and whether the permission is already granted or not.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, PERMISSIONS_REQUEST_READ_CONTACTS);
            //After this point you wait for callback in onRequestPermissionsResult(int, String[], int[]) overriden method
        }else {
            // Android version is lesser than 6.0 or the permission is already granted.
            getContacts();
        }
    }

    private void showContacts(){

        // Check the SDK version and whether the permission is already granted or not.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, PERMISSIONS_REQUEST_READ_CONTACTS);
            //After this point you wait for callback in onRequestPermissionsResult(int, String[], int[]) overriden method
        }else {
            // Android version is lesser than 6.0 or the permission is already granted.
           getContacts();
        }
    }
    /*
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted
                showContacts();
            } else {
                Toast.makeText(this, "Until you grant the permission, we canot display the names", Toast.LENGTH_SHORT).show();
            }
        }
    }
*/
    private void getContacts() {
        ContentResolver cr = getContentResolver();
            Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI,null,null,null,null);

            int ididx = cursor.getColumnIndex(ContactsContract.Contacts._ID);
            int nameidx = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);

            StringBuilder result = new StringBuilder();

            while (cursor.moveToNext()){
                result.append(cursor.getString(nameidx)+" :");

                //전화번호는 서브 쿼리로오오오옹ㅇ
                String id = cursor.getString(ididx);
                Cursor cursor2 = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null
                        ,ContactsContract.CommonDataKinds.Phone.CONTACT_ID+" = ?",
                        new String[]{id}, null);

                int typeidx = cursor2.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE);
                int numidx = cursor2.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);

                //전화의 타입에 따라 여러 가지가 존재한다
                while ( cursor2.moveToNext()){
                    String num = cursor2.getString(numidx);
                    switch (cursor2.getInt(typeidx)){
                        case ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE :
                            result.append(" Mobile :"+num);
                            break;
                        case ContactsContract.CommonDataKinds.Phone.TYPE_HOME :
                            result.append(" Home :"+num);
                            break;
                        case ContactsContract.CommonDataKinds.Phone.TYPE_WORK :
                            result.append(" Work :"+num);
                            break;
                    }
                }
                cursor2.close();
                result.append("\n");
            }
            cursor.close();

            TextView txtResult = (TextView)findViewById(R.id.result);
            txtResult.setText(result);
        }
    }
