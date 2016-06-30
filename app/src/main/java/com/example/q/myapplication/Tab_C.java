package com.example.q.myapplication;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by q on 2016-06-28.
 */
public class Tab_C extends Fragment implements OnMapReadyCallback{
    GoogleMap _map;

    public Tab_C(){}

    public static Tab_C newInstance(Bundle args){
        Tab_C fragment = new Tab_C();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.tab_c, container, false);
        try {
            MapsInitializer.initialize(this.getActivity());

            SupportMapFragment mSupportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);

            mSupportMapFragment.getMapAsync(this);

        }
        catch (InflateException e){
        }
        return rootView;
    }

    @Override
    public void onMapReady(GoogleMap map) {
        _map = map;
        LatLng kaist = new LatLng(36.372125, 127.360513);
        map.moveCamera(CameraUpdateFactory.newLatLng(kaist));
        map.animateCamera(CameraUpdateFactory.zoomTo(14));
        map.getUiSettings().setZoomControlsEnabled(true);
        map.getUiSettings().setRotateGesturesEnabled(false);
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (_map != null) {
            getFragmentManager().beginTransaction()
                    .remove(getFragmentManager().findFragmentById(R.id.map))
                    .commit();
            _map = null;

        }
    }

}
