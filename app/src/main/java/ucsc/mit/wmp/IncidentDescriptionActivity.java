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
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class IncidentDescriptionActivity extends AppCompatActivity {
    final Context context = this;

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
    EditText txtEdit_Description;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.incident_description);
        errorText = (TextView) findViewById(R.id.errorContainer);
        sharedpreferences = getSharedPreferences(IncidentDetails, Context.MODE_PRIVATE);
        picker = (TimePicker) findViewById(R.id.timePicker1);
        picker.setIs24HourView(true);
        calender = (CalendarView) findViewById(R.id.calender);
        incidentType = (Spinner) findViewById(R.id.spinnerIncidentType);
        incidentPriority = (Spinner) findViewById(R.id.spinnerPriority);
        txtEdit_Description = (EditText) findViewById(R.id.editTextDescription);

        String incident_type = sharedpreferences.getString("incident_type", "");
        String incident_priority = sharedpreferences.getString("incident_priority", "");
        String description = sharedpreferences.getString("description", "");
        String incident_date = sharedpreferences.getString("incident_date", "");
        String incident_time_hour = sharedpreferences.getString("incident_time_hour", "");
        String incident_time_minute = sharedpreferences.getString("incident_time_minute", "");
        if(incident_type.length()!=0 && incident_priority.length()!=0 && description.length()!=0 &&
                incident_date.length()!=0 && incident_time_hour.length()!=0 && incident_time_minute
                .length()!=0) {
            for (int i = 0; i < incidentType.getCount(); i++) {
                if (incidentType.getItemAtPosition(i).equals(incident_type)) {
                    incidentType.setSelection(i);
                    break;
                }
            }
            for (int i = 0; i < incidentPriority.getCount(); i++) {
                if (incidentPriority.getItemAtPosition(i).equals(incident_priority)) {
                    incidentPriority.setSelection(i);
                    break;
                }
            }
            txtEdit_Description.setText(description);
            String parts[] = incident_date.split("/");
            int year = Integer.parseInt(parts[0]);
            int month = Integer.parseInt(parts[1]);
            int day = Integer.parseInt(parts[2]);
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, day);
            long milliTime = calendar.getTimeInMillis();
            calender.setDate (milliTime, true, true);

            picker.setHour(Integer.valueOf(incident_time_hour));
            picker.setMinute(Integer.valueOf(incident_time_minute));

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void goIncidentLocation(View pView) {
        if ((incidentType.getSelectedItemPosition() == 0) || (incidentPriority.
                getSelectedItemPosition() == 0) || txtEdit_Description.getText()
                .toString().length() == 0) {
            errorText.setVisibility(View.VISIBLE);
            errorText.setText("Please fill all required fields.");
        } else {
            errorText.setVisibility(View.INVISIBLE);
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


