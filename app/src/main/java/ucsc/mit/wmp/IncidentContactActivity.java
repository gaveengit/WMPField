package ucsc.mit.wmp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class IncidentContactActivity extends AppCompatActivity {
    final Context context = this;
    public static final String stakeholderName = "stakeholderName";
    public static final String email = "email";
    public static final String phone = "phone";
    public static final String incidentDetails = "incidents";
    SharedPreferences sharedpreferences;
    EditText EditTextStakeholderNameInput;
    EditText EditTextEmailInput;
    EditText EditTextPhoneInput;
    TextView errorText;
    TextView username_text;
    TextView title;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.incident_contact);
        EditTextStakeholderNameInput = (EditText) findViewById(R.id.editTextStakeholderName);
        EditTextEmailInput = (EditText) findViewById(R.id.editTextEmail);
        EditTextPhoneInput = (EditText) findViewById(R.id.editTextPhone);
        //errorText = (TextView) findViewById(R.id.errorContainer);
        sharedpreferences = getSharedPreferences(incidentDetails, Context.MODE_PRIVATE);
        String stakeholderName = sharedpreferences.getString("stakeholderName", "");
        String email = sharedpreferences.getString("email", "");
        String phone = sharedpreferences.getString("phone", "");
        EditTextStakeholderNameInput.setText(stakeholderName);
        EditTextEmailInput.setText(email);
        EditTextPhoneInput.setText(phone);
        button = (Button) findViewById(R.id.nextbtn);
        title = (TextView) findViewById(R.id.Title);
        username_text = (TextView) findViewById(R.id.textViewUsername);
        sharedpreferences = getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        username_text.setText(sharedpreferences.getString("UserName", ""));

    }

    public void goIncidentDescription(View pView) {
        int error_flag = 0;
        if (EditTextStakeholderNameInput.getText().toString().length() == 0) {
            EditTextStakeholderNameInput.setError("Stakeholder name is required.");
            error_flag = 1;
        }
        if (EditTextPhoneInput.getText().toString().length() == 0) {
            EditTextPhoneInput.setError("Phone no is required.");
            error_flag = 1;
        }
        if (EditTextPhoneInput.getText().toString().length() != 10) {
            EditTextPhoneInput.setError("Phone no length should be 10 digits.");
            error_flag = 1;
        }

        if (EditTextEmailInput.getText().toString().length() > 0) {
            String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
            if (EditTextEmailInput.getText().toString().matches(emailPattern) == false) {
                EditTextEmailInput.setError("Invalid email address.");
            }
        }

        if (error_flag == 0) {
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString(stakeholderName, EditTextStakeholderNameInput.getText().toString());
            editor.putString(email, EditTextEmailInput.getText().toString());
            editor.putString(phone, EditTextPhoneInput.getText().toString());
            editor.apply();
            Intent intent = new Intent(context, IncidentDescriptionActivity.class);
            startActivity(intent);
        }

    }
    public void goMainMenu(View pView) {
        Intent intent = new Intent(context, MainMenuActivity.class);
        startActivity(intent);
    }
    public void logout(View pView){
        Intent intent = new Intent(context, LoginActivityController.class);
        startActivity(intent);
    }
}


