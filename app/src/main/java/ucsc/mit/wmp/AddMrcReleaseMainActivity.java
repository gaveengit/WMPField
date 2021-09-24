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

public class AddMrcReleaseMainActivity extends AppCompatActivity {
    final Context context = this;
    private DbHandler dbHandler;
    private static final int PRIORITY_HIGH_ACCURACY = 100;
    FusedLocationProviderClient fusedLocationProviderClient;
    public static final String MrcTrapId = "MrcTrapId";
    public static final String ReleaseId = "ReleaseId";
    public static final String ReleaseStatus = "ReleaseStatus";
    public static final String RespondName = "RespondName";
    public static final String LocationCoordinates = "LocationCoordinates";
    public static final String MrcReleaseDetails = "MrcReleaseDetails";
    public static final String MrcRunId = "MrcRunId";
    private List<MrcReleaseModel> mrcReleaseModelList;
    EditText EditTextTrapId;
    RadioGroup RadioGroupReleaseStatus;
    RadioButton RadioReleased;
    RadioButton RadioNotReleased;
    EditText EditTextRespondName;
    EditText EditTextLocationCoordinates;
    EditText EditTextReleaseId;
    TextView errorText;
    SharedPreferences sharedpreferences;
    String form_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_mrcrelease_main);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        EditTextTrapId = (EditText) findViewById(R.id.editTextTrapId);
        EditTextReleaseId = (EditText) findViewById(R.id.editTextReleaseId);
        RadioGroupReleaseStatus = (RadioGroup) findViewById(R.id.ReleaseStatus);
        RadioReleased = (RadioButton) findViewById(R.id.radioReleased);
        RadioNotReleased = (RadioButton) findViewById(R.id.radioNotReleased);
        EditTextRespondName = (EditText) findViewById(R.id.editTextRespondentName);
        EditTextLocationCoordinates = (EditText) findViewById(R.id.editTextLocation);
        errorText = (TextView) findViewById(R.id.errorContainer);
        sharedpreferences = getSharedPreferences(MrcReleaseDetails, Context.MODE_PRIVATE);
        String mrc_trap_id = sharedpreferences.getString(MrcTrapId, "");
        String release_id = sharedpreferences.getString(ReleaseId, "");
        String release_status = sharedpreferences.getString(ReleaseStatus, "");
        String respond_name = sharedpreferences.getString(RespondName, "");
        String location_coordinates = sharedpreferences.getString(LocationCoordinates, "");
        EditTextTrapId.setText(mrc_trap_id);
        EditTextReleaseId.setText(release_id);
        if (release_status.equals("1")) {
            RadioReleased.setChecked(true);
        }
        if (release_status.equals("2")) {
            RadioNotReleased.setChecked(true);
        }
        EditTextRespondName.setText(respond_name);
        EditTextLocationCoordinates.setText(location_coordinates);

    }

    public void goAdditionalMrc(View pView) {
        dbHandler = new DbHandler(context);
        mrcReleaseModelList = new ArrayList<>();
        mrcReleaseModelList = dbHandler.getSingleMrcReleaseTrapById(EditTextReleaseId.getText().toString());
        sharedpreferences = getSharedPreferences(MrcReleaseDetails, Context.MODE_PRIVATE);
        String mrc_trap_id = sharedpreferences.getString(MrcTrapId, "");
        String run_id = sharedpreferences.getString(MrcRunId, "");
        if((mrcReleaseModelList.size()>0) && ((!mrcReleaseModelList.get(0).trap_id.equals(mrc_trap_id)) || (!mrcReleaseModelList.get(0).mrc_run_id.equals(run_id))))
        {
            Toast.makeText(context, "Release id is already existing. Please try using another collection id.",
                    Toast.LENGTH_LONG).show();
        }
        else{
            sharedpreferences = getSharedPreferences(MrcReleaseDetails, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString(ReleaseId, EditTextReleaseId.getText().toString());
            if (RadioReleased.isChecked()) {
                editor.putString(ReleaseStatus, "1");
            }
            if (RadioNotReleased.isChecked()) {
                editor.putString(ReleaseStatus, "2");
            }

            editor.apply();
            Intent intent = new Intent(context, AddMrcReleaseAdditionalActivity.class);
            startActivity(intent);
        }
    }

    public void goListView(View pView) {
        Intent intent = new Intent(context, OvListActivity.class);
        intent.putExtra("type", "mrc");
        startActivity(intent);
    }
}