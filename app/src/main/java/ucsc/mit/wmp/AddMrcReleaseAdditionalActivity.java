package ucsc.mit.wmp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AddMrcReleaseAdditionalActivity extends AppCompatActivity {
    final Context context = this;
    private DbHandler dbHandler;
    public static final String MrcTrapId = "MrcTrapId";
    public static final String ReleaseStatus = "ReleaseStatus";
    public static final String ReleaseId = "ReleaseId";
    public static final String RespondName = "RespondName";
    public static final String LocationCoordinates = "LocationCoordinates";
    public static final String MrcRunId = "MrcRunId";
    public static final String Phone = "Phone";
    public static final String AddressLine1 = "AddressLine1";
    public static final String AddressLine2 = "AddressLine2";
    public static final String LocationDescription = "LocationDescription";
    public static final String MrcReleaseDetails = "MrcReleaseDetails";
    EditText EditTextPhone;
    EditText EditTextAddressLine1;
    EditText EditTextAddressLine2;
    EditText EditTextLocationDescription;
    TextView errorText;
    SharedPreferences sharedpreferences;
    private List<PersonModel> personList;
    private List<AddressModel> addressList;
    private List<String> personListLast;
    private List<String> addressListLast;
    private List<BgTrapModel> bgPersonAddressModelList;
    String form_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_mrcrelease_additional);
        EditTextPhone = (EditText) findViewById(R.id.editTextPhone);
        EditTextAddressLine1 = (EditText) findViewById(R.id.editTextAdd1);
        EditTextAddressLine2 = (EditText) findViewById(R.id.editTextAdd2);
        EditTextLocationDescription = (EditText) findViewById(R.id.editTextLocationDescription);
        errorText = (TextView) findViewById(R.id.errorContainer);
        sharedpreferences = getSharedPreferences(MrcReleaseDetails, Context.MODE_PRIVATE);
        String phone = sharedpreferences.getString(Phone, "");
        String address_line1 = sharedpreferences.getString(AddressLine1, "");
        String address_line2 = sharedpreferences.getString(AddressLine2, "");
        String location_description = sharedpreferences.getString(LocationDescription, "");
        EditTextPhone.setText(phone);
        EditTextAddressLine1.setText(address_line1);
        EditTextAddressLine2.setText(address_line2);
        EditTextLocationDescription.setText(location_description);
    }

    public void goMrcMain(View v) {
        sharedpreferences = getSharedPreferences(MrcReleaseDetails, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(Phone, EditTextPhone.getText().toString());
        editor.putString(AddressLine1, EditTextAddressLine1.getText().toString());
        editor.putString(AddressLine2, EditTextAddressLine2.getText().toString());
        editor.putString(LocationDescription, EditTextLocationDescription.getText().toString());
        editor.apply();
        Intent intent = new Intent(context, AddMrcReleaseMainActivity.class);
        intent.putExtra("form-type", form_type);
        startActivity(intent);
    }

    public void submitMrc(View v) {

        errorText.setVisibility(View.GONE);
        sharedpreferences = getSharedPreferences(MrcReleaseDetails, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(Phone, EditTextPhone.getText().toString());
        editor.putString(AddressLine1, EditTextAddressLine1.getText().toString());
        editor.putString(AddressLine2, EditTextAddressLine2.getText().toString());
        editor.putString(LocationDescription, EditTextLocationDescription.getText().toString());
        editor.apply();

        dbHandler = new DbHandler(context);
        sharedpreferences = getSharedPreferences(MrcReleaseDetails, Context.MODE_PRIVATE);
        String mrc_trap_id = sharedpreferences.getString(MrcTrapId, "");
        String release_status = sharedpreferences.getString(ReleaseStatus, "");
        String mrc_run_id = sharedpreferences.getString(MrcRunId, "");
        String release_id = sharedpreferences.getString(ReleaseId, "");


        String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        Calendar currentTime = Calendar.getInstance();
        String hour = String.valueOf(currentTime.get(Calendar.HOUR_OF_DAY));
        String minute = String.valueOf(currentTime.get(Calendar.MINUTE));
        String current_time = hour + ":" + minute;

        MrcReleaseModel mrcReleaseModel = new MrcReleaseModel(mrc_run_id, mrc_trap_id, release_id, currentDate, current_time, release_status);
        long flag = dbHandler.updateSingleMrcRelease(mrcReleaseModel);
        if (flag != -1) {
            Toast.makeText(context, "MRC release has been updated successfully.",
                    Toast.LENGTH_LONG).show();
            Intent intent = new Intent(context, OvListActivity.class);
            intent.putExtra("type", "mrc");
            startActivity(intent);
        } else {
            Toast.makeText(context, "Failure in adding MRC release. Please try again..",
                    Toast.LENGTH_LONG).show();
            Intent intent = new Intent(context, OvListActivity.class);
            intent.putExtra("type", "mrc");
            startActivity(intent);
        }


    }
}


