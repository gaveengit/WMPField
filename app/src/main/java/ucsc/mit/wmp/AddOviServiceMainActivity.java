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

public class AddOviServiceMainActivity extends AppCompatActivity {
    final Context context = this;
    private DbHandler dbHandler;
    private static final int PRIORITY_HIGH_ACCURACY = 100;
    FusedLocationProviderClient fusedLocationProviderClient;
    public static final String OviTrapId = "OviTrapId";
    public static final String ServiceId = "ServiceId";
    public static final String ServiceStatus = "ServiceStatus";
    public static final String TrapPosition = "TrapPosition";
    public static final String RespondName = "RespondName";
    public static final String LocationCoordinates = "LocationCoordinates";
    public static final String OviServiceDetails = "OviServiceDetails";
    public static final String OviRunId = "OviRunId";
    private List<OviServiceModel> oviServiceModelList;
    EditText EditTextTrapId;
    RadioGroup RadioGroupServiceStatus;
    RadioButton RadioServiced;
    RadioButton RadioNotServiced;
    EditText EditTextTrapPosition;
    EditText EditTextRespondName;
    EditText EditTextLocationCoordinates;
    EditText EditTextServiceId;
    TextView errorText;
    TextView username_text;
    SharedPreferences sharedpreferences;
    String form_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_ovservice_main);
        //form_type = getIntent().getStringExtra("form-type");
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        EditTextTrapId = (EditText) findViewById(R.id.editTextTrapId);
        EditTextServiceId = (EditText) findViewById(R.id.editTextServiceId);
        //trapId.setText(ov_id);
        RadioGroupServiceStatus = (RadioGroup) findViewById(R.id.serviceStatus);
        RadioServiced = (RadioButton) findViewById(R.id.radioServiced);
        RadioNotServiced = (RadioButton) findViewById(R.id.radioNotServiced);
        EditTextTrapPosition = (EditText) findViewById(R.id.editTextTrapPosition);
        EditTextRespondName = (EditText) findViewById(R.id.editTextRespondentName);
        EditTextLocationCoordinates = (EditText) findViewById(R.id.editTextLocation);
        errorText = (TextView) findViewById(R.id.errorContainer);
        sharedpreferences = getSharedPreferences(OviServiceDetails, Context.MODE_PRIVATE);
        String ovi_trap_id = sharedpreferences.getString(OviTrapId, "");
        Log.d("ovi_trap_id", ovi_trap_id);
        String service_id = sharedpreferences.getString(ServiceId, "");
        String service_status = sharedpreferences.getString(ServiceStatus, "");
        String trap_position = sharedpreferences.getString(TrapPosition, "");
        String respond_name = sharedpreferences.getString(RespondName, "");
        String location_coordinates = sharedpreferences.getString(LocationCoordinates, "");
        EditTextTrapId.setText(ovi_trap_id);
        EditTextServiceId.setText(service_id);
        if (service_status.equals("1")) {
            RadioServiced.setChecked(true);
        }
        if (service_status.equals("2")) {
            RadioNotServiced.setChecked(true);
        }
        EditTextTrapPosition.setText(trap_position);
        EditTextRespondName.setText(respond_name);
        EditTextLocationCoordinates.setText(location_coordinates);

        username_text = (TextView) findViewById(R.id.textViewUsername);
        sharedpreferences = getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        username_text.setText(sharedpreferences.getString("UserName", ""));


    }

    public void goAdditionalOv(View pView) {
        int error_flag = 0;
        if (EditTextServiceId.getText().toString().length() == 0) {
            EditTextServiceId.setError("Service Id is required.");
            error_flag = 1;
        }
        if( RadioServiced.isChecked()==false && RadioNotServiced.isChecked()==false){
            RadioServiced.setError("Service status is required");
            RadioNotServiced.setError("Service status is required");
            error_flag = 1;
        }
        if(error_flag==0) {
            dbHandler = new DbHandler(context);
            oviServiceModelList = new ArrayList<>();
            oviServiceModelList = dbHandler.getSingleOviServiceTrapById(EditTextServiceId.getText().toString());
            sharedpreferences = getSharedPreferences(OviServiceDetails, Context.MODE_PRIVATE);
            String ovi_trap_id = sharedpreferences.getString(OviTrapId, "");
            String run_id = sharedpreferences.getString(OviRunId, "");
            if ((oviServiceModelList.size() > 0) && ((!oviServiceModelList.get(0).trap_id.equals(ovi_trap_id)) || (!oviServiceModelList.get(0).ovi_run_id.equals(run_id)))) {
                Toast.makeText(context, "Service id is already existing. Please try using another service id.",
                        Toast.LENGTH_LONG).show();
            } else {
                sharedpreferences = getSharedPreferences(OviServiceDetails, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString(ServiceId, EditTextServiceId.getText().toString());
                if (RadioServiced.isChecked()) {
                    editor.putString(ServiceStatus, "1");
                }
                if (RadioNotServiced.isChecked()) {
                    editor.putString(ServiceStatus, "2");
                }
                editor.apply();
                Intent intent = new Intent(context, AddOviServiceAdditionalActivity.class);
                startActivity(intent);
            }
        }
    }

    public void goListView(View pView) {
        Intent intent = new Intent(context, OvListActivity.class);
        intent.putExtra("type", "ov");
        startActivity(intent);
    }
    public void logout(View pView){
        Intent intent = new Intent(context, LoginActivityController.class);
        startActivity(intent);
    }
}
