package ucsc.mit.wmp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Handler;
import android.os.Message;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.CancellationTokenSource;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class IncidentDescriptionActivity extends AppCompatActivity {
    final Context context = this;
    ProgressDialog progressDialog;
    EditText txtEdit_Description;
    public static final String TrapId = "TrapId";
    public static final String Email = "Email";
    public static final String Phone = "Phone";

    public static final String Incident_type = "incident_type";
    public static final String Incident_priority = "incident_priority";
    public static final String Description = "description";
    public static final String Incident_date = "incident_date";
    public static final String Incident_time_hour = "incident_time_hour";
    public static final String Incident_time_minute = "incident_time_minute";
    public static final String IncidentDetails = "incidents";

    SharedPreferences sharedpreferences;
    CalendarView calender;
    TimePicker picker;
    Spinner incidentType;
    Spinner incidentPriority;
    TextView errorText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.incident_description);
        errorText = (TextView) findViewById(R.id.errorContainer);
        sharedpreferences = getSharedPreferences(IncidentDetails, Context.MODE_PRIVATE);
        String trap = sharedpreferences.getString("stakeholderName", "");
        String phones = sharedpreferences.getString("email", "");
        String emails = sharedpreferences.getString("phone", "");
        Log.d("stakeholderName", trap.toString());
        Log.d("email", emails.toString());
        Log.d("phone", phones.toString());
        picker = (TimePicker) findViewById(R.id.timePicker1);
        picker.setIs24HourView(true);
        incidentType = (Spinner) findViewById(R.id.spinnerIncidentType);
        incidentPriority = (Spinner) findViewById(R.id.spinnerPriority);
        txtEdit_Description = (EditText) findViewById(R.id.editTextDescription);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void goIncidentLocation(View pView) {
        if ((incidentType.getSelectedItemPosition()==0) || (incidentPriority.
                getSelectedItemPosition()==0) || txtEdit_Description.getText()
                .toString().length() == 0) {
            errorText.setVisibility(View.VISIBLE);
            errorText.setText("Please fill all required fields.");
        } else {
            errorText.setVisibility(View.INVISIBLE);
            calender = (CalendarView) findViewById(R.id.calender);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            String selectedDate = sdf.format(new Date(calender.getDate()));
            int hour = picker.getHour();
            int minute = picker.getMinute();
            Log.d("date", selectedDate);
            Log.d("hour", String.valueOf(hour));
            Log.d("minute", String.valueOf(minute));
            Log.d("incidenttype", incidentType.getSelectedItem().toString());
            Log.d("incidentpriority", incidentPriority.getSelectedItem().toString());
            SharedPreferences.Editor editor = sharedpreferences.edit();

            editor.putString(Incident_type, incidentType.getSelectedItem().toString());
            editor.putString(Incident_priority, incidentPriority.getSelectedItem().toString());
            editor.putString(Description, txtEdit_Description.getText().toString());
            editor.putString(Incident_date, selectedDate);
            editor.putString(Incident_time_hour, String.valueOf(hour));
            editor.putString(Incident_time_minute, String.valueOf(minute));
            editor.apply();
            Intent intent = new Intent(context, IncidentLocationActivity.class);
            startActivity(intent);
        }
    }

    public void goBack(View pView) {
        Intent intent = new Intent(context, IncidentContactActivity.class);
        startActivity(intent);
    }

}


