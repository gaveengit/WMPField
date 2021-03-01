package ucsc.mit.wmp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

public class MyAsyncTask extends AsyncTask<String, String, String> {
    private Context context;
    String result;
    ProgressDialog progressDialog;

    public MyAsyncTask(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        LocationService l1 = new LocationService();
        l1.viewCoordinates();
        progressDialog = ProgressDialog.show(context,
                "Progress Dialog", null);
    }

    @Override
    protected String doInBackground(String... args) {
        int value = Integer.parseInt(args[0]);

        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            Log.v("Error: ", e.toString());
        }
        //result = "Please wait for " + (value - i ) + " seconds";
        //publishProgress(result);

        return null;
    }

    @Override
    protected void onProgressUpdate(String... text) {
        //progressDialog.setMessage(text[0]);

    }

    protected void onPostExecute(String result) {
        progressDialog.dismiss();
        Intent intent = new Intent(context, IncidentDescriptionActivity.class);
        intent.putExtra("type", "mrc");
        context.startActivity(intent);
    }

}

