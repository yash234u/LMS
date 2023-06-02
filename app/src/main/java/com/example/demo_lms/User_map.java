package com.example.demo_lms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class User_map extends AppCompatActivity implements OnMapReadyCallback {
    GoogleMap googlemap;
    MarkerOptions marker;
    CameraPosition camera;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_map);


        final ActivityManager activitymanager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        final ConfigurationInfo configurationInfo = activitymanager.getDeviceConfigurationInfo();
        final boolean supportsEs2 = configurationInfo.reqGlEsVersion >= 0x20000;
        if (supportsEs2) {
                /*if(latitude.equals(null)||longitude.equals(null))
                {
        			latitude="0.0";
        			longitude="0.0";
        		}*/
            if (googlemap == null) {
                MapFragment mFrag = (MapFragment) getFragmentManager().findFragmentById(R.id.usermap);
                mFrag.getMapAsync(this);
            }
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap map) {
       // double latitude =19.137797 ;        // userlocation.getLatitude();
      //  double longitude =72.859963;        // userlocation.getLongitude();


        ArrayList<LocationList> lats = new ArrayList<>();
        lats.add(new LocationList("18.969050","72.821180","Mahim Sarvajanik Vachanalay"));
        lats.add(new LocationList("19.050870","72.940300","Ideal book depot"));


        googlemap = map;

        for(int i=0;i<lats.size();i++)
        {

            double latd = Double.parseDouble(lats.get(i).Latitude);
            double longd = Double.parseDouble(lats.get(i).Longitude);
          //  String name = "sagar"; //SelectedPassenger.getName();
         //   marker = new MarkerOptions().position(new LatLng(latd, longd)).title(name);
         //   camera = new CameraPosition.Builder().target(new LatLng(latd, longd)).zoom(15).build();

            if (map != null) {
                //plot point
                googlemap.addMarker(new MarkerOptions().position(new LatLng(latd, longd)).title(lats.get(i).name));
              //  googlemap.addMarker(marker);
                //focus camera to point

            }
            camera = new CameraPosition.Builder().target(new LatLng(latd, longd)).zoom(15).build();
            googlemap.animateCamera(CameraUpdateFactory.newCameraPosition(camera));
        }





        map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);

        //map.setMyLocationEnabled(true);
        map.setTrafficEnabled(true);
        map.setIndoorEnabled(true);
        map.setBuildingsEnabled(true);
        map.getUiSettings().setZoomControlsEnabled(true);
    }

}