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
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

public class Main2Activity extends AppCompatActivity {
    private TextView text1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        text1 = (TextView)findViewById(R.id.textView);

       Intent intent = getIntent();
        String text="";
        if(null != intent) {
             text = getIntent().getExtras().getString("Details");
            System.out.println(text);
        }

             // parsing the document
            Document doc = Jsoup.parseBodyFragment(text);
            Elements paragraphs = doc.select("p");  // starting with <p> tag
            System.out.println(text);  // console
            text="";
            for(Element p : paragraphs){
                text += p.text();      // converting to string
            }
        text = text.substring(text.indexOf("Indra"),text.length());
            text1.setText(text);
        }
    }
