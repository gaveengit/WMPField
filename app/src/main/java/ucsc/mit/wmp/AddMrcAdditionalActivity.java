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

import java.util.ArrayList;
import java.util.List;

public class AddMrcAdditionalActivity extends AppCompatActivity {
    final Context context = this;
    private DbHandler dbHandler;
    public static final String MrcId = "MrcId";
    public static final String MrcStatus = "MrcStatus";
    public static final String MrcPosition = "MrcPosition";
    public static final String RespondName = "RespondName";
    public static final String LocationCoordinates = "LocationCoordinates";
    public static final String MrcRunId = "MrcRunId";
    public static final String Phone = "Phone";
    public static final String AddressLine1 = "AddressLine1";
    public static final String AddressLine2 = "AddressLine2";
    public static final String LocationDescription = "LocationDescription";
    public static final String MrcDetails = "MrcDetails";
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_mrc_additional);
        personList = new ArrayList<>();
        addressList = new ArrayList<>();
        EditTextPhone = (EditText) findViewById(R.id.editTextPhone);
        EditTextAddressLine1 = (EditText) findViewById(R.id.editTextAdd1);
        EditTextAddressLine2 = (EditText) findViewById(R.id.editTextAdd2);
        EditTextLocationDescription = (EditText) findViewById(R.id.editTextLocationDescription);
        errorText = (TextView) findViewById(R.id.errorContainer);
        sharedpreferences = getSharedPreferences(MrcDetails, Context.MODE_PRIVATE);
        String phone = sharedpreferences.getString(Phone, "");
        String address_line1 = sharedpreferences.getString(AddressLine1, "");
        String address_line2 = sharedpreferences.getString(AddressLine2, "");
        String location_description = sharedpreferences.getString(LocationDescription, "");
        EditTextPhone.setText(phone);
        EditTextAddressLine1.setText(address_line1);
        EditTextAddressLine2.setText(address_line2);
        EditTextLocationDescription.setText(location_description);
    }
    public void goMrcMain(View v)
    {
        sharedpreferences = getSharedPreferences(MrcDetails, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(Phone, EditTextPhone.getText().toString());
        editor.putString(AddressLine1, EditTextAddressLine1.getText().toString());
        editor.putString(AddressLine2, EditTextAddressLine2.getText().toString());
        editor.putString(LocationDescription, EditTextLocationDescription.getText().toString());
        editor.apply();
        Intent intent = new Intent(context, AddMrcMainActivity.class);
        startActivity(intent);
    }
    public void SubmitMrc(View v)
    {
        if (EditTextAddressLine1.getText().toString().length() == 0 || EditTextAddressLine2.getText().toString().
                length()
                ==0 ) {
            errorText.setVisibility(View.VISIBLE);
            errorText.setText("Please fill all required fields.");
        }
        else {
            errorText.setVisibility(View.GONE);
            sharedpreferences = getSharedPreferences(MrcDetails, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString(Phone, EditTextPhone.getText().toString());
            editor.putString(AddressLine1, EditTextAddressLine1.getText().toString());
            editor.putString(AddressLine2, EditTextAddressLine2.getText().toString());
            editor.putString(LocationDescription, EditTextLocationDescription.getText().toString());
            editor.apply();

            dbHandler = new DbHandler(context);
            sharedpreferences = getSharedPreferences(MrcDetails, Context.MODE_PRIVATE);
            String mrc_id = sharedpreferences.getString(MrcId, "");
            String mrc_status = sharedpreferences.getString(MrcStatus, "");
            String mrc_position = sharedpreferences.getString(MrcPosition, "");
            String respond_name = sharedpreferences.getString(RespondName, "");
            String location_coordinates = sharedpreferences.getString(LocationCoordinates, "");
            String mrc_run_id = sharedpreferences.getString(MrcRunId, "");
            String phone = sharedpreferences.getString(Phone, "");
            String address_line1 = sharedpreferences.getString(AddressLine1, "");
            String address_line2 = sharedpreferences.getString(AddressLine2, "");
            String location_description = sharedpreferences.getString(LocationDescription, "");

            PersonModel personModel = new PersonModel(respond_name, Integer.parseInt(phone), "no");
            dbHandler.insertDataPerson(personModel);
            personList = dbHandler.getLastPerson();
            int lastPersonId = personList.get(0).person_id;
            AddressModel addressModel = new AddressModel(address_line1, address_line2, location_description, "no");
            dbHandler.insertDataAddress(addressModel);
            addressList = dbHandler.getLastAddresses();
            int lastAddressId = addressList.get(0).address_id;
            MrcModel mrcModel = new MrcModel(mrc_id, mrc_status, mrc_position,
                    mrc_run_id, lastPersonId, lastAddressId, location_coordinates,
                    "no");
            dbHandler.insertDataMrc(mrcModel);
            Log.d("lastPersonId",Integer.toString(lastPersonId));
            Log.d("lastAddressId",Integer.toString(lastAddressId));
            Intent intent = new Intent(context, OvListActivity.class);
            intent.putExtra("type", "mrc");
            startActivity(intent);
        }
    }
}
