package ucsc.mit.wmp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.CancellationTokenSource;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.Locale;

public class IncidentLocationActivity extends AppCompatActivity {
    final Context context = this;
    private static final int PRIORITY_HIGH_ACCURACY = 100;
    FusedLocationProviderClient fusedLocationProviderClient;
    EditText txtEdit_Coordinates;
    EditText txtEdit_Address;
    EditText txtEdit_locationDescription;
    EditText txtEdit_Gnd;
    EditText txtEdit_trapCode;
    TextView errorText;

    public static final String Coordinates = "coordinates";
    public static final String Address = "address";
    public static final String LocationDescription = "location_description";
    public static final String Gnd = "gnd";
    public static final String Trapcode = "trapcode";
    public static final String IncidentDetails = "incidents";
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.incident_location);
        errorText = (TextView) findViewById(R.id.errorContainer);
        sharedpreferences = getSharedPreferences(IncidentDetails, Context.MODE_PRIVATE);
        txtEdit_Coordinates = (EditText) findViewById(R.id.editTextCoordinates);
        txtEdit_Address = (EditText) findViewById(R.id.editTextAddress);
        txtEdit_locationDescription = (EditText) findViewById(R.id.editTextLocationDescription);
        txtEdit_Gnd = (EditText) findViewById(R.id.editTextGn);
        txtEdit_trapCode = (EditText) findViewById(R.id.editTextTrapCode);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

    }

    public void viewCoordinates(View pView) {
        if (ActivityCompat.checkSelfPermission(IncidentLocationActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            getLocation();
        } else {
            ActivityCompat.requestPermissions(IncidentLocationActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }
    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        //fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
        CancellationTokenSource cts = new CancellationTokenSource();

        fusedLocationProviderClient.getCurrentLocation(PRIORITY_HIGH_ACCURACY, cts.getToken()).addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                // initialize location
                Location location = task.getResult();
                if (location != null) {
                    //initialize geocoder
                    Geocoder geocoder = new Geocoder(IncidentLocationActivity.this, Locale.getDefault());
                    Double lat = location.getLatitude();
                    Double lon = location.getLongitude();
                    Log.d("lat", String.valueOf(lat));
                    Log.d("lon", String.valueOf(lon));
                    String coords = String.valueOf(lat) + "," + String.valueOf(lon);
                    txtEdit_Coordinates.setText(coords);
                } else {
                    Log.d("lat", "null");
                    Log.d("lon", "null");
                }
            }
        });
    }

    public void goIncidentDescription(View pView) {
        Intent intent = new Intent(context, IncidentDescriptionActivity.class);
        startActivity(intent);
    }

    public void submitIncident(View pView) {
        if (txtEdit_Address.getText().toString().length() == 0) {
            errorText.setVisibility(View.VISIBLE);
            errorText.setText("Please fill all required fields.");
        } else {
            errorText.setVisibility(View.INVISIBLE);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString(Coordinates, txtEdit_Coordinates.getText().toString());
            editor.putString(Address, txtEdit_Address.getText().toString());
            editor.putString(LocationDescription, txtEdit_locationDescription.getText().toString());
            editor.putString(Gnd, txtEdit_Gnd.getText().toString());
            editor.putString(Trapcode, txtEdit_trapCode.getText().toString());
            editor.apply();

            String stakeholderName = sharedpreferences.getString("stakeholderName", "");
            String email = sharedpreferences.getString("email", "");
            String phone = sharedpreferences.getString("phone", "");

            String incident_type = sharedpreferences.getString("incident_type", "");
            String incident_priority = sharedpreferences.getString("incident_priority", "");
            String description = sharedpreferences.getString("description", "");
            String incident_date = sharedpreferences.getString("incident_date", "");
            String incident_time_hour = sharedpreferences.getString("incident_time_hour", "");
            String incident_time_minute = sharedpreferences.getString("incident_time_minute", "");

            String coordinates = sharedpreferences.getString("coordinates", "");
            String address = sharedpreferences.getString("address", "");
            String location_description = sharedpreferences.getString("location_description", "");
            String gnd = sharedpreferences.getString("gnd", "");
            String trapcode = sharedpreferences.getString("trapcode", "");

            Log.d("check-stakeholderName", stakeholderName);
            Log.d("check-email", email);
            Log.d("check-phone", phone);
            Log.d("check-incident_type", incident_type);
            Log.d("check-incident_priority", incident_priority);
            Log.d("check-description", description);
            Log.d("check-incident_date", incident_date);
            Log.d("check-incident_time_ho", incident_time_hour);
            Log.d("check-incident_time_min", incident_time_minute);
            Log.d("check-coordinates", coordinates);
            Log.d("check-address", address);
            Log.d("check-location_des", location_description);
            Log.d("check-gnd", gnd);
            Log.d("check-trapcode", trapcode);
        }
    }
}