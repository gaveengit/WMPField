package ucsc.mit.wmp;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

import java.util.ArrayList;
import java.util.List;

public class OvListActivity extends AppCompatActivity {
    final Context context = this;
    private DbHandler dbHandler;
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
    private List<OvTrapModel> ovModelList;
    private List<OvPersonAddressModel> ovPersonAddressModelList;
    private List<BgPersonAddressModel> bgPersonAddressModelList;
    private List<MrcPersonAddressModel> mrcPersonAddressModelList;
    private List<String> ovList;
    private List<BgTrapModel> bgModelList;
    private List<String> bgList;
    private List<MrcModel> mrcModelList;
    private List<String> mrcList;


    Spinner spinnerRuns;
    SharedPreferences sharedpreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.localeggs_listview);
        LinearLayout l1 = (LinearLayout) findViewById(R.id.contentLinearLayout);
        l1.removeAllViews();
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

        String run_name = spinnerRuns.getSelectedItem().toString();
        Log.d("field_type",field_type);
        Log.d("run_name",run_name);
        if (field_type.equals("ov")) {
            dbHandler = new DbHandler(context);
            ovModelList = new ArrayList<>();
            ovList = new ArrayList<>();
            ovModelList = dbHandler.getSingleOvTrap(run_name, field_type);
            Log.d("size-ov",Integer.toString(ovModelList.size()));
            for (int i = 0; i < ovModelList.size(); ++i) {
                Log.d("test-ov_trap_id",ovModelList.get(i).ov_trap_id.toString());
                Log.d("test-trap_status",ovModelList.get(i).trap_status.toString());
                Log.d("test-position",ovModelList.get(i).position.toString());
                Log.d("test-run_name",ovModelList.get(i).run_name.toString());
                Log.d("test-person_id",Integer.toString(ovModelList.get(i).person_id));
                Log.d("test-address_id",Integer.toString(ovModelList.get(i).address_id));
                Log.d("test-coordinates",ovModelList.get(i).coordinates.toString());
                Log.d("test-exist",ovModelList.get(i).exist_in_remote_server.toString());
               RelativeLayout rl1 = new RelativeLayout(this);
               rl1.setId(i);
               rl1.setLayoutParams(new ViewGroup.LayoutParams(
                       ViewGroup.LayoutParams.MATCH_PARENT,
                       ViewGroup.LayoutParams.WRAP_CONTENT));
               rl1.setBackground(ContextCompat.getDrawable(context, R.drawable.vertical_single_border));
               rl1.setPadding(15,10,15,10);
               rl1.setTag(ovModelList.get(i).ov_trap_id.toString());
                rl1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        goIndividualOv(v);
                    }
                });
                l1.addView(rl1);
                l1.invalidate();
               LinearLayout l2 = new LinearLayout(this);
               l2.setLayoutParams(new ViewGroup.LayoutParams(
                       ViewGroup.LayoutParams.WRAP_CONTENT,
                       ViewGroup.LayoutParams.WRAP_CONTENT));
               l2.setTag(ovModelList.get(i).ov_trap_id.toString());
               l2.setOrientation(LinearLayout.VERTICAL);
               l2.setPadding(0,10,0,10);
               rl1.addView(l2);
               rl1.invalidate();
               TextView tv1 = new TextView(this);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.setMargins(5,0,0,0);
                tv1.setLayoutParams(params);
                tv1.setBackgroundColor(Color.TRANSPARENT);
                tv1.setTag(ovModelList.get(i).ov_trap_id.toString());
                tv1.setText(ovModelList.get(i).ov_trap_id.toString());
                tv1.setTextColor(Color.BLACK);
                tv1.setTextSize(20);
                l2.addView(tv1);
                l2.invalidate();
                TextView tv2 = new TextView(this);
                tv2.setPadding(0,5,10,0);
                tv2.setLayoutParams(params);
                tv2.setTag(ovModelList.get(i).ov_trap_id.toString());
                tv2.setBackgroundColor(Color.TRANSPARENT);
                tv2.setTextSize(16);
                //RelativeLayout.LayoutParams params3 = (RelativeLayout.LayoutParams) tv2.getLayoutParams();
                //params3.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);

                tv2.setBackground(ContextCompat.getDrawable(context, R.drawable.substract) );
                rl1.addView(tv2);
                rl1.invalidate();
                LayoutParams layoutarams;
                layoutarams = (LayoutParams)tv2.getLayoutParams();
                layoutarams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                tv2.setLayoutParams(layoutarams);
            }
        }
        if (field_type.equals("bg")) {
            dbHandler = new DbHandler(context);
            bgModelList = new ArrayList<>();
            bgList = new ArrayList<>();
            bgModelList = dbHandler.getSingleBgTrap(run_name, field_type);
            //Log.d("size-ov",Integer.toString(ovModelList.size()));
            for (int i = 0; i < bgModelList.size(); ++i) {
                Log.d("test-bg_trap_id",bgModelList.get(i).bg_trap_id.toString());
                Log.d("test-trap_status",bgModelList.get(i).trap_status.toString());
                Log.d("test-position",bgModelList.get(i).position.toString());
                Log.d("test-run_name",bgModelList.get(i).run_name.toString());
                Log.d("test-person_id",Integer.toString(bgModelList.get(i).person_id));
                Log.d("test-address_id",Integer.toString(bgModelList.get(i).address_id));
                Log.d("test-coordinates",bgModelList.get(i).coordinates.toString());
                Log.d("test-exist",bgModelList.get(i).exist_in_remote_server.toString());

                RelativeLayout rl1 = new RelativeLayout(this);
                rl1.setId(i);
                rl1.setLayoutParams(new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));
                rl1.setBackground(ContextCompat.getDrawable(context, R.drawable.vertical_single_border));
                rl1.setPadding(15,10,15,10);
                rl1.setTag(bgModelList.get(i).bg_trap_id.toString());
                rl1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        goIndividualOv(v);
                    }
                });
                l1.addView(rl1);
                l1.invalidate();
                LinearLayout l2 = new LinearLayout(this);
                l2.setLayoutParams(new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));
                l2.setTag(bgModelList.get(i).bg_trap_id.toString());
                l2.setOrientation(LinearLayout.VERTICAL);
                l2.setPadding(0,10,0,10);
                rl1.addView(l2);
                rl1.invalidate();
                TextView tv1 = new TextView(this);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.setMargins(5,0,0,0);
                tv1.setLayoutParams(params);
                tv1.setBackgroundColor(Color.TRANSPARENT);
                tv1.setTag(bgModelList.get(i).bg_trap_id.toString());
                tv1.setText(bgModelList.get(i).bg_trap_id.toString());
                tv1.setTextColor(Color.BLACK);
                tv1.setTextSize(20);
                l2.addView(tv1);
                l2.invalidate();
                TextView tv2 = new TextView(this);
                tv2.setPadding(0,5,10,0);
                tv2.setLayoutParams(params);
                tv2.setTag(bgModelList.get(i).bg_trap_id.toString());
                tv2.setBackgroundColor(Color.TRANSPARENT);
                tv2.setTextSize(16);
                //RelativeLayout.LayoutParams params3 = (RelativeLayout.LayoutParams) tv2.getLayoutParams();
                //params3.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);

                tv2.setBackground(ContextCompat.getDrawable(context, R.drawable.substract) );
                rl1.addView(tv2);
                rl1.invalidate();
                LayoutParams layoutarams;
                layoutarams = (LayoutParams)tv2.getLayoutParams();
                layoutarams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                tv2.setLayoutParams(layoutarams);
            }
        }

        if (field_type.equals("mrc")) {
            dbHandler = new DbHandler(context);
            mrcModelList = new ArrayList<>();
            mrcList = new ArrayList<>();
            mrcModelList = dbHandler.getSingleMrc(run_name, field_type);
            //Log.d("size-ov",Integer.toString(ovModelList.size()));
            for (int i = 0; i < mrcModelList.size(); ++i) {
                Log.d("test-mrc_id",mrcModelList.get(i).identifier.toString());
                Log.d("test-trap_status",mrcModelList.get(i).mrc_status.toString());
                Log.d("test-run_name",mrcModelList.get(i).run_name.toString());
                Log.d("test-person_id",Integer.toString(mrcModelList.get(i).person_id));
                Log.d("test-address_id",Integer.toString(mrcModelList.get(i).address_id));
                Log.d("test-coordinates",mrcModelList.get(i).coordinates.toString());
                Log.d("test-exist",mrcModelList.get(i).exist_in_remote_server.toString());

                RelativeLayout rl1 = new RelativeLayout(this);
                rl1.setId(i);
                rl1.setLayoutParams(new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));
                rl1.setBackground(ContextCompat.getDrawable(context, R.drawable.vertical_single_border));
                rl1.setPadding(15,10,15,10);
                rl1.setTag(mrcModelList.get(i).identifier.toString());
                rl1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        goIndividualOv(v);
                    }
                });
                l1.addView(rl1);
                l1.invalidate();
                LinearLayout l2 = new LinearLayout(this);
                l2.setLayoutParams(new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));
                l2.setTag(mrcModelList.get(i).identifier.toString());
                l2.setOrientation(LinearLayout.VERTICAL);
                l2.setPadding(0,10,0,10);
                rl1.addView(l2);
                rl1.invalidate();
                TextView tv1 = new TextView(this);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.setMargins(5,0,0,0);
                tv1.setLayoutParams(params);
                tv1.setBackgroundColor(Color.TRANSPARENT);
                tv1.setTag(mrcModelList.get(i).identifier.toString());
                tv1.setText(mrcModelList.get(i).identifier.toString());
                tv1.setTextColor(Color.BLACK);
                tv1.setTextSize(20);
                l2.addView(tv1);
                l2.invalidate();
                TextView tv2 = new TextView(this);
                tv2.setPadding(0,5,10,0);
                tv2.setLayoutParams(params);
                tv2.setTag(mrcModelList.get(i).identifier.toString());
                tv2.setBackgroundColor(Color.TRANSPARENT);
                tv2.setTextSize(16);
                //RelativeLayout.LayoutParams params3 = (RelativeLayout.LayoutParams) tv2.getLayoutParams();
                //params3.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);

                tv2.setBackground(ContextCompat.getDrawable(context, R.drawable.substract) );
                rl1.addView(tv2);
                rl1.invalidate();
                LayoutParams layoutarams;
                layoutarams = (LayoutParams)tv2.getLayoutParams();
                layoutarams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                tv2.setLayoutParams(layoutarams);

            }
        }

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
            intent.putExtra("form-type","new");
            startActivity(intent);
        }
        if (field_type.equals("ov")) {
            sharedpreferences = getSharedPreferences(OviDetails, Context.MODE_PRIVATE);
            SharedPreferences.Editor editorOvi = sharedpreferences.edit();
            editorOvi.putString(OviRunId,spinnerRuns.getSelectedItem().toString());
            editorOvi.apply();
            Intent intent = new Intent(context, AddOvMainActivity.class);
            intent.putExtra("form-type","new");
            startActivity(intent);
        }
        if (field_type.equals("mrc")) {
            sharedpreferences = getSharedPreferences(MrcDetails, Context.MODE_PRIVATE);
            SharedPreferences.Editor editorMrc = sharedpreferences.edit();
            editorMrc.putString(MrcRunId,spinnerRuns.getSelectedItem().toString());
            editorMrc.apply();
            Intent intent = new Intent(context, AddMrcMainActivity.class);
            intent.putExtra("form-type","new");
            startActivity(intent);
        }
    }

    public void goIndividualOv(View v) {

        if (field_type.equals("ov")) {
            dbHandler = new DbHandler(context);
            ovPersonAddressModelList = new ArrayList<>();
            ovPersonAddressModelList = dbHandler.getSingleOvTrap(v.getTag().toString());
                sharedpreferences = getSharedPreferences(OviDetails, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString(OviTrapId,ovPersonAddressModelList.get(0).ov_trap_id.toString());
                editor.putString(TrapStatus, ovPersonAddressModelList.get(0).trap_status.toString());
                editor.putString(TrapPosition, ovPersonAddressModelList.get(0).position.toString());
                editor.putString(RespondName, ovPersonAddressModelList.get(0).person_name);
                editor.putString(LocationCoordinates, ovPersonAddressModelList.get(0).coordinates);
                editor.putString(Phone, String.valueOf(ovPersonAddressModelList.get(0).phone));
                editor.putString(AddressLine1, ovPersonAddressModelList.get(0).address_line1);
                editor.putString(AddressLine2, ovPersonAddressModelList.get(0).address_line2);
                editor.putString(LocationDescription, ovPersonAddressModelList.get(0).location_description);
                editor.putString(OviRunId, ovPersonAddressModelList.get(0).run_name);
                editor.apply();

                Intent intent = new Intent(context, AddOvMainActivity.class);
                intent.putExtra("TrapId", v.getTag().toString());
                intent.putExtra("form-type", "edit-new");
                startActivity(intent);
        }
        if (field_type.equals("bg")) {
            dbHandler = new DbHandler(context);
            bgPersonAddressModelList = new ArrayList<>();
            bgPersonAddressModelList = dbHandler.getSingleBgTrap(v.getTag().toString());
            sharedpreferences = getSharedPreferences(BgDetails, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString(BgTrapId,bgPersonAddressModelList.get(0).bg_trap_id.toString());
            editor.putString(TrapStatus, bgPersonAddressModelList.get(0).trap_status.toString());
            editor.putString(TrapPosition, bgPersonAddressModelList.get(0).position.toString());
            editor.putString(RespondName, bgPersonAddressModelList.get(0).person_name);
            editor.putString(LocationCoordinates, bgPersonAddressModelList.get(0).coordinates);
            editor.putString(Phone, String.valueOf(bgPersonAddressModelList.get(0).phone));
            editor.putString(AddressLine1, bgPersonAddressModelList.get(0).address_line1);
            editor.putString(AddressLine2, bgPersonAddressModelList.get(0).address_line2);
            editor.putString(LocationDescription, bgPersonAddressModelList.get(0).location_description);
            editor.putString(BgRunId, bgPersonAddressModelList.get(0).run_name);
            editor.apply();

            Intent intent = new Intent(context, AddBgMainActivity.class);
            intent.putExtra("TrapId", v.getTag().toString());
            intent.putExtra("form-type","edit-new");
            startActivity(intent);
        }
        if (field_type.equals("mrc")) {
            dbHandler = new DbHandler(context);
            mrcPersonAddressModelList = new ArrayList<>();
            mrcPersonAddressModelList = dbHandler.getSingleMrcPersonAddress(v.getTag().toString());
            sharedpreferences = getSharedPreferences(MrcDetails, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString(MrcId,mrcPersonAddressModelList.get(0).identifier.toString());
            editor.putString(MrcStatus, mrcPersonAddressModelList.get(0).mrc_status.toString());
            editor.putString(RespondName, mrcPersonAddressModelList.get(0).person_name);
            editor.putString(LocationCoordinates, mrcPersonAddressModelList.get(0).coordinates);
            editor.putString(Phone, String.valueOf(mrcPersonAddressModelList.get(0).phone));
            editor.putString(AddressLine1, mrcPersonAddressModelList.get(0).address_line1);
            editor.putString(AddressLine2, mrcPersonAddressModelList.get(0).address_line2);
            editor.putString(LocationDescription, mrcPersonAddressModelList.get(0).location_description);
            editor.putString(MrcRunId, mrcPersonAddressModelList.get(0).run_name);
            editor.apply();
            Intent intent = new Intent(context, AddMrcMainActivity.class);
            intent.putExtra("TrapId", v.getTag().toString());
            intent.putExtra("form-type","edit-new");
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
