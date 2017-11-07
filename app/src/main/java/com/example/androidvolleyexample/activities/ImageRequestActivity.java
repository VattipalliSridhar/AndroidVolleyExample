package com.example.androidvolleyexample.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
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
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.example.androidvolleyexample.R;
import com.example.androidvolleyexample.singletonclasses.VolleySingleton;
import com.example.androidvolleyexample.utils.LruBitmapCache;

public class ImageRequestActivity extends AppCompatActivity {

    private ImageView url_img;
    private LruCache<Integer,Bitmap> imageCache;
    private Context context;
    private RequestQueue requestQueue;

    private ImageLoader mImageLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_request);
        context=this;

        requestQueue= VolleySingleton.getVolleySingletonInstance().getRequestQueue();

        setImageCache();
        url_img=(ImageView)findViewById(R.id.uri_img);

        mImageLoader = new ImageLoader(this.requestQueue,
                new LruBitmapCache());

        //ImageRequest imageRequest =getImageRequest();
        //requestQueue.add(imageRequest);
    }


    private void setImageCache()
    {
        final int maximum_memory = (int) (Runtime.getRuntime().maxMemory()/1024);
        final int cache_size = maximum_memory/8;
        imageCache = new LruCache<>(cache_size);
    }


    private ImageRequest getImageRequest()
    {
        String image_url = "http://photy.me/api/v1/image/224e0947440ce00f639798c332fd6c271480424092032.jpeg";
        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.show();
        ImageRequest imageRequest = new ImageRequest(
                image_url,
                new Response.Listener<Bitmap>()
                {
                    @Override
                    public void onResponse(Bitmap response)
                    {
                        url_img.setImageBitmap(response);
                        pDialog.hide();
                    }
                },
                200, 200,
                ImageView.ScaleType.CENTER_CROP,
                Bitmap.Config.ARGB_8888,
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        handleVolleyError(error);
                    }
                });
        return imageRequest;
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
