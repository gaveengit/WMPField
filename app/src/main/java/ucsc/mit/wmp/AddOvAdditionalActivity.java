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

public class AddOvAdditionalActivity extends AppCompatActivity {
    final Context context = this;
    private DbHandler dbHandler;
    public static final String OviTrapId = "OviTrapId";
    public static final String TrapStatus = "TrapStatus";
    public static final String TrapPosition = "TrapPosition";
    public static final String RespondName = "RespondName";
    public static final String LocationCoordinates = "LocationCoordinates";
    public static final String OviRunId = "OviRunId";
    public static final String Phone = "Phone";
    public static final String AddressLine1 = "AddressLine1";
    public static final String AddressLine2 = "AddressLine2";
    public static final String LocationDescription = "LocationDescription";
    public static final String OviDetails = "OviDetails";
    public static final String PersonId = "PersonId";
    public static final String AddressId = "AddressId";
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
    private List<OvPersonAddressModel> ovPersonAddressModelList;
    String form_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_ov_additional);
        form_type = getIntent().getStringExtra("form-type");
        Log.d("form-type",form_type);
        personList = new ArrayList<>();
        addressList = new ArrayList<>();
        EditTextPhone = (EditText) findViewById(R.id.editTextPhone);
        EditTextAddressLine1 = (EditText) findViewById(R.id.editTextAdd1);
        EditTextAddressLine2 = (EditText) findViewById(R.id.editTextAdd2);
        EditTextLocationDescription = (EditText) findViewById(R.id.editTextLocationDescription);
        errorText = (TextView) findViewById(R.id.errorContainer);
        sharedpreferences = getSharedPreferences(OviDetails, Context.MODE_PRIVATE);
        String phone = sharedpreferences.getString(Phone, "");
        String address_line1 = sharedpreferences.getString(AddressLine1, "");
        String address_line2 = sharedpreferences.getString(AddressLine2, "");
        String location_description = sharedpreferences.getString(LocationDescription, "");
        EditTextPhone.setText(phone);
        EditTextAddressLine1.setText(address_line1);
        EditTextAddressLine2.setText(address_line2);
        EditTextLocationDescription.setText(location_description);
    }

    public void goOvMain(View v) {
        sharedpreferences = getSharedPreferences(OviDetails, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(Phone, EditTextPhone.getText().toString());
        editor.putString(AddressLine1, EditTextAddressLine1.getText().toString());
        editor.putString(AddressLine2, EditTextAddressLine2.getText().toString());
        editor.putString(LocationDescription, EditTextLocationDescription.getText().toString());
        editor.apply();
        Intent intent = new Intent(context, AddOvMainActivity.class);
        intent.putExtra("form-type", form_type);
        startActivity(intent);
    }

    public void submitOv(View v) {
        if (EditTextAddressLine1.getText().toString().length() == 0 || EditTextAddressLine2.getText().toString().
                length()
                == 0) {
            errorText.setVisibility(View.VISIBLE);
            errorText.setText("Please fill all required fields.");
        } else {
            errorText.setVisibility(View.GONE);
            sharedpreferences = getSharedPreferences(OviDetails, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString(Phone, EditTextPhone.getText().toString());
            editor.putString(AddressLine1, EditTextAddressLine1.getText().toString());
            editor.putString(AddressLine2, EditTextAddressLine2.getText().toString());
            editor.putString(LocationDescription, EditTextLocationDescription.getText().toString());
            editor.apply();
            dbHandler = new DbHandler(context);
            sharedpreferences = getSharedPreferences(OviDetails, Context.MODE_PRIVATE);
            String ovi_trap_id = sharedpreferences.getString(OviTrapId, "");
            String trap_status = sharedpreferences.getString(TrapStatus, "");
            String trap_position = sharedpreferences.getString(TrapPosition, "");
            String respond_name = sharedpreferences.getString(RespondName, "");
            String location_coordinates = sharedpreferences.getString(LocationCoordinates, "");
            String ovi_run_id = sharedpreferences.getString(OviRunId, "");
            String phone = sharedpreferences.getString(Phone, "");
            String address_line1 = sharedpreferences.getString(AddressLine1, "");
            String address_line2 = sharedpreferences.getString(AddressLine2, "");
            String location_description = sharedpreferences.getString(LocationDescription, "");
            int person_id = sharedpreferences.getInt(PersonId, 0);
            int address_id = sharedpreferences.getInt(AddressId, 0);
            if(form_type.equals("edit-new")){
                PersonModel personModel = new PersonModel(respond_name, Integer.parseInt(phone), "no");
                personModel.setPerson_id(person_id);
                dbHandler.updateSinglePerson(personModel);
                AddressModel addressModel = new AddressModel(address_line1, address_line2, location_description, "no");
                addressModel.setAddress_id(address_id);
                dbHandler.updateSingleAddress(addressModel);
                OvTrapModel ovTrapModel = new OvTrapModel(ovi_trap_id, trap_status, trap_position,
                        ovi_run_id, 0, 0, location_coordinates,
                        "no");
                int flag = dbHandler.updateSingleOvTrap(ovTrapModel);
                Log.d("flag",String.valueOf(flag));
                Intent intent = new Intent(context, OvListActivity.class);
                intent.putExtra("type", "ov");
                startActivity(intent);
            }
            else
            {
                dbHandler = new DbHandler(context);
                ovPersonAddressModelList = new ArrayList<>();
                ovPersonAddressModelList = dbHandler.getSingleOvTrap(ovi_trap_id);
                if(ovPersonAddressModelList.size() > 0){
                    errorText.setVisibility(View.VISIBLE);
                    errorText.setText("Ov trap id is already existing.");
                }
                else {
                    PersonModel personModel = new PersonModel(respond_name, Integer.parseInt(phone), "no");
                    dbHandler.insertDataPerson(personModel);
                    personList = dbHandler.getLastPerson();
                    int lastPersonId = personList.get(0).person_id;
                    AddressModel addressModel = new AddressModel(address_line1, address_line2, location_description, "no");
                    dbHandler.insertDataAddress(addressModel);
                    addressList = dbHandler.getLastAddresses();
                    int lastAddressId = addressList.get(0).address_id;
                    OvTrapModel ovTrapModel = new OvTrapModel(ovi_trap_id, trap_status, trap_position,
                            ovi_run_id, lastPersonId, lastAddressId, location_coordinates,
                            "no");
                    dbHandler.insertDataOvTrap(ovTrapModel);
                    Log.d("lastPersonId", Integer.toString(lastPersonId));
                    Log.d("lastAddressId", Integer.toString(lastAddressId));
                    Intent intent = new Intent(context, OvListActivity.class);
                    intent.putExtra("type", "ov");
                    startActivity(intent);
                }
            }
        }
    }
}
