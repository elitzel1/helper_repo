package eli.example.com.tracktv.network;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.text.TextUtils;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;

/**
 * Created by Administrador on 11/2/15.
 */
public class CustomVolleyRequest extends Application {

    public static final String TAG = CustomVolleyRequest.class.getSimpleName();
    private static CustomVolleyRequest mInstance;
    private final String DEFAULT_CACHE_DIR = "sl_cache";
    private static Context context;
    private RequestQueue mRequestQueue;

    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
    }

    private CustomVolleyRequest(Context context) {
        this.context = context;
        this.mRequestQueue = getRequestQueue();
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        mInstance = this;
    }

    public static synchronized CustomVolleyRequest getInstance()
    {
        return mInstance;
    }


    public static synchronized CustomVolleyRequest getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new CustomVolleyRequest(context);
        }
        return mInstance;
    }


    public RequestQueue getRequestQueue(){
        if(mRequestQueue == null){
            //Cache with 5MB
            Cache cache = new DiskBasedCache(context.getCacheDir(), 5 * 1024 * 1024);
            Network network = new BasicNetwork(new HurlStack());
            mRequestQueue = new RequestQueue(cache,network);
            //Start the request
            mRequestQueue.start();
        }
        return mRequestQueue;
    }
}
