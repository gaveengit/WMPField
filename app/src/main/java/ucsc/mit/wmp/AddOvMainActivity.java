package ucsc.mit.wmp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

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


public class AddOvMainActivity extends AppCompatActivity {
    final Context context = this;
    private static final int PRIORITY_HIGH_ACCURACY = 100;
    FusedLocationProviderClient fusedLocationProviderClient;
    public static final String OviTrapId = "OviTrapId";
    public static final String TrapStatus = "TrapStatus";
    public static final String TrapPosition = "TrapPosition";
    public static final String RespondName = "RespondName";
    public static final String LocationCoordinates = "LocationCoordinates";
    public static final String OviDetails = "OviDetails";
    public static final String OviRunId = "OviRunId";
    EditText EditTextTrapId;
    RadioGroup RadioGroupTrapStatus;
    RadioButton RadioProposed;
    RadioButton RadioSet;
    EditText EditTextTrapPosition;
    EditText EditTextRespondName;
    EditText EditTextLocationCoordinates;
    TextView errorText;
    SharedPreferences sharedpreferences;
    String form_type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_ov_main);
        form_type = getIntent().getStringExtra("form-type");
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        EditTextTrapId = (EditText) findViewById(R.id.editTextTrapId);
        //trapId.setText(ov_id);
        RadioGroupTrapStatus = (RadioGroup) findViewById(R.id.trapStatus);
        RadioProposed = (RadioButton) findViewById(R.id.radioProposed);
        RadioSet = (RadioButton) findViewById(R.id.radioSet);
        EditTextTrapPosition = (EditText) findViewById(R.id.editTextTrapPosition);
        EditTextRespondName = (EditText) findViewById(R.id.editTextRespondentName);
        EditTextLocationCoordinates = (EditText) findViewById(R.id.editTextLocation);
        errorText = (TextView) findViewById(R.id.errorContainer);
        RadioProposed.setChecked(true);
        sharedpreferences = getSharedPreferences(OviDetails, Context.MODE_PRIVATE);
        String ovi_trap_id = sharedpreferences.getString(OviTrapId, "");
        String trap_status = sharedpreferences.getString(TrapStatus, "");
        String trap_position = sharedpreferences.getString(TrapPosition, "");
        String respond_name = sharedpreferences.getString(RespondName, "");
        String location_coordinates = sharedpreferences.getString(LocationCoordinates, "");
        String ovi_run_id = sharedpreferences.getString(OviRunId, "");
        Log.d("ovi_run",ovi_run_id);
        if(respond_name.length()!=0) {
            EditTextTrapId.setText(ovi_trap_id);
            if (trap_status.equals("proposed")) {
                RadioProposed.setChecked(true);
            }
            if (trap_status.equals("set")) {
                RadioSet.setChecked(true);
            }
            EditTextTrapPosition.setText(trap_position);
            EditTextRespondName.setText(respond_name);
            EditTextLocationCoordinates.setText(location_coordinates);
        }
    }

    public void viewCoordinates(View pView) {
        if (ActivityCompat.checkSelfPermission(AddOvMainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            getLocation();
        } else {
            ActivityCompat.requestPermissions(AddOvMainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
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
                    Geocoder geocoder = new Geocoder(AddOvMainActivity.this, Locale.getDefault());
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

    public void goListView(View pView) {
        Intent intent = new Intent(context, OvListActivity.class);
        intent.putExtra("type", "ov");
        startActivity(intent);
    }

    public void goAdditionalOv(View pView) {
        if (EditTextTrapId.getText().toString().length() == 0 || EditTextTrapPosition.getText().toString().length()
                ==0 || EditTextRespondName.getText().toString().length() == 0 || EditTextLocationCoordinates.
                getText().toString().length()==0) {
            errorText.setVisibility(View.VISIBLE);
            errorText.setText("Please fill all required fields.");
        }
        else {
            errorText.setVisibility(View.GONE);
            sharedpreferences = getSharedPreferences(OviDetails, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString(OviTrapId,EditTextTrapId.getText().toString());

            if(RadioProposed.isChecked())
            {
                editor.putString(TrapStatus,"proposed");
            }
            if(RadioSet.isChecked())
            {
                editor.putString(TrapStatus,"set");
            }

            editor.putString(TrapPosition, EditTextTrapPosition.getText().toString());
            editor.putString(RespondName, EditTextRespondName.getText().toString());
            editor.putString(LocationCoordinates, EditTextLocationCoordinates.getText().toString());
            editor.apply();
            Intent intent = new Intent(context, AddOvAdditionalActivity.class);
            Log.d("form-type",form_type);
            if(form_type.equals("edit-new")) {
                intent.putExtra("form-type", "edit-new");
            }
            else{
                intent.putExtra("form-type", "new");
            }
            startActivity(intent);
        }
    }
}

