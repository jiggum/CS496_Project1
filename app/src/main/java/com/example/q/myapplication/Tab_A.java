package com.example.q.myapplication;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by q on 2016-06-28.
 */
public class Tab_A extends Fragment {
    public Tab_A(){}

    public static Tab_A newInstance(Bundle args){
        Tab_A fragment = new Tab_A();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.tab_a, container, false);

        return rootView;
    }
}
