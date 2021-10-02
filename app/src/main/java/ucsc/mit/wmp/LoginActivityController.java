package ucsc.mit.wmp;

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

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LoginActivityController extends AppCompatActivity {
    final Context context = this;
    Button button;
    EditText username;
    EditText password;
    SharedPreferences sharedpreferences;
    public static final String LoginDetails = "LoginDetails";
    public static final String UserId = "UserId";
    public static final String FirstName = "FirstName";
    public static final String LastName = "LastName";
    public static final String UserName = "UserName";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_window);
        button = (Button) findViewById(R.id.loginButton);
        username = (EditText) findViewById(R.id.editTextUsername);
        password = (EditText) findViewById(R.id.editTextPassword);
        username.setText("");
        password.setText("");
        sharedpreferences = getSharedPreferences(LoginDetails, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor_clear_login_service = sharedpreferences.edit();
        editor_clear_login_service.clear();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int error_flag = 0;
                if (username.getText().toString().length() == 0) {
                    username.setError("Username is required.");
                    error_flag = 1;
                }
                if (password.getText().toString().length() == 0) {
                    password.setError("Password is required.");
                    error_flag = 1;
                }
                if (error_flag == 0) {

                    String append_url = username.getText().toString() + "/" + password.getText().toString();

                    AsyncTask asyncTaskCheckLogin = new AsyncTask() {

                        @Override
                        protected Object doInBackground(Object[] objects) {
                            OkHttpClient client = new OkHttpClient();
                            Request request = new Request.Builder().url("http://192.168.8.101/api/index.php/api/LoginController/indexCheckLogin/" + append_url).build();
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
                            int flag=0;
                            try {
                                String objres = o.toString();
                                if(objres != null && objres.equals("")==false) {
                                    JSONArray jsonArr = new JSONArray(o.toString());
                                    JSONObject jsonObj = jsonArr.getJSONObject(0);
                                   if(jsonObj != null) {
                                       sharedpreferences = getSharedPreferences(LoginDetails, Context.MODE_PRIVATE);
                                       SharedPreferences.Editor editor = sharedpreferences.edit();
                                       editor.putString(UserId, jsonObj.getString("user_id"));
                                       editor.putString(FirstName, jsonObj.getString("first_name"));
                                       editor.putString(LastName, jsonObj.getString("last_name"));
                                       editor.putString(UserName, jsonObj.getString("username"));
                                       editor.apply();
                                       username.setText("");
                                       password.setText("");
                                       flag=1;
                                       Intent intent = new Intent(context, MainMenuActivity.class);
                                       startActivity(intent);
                                   }
                                }

                            } catch (Throwable t) {

                            }
                            if(flag==0){
                                Toast.makeText(context, "Invalid credentials. Please try again.",
                                        Toast.LENGTH_LONG).show();
                            }
                        }
                    }.execute();
                }
            };
        });
    }
}
