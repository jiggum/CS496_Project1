package com.example.q.myapplication;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Locale;

/**
 * Created by q on 2016-06-28.
 */
public class Tab_C extends Fragment implements OnMapReadyCallback{
    GoogleMap _map;
    private LocationManager locationManager;
    private GoogleApiClient mGoogleApiClient;
    int PLACE_PICKER_REQUEST = 3;

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

        mGoogleApiClient = new GoogleApiClient
                .Builder(getActivity())
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .build();

        View rootView = inflater.inflate(R.layout.tab_c, container, false);
        try {
            MapsInitializer.initialize(this.getActivity());

            SupportMapFragment mSupportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);

            mSupportMapFragment.getMapAsync(this);

        }
        catch (InflateException e){
        }
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        if( mGoogleApiClient != null )
            mGoogleApiClient.connect();
    }

    @Override
    public void onStop() {
        if( mGoogleApiClient != null && mGoogleApiClient.isConnected() ) {
            mGoogleApiClient.disconnect();
        }
        super.onStop();
    }
    @Override
    public void onMapReady(GoogleMap map) {
        _map = map;
        LatLng kaist = new LatLng(36.372125, 127.360513);

        map.getUiSettings().setZoomControlsEnabled(true);
        map.getUiSettings().setRotateGesturesEnabled(false);
        map.moveCamera(CameraUpdateFactory.newLatLng(kaist));
        map.getUiSettings().setMyLocationButtonEnabled(true);
        map.animateCamera(CameraUpdateFactory.zoomTo(14));
        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng point) {


                Toast toast = Toast.makeText(getContext(), getNearbyPlaceName(point),Toast.LENGTH_SHORT);
                toast.show();

            }
        });
    }


    private String getNearbyPlaceName(LatLng point){
        String NearbyPlaceURL = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?rankby=distance&type=point_of_interest&key=AIzaSyDXgwnlZ35SmzwN1NA2GZsPKl3NUkEGeX0&location=";
        String urlString = NearbyPlaceURL +point.latitude +","+point.longitude;
        try {
            // call API by using HTTPURLConnection
            URL url = new URL(urlString);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            JSONObject json = new JSONObject(getStringFromInputStream(in));

            // parse JSON

            return json.getJSONArray("results").getJSONObject(0).getString("name");

        }catch(MalformedURLException e){
            System.err.println("Malformed URL");
            e.printStackTrace();
            return null;
        }catch(JSONException e) {
            System.err.println("JSON parsing error");
            e.printStackTrace();
            return null;
        }catch(IOException e){
            System.err.println("URL Connection failed");
            e.printStackTrace();
            return null;
        }
    }

    private static String getStringFromInputStream(InputStream is) {

        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();

        String line;
        try {

            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return sb.toString();

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

    public String getAddress(Context context, double lat, double lng) {
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
            Address obj = addresses.get(0);

            String add = obj.getAddressLine(0);
            add = add + "\n" + obj.getCountryName();
            add = add + "\n" + obj.getCountryCode();
            add = add + "\n" + obj.getAdminArea();
            add = add + "\n" + obj.getPostalCode();
            add = add + "\n" + obj.getSubAdminArea();
            add = add + "\n" + obj.getLocality();
            add = add + "\n" + obj.getSubThoroughfare();

            return add;
        } catch (IOException e) {
            return null;
        }
    }


}
