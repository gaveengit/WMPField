package ucsc.mit.wmp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.CancellationTokenSource;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONObject;

import java.io.IOException;
import java.util.Locale;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class IncidentLocationActivity extends AppCompatActivity {
    final Context context = this;
    private static final int PRIORITY_HIGH_ACCURACY = 100;
    FusedLocationProviderClient fusedLocationProviderClient;
    EditText EditTextCoordinates;
    EditText EditTextAddress;
    EditText EditTextLocationDescription;
    EditText EditTextGnd;
    EditText EditTextTrapCode;
    TextView errorText;
    TextView username_text;
    Button button;

    public static final String Coordinates = "coordinates";
    public static final String Address = "address";
    public static final String LocationDescription = "location_description";
    public static final String Gnd = "gnd";
    public static final String Trapcode = "trapcode";
    public static final String IncidentDetails = "incidents";
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.incident_location);
        errorText = (TextView) findViewById(R.id.errorContainer);
        sharedpreferences = getSharedPreferences(IncidentDetails, Context.MODE_PRIVATE);
        EditTextCoordinates= (EditText) findViewById(R.id.editTextCoordinates);
        EditTextAddress= (EditText) findViewById(R.id.editTextAddress);
        EditTextLocationDescription= (EditText) findViewById(R.id.editTextLocationDescription);
        EditTextGnd= (EditText) findViewById(R.id.editTextGnd);
        EditTextTrapCode= (EditText) findViewById(R.id.editTextTrapCode);
        button = (Button) findViewById(R.id.nextBtn);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        String pre_coordinates = sharedpreferences.getString("coordinates", "");
        String pre_address = sharedpreferences.getString("address", "");
        String pre_location_description = sharedpreferences.getString("location_description", "");
        String pre_gnd = sharedpreferences.getString("gnd", "");
        String pre_trapcode = sharedpreferences.getString("trapcode", "");

        if(pre_address.length()!=0)
        {
            EditTextCoordinates.setText(pre_coordinates);
            EditTextAddress.setText(pre_address);
            EditTextLocationDescription.setText(pre_location_description);
            EditTextGnd.setText(pre_gnd);
            EditTextTrapCode.setText(pre_trapcode);
        }
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (EditTextAddress.getText().toString().length() == 0) {
                    EditTextAddress.setError("Full address is required.");

                } else {
                    errorText.setVisibility(View.INVISIBLE);
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString(Coordinates, EditTextCoordinates.getText().toString());
                    editor.putString(Address, EditTextAddress.getText().toString());
                    editor.putString(LocationDescription, EditTextLocationDescription.getText().toString());
                    editor.putString(Gnd, EditTextGnd.getText().toString());
                    editor.putString(Trapcode, EditTextTrapCode.getText().toString());
                    editor.apply();

                    String stakeholderName = sharedpreferences.getString("stakeholderName", "");
                    String email = sharedpreferences.getString("email", "");
                    String phone = sharedpreferences.getString("phone", "");

                    String incident_type = sharedpreferences.getString("incident_type", "");
                    String incident_priority = sharedpreferences.getString("incident_priority", "");
                    String description = sharedpreferences.getString("description", "");
                    String incident_date = sharedpreferences.getString("incident_date", "");
                    String incident_time_hour = sharedpreferences.getString("incident_time_hour", "");
                    String incident_time_minute = sharedpreferences.getString("incident_time_minute", "");

                    String coordinates = sharedpreferences.getString("coordinates", "");
                    String address = sharedpreferences.getString("address", "");
                    String location_description = sharedpreferences.getString("location_description", "");
                    String gnd = sharedpreferences.getString("gnd", "");
                    String trapcode = sharedpreferences.getString("trapcode", "");

                    Log.d("check-stakeholderName", stakeholderName);
                    Log.d("check-email", email);
                    Log.d("check-phone", phone);
                    Log.d("check-incident_type", incident_type);
                    Log.d("check-incident_priority", incident_priority);
                    Log.d("check-description", description);
                    Log.d("check-incident_date", incident_date);
                    Log.d("check-incident_time_ho", incident_time_hour);
                    Log.d("check-incident_time_min", incident_time_minute);
                    Log.d("check-coordinates", coordinates);
                    Log.d("check-address", address);
                    Log.d("check-location_des", location_description);
                    Log.d("check-gnd", gnd);
                    Log.d("check-trapcode", trapcode);

                    if(gnd.equals("")){
                        gnd = "NULL";
                    }
                    if(trapcode.equals("")){
                        trapcode = "NULL";
                    }
                    if(coordinates.equals("")){
                        coordinates = "NULL";
                    }
                    if(email.equals("")){
                        email = "NULL";
                    }
                    if(location_description.equals("")){
                        location_description = "NULL";
                    }


                    String append_url = stakeholderName + "/" + email + "/" + phone + "/" + incident_type + "/" + incident_priority + "/" + description + "/" + incident_date.replace("/","-") + "/" + incident_time_hour + ":" + incident_time_minute + "/" + coordinates + "/" + address + "/" + location_description + "/" + gnd + "/" + trapcode + "/" + "pending";
                    Log.d("url",append_url);
                    AsyncTask asyncTask = new AsyncTask() {

                        @Override
                        protected Object doInBackground(Object[] objects) {
                            OkHttpClient client = new OkHttpClient();
                            Request request = new Request.Builder().url("http://192.168.8.101/api/index.php/api/ApiIncidentController/storeIncident/"+append_url).build();
                            Response response = null;
                            try {
                                response = client.newCall(request).execute();
                                return response.body().string();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            return null;
                        }

                        @Override
                        protected void onPostExecute(Object o) {

                            Log.d("response",o.toString());
                            try {

                                JSONObject obj = new JSONObject(o.toString());

                                Log.d("json_response", String.valueOf(obj.getBoolean("status")));
                                if(String.valueOf(obj.getBoolean("status")).equals("true")) {
                                    Toast.makeText(context, "Field incident has been added successfully.",
                                            Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(context, MainMenuActivity.class);
                                    startActivity(intent);
                                }
                                else{
                                    Toast.makeText(context, "Error in adding field incident. Please try again.",
                                            Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(context, MainMenuActivity.class);
                                    startActivity(intent);
                                }

                            } catch (Throwable t) {

                            }
                        }
                    }.execute();
                }
            }

        });
        username_text = (TextView) findViewById(R.id.textViewUsername);
        sharedpreferences = getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        username_text.setText(sharedpreferences.getString("UserName", ""));

    }

    public void viewCoordinates(View pView) {
        if (ActivityCompat.checkSelfPermission(IncidentLocationActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            getLocation();
        } else {
            ActivityCompat.requestPermissions(IncidentLocationActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }
    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        //fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
        CancellationTokenSource cts = new CancellationTokenSource();

        fusedLocationProviderClient.getCurrentLocation(PRIORITY_HIGH_ACCURACY, cts.getToken()).addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                // initialize location
                Location location = task.getResult();
                if (location != null) {
                    //initialize geocoder
                    Geocoder geocoder = new Geocoder(IncidentLocationActivity.this, Locale.getDefault());
                    Double lat = location.getLatitude();
                    Double lon = location.getLongitude();
                    Log.d("lat", String.valueOf(lat));
                    Log.d("lon", String.valueOf(lon));
                    String coords = String.valueOf(lat) + "," + String.valueOf(lon);
                    EditTextCoordinates.setText(coords);
                } else {
                    Log.d("lat", "null");
                    Log.d("lon", "null");
                }
            }
        });
    }

    public void goIncidentDescription(View pView) {
        Intent intent = new Intent(context, IncidentDescriptionActivity.class);
        startActivity(intent);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(Coordinates, EditTextCoordinates.getText().toString());
        editor.putString(Address, EditTextAddress.getText().toString());
        editor.putString(LocationDescription, EditTextLocationDescription.getText().toString());
        editor.putString(Gnd, EditTextGnd.getText().toString());
        editor.putString(Trapcode, EditTextTrapCode.getText().toString());
        editor.apply();
    }

    public void submitIncident(View pView) {
        if (EditTextAddress.getText().toString().length() == 0) {
            errorText.setVisibility(View.VISIBLE);
            errorText.setText("Please fill all required fields.");

        } else {
            errorText.setVisibility(View.INVISIBLE);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString(Coordinates, EditTextCoordinates.getText().toString());
            editor.putString(Address, EditTextAddress.getText().toString());
            editor.putString(LocationDescription, EditTextLocationDescription.getText().toString());
            editor.putString(Gnd, EditTextGnd.getText().toString());
            editor.putString(Trapcode, EditTextTrapCode.getText().toString());
            editor.apply();

            String stakeholderName = sharedpreferences.getString("stakeholderName", "");
            String email = sharedpreferences.getString("email", "");
            String phone = sharedpreferences.getString("phone", "");

            String incident_type = sharedpreferences.getString("incident_type", "");
            String incident_priority = sharedpreferences.getString("incident_priority", "");
            String description = sharedpreferences.getString("description", "");
            String incident_date = sharedpreferences.getString("incident_date", "");
            String incident_time_hour = sharedpreferences.getString("incident_time_hour", "");
            String incident_time_minute = sharedpreferences.getString("incident_time_minute", "");

            String coordinates = sharedpreferences.getString("coordinates", "");
            String address = sharedpreferences.getString("address", "");
            String location_description = sharedpreferences.getString("location_description", "");
            String gnd = sharedpreferences.getString("gnd", "");
            String trapcode = sharedpreferences.getString("trapcode", "");

            Log.d("check-stakeholderName", stakeholderName);
            Log.d("check-email", email);
            Log.d("check-phone", phone);
            Log.d("check-incident_type", incident_type);
            Log.d("check-incident_priority", incident_priority);
            Log.d("check-description", description);
            Log.d("check-incident_date", incident_date);
            Log.d("check-incident_time_ho", incident_time_hour);
            Log.d("check-incident_time_min", incident_time_minute);
            Log.d("check-coordinates", coordinates);
            Log.d("check-address", address);
            Log.d("check-location_des", location_description);
            Log.d("check-gnd", gnd);
            Log.d("check-trapcode", trapcode);
        }
    }
    public void logout(View pView){
        Intent intent = new Intent(context, LoginActivityController.class);
        startActivity(intent);
    }
}