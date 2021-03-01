package ucsc.mit.wmp;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class OvListActivity extends AppCompatActivity {
    final Context context = this;
    private String field_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.localeggs_listview);
        field_type = getIntent().getStringExtra("type");
        Log.d("field_type", field_type);
        //RelativeLayout list_container = (RelativeLayout) findViewById(R.id.list_container);
    }

    public void goMapView(View pView) {
        Intent intent = new Intent(context, MapsActivity.class);
        startActivity(intent);
    }

    public void goNewOv(View pView) {
        if (field_type.equals("bg")) {
            Intent intent = new Intent(context, AddBgMainActivity.class);
            startActivity(intent);
        }
        if (field_type.equals("ov")) {
            Intent intent = new Intent(context, AddOvMainActivity.class);
            startActivity(intent);
        }
        if (field_type.equals("mrc")) {
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

    /*

    public void goViewOv(View pView) {
        Intent intent = new Intent(context, AddOvMainActivity.class);
        startActivity(intent);
    }



    */
}
