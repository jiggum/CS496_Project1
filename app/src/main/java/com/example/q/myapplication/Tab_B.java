package com.example.q.myapplication;

import android.*;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

/**
 * Created by q on 2016-06-28.
 */
public class Tab_B extends Fragment {
    private GridView gridView;
    private GridViewAdapter gridAdapter;
    private static int RESULT_LOAD_IMG = 1;
    String imgDecodableString;
    int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 202;

    public Tab_B(){}

    public static Tab_B newInstance(Bundle args){
        Tab_B fragment = new Tab_B();
        fragment.setArguments(args);
        return fragment;
    }

    public void loadImagefromGallery(View view) {
        // Create intent to Open Image applications like Gallery, Google Photos
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        // Start the Intent
        startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try {
            // When an Image is picked
            if(requestCode == 2){
                gridView = (GridView) getView().findViewById(R.id.gridView);
                gridAdapter = new GridViewAdapter(getActivity(), R.layout.grid_item_layout, getData());
                gridView.setAdapter(gridAdapter);
                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                        ImageItem item = (ImageItem) parent.getItemAtPosition(position);

                        //Create intent
                        Intent intent = new Intent(getActivity(), DetailsActivity.class);
                        intent.putExtra("title", item.getTitle());
                        intent.putExtra("image", item.getImage());
                        intent.putExtra("position", item.getPosition());

                        //Start details activity
                        startActivityForResult(intent,2);
                    }
                });
            }
            else {
                if (requestCode == RESULT_LOAD_IMG && resultCode == Activity.RESULT_OK && null != data) {
                    // Get the Image from data

                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};

                    // Get the cursor
                    Cursor cursor = getActivity().getContentResolver().query(selectedImage,
                            filePathColumn, null, null, null);
                    // Move to first row
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    imgDecodableString = cursor.getString(columnIndex);
                    cursor.close();

                    try {
                        FileOutputStream outputStream;
                        outputStream = getActivity().openFileOutput("mygallery.txt", getContext().MODE_APPEND);
                        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream));
                        writer.write(imgDecodableString);
                        writer.write("\n");
                        writer.close();
                        outputStream.close();
                        gridView = (GridView) getView().findViewById(R.id.gridView);
                        gridAdapter = new GridViewAdapter(getActivity(), R.layout.grid_item_layout, getData());
                        gridView.setAdapter(gridAdapter);
                        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                                ImageItem item = (ImageItem) parent.getItemAtPosition(position);

                                //Create intent
                                Intent intent = new Intent(getActivity(), DetailsActivity.class);
                                intent.putExtra("title", item.getTitle());
                                intent.putExtra("image", item.getImage());

                                //Start details activity
                                startActivityForResult(intent, 2);
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    // Set the Image in ImageView after decoding the String
                    //imgView.setImageBitmap(BitmapFactory.decodeFile(imgDecodableString));

                } else {
                    Toast.makeText(getActivity(), "You haven't picked Image", Toast.LENGTH_LONG).show();
                }
            }
        } catch (Exception e) {
            Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.


            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }

        View rootView = inflater.inflate(R.layout.tab_b, container, false);

        gridView = (GridView) rootView.findViewById(R.id.gridView);
        gridAdapter = new GridViewAdapter(getActivity(), R.layout.grid_item_layout, getData());
        gridView.setAdapter(gridAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                ImageItem item = (ImageItem) parent.getItemAtPosition(position);

                //Create intent
                Intent intent = new Intent(getActivity(), DetailsActivity.class);
                intent.putExtra("title", item.getTitle());
                intent.putExtra("image", item.getImage());

                //Start details activity
                startActivityForResult(intent,2);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadImagefromGallery(view);
            }
        });

        return rootView;
    }

    private ArrayList<ImageItem> getData() {
        final ArrayList<ImageItem> imageItems = new ArrayList<>();

        try {
            FileInputStream fis = getActivity().openFileInput("mygallery.txt");
            BufferedReader reader= new BufferedReader(new InputStreamReader(fis));
            String s;
            for(int i=0; (s = reader.readLine()) != null; i++){
                imageItems.add(new ImageItem(Bitmap.createScaledBitmap(BitmapFactory.decodeFile(s),300,300,true), s, i));
            }
            reader.close();
            fis.close();
        }catch(Exception e){

        }
        return imageItems;
    }


}
