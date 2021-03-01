package ucsc.mit.wmp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class IncidentContactActivity extends AppCompatActivity {
    final Context context = this;
    public static final String TrapId = "TrapId" ;
    public static final String Email = "Email";
    public static final String Phone = "Phone";
    public static final String MyPREFERENCES = "MyPrefs" ;
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.incident_contact);
    }
    public void goIncidentDescription(View pView) {
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();

        editor.putString(TrapId, "trap1");
        editor.putString(Phone, "0342242323");
        editor.putString(Email, "myemail");
        editor.apply();
        Intent intent = new Intent(context, IncidentDescriptionActivity.class);
        startActivity(intent);
    }
}


