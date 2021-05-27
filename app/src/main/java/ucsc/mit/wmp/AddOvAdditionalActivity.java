package ucsc.mit.wmp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class AddOvAdditionalActivity extends AppCompatActivity {
    final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_ov_additional);
    }
    public void goOvMain(View v)
    {
        Intent intent = new Intent(context, AddOvMainActivity.class);
        startActivity(intent);
    }
}
