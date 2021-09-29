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

public class AddMrcMainActivity extends AppCompatActivity {
    final Context context = this;
    private DbHandler dbHandler;
    private static final int PRIORITY_HIGH_ACCURACY = 100;
    FusedLocationProviderClient fusedLocationProviderClient;
    public static final String MrcId = "MrcId";
    public static final String MrcStatus = "MrcStatus";
    public static final String MrcPosition = "MrcPosition";
    public static final String RespondName = "RespondName";
    public static final String LocationCoordinates = "LocationCoordinates";
    public static final String MrcDetails = "MrcDetails";
    public static final String MrcRunId = "MrcRunId";
    public static final String OriginalMrcId = "OriginalMrcId";
    private List<MrcModel> mrcPersonAddressModelList;
    EditText EditTextMrcId;
    RadioGroup RadioGroupMrcStatus;
    RadioButton RadioProposed;
    RadioButton RadioSet;
    EditText EditTextMrcPosition;
    EditText EditTextRespondName;
    EditText EditTextLocationCoordinates;
    TextView errorText;
    SharedPreferences sharedpreferences;
    String form_type;
    String mrc_id;
    String original_mrc_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_mrc_main);
        form_type = getIntent().getStringExtra("form-type");
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        EditTextMrcId = (EditText) findViewById(R.id.editTextTrapId);
        RadioGroupMrcStatus = (RadioGroup) findViewById(R.id.trapStatus);
        RadioProposed = (RadioButton) findViewById(R.id.radioProposed);
        RadioSet = (RadioButton) findViewById(R.id.radioSet);
        EditTextRespondName = (EditText) findViewById(R.id.editTextRespondentName);
        EditTextLocationCoordinates = (EditText) findViewById(R.id.editTextLocation);
        errorText = (TextView) findViewById(R.id.errorContainer);
        sharedpreferences = getSharedPreferences(MrcDetails, Context.MODE_PRIVATE);
        mrc_id = sharedpreferences.getString(MrcId, "");
        original_mrc_id = sharedpreferences.getString(OriginalMrcId, "");
        String trap_status = sharedpreferences.getString(MrcStatus, "");
        String respond_name = sharedpreferences.getString(RespondName, "");
        String location_coordinates = sharedpreferences.getString(LocationCoordinates, "");
        String mrc_run_id = sharedpreferences.getString(MrcRunId, "");
        Log.d("mrc_run",mrc_run_id);
        Log.d("trap_status",trap_status);
        Log.d("respond_name",respond_name);
        if (respond_name.length() != 0) {
            EditTextMrcId.setText(mrc_id);
            if (trap_status.equals("1")) {
                RadioProposed.setChecked(true);
            }
            if (trap_status.equals("2")) {
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
        int error_flag = 0;
        if (EditTextMrcId.getText().toString().length() == 0) {
            EditTextMrcId.setError("MRC identifier is required.");
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
        if (error_flag == 0) {
            dbHandler = new DbHandler(context);
            Log.d("form_type", form_type);
            mrcPersonAddressModelList = new ArrayList<>();
            mrcPersonAddressModelList = dbHandler.getSingleMrcPersonAddress(EditTextMrcId.getText().toString());
            Log.d("size", String.valueOf(mrcPersonAddressModelList.size()));
            if (((mrcPersonAddressModelList.size() > 0) && form_type.equals("new")) || (!original_mrc_id.equals(EditTextMrcId.getText().toString()) && mrcPersonAddressModelList.size() > 0 && form_type.equals("edit-new"))) {

                errorText.setText("MRC identifier is already existing.");
                Toast.makeText(context, "MRC identifier is already existing.",
                        Toast.LENGTH_LONG).show();
            } else {
                errorText.setVisibility(View.GONE);
                sharedpreferences = getSharedPreferences(MrcDetails, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString(MrcId, EditTextMrcId.getText().toString());

                if (RadioProposed.isChecked()) {
                    editor.putString(MrcStatus, "1");
                }
                if (RadioSet.isChecked()) {
                    editor.putString(MrcStatus, "2");
                }
                editor.putString(RespondName, EditTextRespondName.getText().toString());
                editor.putString(LocationCoordinates, EditTextLocationCoordinates.getText().toString());
                editor.apply();
                Intent intent = new Intent(context, AddMrcAdditionalActivity.class);
                if (form_type.equals("edit-new")) {
                    intent.putExtra("form-type", "edit-new");
                } else {
                    intent.putExtra("form-type", "new");
                }
                startActivity(intent);
            }
        }
    }

    public void goListView(View pView) {
        Intent intent = new Intent(context, OvListActivity.class);
        intent.putExtra("type", "mrc");
        startActivity(intent);
    }
}
