package com.example.androidvolleyexample.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.androidvolleyexample.R;
import com.example.androidvolleyexample.singletonclasses.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonArrayActivity extends AppCompatActivity {

    private RequestQueue requestQueue;
    private Context context;
    private String array_json = "https://api.androidhive.info/volley/person_array.json";
    private String jsonResponse;
    private TextView txtResponse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_json_array);

        txtResponse=(TextView)findViewById(R.id.txtResponse);

        context=this;

        requestQueue = VolleySingleton.getVolleySingletonInstance().getRequestQueue();


        JsonArrayRequest jsonArrayRequest = getJsonArrayRequest();
        requestQueue.add(jsonArrayRequest);
    }


    private JsonArrayRequest getJsonArrayRequest() {
        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.show();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                array_json,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //Toast.makeText(context,response.toString(),Toast.LENGTH_LONG).show();
                        try
                        {
                            jsonResponse = "";
                            for (int i = 0; i < response.length(); i++) {

                                JSONObject person = (JSONObject) response
                                        .get(i);

                                String name = person.getString("name");
                                String email = person.getString("email");
                                JSONObject phone = person.getJSONObject("phone");
                                String home = phone.getString("home");
                                String mobile = phone.getString("mobile");

                                jsonResponse += "Name: " + name + "\n\n";
                                jsonResponse += "Email: " + email + "\n\n";
                                jsonResponse += "Home: " + home + "\n\n";
                                jsonResponse += "Mobile: " + mobile + "\n\n\n";

                            }

                            txtResponse.setText(jsonResponse);
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),
                                    "Error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }

                        pDialog.hide();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        handleVolleyError(error);
                    }
                }
        );
        return jsonArrayRequest;
    }


    private void handleVolleyError(VolleyError error)
    {
        if (error instanceof AuthFailureError || error instanceof TimeoutError)
        {
            Toast.makeText(context,"AuthFailureError/TimeoutError",Toast.LENGTH_LONG).show();
        }
        else if (error instanceof NoConnectionError)
        {
            Toast.makeText(context,"NoConnectionError",Toast.LENGTH_LONG).show();
        }
        else if (error instanceof NetworkError)
        {
            Toast.makeText(context,"NetworkError",Toast.LENGTH_LONG).show();
        }
        else if (error instanceof ServerError)
        {
            Toast.makeText(context,"ServerError",Toast.LENGTH_LONG).show();
        }
        else if (error instanceof ParseError)
        {
            Toast.makeText(context,"ParseError",Toast.LENGTH_LONG).show();
        }
    }
}
