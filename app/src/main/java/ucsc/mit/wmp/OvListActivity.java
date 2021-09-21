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
    public static final String PersonId = "PersonId";
    public static final String AddressId = "AddressId";
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


    Spinner spinnerRuns;
    SharedPreferences sharedpreferences;
    String run_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.localeggs_listview);
        dbHandler = new DbHandler(context);
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



        if (field_type.equals("ov")) {
            dbHandler = new DbHandler(context);
            List<String> ovi_runs = dbHandler.getAllOviRuns();
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, ovi_runs);
            spinnerRuns.setAdapter(dataAdapter);
        }
        if (field_type.equals("bg")) {
            dbHandler = new DbHandler(context);
            List<String> bg_runs = dbHandler.getAllBgRuns();
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, bg_runs);
            spinnerRuns.setAdapter(dataAdapter);
        }

        if (field_type.equals("mrc")) {
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

                            tv2.setBackground(ContextCompat.getDrawable(context, R.drawable.substract));
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
                                        goIndividualOv(v);
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

                                tv2.setBackground(ContextCompat.getDrawable(context, R.drawable.substract));
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
                                            goIndividualOv(v);
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
                                    //RelativeLayout.LayoutParams params3 = (RelativeLayout.LayoutParams) tv2.getLayoutParams();
                                    //params3.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);

                                    tv2.setBackground(ContextCompat.getDrawable(context, R.drawable.substract));
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
                    //Log.d("size-ov",Integer.toString(ovModelList.size()));
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
                            //RelativeLayout.LayoutParams params3 = (RelativeLayout.LayoutParams) tv2.getLayoutParams();
                            //params3.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);

                            tv2.setBackground(ContextCompat.getDrawable(context, R.drawable.substract));
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
                                        goIndividualOv(v);
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
                                //RelativeLayout.LayoutParams params3 = (RelativeLayout.LayoutParams) tv2.getLayoutParams();
                                //params3.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);

                                tv2.setBackground(ContextCompat.getDrawable(context, R.drawable.substract));
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
                                            goIndividualOv(v);
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
                                    //RelativeLayout.LayoutParams params3 = (RelativeLayout.LayoutParams) tv2.getLayoutParams();
                                    //params3.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);

                                    tv2.setBackground(ContextCompat.getDrawable(context, R.drawable.substract));
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
                            //RelativeLayout.LayoutParams params3 = (RelativeLayout.LayoutParams) tv2.getLayoutParams();
                            //params3.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);

                            tv2.setBackground(ContextCompat.getDrawable(context, R.drawable.substract));
                            rl1.addView(tv2);
                            rl1.invalidate();
                            LayoutParams layoutarams;
                            layoutarams = (LayoutParams) tv2.getLayoutParams();
                            layoutarams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                            tv2.setLayoutParams(layoutarams);

                        }
                    }
                    else{
                        dbHandler = new DbHandler(context);
                        mrcServiceModelList = new ArrayList<>();
                        mrcList = new ArrayList<>();
                        mrcServiceModelList = dbHandler.getSingleMrcService(run_name, field_type);
                        if(mrcServiceModelList.size()>0){
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
                                        goIndividualOv(v);
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
                                tv2.setTextSize(16);
                                //RelativeLayout.LayoutParams params3 = (RelativeLayout.LayoutParams) tv2.getLayoutParams();
                                //params3.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);

                                tv2.setBackground(ContextCompat.getDrawable(context, R.drawable.substract));
                                rl1.addView(tv2);
                                rl1.invalidate();
                                LayoutParams layoutarams;
                                layoutarams = (LayoutParams) tv2.getLayoutParams();
                                layoutarams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                                tv2.setLayoutParams(layoutarams);

                            }
                        }
                        else{
                            dbHandler = new DbHandler(context);
                            mrcReleaseModelList = new ArrayList<>();
                            mrcList = new ArrayList<>();
                            mrcReleaseModelList = dbHandler.getSingleMrcRelease(run_name, field_type);
                            if(mrcReleaseModelList.size()>0){
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
                                            goIndividualOv(v);
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
                                    //RelativeLayout.LayoutParams params3 = (RelativeLayout.LayoutParams) tv2.getLayoutParams();
                                    //params3.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);

                                    tv2.setBackground(ContextCompat.getDrawable(context, R.drawable.substract));
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
                if (field_type.equals("bg")) {
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

                            Log.d("response", o.toString());
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

                                    BgServiceModel bgService = new BgServiceModel(bg_run_id, trap_id, trap_position, coordinates,
                                            add_line1, add_line2, location_description, full_name, contact_number);

                                    sync_status = dbHandler.insertDataBgService(bgService);
                                    Log.d("sync_status", String.valueOf(sync_status));
                                    Log.d("obj", jsonObj.getString("trap_id"));
                                }
                                if (sync_status != -1) {
                                    //Toast.makeText(context, "Data synchronization has been successfull.",
                                    //Toast.LENGTH_LONG).show();
                                    //Intent intent = new Intent(context, OvListActivity.class);
                                    //intent.putExtra("type", "bg");
                                    //startActivity(intent);
                                } else {
                                    //Toast.makeText(context, "Error in synchronization. Please try again.",
                                    //Toast.LENGTH_LONG).show();
                                    //Intent intent = new Intent(context, OvListActivity.class);
                                    //intent.putExtra("type", "bg");
                                    //startActivity(intent);
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

                            Log.d("response_col", o.toString());
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

                                    BgCollectionModel bgCollection = new BgCollectionModel(bg_run_id, trap_id, trap_position, coordinates,
                                            add_line1, add_line2, location_description, full_name, contact_number);

                                    sync_status = dbHandler.insertDataBgCollection(bgCollection);
                                    Log.d("sync_status", String.valueOf(sync_status));
                                    Log.d("obj_col", jsonObj.getString("trap_id"));
                                }
                                if (sync_status != -1) {
                                    Toast.makeText(context, "Data synchronization has been successfull.",
                                            Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(context, OvListActivity.class);
                                    intent.putExtra("type", "bg");
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(context, "Error in synchronization. Please try again.",
                                            Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(context, OvListActivity.class);
                                    intent.putExtra("type", "bg");
                                    startActivity(intent);
                                }
                            } catch (Throwable t) {

                            }
                        }
                    }.execute();

                }

                if (field_type.equals("mrc")) {
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

                            Log.d("response", o.toString());
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

                                    MrcServiceModel mrcService = new MrcServiceModel(mrc_run_id, trap_id, coordinates,
                                            add_line1, add_line2, location_description, full_name, contact_number);

                                    sync_status = dbHandler.insertDataMrcService(mrcService);
                                    Log.d("sync_status", String.valueOf(sync_status));
                                    Log.d("obj", jsonObj.getString("trap_id"));
                                }
                                if (sync_status != -1) {
                                    //Toast.makeText(context, "Data synchronization has been successfull.",
                                    //Toast.LENGTH_LONG).show();
                                    //Intent intent = new Intent(context, OvListActivity.class);
                                    //intent.putExtra("type", "mrc");
                                    //startActivity(intent);
                                } else {
                                    //Toast.makeText(context, "Error in synchronization. Please try again.",
                                    //Toast.LENGTH_LONG).show();
                                    //Intent intent = new Intent(context, OvListActivity.class);
                                    //intent.putExtra("type", "mrc");
                                    //startActivity(intent);
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

                            Log.d("response", o.toString());
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

                                    MrcReleaseModel mrcRelease = new MrcReleaseModel(mrc_run_id, trap_id, coordinates,
                                            add_line1, add_line2, location_description, full_name, contact_number);

                                    sync_status = dbHandler.insertDataMrcRelease(mrcRelease);
                                    Log.d("sync_status", String.valueOf(sync_status));
                                    Log.d("obj", jsonObj.getString("trap_id"));
                                }
                                if (sync_status != -1) {
                                    Toast.makeText(context, "Data synchronization has been successfull.",
                                            Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(context, OvListActivity.class);
                                    intent.putExtra("type", "mrc");
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(context, "Error in synchronization. Please try again.",
                                            Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(context, OvListActivity.class);
                                    intent.putExtra("type", "mrc");
                                    startActivity(intent);
                                }
                            } catch (Throwable t) {

                            }
                        }
                    }.execute();

                }
            }

            ;

        });
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
            editor.putString(TrapStatus, ovPersonAddressModelList.get(0).trap_status.toString());
            editor.putString(TrapPosition, ovPersonAddressModelList.get(0).position.toString());
            editor.putString(RespondName, ovPersonAddressModelList.get(0).person_name);
            editor.putString(LocationCoordinates, ovPersonAddressModelList.get(0).coordinates);
            //editor.putString(Phone, String.valueOf(ovPersonAddressModelList.get(0).phone));
            editor.putString(AddressLine1, ovPersonAddressModelList.get(0).address_line1);
            editor.putString(AddressLine2, ovPersonAddressModelList.get(0).address_line2);
            editor.putString(LocationDescription, ovPersonAddressModelList.get(0).location_description);
            editor.putString(OviRunId, ovPersonAddressModelList.get(0).run_name);
            //editor.putInt(PersonId, ovPersonAddressModelList.get(0).person_id);
            //editor.putInt(AddressId, ovPersonAddressModelList.get(0).address_id);
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
            editor.putString(TrapStatus, bgPersonAddressModelList.get(0).trap_status.toString());
            editor.putString(TrapPosition, bgPersonAddressModelList.get(0).position.toString());
            editor.putString(RespondName, bgPersonAddressModelList.get(0).person_name);
            editor.putString(LocationCoordinates, bgPersonAddressModelList.get(0).coordinates);
            //editor.putString(Phone, String.valueOf(bgPersonAddressModelList.get(0).phone));
            editor.putString(AddressLine1, bgPersonAddressModelList.get(0).address_line1);
            editor.putString(AddressLine2, bgPersonAddressModelList.get(0).address_line2);
            editor.putString(LocationDescription, bgPersonAddressModelList.get(0).location_description);
            editor.putString(BgRunId, bgPersonAddressModelList.get(0).run_name);
            //editor.putInt(PersonId, bgPersonAddressModelList.get(0).person_id);
            //editor.putInt(AddressId, bgPersonAddressModelList.get(0).address_id);
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
            editor.putString(MrcStatus, mrcPersonAddressModelList.get(0).mrc_status.toString());
            editor.putString(RespondName, mrcPersonAddressModelList.get(0).person_name);
            editor.putString(LocationCoordinates, mrcPersonAddressModelList.get(0).coordinates);
            // editor.putString(Phone, String.valueOf(mrcPersonAddressModelList.get(0).phone));
            editor.putString(AddressLine1, mrcPersonAddressModelList.get(0).address_line1);
            editor.putString(AddressLine2, mrcPersonAddressModelList.get(0).address_line2);
            editor.putString(LocationDescription, mrcPersonAddressModelList.get(0).location_description);
            editor.putString(MrcRunId, mrcPersonAddressModelList.get(0).run_name);
            //editor.putInt(PersonId, mrcPersonAddressModelList.get(0).person_id);
            //editor.putInt(AddressId, mrcPersonAddressModelList.get(0).address_id);
            editor.apply();
            Intent intent = new Intent(context, AddMrcMainActivity.class);
            intent.putExtra("TrapId", v.getTag().toString());
            intent.putExtra("form-type", "edit-new");
            startActivity(intent);
        }
    }

    public void goMainMenu(View v) {
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
