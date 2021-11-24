package ucsc.mit.wmp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainMenuActivity extends AppCompatActivity {
    final Context context = this;
    public static final String stakeholderName = "stakeholderName";
    public static final String email = "email";
    public static final String phone = "phone";
    public static final String incidentDetails = "incidents" ;

    public static final String Incident_type = "incident_type";
    public static final String Incident_priority = "incident_priority";
    public static final String Description = "description";
    public static final String Incident_date = "incident_date";
    public static final String Incident_time_hour = "incident_time_hour";
    public static final String Incident_time_minute = "incident_time_minute";

    public static final String Coordinates = "coordinates";
    public static final String Address = "address";
    public static final String LocationDescription = "location_description";
    public static final String Gnd = "gnd";
    public static final String Trapcode = "trapcode";

    TextView username_text;
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);
        sharedpreferences = getSharedPreferences(incidentDetails, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(stakeholderName,"");
        editor.putString(email, "");
        editor.putString(phone, "");
        editor.putString(Incident_type, "");
        editor.putString(Incident_priority, "");
        editor.putString(Description, "");
        editor.putString(Incident_date, "");
        editor.putString(Incident_time_hour, "");
        editor.putString(Incident_time_minute, "");
        editor.putString(Coordinates, "");
        editor.putString(Address, "");
        editor.putString(LocationDescription, "");
        editor.putString(Gnd, "");
        editor.putString(Trapcode, "");
        editor.apply();
        username_text = (TextView) findViewById(R.id.textViewUsername);
        sharedpreferences = getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        username_text.setText(sharedpreferences.getString("UserName", ""));

    }
    public void goOvListView(View pView) {
        Intent intent = new Intent(context, OvListActivity.class);
        intent.putExtra("type", "ov");
        startActivity(intent);
    }
    public void goBgListView(View pView) {
        Intent intent = new Intent(context, OvListActivity.class);
        intent.putExtra("type", "bg");
        startActivity(intent);
    }
    public void goMrcListView(View pView) {
        Intent intent = new Intent(context, OvListActivity.class);
        intent.putExtra("type", "mrc");
        startActivity(intent);
    }
   public void goIncidentView(View pView) {
        Intent intent = new Intent(context, IncidentContactActivity.class);
        startActivity(intent);
    }
    public void logout(View pView){
        Intent intent = new Intent(context, LoginActivityController.class);
        startActivity(intent);
    }
}
