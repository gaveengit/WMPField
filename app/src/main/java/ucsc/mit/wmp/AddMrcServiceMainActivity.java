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

public class AddMrcServiceMainActivity extends AppCompatActivity {
    final Context context = this;
    private DbHandler dbHandler;
    private static final int PRIORITY_HIGH_ACCURACY = 100;
    FusedLocationProviderClient fusedLocationProviderClient;
    public static final String MrcTrapId = "MrcTrapId";
    public static final String ServiceId = "ServiceId";
    public static final String ServiceStatus = "ServiceStatus";
    public static final String RespondName = "RespondName";
    public static final String LocationCoordinates = "LocationCoordinates";
    public static final String MrcServiceDetails = "MrcServiceDetails";
    public static final String MrcRunId = "MrcRunId";
    private List<MrcServiceModel> mrcServiceModelList;
    EditText EditTextTrapId;
    RadioGroup RadioGroupServiceStatus;
    RadioButton RadioServiced;
    RadioButton RadioNotServiced;
    EditText EditTextRespondName;
    EditText EditTextLocationCoordinates;
    EditText EditTextServiceId;
    TextView errorText;
    SharedPreferences sharedpreferences;
    String form_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_mrcservice_main);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        EditTextTrapId = (EditText) findViewById(R.id.editTextTrapId);
        EditTextServiceId = (EditText) findViewById(R.id.editTextServiceId);
        //trapId.setText(ov_id);
        RadioGroupServiceStatus = (RadioGroup) findViewById(R.id.serviceStatus);
        RadioServiced = (RadioButton) findViewById(R.id.radioServiced);
        RadioNotServiced = (RadioButton) findViewById(R.id.radioNotServiced);
        EditTextRespondName = (EditText) findViewById(R.id.editTextRespondentName);
        EditTextLocationCoordinates = (EditText) findViewById(R.id.editTextLocation);
        errorText = (TextView) findViewById(R.id.errorContainer);
        sharedpreferences = getSharedPreferences(MrcServiceDetails, Context.MODE_PRIVATE);
        String mrc_trap_id = sharedpreferences.getString(MrcTrapId, "");
        String service_id = sharedpreferences.getString(ServiceId, "");
        String service_status = sharedpreferences.getString(ServiceStatus, "");
        String respond_name = sharedpreferences.getString(RespondName, "");
        String location_coordinates = sharedpreferences.getString(LocationCoordinates, "");
        EditTextTrapId.setText(mrc_trap_id);
        EditTextServiceId.setText(service_id);
        if (service_status.equals("1")) {
            RadioServiced.setChecked(true);
        }
        if (service_status.equals("2")) {
            RadioNotServiced.setChecked(true);
        }
        EditTextRespondName.setText(respond_name);
        EditTextLocationCoordinates.setText(location_coordinates);

    }

    public void goAdditionalMrc(View pView) {
        dbHandler = new DbHandler(context);
        mrcServiceModelList = new ArrayList<>();
        mrcServiceModelList = dbHandler.getSingleMrcServiceTrapById(EditTextServiceId.getText().toString());
        sharedpreferences = getSharedPreferences(MrcServiceDetails, Context.MODE_PRIVATE);
        String mrc_trap_id = sharedpreferences.getString(MrcTrapId, "");
        String run_id = sharedpreferences.getString(MrcRunId, "");
        if((mrcServiceModelList.size()>0) && ((!mrcServiceModelList.get(0).trap_id.equals(mrc_trap_id)) || (!mrcServiceModelList.get(0).mrc_run_id.equals(run_id))))
        {
            Toast.makeText(context, "Service id is already existing. Please try using another service id.",
                    Toast.LENGTH_LONG).show();
        }
        else{
            sharedpreferences = getSharedPreferences(MrcServiceDetails, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString(ServiceId, EditTextServiceId.getText().toString());
            if (RadioServiced.isChecked()) {
                editor.putString(ServiceStatus, "1");
            }
            if (RadioNotServiced.isChecked()) {
                editor.putString(ServiceStatus, "2");
            }

            editor.apply();
            Intent intent = new Intent(context, AddMrcServiceAdditionalActivity.class);
            startActivity(intent);
        }
    }

    public void goListView(View pView) {
        Intent intent = new Intent(context, OvListActivity.class);
        intent.putExtra("type", "mrc");
        startActivity(intent);
    }
}

