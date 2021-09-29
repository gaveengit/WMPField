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
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.CancellationTokenSource;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AddBgMainActivity extends AppCompatActivity {
    final Context context = this;
    private DbHandler dbHandler;
    private static final int PRIORITY_HIGH_ACCURACY = 100;
    FusedLocationProviderClient fusedLocationProviderClient;
    public static final String BgTrapId = "BgTrapId";
    public static final String TrapStatus = "TrapStatus";
    public static final String TrapPosition = "TrapPosition";
    public static final String RespondName = "RespondName";
    public static final String LocationCoordinates = "LocationCoordinates";
    public static final String BgDetails = "BgDetails";
    public static final String BgRunId = "BgRunId";
    public static final String OriginalBgId = "OriginalBgId";
    private List<BgTrapModel> bgPersonAddressModelList;
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
    String original_bg_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_bg_main);
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
        sharedpreferences = getSharedPreferences(BgDetails, Context.MODE_PRIVATE);
        String bg_trap_id = sharedpreferences.getString(BgTrapId, "");
        String trap_status = sharedpreferences.getString(TrapStatus, "");
        String trap_position = sharedpreferences.getString(TrapPosition, "");
        String respond_name = sharedpreferences.getString(RespondName, "");
        String location_coordinates = sharedpreferences.getString(LocationCoordinates, "");
        String bg_run_id = sharedpreferences.getString(BgRunId, "");
        original_bg_id = sharedpreferences.getString(OriginalBgId, "");
        Log.d("bg_run",bg_run_id);
        if(respond_name.length()!=0) {
            EditTextTrapId.setText(bg_trap_id);
            if (trap_status.equals("1")) {
                RadioProposed.setChecked(true);
            }
            if (trap_status.equals("2")) {
                RadioSet.setChecked(true);
            }
            EditTextTrapPosition.setText(trap_position);
            EditTextRespondName.setText(respond_name);
            EditTextLocationCoordinates.setText(location_coordinates);
        }
    }
    public void viewCoordinates(View pView) {
        if (ActivityCompat.checkSelfPermission(AddBgMainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            getLocation();
        } else {
            ActivityCompat.requestPermissions(AddBgMainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
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
                    Geocoder geocoder = new Geocoder(AddBgMainActivity.this, Locale.getDefault());
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

    public void goAdditionalBg(View pView) {
        int error_flag = 0;
        if (EditTextTrapId.getText().toString().length() == 0) {
            EditTextTrapId.setError("BG trap id is required.");
            error_flag = 1;
        }
        if (RadioProposed.isChecked() == false && RadioSet.isChecked() == false) {
            RadioProposed.setError("Trap status is required.");
            RadioSet.setError("Trap status is required.");
            error_flag = 1;
        }
        if (EditTextRespondName.getText().toString().length() == 0) {
            EditTextRespondName.setError("Respondent name is required.");
            error_flag = 1;
        }
        if (EditTextLocationCoordinates.getText().toString().length() == 0) {
            EditTextLocationCoordinates.setError("Location coordinates is required.");
            error_flag = 1;
        }
        if (EditTextTrapPosition.getText().toString().length() == 0) {
            EditTextTrapPosition.setError("Location coordinate is required.");
            error_flag = 1;
        }
        if(error_flag==0) {
            dbHandler = new DbHandler(context);
            bgPersonAddressModelList = new ArrayList<>();
            bgPersonAddressModelList = dbHandler.getSingleBgTrap(EditTextTrapId.getText().toString());
            if (((bgPersonAddressModelList.size() > 0) && form_type.equals("new")) || (!original_bg_id.equals(EditTextTrapId.getText().toString()) && bgPersonAddressModelList.size() > 0 && form_type.equals("edit-new"))) {
                errorText.setVisibility(View.VISIBLE);
                errorText.setText("BG trap id is already existing.");
                Toast.makeText(context, "BG trap id is already existing.",
                        Toast.LENGTH_LONG).show();
            } else {
                sharedpreferences = getSharedPreferences(BgDetails, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString(BgTrapId, EditTextTrapId.getText().toString());
                editor.putString(OriginalBgId, original_bg_id);

                if (RadioProposed.isChecked()) {
                    editor.putString(TrapStatus, "1");
                }
                if (RadioSet.isChecked()) {
                    editor.putString(TrapStatus, "2");
                }

                editor.putString(TrapPosition, EditTextTrapPosition.getText().toString());
                editor.putString(RespondName, EditTextRespondName.getText().toString());
                editor.putString(LocationCoordinates, EditTextLocationCoordinates.getText().toString());
                editor.apply();
                Intent intent = new Intent(context, AddBgAdditionalActivity.class);
                if (form_type.equals("edit-new")) {
                    intent.putExtra("form-type", "edit-new");
                } else {
                    intent.putExtra("form-type", "new");
                }
                startActivity(intent);
            }
        }
    }
    public void goListView(View pView)
    {
        Intent intent = new Intent(context, OvListActivity.class);
        intent.putExtra("type", "bg");
        startActivity(intent);
    }
}