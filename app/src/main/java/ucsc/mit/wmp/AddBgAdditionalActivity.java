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

public class AddBgAdditionalActivity extends AppCompatActivity {
    final Context context = this;
    private DbHandler dbHandler;
    public static final String BgTrapId = "BgTrapId";
    public static final String OriginalBgId = "OriginalBgId";
    public static final String TrapStatus = "TrapStatus";
    public static final String TrapPosition = "TrapPosition";
    public static final String RespondName = "RespondName";
    public static final String LocationCoordinates = "LocationCoordinates";
    public static final String BgRunId = "BgRunId";
    public static final String Phone = "Phone";
    public static final String AddressLine1 = "AddressLine1";
    public static final String AddressLine2 = "AddressLine2";
    public static final String LocationDescription = "LocationDescription";
    public static final String BgDetails = "BgDetails";
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
    private List<BgTrapModel> bgPersonAddressModelList;
    String form_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_bg_additional);
        form_type = getIntent().getStringExtra("form-type");
        personList = new ArrayList<>();
        addressList = new ArrayList<>();
        EditTextPhone = (EditText) findViewById(R.id.editTextPhone);
        EditTextAddressLine1 = (EditText) findViewById(R.id.editTextAdd1);
        EditTextAddressLine2 = (EditText) findViewById(R.id.editTextAdd2);
        EditTextLocationDescription = (EditText) findViewById(R.id.editTextLocationDescription);
        errorText = (TextView) findViewById(R.id.errorContainer);
        sharedpreferences = getSharedPreferences(BgDetails, Context.MODE_PRIVATE);
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
        sharedpreferences = getSharedPreferences(BgDetails, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(Phone, EditTextPhone.getText().toString());
        editor.putString(AddressLine1, EditTextAddressLine1.getText().toString());
        editor.putString(AddressLine2, EditTextAddressLine2.getText().toString());
        editor.putString(LocationDescription, EditTextLocationDescription.getText().toString());
        editor.apply();
        Intent intent = new Intent(context, AddBgMainActivity.class);
        intent.putExtra("form-type", form_type);
        startActivity(intent);
    }

    public void submitBg(View v) {
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

            sharedpreferences = getSharedPreferences(BgDetails, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString(Phone, EditTextPhone.getText().toString());
            editor.putString(AddressLine1, EditTextAddressLine1.getText().toString());
            editor.putString(AddressLine2, EditTextAddressLine2.getText().toString());
            editor.putString(LocationDescription, EditTextLocationDescription.getText().toString());
            editor.apply();

            dbHandler = new DbHandler(context);
            sharedpreferences = getSharedPreferences(BgDetails, Context.MODE_PRIVATE);
            String bg_trap_id = sharedpreferences.getString(BgTrapId, "");
            String bg_trap_id_original = sharedpreferences.getString(OriginalBgId, "");
            String trap_status = sharedpreferences.getString(TrapStatus, "");
            String trap_position = sharedpreferences.getString(TrapPosition, "");
            String respond_name = sharedpreferences.getString(RespondName, "");
            String location_coordinates = sharedpreferences.getString(LocationCoordinates, "");
            String bg_run_id = "Dry Run";
            String phone = sharedpreferences.getString(Phone, "");
            String address_line1 = sharedpreferences.getString(AddressLine1, "");
            String address_line2 = sharedpreferences.getString(AddressLine2, "");
            String location_description = sharedpreferences.getString(LocationDescription, "");

            if (form_type.equals("edit-new")) {
                BgTrapModel bgTrapModel = new BgTrapModel(bg_trap_id, trap_status, trap_position,
                        bg_run_id, respond_name, phone, address_line1, address_line2, location_description, location_coordinates);
                int flag = dbHandler.updateSingleBgTrap(bgTrapModel, bg_trap_id_original);
                if (flag != -1) {
                    Toast.makeText(context, "BG trap has been updated successfully.",
                            Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(context, "Failure in updating BG trap. Please try again.",
                            Toast.LENGTH_LONG).show();
                }
                Intent intent = new Intent(context, MapsActivity.class);
                intent.putExtra("run_name", bg_run_id);
                intent.putExtra("field_type", "bg");
                startActivity(intent);

            } else {
                BgTrapModel bgTrapModel = new BgTrapModel(bg_trap_id, trap_status, trap_position,
                        bg_run_id, respond_name, phone, address_line1, address_line2, location_description, location_coordinates);
                long flag = dbHandler.insertDataBgTrap(bgTrapModel);
                Log.d("flag", String.valueOf(flag));
                if (flag != -1) {
                    Toast.makeText(context, "BG trap has been added successfully.",
                            Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(context, MapsActivity.class);
                    intent.putExtra("run_name", bg_run_id);
                    intent.putExtra("field_type", "bg");
                    startActivity(intent);
                } else {
                    Toast.makeText(context, "Failure in adding BG trap. Please try again..",
                            Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(context, MapsActivity.class);
                    intent.putExtra("run_name", bg_run_id);
                    intent.putExtra("field_type", "bg");
                    startActivity(intent);
                }

            }
        }
    }
}


