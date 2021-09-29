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
    public static final String OviServiceDetails = "OviServiceDetails";
    public static final String BgServiceDetails = "BgServiceDetails";
    public static final String MrcServiceDetails = "MrcServiceDetails";
    public static final String OviCollectionDetails = "OviCollectionDetails";
    public static final String BgCollectionDetails = "BgCollectionDetails";
    public static final String MrcReleaseDetails = "MrcReleaseDetails";
    public static final String OriginalOviId = "OriginalOviId";
    public static final String OriginalBgId = "OriginalBgId";
    public static final String OriginalMrcId = "OriginalMrcId";
    public static final String BgDetails = "BgDetails";
    public static final String MrcDetails = "MrcDetails";
    public static final String MrcId = "MrcId";
    public static final String MrcTrapId = "MrcTrapId";
    public static final String MrcStatus = "MrcStatus";
    public static final String OviRunId = "OviRunId";
    public static final String BgRunId = "BgRunId";
    public static final String MrcRunId = "MrcRunId";
    public static final String PersonId = "PersonId";
    public static final String AddressId = "AddressId";
    public static final String ServiceId = "ServiceId";
    public static final String ServiceStatus = "ServiceStatus";
    public static final String CollectionId = "ServiceId";
    public static final String CollectionStatus = "ServiceStatus";
    public static final String ReleaseId = "ReleaseId";
    public static final String ReleaseStatus = "ReleaseStatus";


    LatLng givenLocation;
    private List<OvTrapModel> ovModelList;
    private List<OviServiceModel> ovServiceModelList;
    private List<OviCollectionModel> ovCollectionModelList;
    private List<OvTrapModel> ovPersonAddressModelList;
    private List<BgTrapModel> bgModelList;
    private List<BgServiceModel> bgServiceModelList;
    private List<BgCollectionModel> bgCollectionModelList;
    private List<BgTrapModel> bgPersonAddressModelList;
    private List<MrcModel> mrcModelList;
    private List<MrcServiceModel> mrcServiceModelList;
    private List<MrcReleaseModel> mrcReleaseModelList;
    private List<MrcModel> mrcPersonAddressModelList;
    private ArrayList<LatLng> latLngArrayList;
    private ArrayList<String> locationNameArraylist;
    SharedPreferences sharedpreferences;
    public static String RunCategory = "";

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
        if (field_type.equals("ov")) {
            ovModelList = new ArrayList<>();
            ovModelList = dbHandler.getSingleOvTrap(run_name, field_type);
            if (ovModelList.size() == 0) {
                ovServiceModelList = dbHandler.getSingleOviService(run_name, field_type);
                Log.d("count", String.valueOf(ovServiceModelList.size()));
                if (ovServiceModelList.size() == 0) {
                    ovCollectionModelList = dbHandler.getSingleOviCollection(run_name, field_type);
                    if (ovCollectionModelList.size() == 0) {

                    } else {
                        RunCategory = "ovi_collection";
                    }
                } else {
                    RunCategory = "ovi_service";
                }
            } else {
                RunCategory = "ovi_dry";
            }


        }
        if (field_type.equals("bg")) {
            bgModelList = new ArrayList<>();
            bgModelList = dbHandler.getSingleBgTrap(run_name, field_type);
            if (bgModelList.size() == 0) {
                bgServiceModelList = dbHandler.getSingleBgService(run_name, field_type);
                if (bgServiceModelList.size() == 0) {
                    bgCollectionModelList = dbHandler.getSingleBgCollection(run_name, field_type);
                    if (bgCollectionModelList.size() == 0) {

                    } else {
                        RunCategory = "bg_collection";
                    }
                } else {
                    RunCategory = "bg_service";
                }
            } else {
                RunCategory = "bg_dry";
            }
        }

        if (field_type.equals("mrc")) {
            mrcModelList = new ArrayList<>();
            mrcModelList = dbHandler.getSingleMrcPersonAddress(run_name, field_type);
            if (mrcModelList.size() == 0) {
                mrcServiceModelList = dbHandler.getSingleMrcService(run_name, field_type);
                if (mrcServiceModelList.size() == 0) {
                    mrcReleaseModelList = dbHandler.getSingleMrcRelease(run_name, field_type);
                    if (mrcReleaseModelList.size() == 0) {

                    } else {
                        RunCategory = "mrc_release";
                    }
                } else {
                    RunCategory = "mrc_service";
                }
            } else {
                RunCategory = "mrc_dry";
            }

        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setZoomGesturesEnabled(true);
        mMap.animateCamera( CameraUpdateFactory.zoomTo( 17.0f ) );

        if (field_type.equals("ov")) {
            if (ovModelList.size() > 0) {
                for (int i = 0; i < ovModelList.size(); i++) {
                    String coordinates = ovModelList.get(i).coordinates.toString();
                    String[] separated_coordinates = coordinates.split(",");
                    String latitude = separated_coordinates[0].trim();
                    String longitude = separated_coordinates[1].trim();
                    givenLocation = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
                    // adding marker to each location on google maps
                    Log.d("status", ovModelList.get(i).trap_status.toString());
                    if (ovModelList.get(i).trap_status.toString().equals("1")) {
                        mMap.addMarker(new MarkerOptions().position(givenLocation).zIndex(2.0f).icon(BitmapDescriptorFactory.defaultMarker
                                (BitmapDescriptorFactory.HUE_YELLOW))).setTag(ovModelList.get(i).ov_trap_id.toString());
                    }
                    if (ovModelList.get(i).trap_status.toString().equals("2")) {
                        mMap.addMarker(new MarkerOptions().position(givenLocation).zIndex(2.0f).icon(BitmapDescriptorFactory.defaultMarker
                                (BitmapDescriptorFactory.HUE_GREEN))).setTag(ovModelList.get(i).ov_trap_id.toString());
                    }
                    // below line is use to move camera.
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(givenLocation,14.0f));
                }

            } else {
                Log.d("test", "ok");
                if (ovServiceModelList.size() > 0) {
                    Log.d("size", String.valueOf(ovServiceModelList.size()));
                    for (int i = 0; i < ovServiceModelList.size(); i++) {
                        String coordinates = ovServiceModelList.get(i).coordinates.toString();
                        String[] separated_coordinates = coordinates.split(",");
                        String latitude = separated_coordinates[0].trim();
                        String longitude = separated_coordinates[1].trim();
                        givenLocation = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
                        // adding marker to each location on google maps
                        if (ovServiceModelList.get(i).service_status == null) {
                            mMap.addMarker(new MarkerOptions().position(givenLocation).zIndex(2.0f).icon(BitmapDescriptorFactory.defaultMarker
                                    (BitmapDescriptorFactory.HUE_BLUE))).setTag(ovServiceModelList.get(i).trap_id.toString());
                        } else if (ovServiceModelList.get(i).service_status.toString().equals("1")) {
                            mMap.addMarker(new MarkerOptions().position(givenLocation).zIndex(2.0f).icon(BitmapDescriptorFactory.defaultMarker
                                    (BitmapDescriptorFactory.HUE_GREEN))).setTag(ovServiceModelList.get(i).trap_id.toString());
                        } else if (ovServiceModelList.get(i).service_status.toString().equals("2")) {
                            mMap.addMarker(new MarkerOptions().position(givenLocation).zIndex(2.0f).icon(BitmapDescriptorFactory.defaultMarker
                                    (BitmapDescriptorFactory.HUE_YELLOW))).setTag(ovServiceModelList.get(i).trap_id.toString());
                        }

                        // below line is use to move camera.
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(givenLocation,14.0f));
                    }

                } else {
                    if (ovCollectionModelList.size() > 0) {
                        for (int i = 0; i < ovCollectionModelList.size(); i++) {
                            String coordinates = ovCollectionModelList.get(i).coordinates.toString();
                            String[] separated_coordinates = coordinates.split(",");
                            String latitude = separated_coordinates[0].trim();
                            String longitude = separated_coordinates[1].trim();
                            givenLocation = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
                            // adding marker to each location on google maps
                            Log.d("col_status", ovCollectionModelList.get(i).collection_status);
                            if (ovCollectionModelList.get(i).collection_status == null) {
                                mMap.addMarker(new MarkerOptions().position(givenLocation).zIndex(2.0f).icon(BitmapDescriptorFactory.defaultMarker
                                        (BitmapDescriptorFactory.HUE_BLUE))).setTag(ovCollectionModelList.get(i).trap_id.toString());
                            } else if (ovCollectionModelList.get(i).collection_status.toString().equals("1")) {
                                mMap.addMarker(new MarkerOptions().position(givenLocation).zIndex(2.0f).icon(BitmapDescriptorFactory.defaultMarker
                                        (BitmapDescriptorFactory.HUE_GREEN))).setTag(ovCollectionModelList.get(i).trap_id.toString());
                            } else if (ovCollectionModelList.get(i).collection_status.toString().equals("2")) {
                                mMap.addMarker(new MarkerOptions().position(givenLocation).zIndex(2.0f).icon(BitmapDescriptorFactory.defaultMarker
                                        (BitmapDescriptorFactory.HUE_YELLOW))).setTag(ovCollectionModelList.get(i).trap_id.toString());
                            }

                            // below line is use to move camera.
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(givenLocation,14.0f));
                        }

                    }
                }
            }
        }
        if (field_type.equals("bg")) {
            if (bgModelList.size() > 0) {
                for (int i = 0; i < bgModelList.size(); i++) {
                    String coordinates = bgModelList.get(i).coordinates.toString();
                    String[] separated_coordinates = coordinates.split(",");
                    String latitude = separated_coordinates[0].trim();
                    String longitude = separated_coordinates[1].trim();
                    givenLocation = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
                    // adding marker to each location on google maps
                    if (bgModelList.get(i).trap_status.toString().equals("1")) {
                        mMap.addMarker(new MarkerOptions().position(givenLocation).zIndex(2.0f).icon(BitmapDescriptorFactory.defaultMarker
                                (BitmapDescriptorFactory.HUE_YELLOW))).setTag(bgModelList.get(i).bg_trap_id.toString());
                    }
                    if (bgModelList.get(i).trap_status.toString().equals("2")) {
                        mMap.addMarker(new MarkerOptions().position(givenLocation).zIndex(2.0f).icon(BitmapDescriptorFactory.defaultMarker
                                (BitmapDescriptorFactory.HUE_GREEN))).setTag(bgModelList.get(i).bg_trap_id.toString());
                    }
                    // below line is use to move camera.
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(givenLocation,14.0f));
                }

            } else {
                if (bgServiceModelList.size() > 0) {
                    Log.d("size", String.valueOf(bgServiceModelList.size()));
                    for (int i = 0; i < bgServiceModelList.size(); i++) {
                        String coordinates = bgServiceModelList.get(i).coordinates.toString();
                        String[] separated_coordinates = coordinates.split(",");
                        String latitude = separated_coordinates[0].trim();
                        String longitude = separated_coordinates[1].trim();
                        givenLocation = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
                        // adding marker to each location on google maps
                        if (bgServiceModelList.get(i).service_status == null) {
                            mMap.addMarker(new MarkerOptions().position(givenLocation).zIndex(2.0f).icon(BitmapDescriptorFactory.defaultMarker
                                    (BitmapDescriptorFactory.HUE_BLUE))).setTag(bgServiceModelList.get(i).trap_id.toString());
                        } else if (bgServiceModelList.get(i).service_status.toString().equals("1")) {
                            mMap.addMarker(new MarkerOptions().position(givenLocation).zIndex(2.0f).icon(BitmapDescriptorFactory.defaultMarker
                                    (BitmapDescriptorFactory.HUE_GREEN))).setTag(bgServiceModelList.get(i).trap_id.toString());
                        } else if (bgServiceModelList.get(i).service_status.toString().equals("2")) {
                            mMap.addMarker(new MarkerOptions().position(givenLocation).zIndex(2.0f).icon(BitmapDescriptorFactory.defaultMarker
                                    (BitmapDescriptorFactory.HUE_YELLOW))).setTag(bgServiceModelList.get(i).trap_id.toString());
                        }

                        // below line is use to move camera.
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(givenLocation,14.0f));
                    }

                } else {
                    if (bgCollectionModelList.size() > 0) {
                        for (int i = 0; i < bgCollectionModelList.size(); i++) {
                            String coordinates = bgCollectionModelList.get(i).coordinates.toString();
                            String[] separated_coordinates = coordinates.split(",");
                            String latitude = separated_coordinates[0].trim();
                            String longitude = separated_coordinates[1].trim();
                            givenLocation = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
                            // adding marker to each location on google maps
                            if (bgCollectionModelList.get(i).collection_status == null) {
                                mMap.addMarker(new MarkerOptions().position(givenLocation).zIndex(2.0f).icon(BitmapDescriptorFactory.defaultMarker
                                        (BitmapDescriptorFactory.HUE_BLUE))).setTag(bgCollectionModelList.get(i).trap_id.toString());
                            } else if (bgCollectionModelList.get(i).collection_status.toString().equals("1")) {
                                mMap.addMarker(new MarkerOptions().position(givenLocation).zIndex(2.0f).icon(BitmapDescriptorFactory.defaultMarker
                                        (BitmapDescriptorFactory.HUE_GREEN))).setTag(bgCollectionModelList.get(i).trap_id.toString());
                            } else if (bgCollectionModelList.get(i).collection_status.toString().equals("2")) {
                                mMap.addMarker(new MarkerOptions().position(givenLocation).zIndex(2.0f).icon(BitmapDescriptorFactory.defaultMarker
                                        (BitmapDescriptorFactory.HUE_YELLOW))).setTag(bgCollectionModelList.get(i).trap_id.toString());
                            }

                            // below line is use to move camera.
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(givenLocation,14.0f));
                        }

                    }
                }
            }
        }

        if (field_type.equals("mrc")) {
            if (mrcModelList.size() > 0) {
                for (int i = 0; i < mrcModelList.size(); i++) {
                    String coordinates = mrcModelList.get(i).coordinates.toString();
                    String[] separated_coordinates = coordinates.split(",");
                    String latitude = separated_coordinates[0].trim();
                    String longitude = separated_coordinates[1].trim();
                    givenLocation = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
                    // adding marker to each location on google maps
                    if (mrcModelList.get(i).mrc_status.toString().equals("1")) {
                        mMap.addMarker(new MarkerOptions().position(givenLocation).zIndex(2.0f).icon(BitmapDescriptorFactory.defaultMarker
                                (BitmapDescriptorFactory.HUE_YELLOW))).setTag(mrcModelList.get(i).identifier.toString());
                    }
                    if (mrcModelList.get(i).mrc_status.toString().equals("2")) {
                        mMap.addMarker(new MarkerOptions().position(givenLocation).zIndex(2.0f).icon(BitmapDescriptorFactory.defaultMarker
                                (BitmapDescriptorFactory.HUE_GREEN))).setTag(mrcModelList.get(i).identifier.toString());
                    }
                    // below line is use to move camera.
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(givenLocation,14.0f));
                }

            } else {
                if (mrcServiceModelList.size() > 0) {
                    for (int i = 0; i < mrcServiceModelList.size(); i++) {
                        String coordinates = mrcServiceModelList.get(i).coordinates.toString();
                        String[] separated_coordinates = coordinates.split(",");
                        String latitude = separated_coordinates[0].trim();
                        String longitude = separated_coordinates[1].trim();
                        givenLocation = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
                        // adding marker to each location on google maps
                        if (mrcServiceModelList.get(i).service_status == null) {
                            mMap.addMarker(new MarkerOptions().position(givenLocation).zIndex(2.0f).icon(BitmapDescriptorFactory.defaultMarker
                                    (BitmapDescriptorFactory.HUE_BLUE))).setTag(mrcServiceModelList.get(i).trap_id.toString());
                        } else if (mrcServiceModelList.get(i).service_status.toString().equals("1")) {
                            mMap.addMarker(new MarkerOptions().position(givenLocation).zIndex(2.0f).icon(BitmapDescriptorFactory.defaultMarker
                                    (BitmapDescriptorFactory.HUE_GREEN))).setTag(mrcServiceModelList.get(i).trap_id.toString());
                        } else if (mrcServiceModelList.get(i).service_status.toString().equals("2")) {
                            mMap.addMarker(new MarkerOptions().position(givenLocation).zIndex(2.0f).icon(BitmapDescriptorFactory.defaultMarker
                                    (BitmapDescriptorFactory.HUE_YELLOW))).setTag(mrcServiceModelList.get(i).trap_id.toString());
                        }

                        // below line is use to move camera.
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(givenLocation,14.0f));
                    }

                } else {
                    if (mrcReleaseModelList.size() > 0) {
                        for (int i = 0; i < mrcReleaseModelList.size(); i++) {
                            String coordinates = mrcReleaseModelList.get(i).coordinates.toString();
                            String[] separated_coordinates = coordinates.split(",");
                            String latitude = separated_coordinates[0].trim();
                            String longitude = separated_coordinates[1].trim();
                            givenLocation = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
                            // adding marker to each location on google maps
                            if (mrcReleaseModelList.get(i).release_status == null) {
                                mMap.addMarker(new MarkerOptions().position(givenLocation).zIndex(2.0f).icon(BitmapDescriptorFactory.defaultMarker
                                        (BitmapDescriptorFactory.HUE_BLUE))).setTag(mrcReleaseModelList.get(i).trap_id.toString());
                            } else if (mrcReleaseModelList.get(i).release_status.toString().equals("1")) {
                                mMap.addMarker(new MarkerOptions().position(givenLocation).zIndex(2.0f).icon(BitmapDescriptorFactory.defaultMarker
                                        (BitmapDescriptorFactory.HUE_GREEN))).setTag(mrcReleaseModelList.get(i).trap_id.toString());
                            } else if (mrcReleaseModelList.get(i).release_status.toString().equals("2")) {
                                mMap.addMarker(new MarkerOptions().position(givenLocation).zIndex(2.0f).icon(BitmapDescriptorFactory.defaultMarker
                                        (BitmapDescriptorFactory.HUE_YELLOW))).setTag(mrcReleaseModelList.get(i).trap_id.toString());
                            }

                            // below line is use to move camera.
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(givenLocation,14.0f));
                        }

                    }
                }
            }
        }


        // adding on click listener to marker of google maps.
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Log.d("run_category",RunCategory);
                if(RunCategory.equals("ovi_dry")){
                    dbHandler = new DbHandler(context);
                    ovPersonAddressModelList = new ArrayList<>();
                    ovPersonAddressModelList = dbHandler.getSingleOvTrap(marker.getTag().toString());
                    sharedpreferences = getSharedPreferences(OviDetails, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString(OviTrapId, ovPersonAddressModelList.get(0).ov_trap_id.toString());
                    editor.putString(OriginalOviId, ovPersonAddressModelList.get(0).ov_trap_id.toString());
                    editor.putString(TrapStatus, ovPersonAddressModelList.get(0).trap_status.toString());
                    editor.putString(TrapPosition, ovPersonAddressModelList.get(0).position.toString());
                    editor.putString(RespondName, ovPersonAddressModelList.get(0).person_name);
                    editor.putString(LocationCoordinates, ovPersonAddressModelList.get(0).coordinates);
                    editor.putString(Phone, String.valueOf(ovPersonAddressModelList.get(0).person_phone));
                    editor.putString(AddressLine1, ovPersonAddressModelList.get(0).address_line1);
                    editor.putString(AddressLine2, ovPersonAddressModelList.get(0).address_line2);
                    editor.putString(LocationDescription, ovPersonAddressModelList.get(0).location_description);
                    editor.putString(OviRunId, ovPersonAddressModelList.get(0).run_name);
                    editor.apply();

                    Intent intent = new Intent(context, AddOvMainActivity.class);
                    intent.putExtra("TrapId", marker.getTag().toString());
                    intent.putExtra("form-type", "edit-new");
                    startActivity(intent);
                }

                if(RunCategory.equals("ovi_service")){
                    dbHandler = new DbHandler(context);
                    ovServiceModelList = new ArrayList<>();
                    ovServiceModelList = dbHandler.getSingleOviServiceTrap(run_name,marker.getTag().toString());
                    sharedpreferences = getSharedPreferences(OviServiceDetails, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString(OviRunId, ovServiceModelList.get(0).ovi_run_id.toString());
                    editor.putString(OviTrapId, ovServiceModelList.get(0).trap_id.toString());
                    editor.putString(TrapPosition, ovServiceModelList.get(0).trap_position.toString());
                    editor.putString(LocationCoordinates, ovServiceModelList.get(0).coordinates);
                    editor.putString(AddressLine1, ovServiceModelList.get(0).add_line1);
                    editor.putString(AddressLine2, ovServiceModelList.get(0).add_line2);
                    editor.putString(LocationDescription, ovServiceModelList.get(0).location_description);
                    editor.putString(RespondName, ovServiceModelList.get(0).full_name);
                    editor.putString(Phone, ovServiceModelList.get(0).contact_number);
                    editor.putString(ServiceId, ovServiceModelList.get(0).service_id);
                    editor.putString(ServiceStatus, ovServiceModelList.get(0).service_status);
                    editor.apply();
                    Intent intent = new Intent(context, AddOviServiceMainActivity.class);
                    startActivity(intent);
                }
                if(RunCategory.equals("ovi_collection")){
                    dbHandler = new DbHandler(context);
                    ovCollectionModelList = new ArrayList<>();
                    ovCollectionModelList = dbHandler.getSingleOviCollectionTrap(run_name,marker.getTag().toString());
                    sharedpreferences = getSharedPreferences(OviCollectionDetails, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString(OviRunId, ovCollectionModelList.get(0).ovi_run_id.toString());
                    editor.putString(OviTrapId, ovCollectionModelList.get(0).trap_id.toString());
                    editor.putString(TrapPosition, ovCollectionModelList.get(0).trap_position.toString());
                    editor.putString(LocationCoordinates, ovCollectionModelList.get(0).coordinates);
                    editor.putString(AddressLine1, ovCollectionModelList.get(0).add_line1);
                    editor.putString(AddressLine2, ovCollectionModelList.get(0).add_line2);
                    editor.putString(LocationDescription, ovCollectionModelList.get(0).location_description);
                    editor.putString(RespondName, ovCollectionModelList.get(0).full_name);
                    editor.putString(Phone, ovCollectionModelList.get(0).contact_number);
                    editor.putString(CollectionId, ovCollectionModelList.get(0).collection_id);
                    editor.putString(CollectionStatus, ovCollectionModelList.get(0).collection_status);
                    editor.apply();
                    Intent intent = new Intent(context, AddOviCollectionMainActivity.class);
                    startActivity(intent);
                }
                if(RunCategory.equals("bg_dry")){
                    dbHandler = new DbHandler(context);
                    bgPersonAddressModelList = new ArrayList<>();
                    bgPersonAddressModelList = dbHandler.getSingleBgTrap(marker.getTag().toString());
                    sharedpreferences = getSharedPreferences(BgDetails, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString(BgTrapId, bgPersonAddressModelList.get(0).bg_trap_id.toString());
                    editor.putString(OriginalBgId, bgPersonAddressModelList.get(0).bg_trap_id.toString());
                    editor.putString(TrapStatus, bgPersonAddressModelList.get(0).trap_status.toString());
                    editor.putString(TrapPosition, bgPersonAddressModelList.get(0).position.toString());
                    editor.putString(RespondName, bgPersonAddressModelList.get(0).person_name);
                    editor.putString(LocationCoordinates, bgPersonAddressModelList.get(0).coordinates);
                    editor.putString(Phone, String.valueOf(bgPersonAddressModelList.get(0).person_phone));
                    editor.putString(AddressLine1, bgPersonAddressModelList.get(0).address_line1);
                    editor.putString(AddressLine2, bgPersonAddressModelList.get(0).address_line2);
                    editor.putString(LocationDescription, bgPersonAddressModelList.get(0).location_description);
                    editor.putString(BgRunId, bgPersonAddressModelList.get(0).run_name);
                    editor.apply();

                    Intent intent = new Intent(context, AddBgMainActivity.class);
                    intent.putExtra("TrapId", marker.getTag().toString());
                    intent.putExtra("form-type", "edit-new");
                    startActivity(intent);
                }

                if(RunCategory.equals("bg_service")){
                    dbHandler = new DbHandler(context);
                    bgServiceModelList = new ArrayList<>();
                    bgServiceModelList = dbHandler.getSingleBgServiceTrap(run_name,marker.getTag().toString());
                    sharedpreferences = getSharedPreferences(BgServiceDetails, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString(BgRunId, bgServiceModelList.get(0).bg_run_id.toString());
                    editor.putString(BgTrapId, bgServiceModelList.get(0).trap_id.toString());
                    editor.putString(TrapPosition, bgServiceModelList.get(0).trap_position.toString());
                    editor.putString(LocationCoordinates, bgServiceModelList.get(0).coordinates);
                    editor.putString(AddressLine1, bgServiceModelList.get(0).add_line1);
                    editor.putString(AddressLine2, bgServiceModelList.get(0).add_line2);
                    editor.putString(LocationDescription, bgServiceModelList.get(0).location_description);
                    editor.putString(RespondName, bgServiceModelList.get(0).full_name);
                    editor.putString(Phone, bgServiceModelList.get(0).contact_number);
                    editor.putString(ServiceId, bgServiceModelList.get(0).service_id);
                    editor.putString(ServiceStatus, bgServiceModelList.get(0).service_status);
                    editor.apply();

                    Intent intent = new Intent(context, AddBgServiceMainActivity.class);
                    startActivity(intent);
                }
                if(RunCategory.equals("bg_collection")){
                    dbHandler = new DbHandler(context);
                    bgCollectionModelList = new ArrayList<>();
                    bgCollectionModelList = dbHandler.getSingleBgCollectionTrap(run_name,marker.getTag().toString());
                    sharedpreferences = getSharedPreferences(BgCollectionDetails, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString(BgRunId, bgCollectionModelList.get(0).bg_run_id.toString());
                    editor.putString(BgTrapId, bgCollectionModelList.get(0).trap_id.toString());
                    editor.putString(TrapPosition, bgCollectionModelList.get(0).trap_position.toString());
                    editor.putString(LocationCoordinates, bgCollectionModelList.get(0).coordinates);
                    editor.putString(AddressLine1, bgCollectionModelList.get(0).add_line1);
                    editor.putString(AddressLine2, bgCollectionModelList.get(0).add_line2);
                    editor.putString(LocationDescription, bgCollectionModelList.get(0).location_description);
                    editor.putString(RespondName, bgCollectionModelList.get(0).full_name);
                    editor.putString(Phone, bgCollectionModelList.get(0).contact_number);
                    editor.putString(CollectionId, bgCollectionModelList.get(0).collection_id);
                    editor.putString(CollectionStatus, bgCollectionModelList.get(0).collection_status);
                    editor.apply();

                    Intent intent = new Intent(context, AddBgCollectionMainActivity.class);
                    startActivity(intent);
                }
                if(RunCategory.equals("mrc_service")){
                    dbHandler = new DbHandler(context);
                    mrcServiceModelList = new ArrayList<>();
                    mrcServiceModelList = dbHandler.getSingleMrcServiceTrap(run_name,marker.getTag().toString());
                    sharedpreferences = getSharedPreferences(MrcServiceDetails, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString(MrcRunId, mrcServiceModelList.get(0).mrc_run_id.toString());
                    editor.putString(MrcTrapId, mrcServiceModelList.get(0).trap_id.toString());
                    editor.putString(LocationCoordinates, mrcServiceModelList.get(0).coordinates);
                    editor.putString(AddressLine1, mrcServiceModelList.get(0).add_line1);
                    editor.putString(AddressLine2, mrcServiceModelList.get(0).add_line2);
                    editor.putString(LocationDescription, mrcServiceModelList.get(0).location_description);
                    editor.putString(RespondName, mrcServiceModelList.get(0).full_name);
                    editor.putString(Phone, mrcServiceModelList.get(0).contact_number);
                    editor.putString(ServiceId, mrcServiceModelList.get(0).service_id);
                    editor.putString(ServiceStatus, mrcServiceModelList.get(0).service_status);
                    editor.apply();
                    Intent intent = new Intent(context, AddMrcServiceMainActivity.class);
                    startActivity(intent);
                }
                if(RunCategory.equals("mrc_release")){
                    dbHandler = new DbHandler(context);
                    mrcReleaseModelList = new ArrayList<>();
                    mrcReleaseModelList = dbHandler.getSingleMrcReleaseTrap(run_name,marker.getTag().toString());
                    sharedpreferences = getSharedPreferences(MrcReleaseDetails, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString(MrcRunId, mrcReleaseModelList.get(0).mrc_run_id.toString());
                    editor.putString(MrcTrapId, mrcReleaseModelList.get(0).trap_id.toString());
                    editor.putString(LocationCoordinates, mrcReleaseModelList.get(0).coordinates);
                    editor.putString(AddressLine1, mrcReleaseModelList.get(0).add_line1);
                    editor.putString(AddressLine2, mrcReleaseModelList.get(0).add_line2);
                    editor.putString(LocationDescription, mrcReleaseModelList.get(0).location_description);
                    editor.putString(RespondName, mrcReleaseModelList.get(0).full_name);
                    editor.putString(Phone, mrcReleaseModelList.get(0).contact_number);
                    editor.putString(ReleaseId, mrcReleaseModelList.get(0).release_id);
                    editor.putString(ReleaseStatus, mrcReleaseModelList.get(0).release_status);
                    editor.apply();
                    Intent intent = new Intent(context, AddMrcReleaseMainActivity.class);
                    startActivity(intent);
                }
                if(RunCategory.equals("mrc_dry")){
                    dbHandler = new DbHandler(context);
                    mrcPersonAddressModelList = new ArrayList<>();
                    mrcPersonAddressModelList = dbHandler.getSingleMrc(marker.getTag().toString());
                    sharedpreferences = getSharedPreferences(MrcDetails, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString(MrcId, mrcPersonAddressModelList.get(0).identifier.toString());
                    editor.putString(OriginalMrcId, mrcPersonAddressModelList.get(0).identifier.toString());
                    editor.putString(MrcStatus, mrcPersonAddressModelList.get(0).mrc_status.toString());
                    editor.putString(RespondName, mrcPersonAddressModelList.get(0).person_name);
                    editor.putString(LocationCoordinates, mrcPersonAddressModelList.get(0).coordinates);
                    editor.putString(Phone, String.valueOf(mrcPersonAddressModelList.get(0).person_phone));
                    editor.putString(AddressLine1, mrcPersonAddressModelList.get(0).address_line1);
                    editor.putString(AddressLine2, mrcPersonAddressModelList.get(0).address_line2);
                    editor.putString(LocationDescription, mrcPersonAddressModelList.get(0).location_description);
                    editor.putString(MrcRunId, mrcPersonAddressModelList.get(0).run_name);
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