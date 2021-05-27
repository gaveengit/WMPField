package ucsc.mit.wmp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class AddOvAdditionalActivity extends AppCompatActivity {
    final Context context = this;
    public static final String Phone = "Phone";
    public static final String AddressLine1 = "AddressLine1";
    public static final String AddressLine2 = "AddressLine2";
    public static final String LocationDescription = "LocationDescription";
    public static final String OviDetails = "OviDetails";
    EditText phone;
    EditText addressLine1;
    EditText addressLine2;
    EditText locationDescription;
    TextView errorText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_ov_additional);
        phone = (EditText) findViewById(R.id.editTextPhone);
        addressLine1 = (EditText) findViewById(R.id.editTextAdd1);
        addressLine2 = (EditText) findViewById(R.id.editTextAdd2);
        locationDescription = (EditText) findViewById(R.id.editTextLocationDescription);
        errorText = (TextView) findViewById(R.id.errorContainer);
    }
    public void goOvMain(View v)
    {
        Intent intent = new Intent(context, AddOvMainActivity.class);
        startActivity(intent);
    }
    public void submitOv(View v)
    {
        if (addressLine1.getText().toString().length() == 0 || addressLine2.getText().toString().
                length()
                ==0 ) {
            errorText.setVisibility(View.VISIBLE);
            errorText.setText("Please fill all required fields.");
        }
        else {
            errorText.setVisibility(View.INVISIBLE);
        }
    }
}
