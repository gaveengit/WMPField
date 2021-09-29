package ucsc.mit.wmp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.location.FusedLocationProviderClient;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.CancellationTokenSource;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OviDataSyncActivity extends AppCompatActivity {
    final Context context = this;
    private DbHandler dbHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ovi_sync);

        AsyncTask asyncTaskGetOviService = new AsyncTask() {

            @Override
            protected Object doInBackground(Object[] objects) {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder().url("http://192.168.8.100/api/index.php/api/OviServiceController/indexOviService").build();
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

                Log.d("response", o.toString());
                try {
                    JSONArray jsonArr = new JSONArray(o.toString());
                    long sync_status = -1;
                    for (int i = 0; i < jsonArr.length(); i++) {
                        JSONObject jsonObj = jsonArr.getJSONObject(i);
                        String ovi_run_id = jsonObj.getString("ovi_run_id");
                        String trap_id = jsonObj.getString("trap_id");
                        String trap_position = jsonObj.getString("trap_position");
                        String coordinates = jsonObj.getString("coordinates");
                        String add_line1 = jsonObj.getString("add_line1");
                        String add_line2 = jsonObj.getString("add_line2");
                        String location_description = jsonObj.getString("location_description");
                        String full_name = jsonObj.getString("full_name");
                        String contact_number = jsonObj.getString("contact_number");

                        OviServiceModel oviService = new OviServiceModel(ovi_run_id, trap_id, trap_position, coordinates,
                                add_line1, add_line2, location_description, full_name, contact_number);

                        sync_status = dbHandler.insertDataOviService(oviService);
                        Log.d("sync_status", String.valueOf(sync_status));
                        Log.d("obj", jsonObj.getString("trap_id"));
                    }
                    if (sync_status != -1) {
                        //Toast.makeText(context, "Data synchronization has been successfull.",
                        //Toast.LENGTH_LONG).show();
                        //Intent intent = new Intent(context, OvListActivity.class);
                        //intent.putExtra("type", "ov");
                        //startActivity(intent);
                    } else {
                        //Toast.makeText(context, "Error in synchronization. Please try again.",
                        //Toast.LENGTH_LONG).show();
                        //Intent intent = new Intent(context, OvListActivity.class);
                        //intent.putExtra("type", "ov");
                        // startActivity(intent);
                    }
                } catch (Throwable t) {

                }
            }
        }.execute();


        AsyncTask asyncTaskGetOviCollection = new AsyncTask() {

            @Override
            protected Object doInBackground(Object[] objects) {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder().url("http://192.168.8.100/api/index.php/api/OviCollectionController/indexOviCollection").build();
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

                Log.d("response", o.toString());
                try {
                    JSONArray jsonArr = new JSONArray(o.toString());
                    long sync_status = -1;
                    for (int i = 0; i < jsonArr.length(); i++) {
                        JSONObject jsonObj = jsonArr.getJSONObject(i);
                        String ovi_run_id = jsonObj.getString("ovi_run_id");
                        String trap_id = jsonObj.getString("trap_id");
                        String trap_position = jsonObj.getString("trap_position");
                        String coordinates = jsonObj.getString("coordinates");
                        String add_line1 = jsonObj.getString("add_line1");
                        String add_line2 = jsonObj.getString("add_line2");
                        String location_description = jsonObj.getString("location_description");
                        String full_name = jsonObj.getString("full_name");
                        String contact_number = jsonObj.getString("contact_number");

                        OviCollectionModel oviCollection = new OviCollectionModel(ovi_run_id, trap_id, trap_position, coordinates,
                                add_line1, add_line2, location_description, full_name, contact_number);

                        sync_status = dbHandler.insertDataOviCollection(oviCollection);
                        Log.d("sync_status", String.valueOf(sync_status));
                        Log.d("obj", jsonObj.getString("trap_id"));
                    }
                    if (sync_status != -1) {
                        Toast.makeText(context, "Data synchronization has been successfull.",
                                Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(context, OvListActivity.class);
                        intent.putExtra("type", "ov");
                        startActivity(intent);
                    } else {
                        Toast.makeText(context, "Error in synchronization. Please try again.",
                                Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(context, OvListActivity.class);
                        intent.putExtra("type", "ov");
                        startActivity(intent);
                    }
                } catch (Throwable t) {

                }
            }
        }.execute();
    }

}
