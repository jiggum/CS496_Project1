package com.example.q.myapplication;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by q on 2016-06-28.
 */
public class Tab_A extends Fragment {

    ListView listPerson;
    ListViewAdapter listViewAdapter;
    JSONObject object;
    JSONArray contactList;
    int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 201;

    public Tab_A(){}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }


    public static Tab_A newInstance(Bundle args){
        Tab_A fragment = new Tab_A();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.tab_a, container, false);



        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.READ_CONTACTS)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.


            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.READ_CONTACTS},
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }

        listPerson = (ListView)rootView.findViewById(R.id.listPerson);//?
        getList();
        return rootView;
    }

    public void getList() {
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;

        String[] projection = new String[] {
                ContactsContract.CommonDataKinds.Phone.NUMBER,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.PHOTO_ID,
                ContactsContract.Data.CONTACT_ID
        };

        String[] selectionArgs = null;

        String sortOrder = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " COLLATE LOCALIZED ASC";
        Cursor contactCursor = getActivity().getContentResolver().query(uri,projection,null,selectionArgs, sortOrder);
        //Cursor contactCursor = managedQuery(uri,null,null,selectionArgs, sortOrder);


        object = new JSONObject();
        contactList = new JSONArray();
        listViewAdapter = new ListViewAdapter(getActivity());
        JSONObject oneContact;


        if (contactCursor.moveToFirst()){
            do {
                try {
                    oneContact = new JSONObject();
                    oneContact.put("name", contactCursor.getString(1));
                    oneContact.put("number", contactCursor.getString(0));
                    contactList.put(oneContact);
                } catch (JSONException e){
                    e.printStackTrace();
                }

                ContentResolver cr = getActivity().getContentResolver();
                int contactId_idx = contactCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID);
                Long contactid = contactCursor.getLong(3);

                Uri puri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI,contactid);
                InputStream input = ContactsContract.Contacts.openContactPhotoInputStream(cr,puri);

                if( input != null ) {
                    Bitmap contactPhoto = BitmapFactory.decodeStream(input);
                    Drawable drawable = (Drawable)(new BitmapDrawable(contactPhoto));
                    listViewAdapter.addItem(drawable, contactCursor.getString(1), contactCursor.getString(0));
                }else {
                    listViewAdapter.addItem(null,contactCursor.getString(1),contactCursor.getString(0));
                }
            }while(contactCursor.moveToNext());
        }
        try {
            object.put("Contact", contactList);

        }catch (JSONException e){
            e.printStackTrace();
        }
        //JSONObject k = new JSONObject()
        /*
        try {
            JSONArray Array = new JSONArray(object.getString("Contact"));
            for (int i = 0; i < Array.length(); i++) {
                JSONObject inside = Array.getJSONObject(i);

                Toast.makeText(this, inside.getString("name"),Toast.LENGTH_SHORT ).show();
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
        */

        Log.e("here","-----------------------------------!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        //리스트뷰에 표시
        listPerson.setAdapter(listViewAdapter);
        ListViewExampleClickListener listViewExampleClickListener = new ListViewExampleClickListener();

        listPerson.setOnItemClickListener(listViewExampleClickListener);

    }
    public class ListViewExampleClickListener implements AdapterView.OnItemClickListener{
        String  pn;
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(getActivity(), Call.class);
            //getViewByPosition(position,(ListView)listPerson);
            //여기에 넘버 찾아서 넘기기 모르겠다 !!

            ListData mData = ListViewAdapter.mListData.get(position);
            intent.putExtra("phoneNumber",mData.mNumber);
            startActivity(intent);
        }
    }

}
