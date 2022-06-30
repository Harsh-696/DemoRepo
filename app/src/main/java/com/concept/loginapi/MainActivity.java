package com.concept.loginapi;

import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    TextView text;
    EditText name, password;
    Button submit;
    String url = "https://newdemo.sangamcrm.com/api/v1/login";
    RequestQueue requestQueue;
    JSONObject requestObject;
    Map<String, String> userData = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = findViewById(R.id.username);
        password = findViewById(R.id.password);
        submit = findViewById(R.id.fetch_data);
        text = findViewById(R.id.sign_in);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = name.getText().toString().trim();
                String key = password.getText().toString().trim();

                requestObject = new JSONObject();

                try {
                    requestObject.put("user_name", username);
                    requestObject.put("password", key);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                postData();

            }
        });

        Typeface typeface = Typeface.createFromAsset(this.getAssets(), "roboto_black.ttf");
        text.setTypeface(typeface);
    }

    private void postData() {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, requestObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(getApplicationContext(), "Response Received", Toast.LENGTH_SHORT).show();

                try {
                    String status_code = response.getString("status");
                    Log.e("STATUS CODE", status_code);
                    String data = response.getString("data");
                    Log.e("DATA", data);
                    JSONObject dataObject = new JSONObject(data);
                    Log.e("dataObjectSize", String.valueOf(dataObject.length()));

                    Iterator<String> iterator = dataObject.keys();

                    while (iterator.hasNext()) {
                        String key = iterator.next();
                        String value = dataObject.getString(key);
                        userData.put(key, value);
                    }

                } catch (JSONException jsonException) {
                    jsonException.printStackTrace();
                }
                for (String key : userData.keySet()) {
                    Log.e("KEYS", key);
                }
                for (String values : userData.values()) {
                    Log.e("VALUES", values);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error in receiving response " +
                        "and the error is : " + error, Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(jsonObjectRequest);
    }

    private void post() {
    }
}