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

import java.util.ArrayList;
import java.util.List;

public class AddOvAdditionalActivity extends AppCompatActivity {
    final Context context = this;
    private DbHandler dbHandler;
    public static final String OviTrapId = "OviTrapId";
    public static final String OriginalOviId = "OriginalOviId";
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
    private List<OvTrapModel> ovPersonAddressModelList;
    String form_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_ov_additional);
        form_type = getIntent().getStringExtra("form-type");
        Log.d("form-type", form_type);
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
        int error_flag = 0;
        if (EditTextAddressLine1.getText().toString().length() == 0) {
            EditTextAddressLine1.setError("Address line1 is required.");
            error_flag = 1;
        }
        if (EditTextAddressLine2.getText().toString().length() == 0) {
            EditTextAddressLine2.setError("Address line2 is required.");
            error_flag = 1;
        }
        if (EditTextLocationDescription.getText().toString().length() == 0) {
            EditTextLocationDescription.setError("Location description is required.");
            error_flag = 1;
        }

        if (EditTextPhone.getText().toString().length() == 0) {
            EditTextPhone.setError("Phone no is required.");
            error_flag = 1;
        }
        if (EditTextPhone.getText().toString().length() > 0 && EditTextPhone.getText().toString().length() != 10) {
            EditTextPhone.setError("Phone no should have 10 digits.");
            error_flag = 1;
        }
        if (error_flag == 0) {
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
            String ovi_trap_id_original = sharedpreferences.getString(OriginalOviId, "");
            String trap_status = sharedpreferences.getString(TrapStatus, "");
            String trap_position = sharedpreferences.getString(TrapPosition, "");
            String respond_name = sharedpreferences.getString(RespondName, "");
            String location_coordinates = sharedpreferences.getString(LocationCoordinates, "");
            String ovi_run_id = "Dry Run";
            String phone = sharedpreferences.getString(Phone, "");
            String address_line1 = sharedpreferences.getString(AddressLine1, "");
            String address_line2 = sharedpreferences.getString(AddressLine2, "");
            String location_description = sharedpreferences.getString(LocationDescription, "");
            if (form_type.equals("edit-new")) {
                OvTrapModel ovTrapModel = new OvTrapModel(ovi_trap_id, trap_status, trap_position,
                        ovi_run_id, respond_name, phone, address_line1, address_line2, location_description, location_coordinates);
                int flag = dbHandler.updateSingleOvTrap(ovTrapModel, ovi_trap_id_original);
                if (flag != -1) {
                    Toast.makeText(context, "OVI has been updated successfully.",
                            Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(context, "Failure in updating OVI trap. Please try again..",
                            Toast.LENGTH_LONG).show();
                }
                Intent intent = new Intent(context, MapsActivity.class);
                intent.putExtra("run_name", ovi_run_id);
                intent.putExtra("field_type", "ov");
                startActivity(intent);

            } else {
                dbHandler = new DbHandler(context);
                long flag_insert_ov = -1;

                OvTrapModel ovTrapModel = new OvTrapModel(ovi_trap_id, trap_status, trap_position,
                        ovi_run_id, respond_name, phone, address_line1, address_line2, location_description, location_coordinates);
                flag_insert_ov = dbHandler.insertDataOvTrap(ovTrapModel);
                if (flag_insert_ov != -1) {
                    Toast.makeText(context, "OVI trap has been added successfully.",
                            Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(context, MapsActivity.class);
                    intent.putExtra("run_name", ovi_run_id);
                    intent.putExtra("field_type", "ov");
                    startActivity(intent);
                } else {
                    Toast.makeText(context, "Failure in adding OVI trap. Please try again.",
                            Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(context, MapsActivity.class);
                    intent.putExtra("run_name", ovi_run_id);
                    intent.putExtra("field_type", "ov");
                    startActivity(intent);
                }

            }
        }
    }
}
