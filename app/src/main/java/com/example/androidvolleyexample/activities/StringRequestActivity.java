package com.example.androidvolleyexample.activities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.androidvolleyexample.R;
import com.example.androidvolleyexample.singletonclasses.VolleySingleton;

public class StringRequestActivity extends AppCompatActivity {

    private RequestQueue requestQueue;
    private Context context;
    private String array_json = "https://api.androidhive.info/volley/person_array.json";
    private String jsonResponse;
    private TextView txtResponse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_string_request);

        txtResponse=(TextView)findViewById(R.id.txtResponse);

        context=this;

        requestQueue = VolleySingleton.getVolleySingletonInstance().getRequestQueue();

        StringRequest stringRequest = getStringRequest();
        requestQueue.add(stringRequest);
    }

    private StringRequest getStringRequest()
    {
        //  @params   REQUEST_METHOD,REQUEST_URL,RESPONSE,ERROR_RESPONSE
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                array_json,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        Toast.makeText(context,response,Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        handleVolleyError(error);
                    }
                });
        return stringRequest;
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
