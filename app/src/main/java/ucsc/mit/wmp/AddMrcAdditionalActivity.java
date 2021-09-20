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
    private List<MrcModel> mrcPersonAddressModelList;
    String form_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_mrc_additional);
        form_type = getIntent().getStringExtra("form-type");
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

    public void goMrcMain(View v) {
        sharedpreferences = getSharedPreferences(MrcDetails, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(Phone, EditTextPhone.getText().toString());
        editor.putString(AddressLine1, EditTextAddressLine1.getText().toString());
        editor.putString(AddressLine2, EditTextAddressLine2.getText().toString());
        editor.putString(LocationDescription, EditTextLocationDescription.getText().toString());
        editor.apply();
        Intent intent = new Intent(context, AddMrcMainActivity.class);
        intent.putExtra("form-type", form_type);
        startActivity(intent);
    }

    public void SubmitMrc(View v) {
        if (EditTextAddressLine1.getText().toString().length() == 0 || EditTextAddressLine2.getText().toString().
                length()
                == 0) {
            errorText.setVisibility(View.VISIBLE);
            errorText.setText("Please fill all required fields.");
        } else {
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
            String respond_phone = sharedpreferences.getString(Phone, "");
            String location_coordinates = sharedpreferences.getString(LocationCoordinates, "");
            String mrc_run_id = "Dry Run";
            String phone = sharedpreferences.getString(Phone, "");
            String address_line1 = sharedpreferences.getString(AddressLine1, "");
            String address_line2 = sharedpreferences.getString(AddressLine2, "");
            String location_description = sharedpreferences.getString(LocationDescription, "");
            if (form_type.equals("edit-new")) {
                //PersonModel personModel = new PersonModel(respond_name, Integer.parseInt(phone), "no");
                //personModel.setPerson_id(person_id);
                //dbHandler.updateSinglePerson(personModel);
                //AddressModel addressModel = new AddressModel(address_line1, address_line2, location_description, "no");
                //addressModel.setAddress_id(address_id);
               // dbHandler.updateSingleAddress(addressModel);
                MrcModel mrcModel = new MrcModel(mrc_id, mrc_status,
                        mrc_run_id,respond_name, respond_phone, address_line1,address_line2,location_description,location_coordinates);
                int flag = dbHandler.updateSingleMrc(mrcModel);
                Toast.makeText(context, "OVI collection has been added successfully.",
                        Toast.LENGTH_LONG).show();
                Intent intent = new Intent(context, OvListActivity.class);
                intent.putExtra("type", "mrc");
                startActivity(intent);
            } else {
                dbHandler = new DbHandler(context);
                mrcPersonAddressModelList = new ArrayList<>();
                mrcPersonAddressModelList = dbHandler.getSingleMrcPersonAddress(mrc_id);
                if(mrcPersonAddressModelList.size() > 0){
                    errorText.setVisibility(View.VISIBLE);
                    errorText.setText("MRC identifier is already existing.");
                    Toast.makeText(context, "MRC identifier is already existing.",
                            Toast.LENGTH_LONG).show();
                }
                else {
                    MrcModel mrcModel = new MrcModel(mrc_id, mrc_status,
                            mrc_run_id, respond_name, respond_phone, address_line1, address_line2, location_description, location_coordinates);
                    long flag = dbHandler.insertDataMrc(mrcModel);
                    if(flag!=-1) {
                        Toast.makeText(context, "MRC has been added successfully.",
                                Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(context, OvListActivity.class);
                        intent.putExtra("type", "mrc");
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(context, "Failure in adding MRC. Please try again..",
                                Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(context, OvListActivity.class);
                        intent.putExtra("type", "mrc");
                        startActivity(intent);
                    }
                }

            }
        }
    }
}
