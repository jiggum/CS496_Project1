package com.example.q.myapplication;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.io.InputStream;
import java.util.ArrayList;

public class PersonList extends AppCompatActivity {

    ListView listPerson;
    ListViewAdapter listViewAdapter;
    JSONObject object;
    JSONArray contactList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_list);

        listPerson = (ListView)findViewById(R.id.listPerson);
        getList();
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
        Cursor contactCursor = managedQuery(uri,projection,null,selectionArgs, sortOrder);
        //Cursor contactCursor = managedQuery(uri,null,null,selectionArgs, sortOrder);

        ArrayList persons = new ArrayList();
       // StringBuffer sb = new StringBuffer();

        object = new JSONObject();
        contactList = new JSONArray();
        listViewAdapter = new ListViewAdapter(this);
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
                //persons.add(contactCursor.getString(1)+"/"+contactCursor.getString(0));
                ContentResolver cr = getContentResolver();
                int contactId_idx = contactCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID);
                Long contactid = contactCursor.getLong(3);
                Log.e("###",contactId_idx+" | "+contactid+contactCursor.getString(1) );

                Uri puri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI,contactid);
                InputStream input = ContactsContract.Contacts.openContactPhotoInputStream(cr,puri);

                if( input != null ) {
                    Bitmap contactPhoto = BitmapFactory.decodeStream(input);
                    Drawable drawable = (Drawable)(new BitmapDrawable(contactPhoto));
                    listViewAdapter.addItem(drawable, contactCursor.getString(1), contactCursor.getString(0));
                }else {

                    Drawable drawable = (Drawable)(new BitmapDrawable(BitmapFactory.decodeFile(String.valueOf(R.drawable.ic_launcher))));
                    listViewAdapter.addItem(drawable,contactCursor.getString(1),contactCursor.getString(0));
                }
            }while(contactCursor.moveToNext());
        }
        try {
            object.put("Contact", contactList);
            System.out.println(object);

        }catch (JSONException e){
            e.printStackTrace();
        }
       //JSONObject k = new JSONObject()
        try {
            JSONArray Array = new JSONArray(object.getString("Contact"));
            for (int i = 0; i < Array.length(); i++) {
                JSONObject inside = Array.getJSONObject(i);

                Toast.makeText(this, inside.getString("name"),Toast.LENGTH_SHORT ).show();
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
        //리스트에 연결할 adapter 설정
        //ArrayAdapter adp = new ArrayAdapter(this,R.layout.listlayout, persons);


        //리스트뷰에 표시
        listPerson.setAdapter(listViewAdapter);
        ListViewExampleClickListener listViewExampleClickListener = new ListViewExampleClickListener();

        listPerson.setOnItemClickListener(listViewExampleClickListener);

    }
    public class ListViewExampleClickListener implements AdapterView.OnItemClickListener{
        String  pn;
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(PersonList.this, Call.class);
            //getViewByPosition(position,(ListView)listPerson);
            //여기에 넘버 찾아서 넘기기 모르겠다 !!

            ListData mData = ListViewAdapter.mListData.get(position);
            intent.putExtra("phoneNumber",mData.mNumber);
            startActivity(intent);
        }
    }
    /*
    public View getViewByPosition(int pos, ListView listView) {
        final int firstListItemPosition = listView.getFirstVisiblePosition();
        final int lastListItemPosition = firstListItemPosition + listView.getChildCount() - 1;

        if (pos < firstListItemPosition || pos > lastListItemPosition ) {
            return listView.getAdapter().getView(pos, null, listView);
        } else {
            final int childIndex = pos - firstListItemPosition;
            return listView.getChildAt(childIndex);
        }
    }
*/
}
