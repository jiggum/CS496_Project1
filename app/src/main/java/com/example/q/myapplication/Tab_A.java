package com.example.q.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by q on 2016-06-28.
 */
public class Tab_A extends Fragment {
    Button button;
    Button button2;

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
        button = (Button)rootView.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v) {
                Intent intent = new Intent(getActivity(), Contacts.class);
                startActivity(intent);
            }
        });
        button2 = (Button)rootView.findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v) {
                Intent intent = new Intent(getActivity(), PersonList.class);
                startActivity(intent);
            }
        });

        return rootView;
    }
}
