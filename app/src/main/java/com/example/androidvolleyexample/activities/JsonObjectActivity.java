package com.example.androidvolleyexample.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.androidvolleyexample.R;
import com.example.androidvolleyexample.singletonclasses.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

public class JsonObjectActivity extends AppCompatActivity {

    private RequestQueue requestQueue;
    private Context context;
    private String object_json = "https://api.androidhive.info/volley/person_object.json";
    private String jsonResponse;
    private TextView txtResponse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_json_object);

        txtResponse=(TextView)findViewById(R.id.txtResponse);

        context=this;

        requestQueue= VolleySingleton.getVolleySingletonInstance().getRequestQueue();


        JsonObjectRequest jsonObjectRequest=getjsonObjectRequest();
        requestQueue.add(jsonObjectRequest);
    }

    private JsonObjectRequest getjsonObjectRequest()
    {
        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.show();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                object_json,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    // Parsing json object response
                    // response will be a json object
                    String name = response.getString("name");
                    String email = response.getString("email");
                    JSONObject phone = response.getJSONObject("phone");
                    String home = phone.getString("home");
                    String mobile = phone.getString("mobile");

                    jsonResponse = "";
                    jsonResponse += "Name: " + name + "\n\n";
                    jsonResponse += "Email: " + email + "\n\n";
                    jsonResponse += "Home: " + home + "\n\n";
                    jsonResponse += "Mobile: " + mobile + "\n\n";

                    txtResponse.setText(jsonResponse);

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),
                            "Error: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
                pDialog.hide();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }
        );
        return jsonObjectRequest;
    }
}
