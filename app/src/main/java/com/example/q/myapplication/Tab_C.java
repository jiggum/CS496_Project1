package com.example.q.myapplication;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by q on 2016-06-28.
 */
public class Tab_C extends Fragment {
    public Tab_C(){}

    public static Tab_C newInstance(Bundle args){
        Tab_C fragment = new Tab_C();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.tab_c, container, false);

        return rootView;
    }
}
