package ucsc.mit.wmp;

import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.CancellationTokenSource;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.Locale;

public class IncidentDescriptionActivity extends AppCompatActivity {
    final Context context = this;
    ProgressDialog progressDialog;
    EditText txtEdit_Description;
    public static final String TrapId = "TrapId" ;
    public static final String Email = "Email";
    public static final String Phone = "Phone";
    public static final String MyPREFERENCES = "MyPrefs" ;

    Button btnStartProgress;
    ProgressDialog progressBar;
    private int progressBarStatus = 0;
    private Handler progressBarHandler = new Handler();
    private long fileSize = 0;
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.incident_description);
        String des = getIntent().getStringExtra("type");
        EditText txtEdit_Des = (EditText) findViewById(R.id.editTextDescription);
        txtEdit_Des.setText(des);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        String trap = sharedpreferences.getString("TrapId", "defaultValue");
        String phones = sharedpreferences.getString("Phone", "defaultValue");
        String emails = sharedpreferences.getString("Email", "defaultValue");
        Log.d("trap id",trap);
        Log.d("phone",phones);
        Log.d("Email",emails);
    }
    public void goIncidentLocation(View pView) {
        Intent intent = new Intent(context, IncidentLocationActivity.class);
        startActivity(intent);
    }

    public void goBack(View pView) {

        //new MyAsyncTask(context).execute("10");
        //LocationService l1 = new LocationService();
        //l1.viewCoordinates();
    }

}


