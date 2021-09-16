package ucsc.mit.wmp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.data.geojson.GeoJsonLayer;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    final Context context = this;
    String run_name;
    String field_type;
    private DbHandler dbHandler;
    public static final String OviTrapId = "OviTrapId";
    public static final String BgTrapId = "BgTrapId";
    public static final String TrapStatus = "TrapStatus";
    public static final String TrapPosition = "TrapPosition";
    public static final String RespondName = "RespondName";
    public static final String LocationCoordinates = "LocationCoordinates";
    public static final String Phone = "Phone";
    public static final String AddressLine1 = "AddressLine1";
    public static final String AddressLine2 = "AddressLine2";
    public static final String LocationDescription = "LocationDescription";
    public static final String OviDetails = "OviDetails";
    public static final String BgDetails = "BgDetails";
    public static final String MrcDetails = "MrcDetails";
    public static final String MrcId = "MrcId";
    public static final String MrcStatus = "MrcStatus";
    public static final String OviRunId = "OviRunId";
    public static final String BgRunId = "BgRunId";
    public static final String MrcRunId = "MrcRunId";
    public static final String PersonId = "PersonId";
    public static final String AddressId = "AddressId";
    LatLng givenLocation;
    private List<OvTrapModel> ovModelList;
    private List<OvPersonAddressModel> ovPersonAddressModelList;
    private List<BgTrapModel> bgModelList;
    private List<BgPersonAddressModel> bgPersonAddressModelList;
    private List<MrcModel> mrcModelList;
    private List<MrcPersonAddressModel> mrcPersonAddressModelList;
    private ArrayList<LatLng> latLngArrayList;
    private ArrayList<String> locationNameArraylist;
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_view);
        field_type = getIntent().getStringExtra("field_type");
        run_name = getIntent().getStringExtra("run_name");
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // initializing our array lists.
        latLngArrayList = new ArrayList<>();
        locationNameArraylist = new ArrayList<>();

        dbHandler = new DbHandler(context);
        if(field_type.equals("ov")) {
            ovModelList = new ArrayList<>();
            ovModelList = dbHandler.getSingleOvTrap(run_name, field_type);
            for (int i = 0; i < ovModelList.size(); ++i) {
                String coordinates = ovModelList.get(i).coordinates.toString();
                String[] separated_coordinates = coordinates.split(",");
                String latitude = separated_coordinates[0].trim();
                String longitude = separated_coordinates[1].trim();
                givenLocation = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
                latLngArrayList.add(givenLocation);
                locationNameArraylist.add(ovModelList.get(i).ov_trap_id);
            }
        }
        if(field_type.equals("bg")) {
            bgModelList = new ArrayList<>();
            bgModelList = dbHandler.getSingleBgTrap(run_name, field_type);
            for (int i = 0; i < bgModelList.size(); ++i) {
                String coordinates = bgModelList.get(i).coordinates.toString();
                String[] separated_coordinates = coordinates.split(",");
                String latitude = separated_coordinates[0].trim();
                String longitude = separated_coordinates[1].trim();
                givenLocation = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
                latLngArrayList.add(givenLocation);
                locationNameArraylist.add(bgModelList.get(i).bg_trap_id);
            }
        }
        if(field_type.equals("mrc")) {
            mrcModelList = new ArrayList<>();
            mrcModelList = dbHandler.getSingleMrc(run_name, field_type);
            for (int i = 0; i < mrcModelList.size(); ++i) {
                String coordinates = mrcModelList.get(i).coordinates.toString();
                String[] separated_coordinates = coordinates.split(",");
                String latitude = separated_coordinates[0].trim();
                String longitude = separated_coordinates[1].trim();
                givenLocation = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
                latLngArrayList.add(givenLocation);
                locationNameArraylist.add(mrcModelList.get(i).identifier);
            }
        }
        // on below line we are adding
        // data to our array list.
        //latLngArrayList.add(TamWorth);
        //locationNameArraylist.add("TamWorth");
        //latLngArrayList.add(NewCastle);
        //locationNameArraylist.add("New Castle");
        //latLngArrayList.add(Brisbane);
        //locationNameArraylist.add("Brisbase");
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setZoomGesturesEnabled(true);
/*
        // Add geojson layers
        GeoJsonLayer layer_cmc = null;
        GeoJsonLayer layer_nug = null;
        try {
            layer_cmc = new GeoJsonLayer(mMap, R.raw.cmc84,
                    getApplicationContext());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        layer_cmc.addLayerToMap();


        try {
            layer_nug = new GeoJsonLayer(mMap, R.raw.nug84,
                    getApplicationContext());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        layer_nug.addLayerToMap();
*/
        // below line is to add marker to google maps
        for (int i = 0; i < latLngArrayList.size(); i++) {

            // adding marker to each location on google maps
            mMap.addMarker(new MarkerOptions().position(latLngArrayList.get(i)).zIndex(2.0f).icon(BitmapDescriptorFactory.defaultMarker
                    (BitmapDescriptorFactory.HUE_ROSE))).setTag(locationNameArraylist.get(i));

            // below line is use to move camera.
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLngArrayList.get(0)));
        }

        // adding on click listener to marker of google maps.
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Log.d("testclick","testclick");

                // on marker click we are getting the title of our marker
                // which is clicked and displaying it in a toast message.

                if(field_type.equals("ov")) {
                    dbHandler = new DbHandler(context);
                    ovPersonAddressModelList = new ArrayList<>();
                    ovPersonAddressModelList = dbHandler.getSingleOvTrap(marker.getTag().toString());
                    sharedpreferences = getSharedPreferences(OviDetails, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString(OviTrapId, ovPersonAddressModelList.get(0).ov_trap_id.toString());
                    editor.putString(TrapStatus, ovPersonAddressModelList.get(0).trap_status.toString());
                    editor.putString(TrapPosition, ovPersonAddressModelList.get(0).position.toString());
                    editor.putString(RespondName, ovPersonAddressModelList.get(0).person_name);
                    editor.putString(LocationCoordinates, ovPersonAddressModelList.get(0).coordinates);
                    editor.putString(Phone, String.valueOf(ovPersonAddressModelList.get(0).phone));
                    editor.putString(AddressLine1, ovPersonAddressModelList.get(0).address_line1);
                    editor.putString(AddressLine2, ovPersonAddressModelList.get(0).address_line2);
                    editor.putString(LocationDescription, ovPersonAddressModelList.get(0).location_description);
                    editor.putString(OviRunId, ovPersonAddressModelList.get(0).run_name);
                    editor.putInt(PersonId, ovPersonAddressModelList.get(0).person_id);
                    editor.putInt(AddressId, ovPersonAddressModelList.get(0).address_id);
                    editor.apply();

                    Intent intent = new Intent(context, AddOvMainActivity.class);
                    intent.putExtra("TrapId", marker.getTag().toString());
                    intent.putExtra("form-type", "edit-new");
                    startActivity(intent);
                }
                if(field_type.equals("bg")) {
                    dbHandler = new DbHandler(context);
                    bgPersonAddressModelList = new ArrayList<>();
                    bgPersonAddressModelList = dbHandler.getSingleBgTrap(marker.getTag().toString());
                    sharedpreferences = getSharedPreferences(BgDetails, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString(BgTrapId, bgPersonAddressModelList.get(0).bg_trap_id.toString());
                    editor.putString(TrapStatus, bgPersonAddressModelList.get(0).trap_status.toString());
                    editor.putString(TrapPosition, bgPersonAddressModelList.get(0).position.toString());
                    editor.putString(RespondName, bgPersonAddressModelList.get(0).person_name);
                    editor.putString(LocationCoordinates, bgPersonAddressModelList.get(0).coordinates);
                    editor.putString(Phone, String.valueOf(bgPersonAddressModelList.get(0).phone));
                    editor.putString(AddressLine1, bgPersonAddressModelList.get(0).address_line1);
                    editor.putString(AddressLine2, bgPersonAddressModelList.get(0).address_line2);
                    editor.putString(LocationDescription, bgPersonAddressModelList.get(0).location_description);
                    editor.putString(BgRunId, bgPersonAddressModelList.get(0).run_name);
                    editor.putInt(PersonId, bgPersonAddressModelList.get(0).person_id);
                    editor.putInt(AddressId, bgPersonAddressModelList.get(0).address_id);
                    editor.apply();

                    Intent intent = new Intent(context, AddBgMainActivity.class);
                    intent.putExtra("TrapId", marker.getTag().toString());
                    intent.putExtra("form-type", "edit-new");
                    startActivity(intent);
                }
                if(field_type.equals("mrc")) {
                    dbHandler = new DbHandler(context);
                    mrcPersonAddressModelList = new ArrayList<>();
                    mrcPersonAddressModelList = dbHandler.getSingleMrcPersonAddress(marker.getTag().toString());
                    sharedpreferences = getSharedPreferences(MrcDetails, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString(MrcId, mrcPersonAddressModelList.get(0).identifier.toString());
                    editor.putString(TrapStatus, mrcPersonAddressModelList.get(0).mrc_status.toString());
                    editor.putString(RespondName, mrcPersonAddressModelList.get(0).person_name);
                    editor.putString(LocationCoordinates, mrcPersonAddressModelList.get(0).coordinates);
                    editor.putString(Phone, String.valueOf(mrcPersonAddressModelList.get(0).phone));
                    editor.putString(AddressLine1, mrcPersonAddressModelList.get(0).address_line1);
                    editor.putString(AddressLine2, mrcPersonAddressModelList.get(0).address_line2);
                    editor.putString(LocationDescription, mrcPersonAddressModelList.get(0).location_description);
                    editor.putString(MrcRunId, mrcPersonAddressModelList.get(0).run_name);
                    editor.putInt(PersonId, mrcPersonAddressModelList.get(0).person_id);
                    editor.putInt(AddressId, mrcPersonAddressModelList.get(0).address_id);
                    editor.apply();

                    Intent intent = new Intent(context, AddMrcMainActivity.class);
                    intent.putExtra("TrapId", marker.getTag().toString());
                    intent.putExtra("form-type", "edit-new");
                    startActivity(intent);
                }
                return false;

            }
        });

    }
    public void goListView(View pView) {
        Intent intent = new Intent(context, OvListActivity.class);
        if (field_type.equals("ov")) {
            intent.putExtra("type", "ov");
        }
        if (field_type.equals("bg")) {
            intent.putExtra("type", "bg");
        }
        if (field_type.equals("mrc")) {
            intent.putExtra("type", "mrc");
        }
        startActivity(intent);
    }
}