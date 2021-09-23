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

public class AddBgCollectionAdditionalActivity extends AppCompatActivity {
    final Context context = this;
    private DbHandler dbHandler;
    public static final String BgTrapId = "BgTrapId";
    public static final String CollectionStatus = "CollectionStatus";
    public static final String CollectionId = "CollectionId";
    public static final String TrapPosition = "TrapPosition";
    public static final String RespondName = "RespondName";
    public static final String LocationCoordinates = "LocationCoordinates";
    public static final String BgRunId = "BgRunId";
    public static final String Phone = "Phone";
    public static final String AddressLine1 = "AddressLine1";
    public static final String AddressLine2 = "AddressLine2";
    public static final String LocationDescription = "LocationDescription";
    public static final String BgCollectionDetails = "BgCollectionDetails";
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
        setContentView(R.layout.add_bgcollection_additional);
        EditTextPhone = (EditText) findViewById(R.id.editTextPhone);
        EditTextAddressLine1 = (EditText) findViewById(R.id.editTextAdd1);
        EditTextAddressLine2 = (EditText) findViewById(R.id.editTextAdd2);
        EditTextLocationDescription = (EditText) findViewById(R.id.editTextLocationDescription);
        errorText = (TextView) findViewById(R.id.errorContainer);
        sharedpreferences = getSharedPreferences(BgCollectionDetails, Context.MODE_PRIVATE);
        String phone = sharedpreferences.getString(Phone, "");
        String address_line1 = sharedpreferences.getString(AddressLine1, "");
        String address_line2 = sharedpreferences.getString(AddressLine2, "");
        String location_description = sharedpreferences.getString(LocationDescription, "");
        EditTextPhone.setText(phone);
        EditTextAddressLine1.setText(address_line1);
        EditTextAddressLine2.setText(address_line2);
        EditTextLocationDescription.setText(location_description);
    }

    public void goBgMain(View v) {
        sharedpreferences = getSharedPreferences(BgCollectionDetails, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(Phone, EditTextPhone.getText().toString());
        editor.putString(AddressLine1, EditTextAddressLine1.getText().toString());
        editor.putString(AddressLine2, EditTextAddressLine2.getText().toString());
        editor.putString(LocationDescription, EditTextLocationDescription.getText().toString());
        editor.apply();
        Intent intent = new Intent(context, AddBgServiceMainActivity.class);
        intent.putExtra("form-type", form_type);
        startActivity(intent);
    }

    public void submitBg(View v) {

        errorText.setVisibility(View.GONE);
        sharedpreferences = getSharedPreferences(BgCollectionDetails, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(Phone, EditTextPhone.getText().toString());
        editor.putString(AddressLine1, EditTextAddressLine1.getText().toString());
        editor.putString(AddressLine2, EditTextAddressLine2.getText().toString());
        editor.putString(LocationDescription, EditTextLocationDescription.getText().toString());
        editor.apply();

        dbHandler = new DbHandler(context);
        sharedpreferences = getSharedPreferences(BgCollectionDetails, Context.MODE_PRIVATE);
        String bg_trap_id = sharedpreferences.getString(BgTrapId, "");
        String collection_status = sharedpreferences.getString(CollectionStatus, "");
        String trap_position = sharedpreferences.getString(TrapPosition, "");
        String respond_name = sharedpreferences.getString(RespondName, "");
        String location_coordinates = sharedpreferences.getString(LocationCoordinates, "");
        String bg_run_id = sharedpreferences.getString(BgRunId, "");
        String phone = sharedpreferences.getString(Phone, "");
        String address_line1 = sharedpreferences.getString(AddressLine1, "");
        String address_line2 = sharedpreferences.getString(AddressLine2, "");
        String location_description = sharedpreferences.getString(LocationDescription, "");
        String collection_id = sharedpreferences.getString(CollectionId, "");

        String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        Calendar currentTime = Calendar.getInstance();
        String hour = String.valueOf(currentTime.get(Calendar.HOUR_OF_DAY));
        String minute = String.valueOf(currentTime.get(Calendar.MINUTE));
        String current_time = hour + ":" + minute;

        BgCollectionModel bgCollectionModel = new BgCollectionModel(bg_run_id, bg_trap_id, collection_id, currentDate, current_time, collection_status);
        long flag = dbHandler.updateSingleBgCollection(bgCollectionModel);
        if (flag != -1) {
            Toast.makeText(context, "BG collection has been added successfully.",
                    Toast.LENGTH_LONG).show();
            Intent intent = new Intent(context, OvListActivity.class);
            intent.putExtra("type", "bg");
            startActivity(intent);
        } else {
            Toast.makeText(context, "Failure in updating BG collection. Please try again..",
                    Toast.LENGTH_LONG).show();
            Intent intent = new Intent(context, OvListActivity.class);
            intent.putExtra("type", "bg");
            startActivity(intent);
        }


    }
}


