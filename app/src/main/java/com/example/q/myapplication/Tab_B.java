package com.example.q.myapplication;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by q on 2016-06-28.
 */
public class Tab_B extends Fragment {
    public Tab_B(){}

    public static Tab_B newInstance(Bundle args){
        Tab_B fragment = new Tab_B();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.tab_b, container, false);

        return rootView;
    }
}
