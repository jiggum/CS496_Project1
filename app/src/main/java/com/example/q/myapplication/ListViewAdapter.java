package com.example.q.myapplication;

import android.content.Context;

import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;


/**
 * Created by q on 2016-06-29.
 */
public class ListViewAdapter extends BaseAdapter {

    private Context mContext = null;
    public static  ArrayList<ListData> mListData = new ArrayList<ListData>();

    public ListViewAdapter(Context mContext){
        super();
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return mListData.size();
    }

    @Override
    public Object getItem(int position){
        return  mListData.get(position);
    }
    public long getItemId(int position){
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
       ViewHolder holder;
        if (convertView == null){
            holder = new ViewHolder();

            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listlayout,null);

            holder.mPhoto = (ImageView) convertView.findViewById(R.id.mPhoto);
            holder.mName = (TextView) convertView.findViewById(R.id.mName);
            holder.mNumber = (TextView) convertView.findViewById(R.id.mNumber);

            convertView.setTag(holder);

        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        ListData mData = mListData.get(position);

        if (mData.mPhoto!=null){
            holder.mPhoto.setVisibility(View.VISIBLE);
            holder.mPhoto.setImageDrawable(mData.mPhoto);
        }else {

        }

        holder.mName.setText(mData.mName);
        holder.mNumber.setText(mData.mNumber);

        return convertView;

    }

    public void addItem(Drawable photo, String name, String number){
        ListData addInfo = null;
        addInfo = new ListData();
        addInfo.mPhoto = photo;
        addInfo.mName = name;
        addInfo.mNumber = number;

        mListData.add(addInfo);
    }


}
