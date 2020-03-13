package phonebase.hilmi.hitech.config;

import android.app.Application;
import android.text.TextUtils;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Created by User on 12/03/2020.
 */

public class ConfigVolley extends Application {
    public static final String TAG = ConfigVolley
            .class.getSimpleName();

    private RequestQueue requestQueue;
    private ImageLoader imageLoader;

    private static ConfigVolley getInstance;



    @Override
    public void onCreate() {
        super.onCreate();
        getInstance = this;
        requestQueue = Volley.newRequestQueue(ConfigVolley.this);
    }

    public static ConfigVolley getInstance() {
        return getInstance;
    }

    public  static synchronized ConfigVolley getAppConfigVolley(){
        return getInstance;
    }

    public RequestQueue getRequestQueue(){
        if (requestQueue == null){
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return requestQueue;
    }

    public ImageLoader getImageLoader(){
        getRequestQueue();
        if (imageLoader == null){
//            imageLoader = new ImageLoader(this.requestQueue, new LruBitmapCache());
        }
        return this.imageLoader;
    }

    public <T> void addToRequestQueue(Request<T> request, String tag){
        // set the default tag if tag is empty
        requestQueue = Volley.newRequestQueue(this);
        request.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(request);
    }

    public void cancelPendingRequest(Object object){
        if (requestQueue != null){
            requestQueue.cancelAll(object);
        }
    }
}