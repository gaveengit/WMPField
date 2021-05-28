package ucsc.mit.wmp;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;

public class OvListActivity extends AppCompatActivity {
    final Context context = this;
    private String field_type;
    public static final String OviTrapId = "OviTrapId";
    public static final String BgTrapId = "BgTrapId";
    public static final String TrapStatus = "TrapStatus";
    public static final String TrapPosition = "TrapPosition";
    public static final String RespondName = "RespondName";
    public static final String LocationCoordinates = "LocationCoordinates";
    public static final String Phone = "Phone";
    public static final String AddressLine1 = "AddressLine1";
    public static final String AddressLine2 = "AddressLine2";
    public static final String LocationDescription = "LocationDescription";
    public static final String OviDetails = "OviDetails";
    public static final String BgDetails = "BgDetails";
    public static final String MrcDetails = "MrcDetails";
    public static final String MrcId = "MrcId";
    public static final String MrcStatus = "MrcStatus";
    public static final String OviRunId = "OviRunId";
    public static final String BgRunId = "BgRunId";
    public static final String MrcRunId = "MrcRunId";
    Spinner spinnerRuns;
    SharedPreferences sharedpreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.localeggs_listview);
        field_type = getIntent().getStringExtra("type");
        spinnerRuns = (Spinner) findViewById(R.id.spinnerRuns);
        Log.d("field_type", field_type);
        sharedpreferences = getSharedPreferences(OviDetails, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(OviTrapId,"");
        editor.putString(TrapStatus, "");
        editor.putString(TrapPosition, "");
        editor.putString(RespondName, "");
        editor.putString(LocationCoordinates, "");
        editor.putString(Phone, "");
        editor.putString(AddressLine1, "");
        editor.putString(AddressLine2, "");
        editor.putString(LocationDescription, "");
        editor.putString(OviRunId, "");
        editor.apply();
        sharedpreferences = getSharedPreferences(BgDetails, Context.MODE_PRIVATE);
        SharedPreferences.Editor editorBg = sharedpreferences.edit();
        editorBg.putString(BgTrapId,"");
        editorBg.putString(TrapStatus, "");
        editorBg.putString(TrapPosition, "");
        editorBg.putString(RespondName, "");
        editorBg.putString(LocationCoordinates, "");
        editorBg.putString(Phone, "");
        editorBg.putString(AddressLine1, "");
        editorBg.putString(AddressLine2, "");
        editorBg.putString(LocationDescription, "");
        editor.putString(BgRunId, "");
        editorBg.apply();
        sharedpreferences = getSharedPreferences(MrcDetails, Context.MODE_PRIVATE);
        SharedPreferences.Editor editorMrc = sharedpreferences.edit();
        editorMrc.putString(MrcId,"");
        editorMrc.putString(MrcStatus, "");
        editorMrc.putString(RespondName, "");
        editorMrc.putString(LocationCoordinates, "");
        editorMrc.putString(Phone, "");
        editorMrc.putString(AddressLine1, "");
        editorMrc.putString(AddressLine2, "");
        editorMrc.putString(LocationDescription, "");
        editor.putString(MrcRunId, "");
        editorMrc.apply();
    }

    public void goMapView(View pView) {
        Intent intent = new Intent(context, MapsActivity.class);
        startActivity(intent);
    }

    public void goNewOv(View pView) {
        if (field_type.equals("bg")) {
            sharedpreferences = getSharedPreferences(BgDetails, Context.MODE_PRIVATE);
            SharedPreferences.Editor editorBg = sharedpreferences.edit();
            editorBg.putString(BgRunId,spinnerRuns.getSelectedItem().toString());
            editorBg.apply();
            Intent intent = new Intent(context, AddBgMainActivity.class);
            startActivity(intent);
        }
        if (field_type.equals("ov")) {
            sharedpreferences = getSharedPreferences(OviDetails, Context.MODE_PRIVATE);
            SharedPreferences.Editor editorOvi = sharedpreferences.edit();
            editorOvi.putString(OviRunId,spinnerRuns.getSelectedItem().toString());
            editorOvi.apply();
            Intent intent = new Intent(context, AddOvMainActivity.class);
            startActivity(intent);
        }
        if (field_type.equals("mrc")) {
            sharedpreferences = getSharedPreferences(MrcDetails, Context.MODE_PRIVATE);
            SharedPreferences.Editor editorMrc = sharedpreferences.edit();
            editorMrc.putString(MrcRunId,spinnerRuns.getSelectedItem().toString());
            editorMrc.apply();
            Intent intent = new Intent(context, AddMrcMainActivity.class);
            startActivity(intent);
        }
    }

    public void goIndividualOv(View v) {

        if (field_type.equals("ov")) {
            Intent intent = new Intent(context, AddOvMainActivity.class);
            intent.putExtra("TrapId", v.getTag().toString());
            startActivity(intent);
        }

        if (field_type.equals("bg")) {
            Intent intent = new Intent(context, AddBgMainActivity.class);
            intent.putExtra("TrapId", v.getTag().toString());
            startActivity(intent);
        }
        if (field_type.equals("mrc")) {
            Intent intent = new Intent(context, AddMrcMainActivity.class);
            intent.putExtra("TrapId", v.getTag().toString());
            startActivity(intent);
        }
    }
    public void goMainMenu(View v)
    {
        Intent intent = new Intent(context, MainMenuActivity.class);
        startActivity(intent);
    }

    /*

    public void goViewOv(View pView) {
        Intent intent = new Intent(context, AddOvMainActivity.class);
        startActivity(intent);
    }



    */
}
