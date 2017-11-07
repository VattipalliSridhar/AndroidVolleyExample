package com.example.androidvolleyexample.activities;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
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
import com.android.volley.toolbox.NetworkImageView;
import com.example.androidvolleyexample.R;
import com.example.androidvolleyexample.singletonclasses.VolleySingleton;
import com.example.androidvolleyexample.utils.LruBitmapCache;
import com.android.volley.Cache;
import com.android.volley.Cache.Entry;

import java.io.UnsupportedEncodingException;

public class ImageRequestActivity extends AppCompatActivity {

    private ImageView url_img;
    private LruCache<Integer,Bitmap> imageCache;
    private Context context;
    private RequestQueue requestQueue;

    private ImageLoader mImageLoader;
    String image_url = "http://photy.me/api/v1/image/224e0947440ce00f639798c332fd6c271480424092032.jpeg";
    private NetworkImageView imgNetWorkView;
    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_request);
        imgNetWorkView = (NetworkImageView) findViewById(R.id.imgNetwork);
        imageView = (ImageView) findViewById(R.id.imgView);
        context=this;

        requestQueue= VolleySingleton.getVolleySingletonInstance().getRequestQueue();

        setImageCache();
        url_img=(ImageView)findViewById(R.id.uri_img);

        mImageLoader = new ImageLoader(this.requestQueue,
                new LruBitmapCache());

        // If you are using NetworkImageView
        imgNetWorkView.setImageUrl(image_url, mImageLoader);


        // If you are using normal ImageView
		/*imageLoader.get(Const.URL_IMAGE, new ImageListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				Log.e(TAG, "Image Load Error: " + error.getMessage());
			}

			@Override
			public void onResponse(ImageContainer response, boolean arg1) {
				if (response.getBitmap() != null) {
					// load image into imageview
					imageView.setImageBitmap(response.getBitmap());
				}
			}
		});*/

        // Loading image with placeholder and error image
        mImageLoader.get(image_url, ImageLoader.getImageListener(
                imageView, R.drawable.ico_loading, R.drawable.ico_error));

        Cache cache = requestQueue.getCache();
        Entry entry = cache.get(image_url);
        if(entry != null){
            try {
                String data = new String(entry.data, "UTF-8");
                // handle data, like converting it to xml, json, bitmap etc.,
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }else{
            // cached response doesn't exists. Make a network call here
        }


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
