package ucsc.mit.wmp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.location.FusedLocationProviderClient;

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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.CancellationTokenSource;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.Locale;

public class AddMrcMainActivity extends AppCompatActivity {
    final Context context = this;
    private static final int PRIORITY_HIGH_ACCURACY = 100;
    FusedLocationProviderClient fusedLocationProviderClient;
    public static final String MrcId = "MrcId";
    public static final String MrcStatus = "MrcStatus";
    public static final String MrcPosition = "MrcPosition";
    public static final String RespondName = "RespondName";
    public static final String LocationCoordinates = "LocationCoordinates";
    public static final String MrcDetails = "MrcDetails";

    EditText EditTextMrcId;
    RadioGroup RadioGroupMrcStatus;
    RadioButton RadioProposed;
    RadioButton RadioSet;
    EditText EditTextMrcPosition;
    EditText EditTextRespondName;
    EditText EditTextLocationCoordinates;
    TextView errorText;
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_mrc_main);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        EditTextMrcId = (EditText) findViewById(R.id.editTextTrapId);
        RadioGroupMrcStatus = (RadioGroup) findViewById(R.id.trapStatus);
        RadioProposed = (RadioButton) findViewById(R.id.radioProposed);
        RadioSet = (RadioButton) findViewById(R.id.radioSet);
        EditTextRespondName = (EditText) findViewById(R.id.editTextRespondentName);
        EditTextLocationCoordinates = (EditText) findViewById(R.id.editTextLocation);
        errorText = (TextView) findViewById(R.id.errorContainer);
        RadioProposed.setChecked(true);
        sharedpreferences = getSharedPreferences(MrcDetails, Context.MODE_PRIVATE);
        String ovi_trap_id = sharedpreferences.getString(MrcId, "");
        String trap_status = sharedpreferences.getString(MrcStatus, "");
        String respond_name = sharedpreferences.getString(RespondName, "");
        String location_coordinates = sharedpreferences.getString(LocationCoordinates, "");
        if (respond_name.length() != 0) {
            EditTextMrcId.setText(ovi_trap_id);
            if (trap_status == "proposed") {
                RadioProposed.setChecked(true);
            }
            if (trap_status == "set") {
                RadioSet.setChecked(true);
            }
            EditTextRespondName.setText(respond_name);
            EditTextLocationCoordinates.setText(location_coordinates);
        }
    }

    public void viewCoordinates(View pView) {
        if (ActivityCompat.checkSelfPermission(AddMrcMainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            getLocation();
        } else {
            ActivityCompat.requestPermissions(AddMrcMainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
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
                    Geocoder geocoder = new Geocoder(AddMrcMainActivity.this, Locale.getDefault());
                    Double lat = location.getLatitude();
                    Double lon = location.getLongitude();
                    Log.d("lat", String.valueOf(lat));
                    Log.d("lon", String.valueOf(lon));
                    String coords = String.valueOf(lat) + "," + String.valueOf(lon);
                    EditTextLocationCoordinates.setText(coords);
                } else {
                    Log.d("lat", "null");
                    Log.d("lon", "null");
                }
            }
        });
    }

    public void goAdditionalMrc(View pView) {
        if (EditTextMrcId.getText().toString().length() == 0 || EditTextRespondName.getText().
                toString().length() == 0 || EditTextLocationCoordinates.
                getText().toString().length() == 0) {
            errorText.setVisibility(View.VISIBLE);
            errorText.setText("Please fill all required fields.");
        } else {
            errorText.setVisibility(View.GONE);
            sharedpreferences = getSharedPreferences(MrcDetails, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString(MrcId, EditTextMrcId.getText().toString());

            if (RadioProposed.isChecked()) {
                editor.putString(MrcStatus, "proposed");
            }
            if (RadioSet.isChecked()) {
                editor.putString(MrcStatus, "set");
            }
            editor.putString(RespondName, EditTextRespondName.getText().toString());
            editor.putString(LocationCoordinates, EditTextLocationCoordinates.getText().toString());
            editor.apply();
            Intent intent = new Intent(context, AddMrcAdditionalActivity.class);
            startActivity(intent);
        }
    }

    public void goListView(View pView) {
        Intent intent = new Intent(context, OvListActivity.class);
        intent.putExtra("type", "mrc");
        startActivity(intent);
    }
}
