package ucsc.mit.wmp;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.data.geojson.GeoJsonLayer;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapsActivity2 extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    final Context context = this;
    private DbHandler dbHandler;
    String run_name;
    String field_type;
    LatLng givenLocation;
    private List<OvTrapModel> ovModelList;
    private List<BgTrapModel> bgModelList;
    private List<MrcModel> mrcModelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_view);
        field_type = getIntent().getStringExtra("field_type");
        run_name = getIntent().getStringExtra("run_name");
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
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
        // Add a marker in Sydney and move the camera
        if (field_type.equals("ov")) {
            dbHandler = new DbHandler(context);
            ovModelList = new ArrayList<>();
            ovModelList = dbHandler.getSingleOvTrap(run_name, field_type);
            Log.d("size-ov", Integer.toString(ovModelList.size()));
            for (int i = 0; i < ovModelList.size(); ++i) {
                String coordinates = ovModelList.get(i).coordinates.toString();
                String[] separated_coordinates = coordinates.split(",");
                String latitude = separated_coordinates[0].trim();
                String longitude = separated_coordinates[1].trim();
                givenLocation = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
                mMap.addMarker(new MarkerOptions().position(givenLocation)).
                        setTag(ovModelList.get(i).ov_trap_id);
            }
            float zoomLevel = 10.0f;
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(givenLocation, zoomLevel));
            //mMap.moveCamera(center);
            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    // on marker click we are getting the title of our marker
                    // which is clicked and displaying it in a toast message.
                    Intent intent = new Intent(context, MainMenuActivity.class);
                    startActivity(intent);
                    return false;
                }
            });
        }
        if (field_type.equals("bg")) {
            dbHandler = new DbHandler(context);
            bgModelList = new ArrayList<>();
            bgModelList = dbHandler.getSingleBgTrap(run_name, field_type);
            for (int i = 0; i < bgModelList.size(); ++i) {
                String coordinates = bgModelList.get(i).coordinates.toString();
                String[] separated_coordinates = coordinates.split(",");
                String latitude = separated_coordinates[0].trim();
                String longitude = separated_coordinates[1].trim();
                givenLocation = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
                mMap.addMarker(new MarkerOptions().position(givenLocation).title("Wolbachia"));
            }
            float zoomLevel = 10.0f;
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(givenLocation, zoomLevel));
            //mMap.moveCamera(center);
        }
        if (field_type.equals("mrc")) {
            dbHandler = new DbHandler(context);
            mrcModelList = new ArrayList<>();
            mrcModelList = dbHandler.getSingleMrc(run_name, field_type);
            for (int i = 0; i < mrcModelList.size(); ++i) {
                String coordinates = mrcModelList.get(i).coordinates.toString();
                String[] separated_coordinates = coordinates.split(",");
                String latitude = separated_coordinates[0].trim();
                String longitude = separated_coordinates[1].trim();
                givenLocation = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
                mMap.addMarker(new MarkerOptions().position(givenLocation).title("Wolbachia"));
            }
            float zoomLevel = 10.0f;
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(givenLocation, zoomLevel));
            //mMap.moveCamera(center);
        }

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
