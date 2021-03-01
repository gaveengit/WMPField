package ucsc.mit.wmp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class AddMrcMainActivity extends AppCompatActivity {
    final Context context = this;
    EditText txtEdit_TrapId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_mrc_main);
        String bg_id = getIntent().getStringExtra("TrapId");
        txtEdit_TrapId = (EditText) findViewById(R.id.editTextTrapId);
        txtEdit_TrapId.setText(bg_id);
    }

    public void goAdditionalMrc(View pView) {
        Intent intent = new Intent(context, AddMrcAdditionalActivity.class);
        startActivity(intent);
    }
}
