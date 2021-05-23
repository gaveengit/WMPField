package ucsc.mit.wmp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class IncidentContactActivity extends AppCompatActivity {
    final Context context = this;
    public static final String stakeholderName = "stakeholderName";
    public static final String email = "email";
    public static final String phone = "phone";
    public static final String incidentDetails = "incidents" ;
    SharedPreferences sharedpreferences;
    EditText stakeholderNameInput;
    EditText emailInput;
    EditText phoneInput;
    TextView errorText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.incident_contact);
    }
    public void goIncidentDescription(View pView) {
        stakeholderNameInput = (EditText) findViewById(R.id.editTextStakeholderName);
        emailInput = (EditText) findViewById(R.id.editTextEmail);
        phoneInput = (EditText) findViewById(R.id.editTextPhone);
        errorText = (TextView) findViewById(R.id.errorContainer);
        if((stakeholderNameInput.getText().toString().length()==0) || (phoneInput.getText().toString().length()==0)){
            errorText.setVisibility(View.VISIBLE);
            errorText.setText("Please fill all required fields.");
        }
        else {
            errorText.setVisibility(View.INVISIBLE);
            sharedpreferences = getSharedPreferences(incidentDetails, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString(stakeholderName, stakeholderNameInput.getText().toString());
            editor.putString(email, emailInput.getText().toString());
            editor.putString(phone, phoneInput.getText().toString());
            editor.apply();
            Intent intent = new Intent(context, IncidentDescriptionActivity.class);
            startActivity(intent);
        }

    }
    public void goMainMenu(View pView){
        Intent intent = new Intent(context, MainMenuActivity.class);
        startActivity(intent);
    }
}


