package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DownloadManager;
import android.app.VoiceInteractor;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.card.MaterialCardView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Iterator;

public class MainActivity extends AppCompatActivity {

    EditText sc;
    MaterialCardView scb;
    ImageView wimg;
    TextView wdesc, wtemp, wlocal, wquery, wpressure, whumidity, wwind, wvisibility, wcloud, wuv, wwinddeg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sc = findViewById(R.id.sc);

        scb = findViewById(R.id.scb);

        wimg = findViewById(R.id.wimg);

        wdesc = findViewById(R.id.wdesc);
        wtemp = findViewById(R.id.wtemp);
        wlocal = findViewById(R.id.wlocal);
        wquery = findViewById(R.id.wquery);
        wpressure = findViewById(R.id.wpressure);
        whumidity = findViewById(R.id.whumidity);
        wwind = findViewById(R.id.wwind);
        wvisibility = findViewById(R.id.wvisibility);
        wcloud = findViewById(R.id.wcloud);
        wuv = findViewById(R.id.wuv);
        wwinddeg = findViewById(R.id.wwinddeg);

        defaultweather();

        scb.setOnClickListener(view->{
            weather();
        });

    }

    public void defaultweather()
    {
        String APIURL = "http://api.weatherstack.com/current?access_key=913ca1038d7fe739f136f51d1dd9ca73&query=Karachi";
        RequestQueue reg;
        reg = Volley.newRequestQueue(this);

        JsonObjectRequest json = new JsonObjectRequest(Request.Method.GET, APIURL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //Toast.makeText(MainActivity.this, response+"", Toast.LENGTH_SHORT).show();\
                try {
                    JSONObject locate = new JSONObject(response.getString("location"));

                    JSONObject region = new JSONObject(response.getString("request"));

                    JSONObject cur = new JSONObject(response.getString("current"));

                    String cel = cur.getString("temperature");
                    int far = Integer.parseInt(cel);
                    int farenhite = far+32;

                    wdesc.setText("C / "+farenhite+" F");
                    wtemp.setText(""+cur.getString("temperature"));
                    wlocal.setText(""+locate.getString("localtime"));
                    wquery.setText(""+region.getString("query"));
                    wpressure.setText(""+cur.getString("pressure")+" mb");
                    whumidity.setText(""+cur.getString("humidity")+" %");
                    wwind.setText(""+cur.getString("wind_speed")+" km/h");
                    wvisibility.setText(""+cur.getString("visibility")+" m");
                    wcloud.setText(""+cur.getString("cloudcover")+" oktas");
                    wuv.setText(""+cur.getString("uv_index")+" UV");
                    wwinddeg.setText(""+cur.getString("wind_degree"));

                }

                catch (Exception ex)
                {
                    ex.printStackTrace();
                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                wpressure.setText(error.getLocalizedMessage().toString());
            }

        });
        reg.add(json);
    }

    public void weather()
    {
        String name = sc.getText().toString();

        if(name.isEmpty())
        {
            sc.setError("Enter City/ Region/ Country");
            sc.requestFocus();
            return;
        }

        String APIURL = "http://api.weatherstack.com/current?access_key=913ca1038d7fe739f136f51d1dd9ca73&query="+name;
        RequestQueue reg;
        reg = Volley.newRequestQueue(this);

        JsonObjectRequest json = new JsonObjectRequest(Request.Method.GET, APIURL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //Toast.makeText(MainActivity.this, response+"", Toast.LENGTH_SHORT).show();\
                try {
                    JSONObject locate = new JSONObject(response.getString("location"));

                    JSONObject region = new JSONObject(response.getString("request"));

                    JSONObject cur = new JSONObject(response.getString("current"));

                    String cel = cur.getString("temperature");
                    int far = Integer.parseInt(cel);
                    int farenhite = far+32;

                    wdesc.setText("C / "+farenhite+" F");
                    wtemp.setText(""+cur.getString("temperature"));
                    wlocal.setText(""+locate.getString("localtime"));
                    wquery.setText(""+region.getString("query"));
                    wpressure.setText(""+cur.getString("pressure")+" mb");
                    whumidity.setText(""+cur.getString("humidity")+" %");
                    wwind.setText(""+cur.getString("wind_speed")+" km/h");
                    wvisibility.setText(""+cur.getString("visibility")+" m");
                    wcloud.setText(""+cur.getString("cloudcover")+" oktas");
                    wuv.setText(""+cur.getString("uv_index")+" UV");
                    wwinddeg.setText(""+cur.getString("wind_degree")+""+cur.getString("winddir"));
                }

                catch (Exception ex)
                {

                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {

            }

        });
        reg.add(json);
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Exit")
                .setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        moveTaskToBack(true);
                        android.os.Process.killProcess(android.os.Process.myPid());
                        System.exit(1);
                    }
                }).create().show();

    }

}