package com.example.rivu.downloaddata;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private static final String DEBUG_TAG = "DownloadData";
    //private EditText urlText;
    private TextView textView;
    private Button download;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView)findViewById(R.id.textView2);
        download = (Button)findViewById(R.id.button);


        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
           /* Bundle bundle = new Bundle ();
            Intent intent =  new Intent(getApplicationContext(), SaveActivity.class);
            bundle.putString(NAME , mName.getText().toString());
            bundle.putString(EMAIL , mEmail.getText().toString());
            intent.putExtras(bundle);
            startActivity(intent); */
                String stringUrl = "https://www.iiitd.ac.in/about";
                ConnectivityManager connMgr = (ConnectivityManager)
                        getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected()) {
                    new DownloadWebpageTask().execute(stringUrl);
                } else {
                    textView.setText("No network connection available.");
                }
            }
        });
    }

   /* public void myClickHandler(View view) {
        // Gets the URL from the UI's text field.
        String stringUrl = "https://www.iiitd.ac.in/about";
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            new DownloadWebpageTask().execute(stringUrl);
        } else {
            textView.setText("No network connection available.");
        }
    } */



    private class DownloadWebpageTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            // params comes from the execute() call: params[0] is the url.
            try {
                return downloadUrl(urls[0]);
            } catch (IOException e) {
                return "Unable to retrieve web page. URL may be invalid.";
            }
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            Bundle bundle = new Bundle();
            Intent intent = new Intent(getApplicationContext(),Main2Activity.class);
            bundle.putString("Details",result);
            intent.putExtras(bundle);
            startActivity(intent);

        }
    }
    public String downloadUrl(String myurl) throws IOException {
        InputStream is = null;
        // Only display the first 500 characters of the retrieved
        // web page content.
        //int len = 100000;

        try {
            URL url = new URL(myurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            // Starts the query
            conn.connect();
            int response = conn.getResponseCode();
            Log.d(DEBUG_TAG, "The response is: " + response);
            is = conn.getInputStream();

            // Convert the InputStream into a string
            String contentAsString = readIt(is);
            return contentAsString;

            // Makes sure that the InputStream is closed after the app is
            // finished using it.
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }


    public String readIt(InputStream stream) throws IOException, UnsupportedEncodingException {
        StringBuilder builder  = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(stream));
        String buff = null;
        while((buff = br.readLine())!=null){
            builder.append(buff);
        }
        return (builder.toString());
    }


}
