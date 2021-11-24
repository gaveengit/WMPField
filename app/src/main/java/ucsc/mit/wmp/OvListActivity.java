package ucsc.mit.wmp;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OvListActivity extends AppCompatActivity {
    final Context context = this;
    private DbHandler dbHandler;
    private String field_type;
    Button button;
    public static final String OviTrapId = "OviTrapId";
    public static final String OriginalOviId = "OriginalOviId";
    public static final String BgTrapId = "BgTrapId";
    public static final String OriginalBgId = "OriginalBgId";
    public static final String MrcTrapId = "MrcTrapId";
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
    public static final String OriginalMrcId = "OriginalMrcId";
    public static final String MrcStatus = "MrcStatus";
    public static final String OviRunId = "OviRunId";
    public static final String BgRunId = "BgRunId";
    public static final String MrcRunId = "MrcRunId";
    public static final String PersonId = "PersonId";
    public static final String AddressId = "AddressId";
    public static final String BgServiceDetails = "BgServiceDetails";
    public static final String OviServiceDetails = "OviServiceDetails";
    public static final String MrcServiceDetails = "MrcServiceDetails";
    public static final String MrcReleaseDetails = "MrcReleaseDetails";
    public static final String BgCollectionDetails = "BgCollectionDetails";
    public static final String OviCollectionDetails = "OviCollectionDetails";
    public static final String ServiceId = "ServiceId";
    public static final String ServiceStatus = "ServiceStatus";
    public static final String CollectionId = "CollectionId";
    public static final String CollectionStatus = "CollectionStatus";
    public static final String ReleaseId = "ReleaseId";
    public static final String ReleaseStatus = "ReleaseStatus";
    private List<OvTrapModel> ovModelList;
    private List<OviServiceModel> ovServiceModelList;
    private List<OviCollectionModel> ovCollectionModelList;
    private List<OvTrapModel> ovPersonAddressModelList;
    private List<BgTrapModel> bgPersonAddressModelList;
    private List<MrcModel> mrcPersonAddressModelList;
    private List<String> ovList;
    private List<BgTrapModel> bgModelList;
    private List<BgServiceModel> bgServiceModelList;
    private List<BgCollectionModel> bgCollectionModelList;
    private List<String> bgList;
    private List<MrcModel> mrcModelList;
    private List<MrcServiceModel> mrcServiceModelList;
    private List<MrcReleaseModel> mrcReleaseModelList;
    private List<String> mrcList;
    TextView titleText;
    TextView username_text;

    Spinner spinnerRuns;
    SharedPreferences sharedpreferences;
    String run_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.localeggs_listview);
        dbHandler = new DbHandler(context);
        titleText = (TextView) findViewById(R.id.Title);
        LinearLayout l1 = (LinearLayout) findViewById(R.id.contentLinearLayout);
        button = (Button) findViewById(R.id.sync_button);
        l1.removeAllViews();
        field_type = getIntent().getStringExtra("type");
        spinnerRuns = (Spinner) findViewById(R.id.spinnerRuns);
        Log.d("field_type", field_type);
        sharedpreferences = getSharedPreferences(OviDetails, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(OviTrapId, "");
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
        editorBg.putString(BgTrapId, "");
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
        editorMrc.putString(MrcId, "");
        editorMrc.putString(MrcStatus, "");
        editorMrc.putString(RespondName, "");
        editorMrc.putString(LocationCoordinates, "");
        editorMrc.putString(Phone, "");
        editorMrc.putString(AddressLine1, "");
        editorMrc.putString(AddressLine2, "");
        editorMrc.putString(LocationDescription, "");
        editor.putString(MrcRunId, "");
        editorMrc.apply();

        sharedpreferences = getSharedPreferences(MrcServiceDetails, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor_clear_mrc_service = sharedpreferences.edit();
        editor_clear_mrc_service.clear();

        sharedpreferences = getSharedPreferences(MrcReleaseDetails, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor_clear_mrc_release = sharedpreferences.edit();
        editor_clear_mrc_release.clear();

        sharedpreferences = getSharedPreferences(BgServiceDetails, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor_clear_bg_service = sharedpreferences.edit();
        editor_clear_bg_service.clear();

        sharedpreferences = getSharedPreferences(BgCollectionDetails, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor_clear_bg_collection = sharedpreferences.edit();
        editor_clear_bg_collection.clear();

        sharedpreferences = getSharedPreferences(OviServiceDetails, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor_clear_ovi_service = sharedpreferences.edit();
        editor_clear_ovi_service.clear();

        sharedpreferences = getSharedPreferences(OviCollectionDetails, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor_clear_ovi_collection = sharedpreferences.edit();
        editor_clear_ovi_collection.clear();

        if (field_type.equals("ov")) {
            titleText.setText("OVI Field Activities");
            dbHandler = new DbHandler(context);
            List<String> ovi_runs = dbHandler.getAllOviRuns();
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, ovi_runs);
            spinnerRuns.setAdapter(dataAdapter);
        }
        if (field_type.equals("bg")) {
            titleText.setText("BG Field Activities");
            dbHandler = new DbHandler(context);
            List<String> bg_runs = dbHandler.getAllBgRuns();
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, bg_runs);
            spinnerRuns.setAdapter(dataAdapter);
        }

        if (field_type.equals("mrc")) {
            titleText.setText("MRC Field Activities");
            dbHandler = new DbHandler(context);
            List<String> mrc_runs = dbHandler.getAllMrcRuns();
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, mrc_runs);
            spinnerRuns.setAdapter(dataAdapter);
        }
        spinnerRuns.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (field_type.equals("ov")) {
                    LinearLayout l1 = (LinearLayout) findViewById(R.id.contentLinearLayout);
                    l1.removeAllViews();
                    dbHandler = new DbHandler(context);

                    if (spinnerRuns.getSelectedItem() == null) {
                        run_name = "";
                    } else {
                        run_name = spinnerRuns.getSelectedItem().toString();
                    }
                    ovModelList = new ArrayList<>();
                    ovList = new ArrayList<>();
                    ovModelList = dbHandler.getSingleOvTrap(run_name, field_type);
                    Log.d("size-ov", Integer.toString(ovModelList.size()));
                    if (ovModelList.size() > 0) {
                        for (int i = 0; i < ovModelList.size(); ++i) {
                            RelativeLayout rl1 = new RelativeLayout(context);
                            rl1.setId(i);
                            rl1.setLayoutParams(new ViewGroup.LayoutParams(
                                    ViewGroup.LayoutParams.MATCH_PARENT,
                                    ViewGroup.LayoutParams.WRAP_CONTENT));
                            rl1.setBackground(ContextCompat.getDrawable(context, R.drawable.vertical_single_border));
                            rl1.setPadding(15, 10, 15, 10);
                            rl1.setTag(ovModelList.get(i).ov_trap_id.toString());
                            rl1.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    goIndividualOv(v);
                                }
                            });
                            l1.addView(rl1);
                            l1.invalidate();
                            LinearLayout l2 = new LinearLayout(context);
                            l2.setLayoutParams(new ViewGroup.LayoutParams(
                                    ViewGroup.LayoutParams.WRAP_CONTENT,
                                    ViewGroup.LayoutParams.WRAP_CONTENT));
                            l2.setTag(ovModelList.get(i).ov_trap_id.toString());
                            l2.setOrientation(LinearLayout.VERTICAL);
                            l2.setPadding(0, 10, 0, 10);
                            rl1.addView(l2);
                            rl1.invalidate();
                            TextView tv1 = new TextView(context);
                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                            params.setMargins(5, 0, 0, 0);
                            tv1.setLayoutParams(params);
                            tv1.setBackgroundColor(Color.TRANSPARENT);
                            tv1.setTag(ovModelList.get(i).ov_trap_id.toString());
                            tv1.setText(ovModelList.get(i).ov_trap_id.toString());
                            tv1.setTextColor(Color.BLACK);
                            tv1.setTextSize(20);
                            l2.addView(tv1);
                            l2.invalidate();
                            TextView tv2 = new TextView(context);
                            tv2.setPadding(0, 5, 10, 0);
                            tv2.setLayoutParams(params);
                            tv2.setTag(ovModelList.get(i).ov_trap_id.toString());
                            tv2.setBackgroundColor(Color.TRANSPARENT);
                            tv2.setTextSize(16);
                            //RelativeLayout.LayoutParams params3 = (RelativeLayout.LayoutParams) tv2.getLayoutParams();
                            //params3.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
                            if(ovModelList.get(i).trap_status != null && ovModelList.get(i).trap_status.equals("1")) {
                                tv2.setBackground(ContextCompat.getDrawable(context, R.drawable.substract));
                            }
                            else if(ovModelList.get(i).trap_status != null && ovModelList.get(i).trap_status.equals("2")) {
                                tv2.setBackground(ContextCompat.getDrawable(context, R.drawable.right));
                            }
                            rl1.addView(tv2);
                            rl1.invalidate();
                            LayoutParams layoutarams;
                            layoutarams = (LayoutParams) tv2.getLayoutParams();
                            layoutarams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                            tv2.setLayoutParams(layoutarams);
                        }
                    } else {
                        ovServiceModelList = new ArrayList<>();
                        ovList = new ArrayList<>();
                        ovServiceModelList = dbHandler.getSingleOviService(run_name, field_type);
                        if (ovServiceModelList.size() > 0) {
                            for (int i = 0; i < ovServiceModelList.size(); ++i) {
                                RelativeLayout rl1 = new RelativeLayout(context);
                                rl1.setId(i);
                                rl1.setLayoutParams(new ViewGroup.LayoutParams(
                                        ViewGroup.LayoutParams.MATCH_PARENT,
                                        ViewGroup.LayoutParams.WRAP_CONTENT));
                                rl1.setBackground(ContextCompat.getDrawable(context, R.drawable.vertical_single_border));
                                rl1.setPadding(15, 10, 15, 10);
                                rl1.setTag(ovServiceModelList.get(i).trap_id.toString());
                                rl1.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        goIndividualOviService(v);
                                    }
                                });
                                l1.addView(rl1);
                                l1.invalidate();
                                LinearLayout l2 = new LinearLayout(context);
                                l2.setLayoutParams(new ViewGroup.LayoutParams(
                                        ViewGroup.LayoutParams.WRAP_CONTENT,
                                        ViewGroup.LayoutParams.WRAP_CONTENT));
                                l2.setTag(ovServiceModelList.get(i).trap_id.toString());
                                l2.setOrientation(LinearLayout.VERTICAL);
                                l2.setPadding(0, 10, 0, 10);
                                rl1.addView(l2);
                                rl1.invalidate();
                                TextView tv1 = new TextView(context);
                                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                params.setMargins(5, 0, 0, 0);
                                tv1.setLayoutParams(params);
                                tv1.setBackgroundColor(Color.TRANSPARENT);
                                tv1.setTag(ovServiceModelList.get(i).trap_id.toString());
                                tv1.setText(ovServiceModelList.get(i).trap_id.toString());
                                tv1.setTextColor(Color.BLACK);
                                tv1.setTextSize(20);
                                l2.addView(tv1);
                                l2.invalidate();
                                TextView tv2 = new TextView(context);
                                tv2.setPadding(0, 5, 10, 0);
                                tv2.setLayoutParams(params);
                                tv2.setTag(ovServiceModelList.get(i).trap_id.toString());
                                tv2.setBackgroundColor(Color.TRANSPARENT);
                                tv2.setTextSize(16);
                                //RelativeLayout.LayoutParams params3 = (RelativeLayout.LayoutParams) tv2.getLayoutParams();
                                //params3.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);

                                if(ovServiceModelList.get(i).service_status != null && ovServiceModelList.get(i).service_status.equals("1")) {
                                    tv2.setBackground(ContextCompat.getDrawable(context, R.drawable.right));
                                }
                                else if(ovServiceModelList.get(i).service_status != null && ovServiceModelList.get(i).service_status.equals("2")) {
                                    tv2.setBackground(ContextCompat.getDrawable(context, R.drawable.cross));
                                }
                                else{
                                    tv2.setBackground(ContextCompat.getDrawable(context, R.drawable.substract));
                                }
                                rl1.addView(tv2);
                                rl1.invalidate();
                                LayoutParams layoutarams;
                                layoutarams = (LayoutParams) tv2.getLayoutParams();
                                layoutarams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                                tv2.setLayoutParams(layoutarams);
                            }
                        } else {
                            ovCollectionModelList = new ArrayList<>();
                            ovList = new ArrayList<>();
                            ovCollectionModelList = dbHandler.getSingleOviCollection(run_name, field_type);
                            if (ovCollectionModelList.size() > 0) {
                                for (int i = 0; i < ovCollectionModelList.size(); ++i) {
                                    RelativeLayout rl1 = new RelativeLayout(context);
                                    rl1.setId(i);
                                    rl1.setLayoutParams(new ViewGroup.LayoutParams(
                                            ViewGroup.LayoutParams.MATCH_PARENT,
                                            ViewGroup.LayoutParams.WRAP_CONTENT));
                                    rl1.setBackground(ContextCompat.getDrawable(context, R.drawable.vertical_single_border));
                                    rl1.setPadding(15, 10, 15, 10);
                                    rl1.setTag(ovCollectionModelList.get(i).trap_id.toString());
                                    rl1.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            goIndividualOviCollection(v);
                                        }
                                    });
                                    l1.addView(rl1);
                                    l1.invalidate();
                                    LinearLayout l2 = new LinearLayout(context);
                                    l2.setLayoutParams(new ViewGroup.LayoutParams(
                                            ViewGroup.LayoutParams.WRAP_CONTENT,
                                            ViewGroup.LayoutParams.WRAP_CONTENT));
                                    l2.setTag(ovCollectionModelList.get(i).trap_id.toString());
                                    l2.setOrientation(LinearLayout.VERTICAL);
                                    l2.setPadding(0, 10, 0, 10);
                                    rl1.addView(l2);
                                    rl1.invalidate();
                                    TextView tv1 = new TextView(context);
                                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                    params.setMargins(5, 0, 0, 0);
                                    tv1.setLayoutParams(params);
                                    tv1.setBackgroundColor(Color.TRANSPARENT);
                                    tv1.setTag(ovCollectionModelList.get(i).trap_id.toString());
                                    tv1.setText(ovCollectionModelList.get(i).trap_id.toString());
                                    tv1.setTextColor(Color.BLACK);
                                    tv1.setTextSize(20);
                                    l2.addView(tv1);
                                    l2.invalidate();
                                    TextView tv2 = new TextView(context);
                                    tv2.setPadding(0, 5, 10, 0);
                                    tv2.setLayoutParams(params);
                                    tv2.setTag(ovCollectionModelList.get(i).trap_id.toString());
                                    tv2.setBackgroundColor(Color.TRANSPARENT);
                                    tv2.setTextSize(16);
                                    if(ovCollectionModelList.get(i).collection_status != null && ovCollectionModelList.get(i).collection_status.equals("1")) {
                                        tv2.setBackground(ContextCompat.getDrawable(context, R.drawable.right));
                                    }
                                    else if(ovCollectionModelList.get(i).collection_status != null && ovCollectionModelList.get(i).collection_status.equals("2")) {
                                        tv2.setBackground(ContextCompat.getDrawable(context, R.drawable.cross));
                                    }
                                    else{
                                        tv2.setBackground(ContextCompat.getDrawable(context, R.drawable.substract));
                                    }
                                    rl1.addView(tv2);
                                    rl1.invalidate();
                                    LayoutParams layoutarams;
                                    layoutarams = (LayoutParams) tv2.getLayoutParams();
                                    layoutarams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                                    tv2.setLayoutParams(layoutarams);
                                }
                            }
                        }
                    }
                }
                if (field_type.equals("bg")) {
                    LinearLayout l1 = (LinearLayout) findViewById(R.id.contentLinearLayout);
                    l1.removeAllViews();
                    if (spinnerRuns.getSelectedItem() == null) {
                        run_name = "";
                    } else {
                        run_name = spinnerRuns.getSelectedItem().toString();
                    }
                    dbHandler = new DbHandler(context);
                    bgModelList = new ArrayList<>();
                    bgList = new ArrayList<>();
                    bgModelList = dbHandler.getSingleBgTrap(run_name, field_type);
                    Log.d("size-ov", Integer.toString(bgModelList.size()));
                    if (bgModelList.size() > 0) {
                        for (int i = 0; i < bgModelList.size(); ++i) {
                            RelativeLayout rl1 = new RelativeLayout(context);
                            rl1.setId(i);
                            rl1.setLayoutParams(new ViewGroup.LayoutParams(
                                    ViewGroup.LayoutParams.MATCH_PARENT,
                                    ViewGroup.LayoutParams.WRAP_CONTENT));
                            rl1.setBackground(ContextCompat.getDrawable(context, R.drawable.vertical_single_border));
                            rl1.setPadding(15, 10, 15, 10);
                            rl1.setTag(bgModelList.get(i).bg_trap_id.toString());
                            rl1.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    goIndividualOv(v);
                                }
                            });
                            l1.addView(rl1);
                            l1.invalidate();
                            LinearLayout l2 = new LinearLayout(context);
                            l2.setLayoutParams(new ViewGroup.LayoutParams(
                                    ViewGroup.LayoutParams.WRAP_CONTENT,
                                    ViewGroup.LayoutParams.WRAP_CONTENT));
                            l2.setTag(bgModelList.get(i).bg_trap_id.toString());
                            l2.setOrientation(LinearLayout.VERTICAL);
                            l2.setPadding(0, 10, 0, 10);
                            rl1.addView(l2);
                            rl1.invalidate();
                            TextView tv1 = new TextView(context);
                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                            params.setMargins(5, 0, 0, 0);
                            tv1.setLayoutParams(params);
                            tv1.setBackgroundColor(Color.TRANSPARENT);
                            tv1.setTag(bgModelList.get(i).bg_trap_id.toString());
                            tv1.setText(bgModelList.get(i).bg_trap_id.toString());
                            tv1.setTextColor(Color.BLACK);
                            tv1.setTextSize(20);
                            l2.addView(tv1);
                            l2.invalidate();
                            TextView tv2 = new TextView(context);
                            tv2.setPadding(0, 5, 10, 0);
                            tv2.setLayoutParams(params);
                            tv2.setTag(bgModelList.get(i).bg_trap_id.toString());
                            tv2.setBackgroundColor(Color.TRANSPARENT);
                            tv2.setTextSize(16);
                            if(bgModelList.get(i).trap_status != null && bgModelList.get(i).trap_status.equals("1")) {
                                tv2.setBackground(ContextCompat.getDrawable(context, R.drawable.substract));
                            }
                            else if(bgModelList.get(i).trap_status != null && bgModelList.get(i).trap_status.equals("2")) {
                                tv2.setBackground(ContextCompat.getDrawable(context, R.drawable.right));
                            }
                            rl1.addView(tv2);
                            rl1.invalidate();
                            LayoutParams layoutarams;
                            layoutarams = (LayoutParams) tv2.getLayoutParams();
                            layoutarams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                            tv2.setLayoutParams(layoutarams);
                        }
                    } else {
                        dbHandler = new DbHandler(context);
                        bgServiceModelList = new ArrayList<>();
                        bgList = new ArrayList<>();
                        bgServiceModelList = dbHandler.getSingleBgService(run_name, field_type);
                        if (bgServiceModelList.size() > 0) {
                            for (int i = 0; i < bgServiceModelList.size(); ++i) {
                                RelativeLayout rl1 = new RelativeLayout(context);
                                rl1.setId(i);
                                rl1.setLayoutParams(new ViewGroup.LayoutParams(
                                        ViewGroup.LayoutParams.MATCH_PARENT,
                                        ViewGroup.LayoutParams.WRAP_CONTENT));
                                rl1.setBackground(ContextCompat.getDrawable(context, R.drawable.vertical_single_border));
                                rl1.setPadding(15, 10, 15, 10);
                                rl1.setTag(bgServiceModelList.get(i).trap_id.toString());
                                rl1.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        goIndividualBgService(v);
                                    }
                                });
                                l1.addView(rl1);
                                l1.invalidate();
                                LinearLayout l2 = new LinearLayout(context);
                                l2.setLayoutParams(new ViewGroup.LayoutParams(
                                        ViewGroup.LayoutParams.WRAP_CONTENT,
                                        ViewGroup.LayoutParams.WRAP_CONTENT));
                                l2.setTag(bgServiceModelList.get(i).trap_id.toString());
                                l2.setOrientation(LinearLayout.VERTICAL);
                                l2.setPadding(0, 10, 0, 10);
                                rl1.addView(l2);
                                rl1.invalidate();
                                TextView tv1 = new TextView(context);
                                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                params.setMargins(5, 0, 0, 0);
                                tv1.setLayoutParams(params);
                                tv1.setBackgroundColor(Color.TRANSPARENT);
                                tv1.setTag(bgServiceModelList.get(i).trap_id.toString());
                                tv1.setText(bgServiceModelList.get(i).trap_id.toString());
                                tv1.setTextColor(Color.BLACK);
                                tv1.setTextSize(20);
                                l2.addView(tv1);
                                l2.invalidate();
                                TextView tv2 = new TextView(context);
                                tv2.setPadding(0, 5, 10, 0);
                                tv2.setLayoutParams(params);
                                tv2.setTag(bgServiceModelList.get(i).trap_id.toString());
                                tv2.setBackgroundColor(Color.TRANSPARENT);
                                tv2.setTextSize(16);
                                if(bgServiceModelList.get(i).service_status != null && bgServiceModelList.get(i).service_status.equals("1")) {
                                    tv2.setBackground(ContextCompat.getDrawable(context, R.drawable.right));
                                }
                                else if(bgServiceModelList.get(i).service_status != null && bgServiceModelList.get(i).service_status.equals("2")) {
                                    tv2.setBackground(ContextCompat.getDrawable(context, R.drawable.cross));
                                }
                                else{
                                    tv2.setBackground(ContextCompat.getDrawable(context, R.drawable.substract));
                                }
                                rl1.addView(tv2);
                                rl1.invalidate();
                                LayoutParams layoutarams;
                                layoutarams = (LayoutParams) tv2.getLayoutParams();
                                layoutarams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                                tv2.setLayoutParams(layoutarams);
                            }
                        } else {
                            dbHandler = new DbHandler(context);
                            bgCollectionModelList = new ArrayList<>();
                            bgList = new ArrayList<>();
                            bgCollectionModelList = dbHandler.getSingleBgCollection(run_name, field_type);
                            if (bgCollectionModelList.size() > 0) {
                                for (int i = 0; i < bgCollectionModelList.size(); ++i) {
                                    RelativeLayout rl1 = new RelativeLayout(context);
                                    rl1.setId(i);
                                    rl1.setLayoutParams(new ViewGroup.LayoutParams(
                                            ViewGroup.LayoutParams.MATCH_PARENT,
                                            ViewGroup.LayoutParams.WRAP_CONTENT));
                                    rl1.setBackground(ContextCompat.getDrawable(context, R.drawable.vertical_single_border));
                                    rl1.setPadding(15, 10, 15, 10);
                                    rl1.setTag(bgCollectionModelList.get(i).trap_id.toString());
                                    rl1.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            goIndividualBgCollection(v);
                                        }
                                    });
                                    l1.addView(rl1);
                                    l1.invalidate();
                                    LinearLayout l2 = new LinearLayout(context);
                                    l2.setLayoutParams(new ViewGroup.LayoutParams(
                                            ViewGroup.LayoutParams.WRAP_CONTENT,
                                            ViewGroup.LayoutParams.WRAP_CONTENT));
                                    l2.setTag(bgCollectionModelList.get(i).trap_id.toString());
                                    l2.setOrientation(LinearLayout.VERTICAL);
                                    l2.setPadding(0, 10, 0, 10);
                                    rl1.addView(l2);
                                    rl1.invalidate();
                                    TextView tv1 = new TextView(context);
                                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                    params.setMargins(5, 0, 0, 0);
                                    tv1.setLayoutParams(params);
                                    tv1.setBackgroundColor(Color.TRANSPARENT);
                                    tv1.setTag(bgCollectionModelList.get(i).trap_id.toString());
                                    tv1.setText(bgCollectionModelList.get(i).trap_id.toString());
                                    tv1.setTextColor(Color.BLACK);
                                    tv1.setTextSize(20);
                                    l2.addView(tv1);
                                    l2.invalidate();
                                    TextView tv2 = new TextView(context);
                                    tv2.setPadding(0, 5, 10, 0);
                                    tv2.setLayoutParams(params);
                                    tv2.setTag(bgCollectionModelList.get(i).trap_id.toString());
                                    tv2.setBackgroundColor(Color.TRANSPARENT);
                                    tv2.setTextSize(16);
                                    if(bgCollectionModelList.get(i).collection_status != null && bgCollectionModelList.get(i).collection_status.equals("1")) {
                                        tv2.setBackground(ContextCompat.getDrawable(context, R.drawable.right));
                                    }
                                    else if(bgCollectionModelList.get(i).collection_status != null && bgCollectionModelList.get(i).collection_status.equals("2")) {
                                        tv2.setBackground(ContextCompat.getDrawable(context, R.drawable.cross));
                                    }
                                    else{
                                        tv2.setBackground(ContextCompat.getDrawable(context, R.drawable.substract));
                                    }
                                    rl1.addView(tv2);
                                    rl1.invalidate();
                                    LayoutParams layoutarams;
                                    layoutarams = (LayoutParams) tv2.getLayoutParams();
                                    layoutarams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                                    tv2.setLayoutParams(layoutarams);
                                }
                            }
                        }
                    }
                }
                if (field_type.equals("mrc")) {
                    LinearLayout l1 = (LinearLayout) findViewById(R.id.contentLinearLayout);
                    l1.removeAllViews();
                    dbHandler = new DbHandler(context);
                    if (spinnerRuns.getSelectedItem() == null) {
                        run_name = "";
                    } else {
                        run_name = spinnerRuns.getSelectedItem().toString();
                    }
                    dbHandler = new DbHandler(context);
                    mrcModelList = new ArrayList<>();
                    mrcList = new ArrayList<>();
                    mrcModelList = dbHandler.getSingleMrc(run_name, field_type);
                    if (mrcModelList.size() > 0) {
                        for (int i = 0; i < mrcModelList.size(); ++i) {
                            RelativeLayout rl1 = new RelativeLayout(context);
                            rl1.setId(i);
                            rl1.setLayoutParams(new ViewGroup.LayoutParams(
                                    ViewGroup.LayoutParams.MATCH_PARENT,
                                    ViewGroup.LayoutParams.WRAP_CONTENT));
                            rl1.setBackground(ContextCompat.getDrawable(context, R.drawable.vertical_single_border));
                            rl1.setPadding(15, 10, 15, 10);
                            rl1.setTag(mrcModelList.get(i).identifier.toString());
                            rl1.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    goIndividualOv(v);
                                }
                            });
                            l1.addView(rl1);
                            l1.invalidate();
                            LinearLayout l2 = new LinearLayout(context);
                            l2.setLayoutParams(new ViewGroup.LayoutParams(
                                    ViewGroup.LayoutParams.WRAP_CONTENT,
                                    ViewGroup.LayoutParams.WRAP_CONTENT));
                            l2.setTag(mrcModelList.get(i).identifier.toString());
                            l2.setOrientation(LinearLayout.VERTICAL);
                            l2.setPadding(0, 10, 0, 10);
                            rl1.addView(l2);
                            rl1.invalidate();
                            TextView tv1 = new TextView(context);
                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                            params.setMargins(5, 0, 0, 0);
                            tv1.setLayoutParams(params);
                            tv1.setBackgroundColor(Color.TRANSPARENT);
                            tv1.setTag(mrcModelList.get(i).identifier.toString());
                            tv1.setText(mrcModelList.get(i).identifier.toString());
                            tv1.setTextColor(Color.BLACK);
                            tv1.setTextSize(20);
                            l2.addView(tv1);
                            l2.invalidate();
                            TextView tv2 = new TextView(context);
                            tv2.setPadding(0, 5, 10, 0);
                            tv2.setLayoutParams(params);
                            tv2.setTag(mrcModelList.get(i).identifier.toString());
                            tv2.setBackgroundColor(Color.TRANSPARENT);
                            tv2.setTextSize(16);
                            if(mrcModelList.get(i).mrc_status != null && mrcModelList.get(i).mrc_status.equals("1")) {
                                tv2.setBackground(ContextCompat.getDrawable(context, R.drawable.substract));
                            }
                            else if(mrcModelList.get(i).mrc_status != null && mrcModelList.get(i).mrc_status.equals("2")) {
                                tv2.setBackground(ContextCompat.getDrawable(context, R.drawable.right));
                            }
                            rl1.addView(tv2);
                            rl1.invalidate();
                            LayoutParams layoutarams;
                            layoutarams = (LayoutParams) tv2.getLayoutParams();
                            layoutarams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                            tv2.setLayoutParams(layoutarams);

                        }
                    } else {
                        dbHandler = new DbHandler(context);
                        mrcServiceModelList = new ArrayList<>();
                        mrcList = new ArrayList<>();
                        mrcServiceModelList = dbHandler.getSingleMrcService(run_name, field_type);
                        if (mrcServiceModelList.size() > 0) {
                            for (int i = 0; i < mrcServiceModelList.size(); ++i) {

                                RelativeLayout rl1 = new RelativeLayout(context);
                                rl1.setId(i);
                                rl1.setLayoutParams(new ViewGroup.LayoutParams(
                                        ViewGroup.LayoutParams.MATCH_PARENT,
                                        ViewGroup.LayoutParams.WRAP_CONTENT));
                                rl1.setBackground(ContextCompat.getDrawable(context, R.drawable.vertical_single_border));
                                rl1.setPadding(15, 10, 15, 10);
                                rl1.setTag(mrcServiceModelList.get(i).trap_id.toString());
                                rl1.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        goIndividualMrcService(v);
                                    }
                                });
                                l1.addView(rl1);
                                l1.invalidate();
                                LinearLayout l2 = new LinearLayout(context);
                                l2.setLayoutParams(new ViewGroup.LayoutParams(
                                        ViewGroup.LayoutParams.WRAP_CONTENT,
                                        ViewGroup.LayoutParams.WRAP_CONTENT));
                                l2.setTag(mrcServiceModelList.get(i).trap_id.toString());
                                l2.setOrientation(LinearLayout.VERTICAL);
                                l2.setPadding(0, 10, 0, 10);
                                rl1.addView(l2);
                                rl1.invalidate();
                                TextView tv1 = new TextView(context);
                                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                params.setMargins(5, 0, 0, 0);
                                tv1.setLayoutParams(params);
                                tv1.setBackgroundColor(Color.TRANSPARENT);
                                tv1.setTag(mrcServiceModelList.get(i).trap_id.toString());
                                tv1.setText(mrcServiceModelList.get(i).trap_id.toString());
                                tv1.setTextColor(Color.BLACK);
                                tv1.setTextSize(20);
                                l2.addView(tv1);
                                l2.invalidate();
                                TextView tv2 = new TextView(context);
                                tv2.setPadding(0, 5, 10, 0);
                                tv2.setLayoutParams(params);
                                tv2.setTag(mrcServiceModelList.get(i).trap_id.toString());
                                tv2.setBackgroundColor(Color.TRANSPARENT);
                                if(mrcServiceModelList.get(i).service_status != null && mrcServiceModelList.get(i).service_status.equals("1")) {
                                    tv2.setBackground(ContextCompat.getDrawable(context, R.drawable.right));
                                }
                                else if(mrcServiceModelList.get(i).service_status != null && mrcServiceModelList.get(i).service_status.equals("2")) {
                                    tv2.setBackground(ContextCompat.getDrawable(context, R.drawable.cross));
                                }
                                else{
                                    tv2.setBackground(ContextCompat.getDrawable(context, R.drawable.substract));
                                }
                                rl1.addView(tv2);
                                rl1.invalidate();
                                LayoutParams layoutarams;
                                layoutarams = (LayoutParams) tv2.getLayoutParams();
                                layoutarams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                                tv2.setLayoutParams(layoutarams);

                            }
                        } else {
                            dbHandler = new DbHandler(context);
                            mrcReleaseModelList = new ArrayList<>();
                            mrcList = new ArrayList<>();
                            mrcReleaseModelList = dbHandler.getSingleMrcRelease(run_name, field_type);
                            if (mrcReleaseModelList.size() > 0) {
                                for (int i = 0; i < mrcReleaseModelList.size(); ++i) {

                                    RelativeLayout rl1 = new RelativeLayout(context);
                                    rl1.setId(i);
                                    rl1.setLayoutParams(new ViewGroup.LayoutParams(
                                            ViewGroup.LayoutParams.MATCH_PARENT,
                                            ViewGroup.LayoutParams.WRAP_CONTENT));
                                    rl1.setBackground(ContextCompat.getDrawable(context, R.drawable.vertical_single_border));
                                    rl1.setPadding(15, 10, 15, 10);
                                    rl1.setTag(mrcReleaseModelList.get(i).trap_id.toString());
                                    rl1.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            goIndividualMrcRelease(v);
                                        }
                                    });
                                    l1.addView(rl1);
                                    l1.invalidate();
                                    LinearLayout l2 = new LinearLayout(context);
                                    l2.setLayoutParams(new ViewGroup.LayoutParams(
                                            ViewGroup.LayoutParams.WRAP_CONTENT,
                                            ViewGroup.LayoutParams.WRAP_CONTENT));
                                    l2.setTag(mrcReleaseModelList.get(i).trap_id.toString());
                                    l2.setOrientation(LinearLayout.VERTICAL);
                                    l2.setPadding(0, 10, 0, 10);
                                    rl1.addView(l2);
                                    rl1.invalidate();
                                    TextView tv1 = new TextView(context);
                                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                    params.setMargins(5, 0, 0, 0);
                                    tv1.setLayoutParams(params);
                                    tv1.setBackgroundColor(Color.TRANSPARENT);
                                    tv1.setTag(mrcReleaseModelList.get(i).trap_id.toString());
                                    tv1.setText(mrcReleaseModelList.get(i).trap_id.toString());
                                    tv1.setTextColor(Color.BLACK);
                                    tv1.setTextSize(20);
                                    l2.addView(tv1);
                                    l2.invalidate();
                                    TextView tv2 = new TextView(context);
                                    tv2.setPadding(0, 5, 10, 0);
                                    tv2.setLayoutParams(params);
                                    tv2.setTag(mrcReleaseModelList.get(i).trap_id.toString());
                                    tv2.setBackgroundColor(Color.TRANSPARENT);
                                    tv2.setTextSize(16);
                                    if(mrcReleaseModelList.get(i).release_status != null && mrcReleaseModelList.get(i).release_status.equals("1")) {
                                        tv2.setBackground(ContextCompat.getDrawable(context, R.drawable.right));
                                    }
                                    else if(mrcReleaseModelList.get(i).release_status != null && mrcReleaseModelList.get(i).release_status.equals("2")) {
                                        tv2.setBackground(ContextCompat.getDrawable(context, R.drawable.cross));
                                    }
                                    else{
                                        tv2.setBackground(ContextCompat.getDrawable(context, R.drawable.substract));
                                    }
                                    rl1.addView(tv2);
                                    rl1.invalidate();
                                    LayoutParams layoutarams;
                                    layoutarams = (LayoutParams) tv2.getLayoutParams();
                                    layoutarams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                                    tv2.setLayoutParams(layoutarams);

                                }
                            }
                        }
                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (field_type.equals("ov")) {
                    Toast.makeText(context, "Data synchronization is being processed. Please wait.",
                            Toast.LENGTH_SHORT).show();

                    ovServiceModelList = new ArrayList<>();
                    ovServiceModelList = dbHandler.getAllOviServicesForSync();
                    Log.d("service_size", String.valueOf(ovServiceModelList.size()));
                    if (ovServiceModelList.size() != 0) {
                        for (int i = 0; i < ovServiceModelList.size(); ++i) {
                            String service_id = ovServiceModelList.get(i).getService_id();
                            String trap_id = ovServiceModelList.get(i).getOvi_trap_id();
                            String service_date = ovServiceModelList.get(i).getDate();
                            String service_time = ovServiceModelList.get(i).getTime();
                            String service_status = ovServiceModelList.get(i).getService_status();
                            String run_id = ovServiceModelList.get(i).getOvi_run_id();
                            String append_url = service_id + "/" + trap_id + "/" + service_date + "/" + service_time + "/" + service_status + "/" + run_id;
                            AsyncTask asyncTask = new AsyncTask() {

                                @Override
                                protected Object doInBackground(Object[] objects) {
                                    OkHttpClient client = new OkHttpClient();
                                    Request request = new Request.Builder().url("http://192.168.8.101/api/index.php/api/OviServiceController/insertOviService/" + append_url).build();
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

                                        JSONObject obj = new JSONObject(o.toString());

                                        Log.d("json_response", String.valueOf(obj.getBoolean("status")));
                                        if (String.valueOf(obj.getBoolean("status")).equals("true")) {
                                            Toast.makeText(context, service_id + " has been transfered to server successfully.",
                                                    Toast.LENGTH_SHORT).show();
                                            int del_count = (int) dbHandler.deleteDataOviService(service_id);
                                            if (del_count > 0) {
                                                Toast.makeText(context, service_id + " has been deleted from mobile database successfully.",
                                                        Toast.LENGTH_SHORT).show();
                                            }

                                        } else {
                                            Toast.makeText(context, "Error in transfering " + service_id + " to server." + obj.get("message") + " Please try again.",
                                                    Toast.LENGTH_SHORT).show();
                                        }

                                    } catch (Throwable t) {

                                    }
                                }
                            }.execute();

                        }
                    }
                    ovCollectionModelList = new ArrayList<>();
                    ovCollectionModelList = dbHandler.getAllOviCollectionsForSync();
                    Log.d("collection_size", String.valueOf(ovCollectionModelList.size()));
                    if (ovCollectionModelList.size() != 0) {
                        for (int i = 0; i < ovCollectionModelList.size(); ++i) {
                            String collection_id = ovCollectionModelList.get(i).getCollection_id();
                            String trap_id = ovCollectionModelList.get(i).getOvi_trap_id();
                            String collection_date = ovCollectionModelList.get(i).getDate();
                            String collection_time = ovCollectionModelList.get(i).getTime();
                            String collection_status = ovCollectionModelList.get(i).getCollection_status();
                            String run_id = ovCollectionModelList.get(i).getOvi_run_id();
                            String append_url = collection_id + "/" + trap_id + "/" + collection_date + "/" + collection_time + "/" + collection_status + "/" + run_id;
                            AsyncTask asyncTask = new AsyncTask() {

                                @Override
                                protected Object doInBackground(Object[] objects) {
                                    OkHttpClient client = new OkHttpClient();
                                    Request request = new Request.Builder().url("http://192.168.8.101/api/index.php/api/OviCollectionController/insertOviCollection/" + append_url).build();
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

                                        JSONObject obj = new JSONObject(o.toString());

                                        Log.d("json_response", String.valueOf(obj.getBoolean("status")));
                                        if (String.valueOf(obj.getBoolean("status")).equals("true")) {
                                            Toast.makeText(context, collection_id + " has been transfered to server successfully.",
                                                    Toast.LENGTH_SHORT).show();
                                            int del_count = (int) dbHandler.deleteDataOviCollection(collection_id);
                                            if (del_count > 0) {
                                                Toast.makeText(context, collection_id + " has been deleted from mobile database successfully.",
                                                        Toast.LENGTH_SHORT).show();
                                            }

                                        } else {
                                            Toast.makeText(context, "Error in transfering " + collection_id + " to server." + obj.get("message") + " Please try again.",
                                                    Toast.LENGTH_SHORT).show();
                                        }

                                    } catch (Throwable t) {

                                    }
                                }
                            }.execute();

                        }
                    }
                    ovModelList = new ArrayList<>();
                    ovModelList = dbHandler.getAllOviTrapForSync();
                    if (ovModelList.size() > 0) {
                        for (int i = 0; i < ovModelList.size(); ++i) {
                            String trap_id = ovModelList.get(i).getOv_trap_id();
                            String trap_status = ovModelList.get(i).getTrap_status();
                            String trap_position = ovModelList.get(i).getPosition();
                            String coordinates = ovModelList.get(i).getCoordinates();
                            String ovi_date = ovModelList.get(i).getDate();
                            String ovi_time = ovModelList.get(i).getTime();
                            String person_name = ovModelList.get(i).getPerson_name();
                            String person_phone = ovModelList.get(i).getPerson_phone();
                            String add_line1 = ovModelList.get(i).getAddress_line1();
                            String add_line2 = ovModelList.get(i).getAddress_line2();
                            String location_description = ovModelList.get(i).getLocation_description();

                            String append_url = trap_id + "/" + trap_status + "/" + trap_position + "/" + coordinates + "/" + ovi_date + "/" + ovi_time + "/" + person_name
                                    + "/" + person_phone + "/" + add_line1 + "/" + add_line2 + "/" + location_description;
                            AsyncTask asyncTask = new AsyncTask() {

                                @Override
                                protected Object doInBackground(Object[] objects) {
                                    OkHttpClient client = new OkHttpClient();
                                    Request request = new Request.Builder().url("http://192.168.8.101/api/index.php/api/OviTrapController/insertOviTrap/" + append_url).build();
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

                                        JSONObject obj = new JSONObject(o.toString());

                                        Log.d("json_response", String.valueOf(obj.getBoolean("status")));
                                        if (String.valueOf(obj.getBoolean("status")).equals("true")) {
                                            Toast.makeText(context, trap_id + " has been transfered to server successfully.",
                                                    Toast.LENGTH_SHORT).show();
                                            int del_count = (int) dbHandler.deleteDataOviTrap(trap_id);
                                            if (del_count > 0) {
                                                Toast.makeText(context, trap_id + " has been deleted from mobile database successfully.",
                                                        Toast.LENGTH_SHORT).show();
                                            }

                                        } else {
                                            Toast.makeText(context, "Error in transfering " + trap_id + " to server." + obj.get("message") + " Please try again.",
                                                    Toast.LENGTH_SHORT).show();
                                        }

                                    } catch (Throwable t) {

                                    }
                                }
                            }.execute();

                        }
                    }

                    AsyncTask asyncTaskGetOviService = new AsyncTask() {

                        @Override
                        protected Object doInBackground(Object[] objects) {
                            OkHttpClient client = new OkHttpClient();
                            Request request = new Request.Builder().url("http://192.168.8.101/api/index.php/api/OviServiceController/indexOviService").build();
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
                                    ovServiceModelList = new ArrayList<>();
                                    ovServiceModelList = dbHandler.getSingleOviServiceTrap(ovi_run_id, trap_id);
                                    if (ovServiceModelList.size() == 0) {
                                        OviServiceModel oviService = new OviServiceModel(ovi_run_id, trap_id, trap_position, coordinates,
                                                add_line1, add_line2, location_description, full_name, contact_number);

                                        sync_status = dbHandler.insertDataOviService(oviService);
                                        if (sync_status > 1) {
                                            Toast.makeText(context, trap_id + " of " + ovi_run_id + " has been added to mobile database.",
                                                    Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(context, trap_id + " of " + ovi_run_id + " has occured an error in adding to mobile database.",
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                        Log.d("sync_status", String.valueOf(sync_status));
                                        Log.d("obj", jsonObj.getString("trap_id"));
                                    }
                                }
                            } catch (Throwable t) {

                            }
                        }
                    }.execute();
                    AsyncTask asyncTaskGetOviCollection = new AsyncTask() {

                        @Override
                        protected Object doInBackground(Object[] objects) {
                            OkHttpClient client = new OkHttpClient();
                            Request request = new Request.Builder().url("http://192.168.8.101/api/index.php/api/OviCollectionController/indexOviCollection").build();
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
                                    ovCollectionModelList = new ArrayList<>();
                                    ovCollectionModelList = dbHandler.getSingleOviCollectionTrap(ovi_run_id, trap_id);
                                    if (ovCollectionModelList.size() == 0) {
                                        OviCollectionModel oviCollection = new OviCollectionModel(ovi_run_id, trap_id, trap_position, coordinates,
                                                add_line1, add_line2, location_description, full_name, contact_number);

                                        sync_status = dbHandler.insertDataOviCollection(oviCollection);
                                        if (sync_status > 1) {
                                            Toast.makeText(context, trap_id + " of " + ovi_run_id + " has been added to mobile database.",
                                                    Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(context, trap_id + " of " + ovi_run_id + " has occured an error in adding to mobile database.",
                                                    Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                }
                            } catch (Throwable t) {

                            }
                            Intent intent = new Intent(context, OvListActivity.class);
                            intent.putExtra("type", "ov");
                            startActivity(intent);

                        }
                    }.execute();


                }
                if (field_type.equals("bg")) {

                    Toast.makeText(context, "Data synchronization is being processed. Please wait.",
                            Toast.LENGTH_SHORT).show();

                    bgServiceModelList = new ArrayList<>();
                    bgServiceModelList = dbHandler.getAllBgServicesForSync();
                    if (bgServiceModelList.size() != 0) {
                        for (int i = 0; i < bgServiceModelList.size(); ++i) {
                            String service_id = bgServiceModelList.get(i).getService_id();
                            String trap_id = bgServiceModelList.get(i).getBg_trap_id();
                            String service_date = bgServiceModelList.get(i).getDate();
                            String service_time = bgServiceModelList.get(i).getTime();
                            String service_status = bgServiceModelList.get(i).getService_status();
                            String run_id = bgServiceModelList.get(i).getBg_run_id();
                            String append_url = service_id + "/" + trap_id + "/" + service_date + "/" + service_time + "/" + service_status + "/" + run_id;
                            AsyncTask asyncTask = new AsyncTask() {

                                @Override
                                protected Object doInBackground(Object[] objects) {
                                    OkHttpClient client = new OkHttpClient();
                                    Request request = new Request.Builder().url("http://192.168.8.101/api/index.php/api/BgServiceController/insertBgService/" + append_url).build();
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

                                        JSONObject obj = new JSONObject(o.toString());

                                        Log.d("json_response", String.valueOf(obj.getBoolean("status")));
                                        if (String.valueOf(obj.getBoolean("status")).equals("true")) {
                                            Toast.makeText(context, service_id + " has been transfered to server successfully.",
                                                    Toast.LENGTH_SHORT).show();
                                            int del_count = (int) dbHandler.deleteDataBgService(service_id);
                                            if (del_count > 0) {
                                                Toast.makeText(context, service_id + " has been deleted from mobile database successfully.",
                                                        Toast.LENGTH_SHORT).show();
                                            }

                                        } else {
                                            Toast.makeText(context, "Error in transfering " + service_id + " to server." + obj.get("message") + " Please try again.",
                                                    Toast.LENGTH_SHORT).show();
                                        }

                                    } catch (Throwable t) {

                                    }
                                }
                            }.execute();

                        }
                    }
                    bgCollectionModelList = new ArrayList<>();
                    bgCollectionModelList = dbHandler.getAllBgCollectionsForSync();
                    if (bgCollectionModelList.size() != 0) {
                        for (int i = 0; i < bgCollectionModelList.size(); ++i) {
                            String collection_id = bgCollectionModelList.get(i).getCollection_id();
                            String trap_id = bgCollectionModelList.get(i).getBg_trap_id();
                            String collection_date = bgCollectionModelList.get(i).getDate();
                            String collection_time = bgCollectionModelList.get(i).getTime();
                            String collection_status = bgCollectionModelList.get(i).getCollection_status();
                            String run_id = bgCollectionModelList.get(i).getBg_run_id();
                            String append_url = collection_id + "/" + trap_id + "/" + collection_date + "/" + collection_time + "/" + collection_status + "/" + run_id;
                            AsyncTask asyncTask = new AsyncTask() {

                                @Override
                                protected Object doInBackground(Object[] objects) {
                                    OkHttpClient client = new OkHttpClient();
                                    Request request = new Request.Builder().url("http://192.168.8.101/api/index.php/api/BgCollectionController/insertBgCollection/" + append_url).build();
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

                                        JSONObject obj = new JSONObject(o.toString());

                                        Log.d("json_response", String.valueOf(obj.getBoolean("status")));
                                        if (String.valueOf(obj.getBoolean("status")).equals("true")) {
                                            Toast.makeText(context, collection_id + " has been transfered to server successfully.",
                                                    Toast.LENGTH_SHORT).show();
                                            int del_count = (int) dbHandler.deleteDataBgCollection(collection_id);
                                            if (del_count > 0) {
                                                Toast.makeText(context, collection_id + " has been deleted from mobile database successfully.",
                                                        Toast.LENGTH_SHORT).show();
                                            }

                                        } else {
                                            Toast.makeText(context, "Error in transfering " + collection_id + " to server." + obj.get("message") + " Please try again.",
                                                    Toast.LENGTH_SHORT).show();
                                        }

                                    } catch (Throwable t) {

                                    }
                                }
                            }.execute();

                        }
                    }
                    bgModelList = new ArrayList<>();
                    bgModelList = dbHandler.getAllBgTrapForSync();
                    if (bgModelList.size() > 0) {
                        for (int i = 0; i < bgModelList.size(); ++i) {
                            String trap_id = bgModelList.get(i).getBg_trap_id();
                            String trap_status = bgModelList.get(i).getTrap_status();
                            String trap_position = bgModelList.get(i).getPosition();
                            String coordinates = bgModelList.get(i).getCoordinates();
                            String ovi_date = bgModelList.get(i).getDate();
                            String ovi_time = bgModelList.get(i).getTime();
                            String person_name = bgModelList.get(i).getPerson_name();
                            String person_phone = bgModelList.get(i).getPerson_phone();
                            String add_line1 = bgModelList.get(i).getAddress_line1();
                            String add_line2 = bgModelList.get(i).getAddress_line2();
                            String location_description = bgModelList.get(i).getLocation_description();

                            String append_url = trap_id + "/" + trap_status + "/" + trap_position + "/" + coordinates + "/" + ovi_date + "/" + ovi_time + "/" + person_name
                                    + "/" + person_phone + "/" + add_line1 + "/" + add_line2 + "/" + location_description;
                            AsyncTask asyncTask = new AsyncTask() {

                                @Override
                                protected Object doInBackground(Object[] objects) {
                                    OkHttpClient client = new OkHttpClient();
                                    Request request = new Request.Builder().url("http://192.168.8.101/api/index.php/api/BgTrapController/insertBgTrap/" + append_url).build();
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

                                        JSONObject obj = new JSONObject(o.toString());

                                        Log.d("json_response", String.valueOf(obj.getBoolean("status")));
                                        if (String.valueOf(obj.getBoolean("status")).equals("true")) {
                                            Toast.makeText(context, trap_id + " has been transfered to server successfully.",
                                                    Toast.LENGTH_SHORT).show();
                                            int del_count = (int) dbHandler.deleteDataBgTrap(trap_id);
                                            if (del_count > 0) {
                                                Toast.makeText(context, trap_id + " has been deleted from mobile database successfully.",
                                                        Toast.LENGTH_SHORT).show();
                                            }

                                        } else {
                                            Toast.makeText(context, "Error in transfering " + trap_id + " to server." + obj.get("message") + " Please try again.",
                                                    Toast.LENGTH_SHORT).show();
                                        }

                                    } catch (Throwable t) {

                                    }
                                }
                            }.execute();

                        }
                    }

                    AsyncTask asyncTaskGetBgService = new AsyncTask() {

                        @Override
                        protected Object doInBackground(Object[] objects) {
                            OkHttpClient client = new OkHttpClient();
                            Request request = new Request.Builder().url("http://192.168.8.101/api/index.php/api/BgServiceController/indexBgService").build();
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


                            try {
                                JSONArray jsonArr = new JSONArray(o.toString());
                                long sync_status = -1;
                                for (int i = 0; i < jsonArr.length(); i++) {
                                    JSONObject jsonObj = jsonArr.getJSONObject(i);
                                    String bg_run_id = jsonObj.getString("bg_run_id");
                                    String trap_id = jsonObj.getString("trap_id");
                                    String trap_position = jsonObj.getString("trap_position");
                                    String coordinates = jsonObj.getString("coordinates");
                                    String add_line1 = jsonObj.getString("add_line1");
                                    String add_line2 = jsonObj.getString("add_line2");
                                    String location_description = jsonObj.getString("location_description");
                                    String full_name = jsonObj.getString("full_name");
                                    String contact_number = jsonObj.getString("contact_number");
                                    bgServiceModelList = new ArrayList<>();
                                    bgServiceModelList = dbHandler.getSingleBgServiceTrap(bg_run_id, trap_id);
                                    if (bgServiceModelList.size() == 0) {
                                        BgServiceModel bgService = new BgServiceModel(bg_run_id, trap_id, trap_position, coordinates,
                                                add_line1, add_line2, location_description, full_name, contact_number);

                                        sync_status = dbHandler.insertDataBgService(bgService);
                                        if (sync_status > 1) {
                                            Toast.makeText(context, trap_id + " of " + bg_run_id + " has been added to mobile database.",
                                                    Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(context, trap_id + " of " + bg_run_id + " has occured an error in adding to mobile database.",
                                                    Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                }
                            } catch (Throwable t) {

                            }
                        }
                    }.execute();
                    AsyncTask asyncTaskGetBgCollection = new AsyncTask() {

                        @Override
                        protected Object doInBackground(Object[] objects) {
                            OkHttpClient client = new OkHttpClient();
                            Request request = new Request.Builder().url("http://192.168.8.101/api/index.php/api/BgCollectionController/indexBgCollection").build();
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

                            try {
                                JSONArray jsonArr = new JSONArray(o.toString());
                                long sync_status = -1;
                                for (int i = 0; i < jsonArr.length(); i++) {
                                    JSONObject jsonObj = jsonArr.getJSONObject(i);
                                    String bg_run_id = jsonObj.getString("bg_run_id");
                                    String trap_id = jsonObj.getString("trap_id");
                                    String trap_position = jsonObj.getString("trap_position");
                                    String coordinates = jsonObj.getString("coordinates");
                                    String add_line1 = jsonObj.getString("add_line1");
                                    String add_line2 = jsonObj.getString("add_line2");
                                    String location_description = jsonObj.getString("location_description");
                                    String full_name = jsonObj.getString("full_name");
                                    String contact_number = jsonObj.getString("contact_number");
                                    bgCollectionModelList = new ArrayList<>();
                                    bgCollectionModelList = dbHandler.getSingleBgCollectionTrap(bg_run_id, trap_id);
                                    if (bgCollectionModelList.size() == 0) {
                                        BgCollectionModel bgCollection = new BgCollectionModel(bg_run_id, trap_id, trap_position, coordinates,
                                                add_line1, add_line2, location_description, full_name, contact_number);

                                        sync_status = dbHandler.insertDataBgCollection(bgCollection);
                                        if (sync_status > 1) {
                                            Toast.makeText(context, trap_id + " of " + bg_run_id + " has been added to mobile database.",
                                                    Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(context, trap_id + " of " + bg_run_id + " has occured an error in adding to mobile database.",
                                                    Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                }
                            } catch (Throwable t) {

                            }
                            Intent intent = new Intent(context, OvListActivity.class);
                            intent.putExtra("type", "bg");
                            startActivity(intent);

                        }
                    }.execute();
                }

                if (field_type.equals("mrc")) {
                    Toast.makeText(context, "Data synchronization is being processed. Please wait.",
                            Toast.LENGTH_SHORT).show();

                    mrcServiceModelList = new ArrayList<>();
                    mrcServiceModelList = dbHandler.getAllMrcServicesForSync();
                    if (mrcServiceModelList.size() != 0) {
                        for (int i = 0; i < mrcServiceModelList.size(); ++i) {
                            String service_id = mrcServiceModelList.get(i).getService_id();
                            String trap_id = mrcServiceModelList.get(i).getMrc_trap_id();
                            String service_date = mrcServiceModelList.get(i).getDate();
                            String service_time = mrcServiceModelList.get(i).getTime();
                            String service_status = mrcServiceModelList.get(i).getService_status();
                            String run_id = mrcServiceModelList.get(i).getMrc_run_id();
                            String append_url = service_id + "/" + trap_id + "/" + service_date + "/" + service_time + "/" + service_status + "/" + run_id;
                            AsyncTask asyncTask = new AsyncTask() {

                                @Override
                                protected Object doInBackground(Object[] objects) {
                                    OkHttpClient client = new OkHttpClient();
                                    Request request = new Request.Builder().url("http://192.168.8.101/api/index.php/api/mrcServiceController/insertMrcService/" + append_url).build();
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

                                        JSONObject obj = new JSONObject(o.toString());

                                        Log.d("json_response", String.valueOf(obj.getBoolean("status")));
                                        if (String.valueOf(obj.getBoolean("status")).equals("true")) {
                                            Toast.makeText(context, service_id + " has been transfered to server successfully.",
                                                    Toast.LENGTH_SHORT).show();
                                            int del_count = (int) dbHandler.deleteDataMrcService(service_id);
                                            if (del_count > 0) {
                                                Toast.makeText(context, service_id + " has been deleted from mobile database successfully.",
                                                        Toast.LENGTH_SHORT).show();
                                            }

                                        } else {
                                            Toast.makeText(context, "Error in transfering " + service_id + " to server." + obj.get("message") + " Please try again.",
                                                    Toast.LENGTH_SHORT).show();
                                        }

                                    } catch (Throwable t) {

                                    }
                                }
                            }.execute();

                        }
                    }
                    mrcReleaseModelList = new ArrayList<>();
                    mrcReleaseModelList = dbHandler.getAllMrcReleaseForSync();
                    if (mrcReleaseModelList.size() != 0) {
                        for (int i = 0; i < mrcReleaseModelList.size(); ++i) {
                            String release_id = mrcReleaseModelList.get(i).getRelease_id();
                            String trap_id = mrcReleaseModelList.get(i).getMrc_trap_id();
                            String release_date = mrcReleaseModelList.get(i).getDate();
                            String release_time = mrcReleaseModelList.get(i).getTime();
                            String release_status = mrcReleaseModelList.get(i).getRelease_status();
                            String run_id = mrcReleaseModelList.get(i).getMrc_run_id();
                            String append_url = release_id + "/" + trap_id + "/" + release_date + "/" + release_time + "/" + release_status + "/" + run_id;
                            AsyncTask asyncTask = new AsyncTask() {

                                @Override
                                protected Object doInBackground(Object[] objects) {
                                    OkHttpClient client = new OkHttpClient();
                                    Request request = new Request.Builder().url("http://192.168.8.101/api/index.php/api/MrcReleaseController/insertMrcRelease/" + append_url).build();
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

                                        JSONObject obj = new JSONObject(o.toString());

                                        Log.d("json_response", String.valueOf(obj.getBoolean("status")));
                                        if (String.valueOf(obj.getBoolean("status")).equals("true")) {
                                            Toast.makeText(context, release_id + " has been transfered to server successfully.",
                                                    Toast.LENGTH_SHORT).show();
                                            int del_count = (int) dbHandler.deleteDataMrcRelease(release_id);
                                            if (del_count > 0) {
                                                Toast.makeText(context, release_id + " has been deleted from mobile database successfully.",
                                                        Toast.LENGTH_SHORT).show();
                                            }

                                        } else {
                                            Toast.makeText(context, "Error in transfering " + release_id + " to server." + obj.get("message") + " Please try again.",
                                                    Toast.LENGTH_SHORT).show();
                                        }

                                    } catch (Throwable t) {

                                    }
                                }
                            }.execute();

                        }
                    }
                    mrcModelList = new ArrayList<>();
                    mrcModelList = dbHandler.getAllMrcTrapForSync();
                    if (mrcModelList.size() > 0) {
                        for (int i = 0; i < mrcModelList.size(); ++i) {
                            String trap_id = mrcModelList.get(i).getIdentifier();
                            String trap_status = mrcModelList.get(i).getMrc_status();
                            String coordinates = mrcModelList.get(i).getCoordinates();
                            String mrc_date = mrcModelList.get(i).getDate();
                            String mrc_time = mrcModelList.get(i).getTime();
                            String person_name = mrcModelList.get(i).getPerson_name();
                            String person_phone = mrcModelList.get(i).getPerson_phone();
                            String add_line1 = mrcModelList.get(i).getAddress_line1();
                            String add_line2 = mrcModelList.get(i).getAddress_line2();
                            String location_description = mrcModelList.get(i).getLocation_description();

                            String append_url = trap_id + "/" + trap_status + "/" + coordinates + "/" + mrc_date + "/" + mrc_time + "/" + person_name
                                    + "/" + person_phone + "/" + add_line1 + "/" + add_line2 + "/" + location_description;
                            AsyncTask asyncTask = new AsyncTask() {

                                @Override
                                protected Object doInBackground(Object[] objects) {
                                    OkHttpClient client = new OkHttpClient();
                                    Request request = new Request.Builder().url("http://192.168.8.101/api/index.php/api/MrcTrapController/insertMrcTrap/" + append_url).build();
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

                                        JSONObject obj = new JSONObject(o.toString());

                                        Log.d("json_response", String.valueOf(obj.getBoolean("status")));
                                        if (String.valueOf(obj.getBoolean("status")).equals("true")) {
                                            Toast.makeText(context, trap_id + " has been transfered to server successfully.",
                                                    Toast.LENGTH_SHORT).show();
                                            int del_count = (int) dbHandler.deleteDataMrcTrap(trap_id);
                                            if (del_count > 0) {
                                                Toast.makeText(context, trap_id + " has been deleted from mobile database successfully.",
                                                        Toast.LENGTH_SHORT).show();
                                            }

                                        } else {
                                            Toast.makeText(context, "Error in transfering " + trap_id + " to server." + obj.get("message") + " Please try again.",
                                                    Toast.LENGTH_SHORT).show();
                                        }

                                    } catch (Throwable t) {

                                    }
                                }
                            }.execute();

                        }
                    }

                    AsyncTask asyncTaskGetMrcService = new AsyncTask() {

                        @Override
                        protected Object doInBackground(Object[] objects) {
                            OkHttpClient client = new OkHttpClient();
                            Request request = new Request.Builder().url("http://192.168.8.101/api/index.php/api/MrcServiceController/indexMrcService").build();
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


                            try {
                                JSONArray jsonArr = new JSONArray(o.toString());
                                long sync_status = -1;
                                for (int i = 0; i < jsonArr.length(); i++) {
                                    JSONObject jsonObj = jsonArr.getJSONObject(i);
                                    String mrc_run_id = jsonObj.getString("mrc_run_id");
                                    String trap_id = jsonObj.getString("trap_id");
                                    String coordinates = jsonObj.getString("coordinates");
                                    String add_line1 = jsonObj.getString("add_line1");
                                    String add_line2 = jsonObj.getString("add_line2");
                                    String location_description = jsonObj.getString("location_description");
                                    String full_name = jsonObj.getString("full_name");
                                    String contact_number = jsonObj.getString("contact_number");
                                    mrcServiceModelList = new ArrayList<>();
                                    mrcServiceModelList = dbHandler.getSingleMrcServiceTrap(mrc_run_id, trap_id);
                                    if (mrcServiceModelList.size() == 0) {
                                        MrcServiceModel mrcService = new MrcServiceModel(mrc_run_id, trap_id, coordinates,
                                                add_line1, add_line2, location_description, full_name, contact_number);

                                        sync_status = dbHandler.insertDataMrcService(mrcService);
                                        if (sync_status > 1) {
                                            Toast.makeText(context, trap_id + " of " + mrc_run_id + " has been added to mobile database.",
                                                    Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(context, trap_id + " of " + mrc_run_id + " has occured an error in adding to mobile database.",
                                                    Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                }
                            } catch (Throwable t) {

                            }
                        }
                    }.execute();
                    AsyncTask asyncTaskGetMrcRelease = new AsyncTask() {

                        @Override
                        protected Object doInBackground(Object[] objects) {
                            OkHttpClient client = new OkHttpClient();
                            Request request = new Request.Builder().url("http://192.168.8.101/api/index.php/api/MrcReleaseController/indexMrcRelease").build();
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

                            try {
                                JSONArray jsonArr = new JSONArray(o.toString());
                                long sync_status = -1;
                                for (int i = 0; i < jsonArr.length(); i++) {
                                    JSONObject jsonObj = jsonArr.getJSONObject(i);
                                    String mrc_run_id = jsonObj.getString("mrc_run_id");
                                    String trap_id = jsonObj.getString("trap_id");
                                    String coordinates = jsonObj.getString("coordinates");
                                    String add_line1 = jsonObj.getString("add_line1");
                                    String add_line2 = jsonObj.getString("add_line2");
                                    String location_description = jsonObj.getString("location_description");
                                    String full_name = jsonObj.getString("full_name");
                                    String contact_number = jsonObj.getString("contact_number");
                                    mrcReleaseModelList = new ArrayList<>();
                                    mrcReleaseModelList = dbHandler.getSingleMrcReleaseTrap(mrc_run_id, trap_id);
                                    if (mrcReleaseModelList.size() == 0) {
                                        MrcReleaseModel mrcRelease = new MrcReleaseModel(mrc_run_id, trap_id, coordinates,
                                                add_line1, add_line2, location_description, full_name, contact_number);

                                        sync_status = dbHandler.insertDataMrcRelease(mrcRelease);
                                        if (sync_status > 1) {
                                            Toast.makeText(context, trap_id + " of " + mrc_run_id + " has been added to mobile database.",
                                                    Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(context, trap_id + " of " + mrc_run_id + " has occured an error in adding to mobile database.",
                                                    Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                }
                            } catch (Throwable t) {

                            }
                            Intent intent = new Intent(context, OvListActivity.class);
                            intent.putExtra("type", "mrc");
                            startActivity(intent);

                        }
                    }.execute();
                }

            }

            ;


        });
        username_text = (TextView) findViewById(R.id.textViewUsername);
        sharedpreferences = getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        username_text.setText(sharedpreferences.getString("UserName", ""));

    }

    public void goMapView(View pView) {
        String run_name = spinnerRuns.getSelectedItem().toString();
        Intent intent = new Intent(context, MapsActivity.class);
        intent.putExtra("run_name", run_name);
        intent.putExtra("field_type", field_type);
        startActivity(intent);
    }

    public void goNewOv(View pView) {
        if (field_type.equals("bg")) {
            Intent intent = new Intent(context, AddBgMainActivity.class);
            intent.putExtra("form-type", "new");
            startActivity(intent);
        }
        if (field_type.equals("ov")) {
            Intent intent = new Intent(context, AddOvMainActivity.class);
            intent.putExtra("form-type", "new");
            startActivity(intent);
        }
        if (field_type.equals("mrc")) {
            Intent intent = new Intent(context, AddMrcMainActivity.class);
            intent.putExtra("form-type", "new");
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
            editor.putString(OviTrapId, ovPersonAddressModelList.get(0).ov_trap_id.toString());
            editor.putString(OriginalOviId, ovPersonAddressModelList.get(0).ov_trap_id.toString());
            editor.putString(TrapStatus, ovPersonAddressModelList.get(0).trap_status.toString());
            editor.putString(TrapPosition, ovPersonAddressModelList.get(0).position.toString());
            editor.putString(RespondName, ovPersonAddressModelList.get(0).person_name);
            editor.putString(LocationCoordinates, ovPersonAddressModelList.get(0).coordinates);
            editor.putString(Phone, String.valueOf(ovPersonAddressModelList.get(0).person_phone));
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
            editor.putString(BgTrapId, bgPersonAddressModelList.get(0).bg_trap_id.toString());
            editor.putString(OriginalBgId, bgPersonAddressModelList.get(0).bg_trap_id.toString());
            editor.putString(TrapStatus, bgPersonAddressModelList.get(0).trap_status.toString());
            editor.putString(TrapPosition, bgPersonAddressModelList.get(0).position.toString());
            editor.putString(RespondName, bgPersonAddressModelList.get(0).person_name);
            editor.putString(LocationCoordinates, bgPersonAddressModelList.get(0).coordinates);
            editor.putString(Phone, String.valueOf(bgPersonAddressModelList.get(0).person_phone));
            editor.putString(AddressLine1, bgPersonAddressModelList.get(0).address_line1);
            editor.putString(AddressLine2, bgPersonAddressModelList.get(0).address_line2);
            editor.putString(LocationDescription, bgPersonAddressModelList.get(0).location_description);
            editor.putString(BgRunId, bgPersonAddressModelList.get(0).run_name);
            editor.apply();

            Intent intent = new Intent(context, AddBgMainActivity.class);
            intent.putExtra("TrapId", v.getTag().toString());
            intent.putExtra("form-type", "edit-new");
            startActivity(intent);
        }
        if (field_type.equals("mrc")) {
            dbHandler = new DbHandler(context);
            mrcPersonAddressModelList = new ArrayList<>();
            mrcPersonAddressModelList = dbHandler.getSingleMrcPersonAddress(v.getTag().toString());
            sharedpreferences = getSharedPreferences(MrcDetails, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString(MrcId, mrcPersonAddressModelList.get(0).identifier.toString());
            editor.putString(OriginalMrcId, mrcPersonAddressModelList.get(0).identifier.toString());
            editor.putString(MrcStatus, mrcPersonAddressModelList.get(0).mrc_status.toString());
            editor.putString(RespondName, mrcPersonAddressModelList.get(0).person_name);
            editor.putString(LocationCoordinates, mrcPersonAddressModelList.get(0).coordinates);
            editor.putString(Phone, String.valueOf(mrcPersonAddressModelList.get(0).person_phone));
            editor.putString(AddressLine1, mrcPersonAddressModelList.get(0).address_line1);
            editor.putString(AddressLine2, mrcPersonAddressModelList.get(0).address_line2);
            editor.putString(LocationDescription, mrcPersonAddressModelList.get(0).location_description);
            editor.putString(MrcRunId, mrcPersonAddressModelList.get(0).run_name);
            editor.apply();
            Intent intent = new Intent(context, AddMrcMainActivity.class);
            intent.putExtra("TrapId", v.getTag().toString());
            intent.putExtra("form-type", "edit-new");
            startActivity(intent);
        }
    }

    public void goIndividualBgService(View v) {
        dbHandler = new DbHandler(context);
        bgServiceModelList = new ArrayList<>();
        bgServiceModelList = dbHandler.getSingleBgServiceTrap(run_name, v.getTag().toString());
        sharedpreferences = getSharedPreferences(BgServiceDetails, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(BgRunId, bgServiceModelList.get(0).bg_run_id.toString());
        editor.putString(BgTrapId, bgServiceModelList.get(0).trap_id.toString());
        editor.putString(TrapPosition, bgServiceModelList.get(0).trap_position.toString());
        editor.putString(LocationCoordinates, bgServiceModelList.get(0).coordinates);
        editor.putString(AddressLine1, bgServiceModelList.get(0).add_line1);
        editor.putString(AddressLine2, bgServiceModelList.get(0).add_line2);
        editor.putString(LocationDescription, bgServiceModelList.get(0).location_description);
        editor.putString(RespondName, bgServiceModelList.get(0).full_name);
        editor.putString(Phone, bgServiceModelList.get(0).contact_number);
        editor.putString(ServiceId, bgServiceModelList.get(0).service_id);
        editor.putString(ServiceStatus, bgServiceModelList.get(0).service_status);
        editor.apply();

        Intent intent = new Intent(context, AddBgServiceMainActivity.class);
        startActivity(intent);
    }

    public void goIndividualBgCollection(View v) {
        dbHandler = new DbHandler(context);
        bgCollectionModelList = new ArrayList<>();
        bgCollectionModelList = dbHandler.getSingleBgCollectionTrap(run_name, v.getTag().toString());
        sharedpreferences = getSharedPreferences(BgCollectionDetails, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(BgRunId, bgCollectionModelList.get(0).bg_run_id.toString());
        editor.putString(BgTrapId, bgCollectionModelList.get(0).trap_id.toString());
        editor.putString(TrapPosition, bgCollectionModelList.get(0).trap_position.toString());
        editor.putString(LocationCoordinates, bgCollectionModelList.get(0).coordinates);
        editor.putString(AddressLine1, bgCollectionModelList.get(0).add_line1);
        editor.putString(AddressLine2, bgCollectionModelList.get(0).add_line2);
        editor.putString(LocationDescription, bgCollectionModelList.get(0).location_description);
        editor.putString(RespondName, bgCollectionModelList.get(0).full_name);
        editor.putString(Phone, bgCollectionModelList.get(0).contact_number);
        editor.putString(CollectionId, bgCollectionModelList.get(0).collection_id);
        editor.putString(CollectionStatus, bgCollectionModelList.get(0).collection_status);
        editor.apply();

        Intent intent = new Intent(context, AddBgCollectionMainActivity.class);
        startActivity(intent);
    }

    public void goIndividualOviService(View v) {
        dbHandler = new DbHandler(context);
        ovServiceModelList = new ArrayList<>();
        ovServiceModelList = dbHandler.getSingleOviServiceTrap(run_name, v.getTag().toString());
        sharedpreferences = getSharedPreferences(OviServiceDetails, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(OviRunId, ovServiceModelList.get(0).ovi_run_id.toString());
        editor.putString(OviTrapId, ovServiceModelList.get(0).trap_id.toString());
        editor.putString(TrapPosition, ovServiceModelList.get(0).trap_position.toString());
        editor.putString(LocationCoordinates, ovServiceModelList.get(0).coordinates);
        editor.putString(AddressLine1, ovServiceModelList.get(0).add_line1);
        editor.putString(AddressLine2, ovServiceModelList.get(0).add_line2);
        editor.putString(LocationDescription, ovServiceModelList.get(0).location_description);
        editor.putString(RespondName, ovServiceModelList.get(0).full_name);
        editor.putString(Phone, ovServiceModelList.get(0).contact_number);
        editor.putString(ServiceId, ovServiceModelList.get(0).service_id);
        editor.putString(ServiceStatus, ovServiceModelList.get(0).service_status);
        editor.apply();
        Intent intent = new Intent(context, AddOviServiceMainActivity.class);
        startActivity(intent);
    }

    public void goIndividualOviCollection(View v) {
        dbHandler = new DbHandler(context);
        ovCollectionModelList = new ArrayList<>();
        ovCollectionModelList = dbHandler.getSingleOviCollectionTrap(run_name, v.getTag().toString());
        sharedpreferences = getSharedPreferences(OviCollectionDetails, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(OviRunId, ovCollectionModelList.get(0).ovi_run_id.toString());
        editor.putString(OviTrapId, ovCollectionModelList.get(0).trap_id.toString());
        editor.putString(TrapPosition, ovCollectionModelList.get(0).trap_position.toString());
        editor.putString(LocationCoordinates, ovCollectionModelList.get(0).coordinates);
        editor.putString(AddressLine1, ovCollectionModelList.get(0).add_line1);
        editor.putString(AddressLine2, ovCollectionModelList.get(0).add_line2);
        editor.putString(LocationDescription, ovCollectionModelList.get(0).location_description);
        editor.putString(RespondName, ovCollectionModelList.get(0).full_name);
        editor.putString(Phone, ovCollectionModelList.get(0).contact_number);
        editor.putString(CollectionId, ovCollectionModelList.get(0).collection_id);
        editor.putString(CollectionStatus, ovCollectionModelList.get(0).collection_status);
        editor.apply();
        Intent intent = new Intent(context, AddOviCollectionMainActivity.class);
        startActivity(intent);
    }

    public void goIndividualMrcService(View v) {
        dbHandler = new DbHandler(context);
        mrcServiceModelList = new ArrayList<>();
        mrcServiceModelList = dbHandler.getSingleMrcServiceTrap(run_name, v.getTag().toString());
        sharedpreferences = getSharedPreferences(MrcServiceDetails, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(MrcRunId, mrcServiceModelList.get(0).mrc_run_id.toString());
        editor.putString(MrcTrapId, mrcServiceModelList.get(0).trap_id.toString());
        editor.putString(LocationCoordinates, mrcServiceModelList.get(0).coordinates);
        editor.putString(AddressLine1, mrcServiceModelList.get(0).add_line1);
        editor.putString(AddressLine2, mrcServiceModelList.get(0).add_line2);
        editor.putString(LocationDescription, mrcServiceModelList.get(0).location_description);
        editor.putString(RespondName, mrcServiceModelList.get(0).full_name);
        editor.putString(Phone, mrcServiceModelList.get(0).contact_number);
        editor.putString(ServiceId, mrcServiceModelList.get(0).service_id);
        editor.putString(ServiceStatus, mrcServiceModelList.get(0).service_status);
        editor.apply();
        Intent intent = new Intent(context, AddMrcServiceMainActivity.class);
        startActivity(intent);
    }

    public void goIndividualMrcRelease(View v) {
        dbHandler = new DbHandler(context);
        mrcReleaseModelList = new ArrayList<>();
        mrcReleaseModelList = dbHandler.getSingleMrcReleaseTrap(run_name, v.getTag().toString());
        sharedpreferences = getSharedPreferences(MrcReleaseDetails, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(MrcRunId, mrcReleaseModelList.get(0).mrc_run_id.toString());
        editor.putString(MrcTrapId, mrcReleaseModelList.get(0).trap_id.toString());
        editor.putString(LocationCoordinates, mrcReleaseModelList.get(0).coordinates);
        editor.putString(AddressLine1, mrcReleaseModelList.get(0).add_line1);
        editor.putString(AddressLine2, mrcReleaseModelList.get(0).add_line2);
        editor.putString(LocationDescription, mrcReleaseModelList.get(0).location_description);
        editor.putString(RespondName, mrcReleaseModelList.get(0).full_name);
        editor.putString(Phone, mrcReleaseModelList.get(0).contact_number);
        editor.putString(ReleaseId, mrcReleaseModelList.get(0).release_id);
        editor.putString(ReleaseStatus, mrcReleaseModelList.get(0).release_status);
        editor.apply();
        Intent intent = new Intent(context, AddMrcReleaseMainActivity.class);

        startActivity(intent);
    }

    public void goMainMenu(View v) {
        Intent intent = new Intent(context, MainMenuActivity.class);
        startActivity(intent);
    }
    public void logout(View pView){
        Intent intent = new Intent(context, LoginActivityController.class);
        startActivity(intent);
    }

}
