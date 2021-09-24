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

public class AddBgCollectionMainActivity extends AppCompatActivity {
    final Context context = this;
    private DbHandler dbHandler;
    private static final int PRIORITY_HIGH_ACCURACY = 100;
    FusedLocationProviderClient fusedLocationProviderClient;
    public static final String BgTrapId = "BgTrapId";
    public static final String CollectionId = "CollectionId";
    public static final String CollectionStatus = "CollectionStatus";
    public static final String TrapPosition = "TrapPosition";
    public static final String RespondName = "RespondName";
    public static final String LocationCoordinates = "LocationCoordinates";
    public static final String BgCollectionDetails = "BgCollectionDetails";
    public static final String BgRunId = "BgRunId";
    private List<BgCollectionModel> bgCollectionModelList;
    EditText EditTextTrapId;
    RadioGroup RadioGroupCollectionStatus;
    RadioButton RadioCollected;
    RadioButton RadioNotCollected;
    EditText EditTextTrapPosition;
    EditText EditTextRespondName;
    EditText EditTextLocationCoordinates;
    EditText EditTextCollectionId;
    TextView errorText;
    SharedPreferences sharedpreferences;
    String form_type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_bgcollection_main);
        //form_type = getIntent().getStringExtra("form-type");
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        EditTextTrapId = (EditText) findViewById(R.id.editTextTrapId);
        EditTextCollectionId = (EditText) findViewById(R.id.editTextCollectionId);
        RadioGroupCollectionStatus = (RadioGroup) findViewById(R.id.collectionStatus);
        RadioCollected = (RadioButton) findViewById(R.id.radioCollected);
        RadioNotCollected= (RadioButton) findViewById(R.id.radioNotCollected);
        EditTextTrapPosition = (EditText) findViewById(R.id.editTextTrapPosition);
        EditTextRespondName = (EditText) findViewById(R.id.editTextRespondentName);
        EditTextLocationCoordinates = (EditText) findViewById(R.id.editTextLocation);
        errorText = (TextView) findViewById(R.id.errorContainer);
        sharedpreferences = getSharedPreferences(BgCollectionDetails, Context.MODE_PRIVATE);
        String bg_trap_id = sharedpreferences.getString(BgTrapId, "");
        String collection_id = sharedpreferences.getString(CollectionId, "");
        String collection_status = sharedpreferences.getString(CollectionStatus, "");
        String trap_position = sharedpreferences.getString(TrapPosition, "");
        String respond_name = sharedpreferences.getString(RespondName, "");
        String location_coordinates = sharedpreferences.getString(LocationCoordinates, "");
        EditTextTrapId.setText(bg_trap_id);
        EditTextCollectionId.setText(collection_id);
        if (collection_status.equals("1")) {
            RadioCollected.setChecked(true);
        }
        if (collection_status.equals("2")) {
            RadioNotCollected.setChecked(true);
        }
        EditTextTrapPosition.setText(trap_position);
        EditTextRespondName.setText(respond_name);
        EditTextLocationCoordinates.setText(location_coordinates);

    }

    public void goAdditionalBg(View pView) {
        dbHandler = new DbHandler(context);
        bgCollectionModelList = new ArrayList<>();
        bgCollectionModelList = dbHandler.getSingleBgCollectionTrapById(EditTextCollectionId.getText().toString());
        sharedpreferences = getSharedPreferences(BgCollectionDetails, Context.MODE_PRIVATE);
        String bg_trap_id = sharedpreferences.getString(BgTrapId, "");
        String run_id = sharedpreferences.getString(BgRunId, "");
        if((bgCollectionModelList.size()>0) && ((!bgCollectionModelList.get(0).trap_id.equals(bg_trap_id)) || (!bgCollectionModelList.get(0).bg_run_id.equals(run_id))))
        {
            Toast.makeText(context, "Collection id is already existing. Please try using another collection id.",
                    Toast.LENGTH_LONG).show();
        }
        else {
            sharedpreferences = getSharedPreferences(BgCollectionDetails, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString(CollectionId, EditTextCollectionId.getText().toString());
            if (RadioCollected.isChecked()) {
                editor.putString(CollectionStatus, "1");
            }
            if (RadioNotCollected.isChecked()) {
                editor.putString(CollectionStatus, "2");
            }
            editor.apply();
            Intent intent = new Intent(context, AddBgCollectionAdditionalActivity.class);
            startActivity(intent);
        }
    }

    public void goListView(View pView)
    {
        Intent intent = new Intent(context, OvListActivity.class);
        intent.putExtra("type", "bg");
        startActivity(intent);
    }
}