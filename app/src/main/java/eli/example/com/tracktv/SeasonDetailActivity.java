package eli.example.com.tracktv;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import org.json.JSONArray;

import java.util.ArrayList;

import eli.example.com.tracktv.adapters.AdapterEpisodes;
import eli.example.com.tracktv.entities.Season;
import eli.example.com.tracktv.network.CustomJSONArrayRequest;
import eli.example.com.tracktv.network.CustomVolleyRequest;
import eli.example.com.tracktv.network.EndPoints;
import eli.example.com.tracktv.utils.Constants;
import eli.example.com.tracktv.utils.JSONUtils;
import eli.example.com.tracktv.utils.Utility;

public class SeasonDetailActivity extends AppCompatActivity implements Response.Listener,Response.ErrorListener {
    private  ProgressBar progressBar;
    private ProgressBar progressBarPoster;
    private AdapterEpisodes adapter;
    private TextView emptyView;

    private RequestQueue mRequestQueue;
    public static final String REQUEST_TAG = "TrackTV";

    private ArrayList<String> mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chapters_activity);
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        emptyView = (TextView)findViewById(R.id.emptyView);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mData = new ArrayList<>();

        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter
        adapter = new AdapterEpisodes(mData);
        mRecyclerView.setAdapter(adapter);

        Season season = getIntent().getParcelableExtra(Utility.TAG_EXTRAS);
        setupData(season);

        if (Utility.verificaConexion(this)) {
            setupRequest(season.getNumber());
        }else{
            emptyView.setVisibility(View.VISIBLE);
        }
    }


    private void setupData(Season season){
        ImageView thumb = (ImageView)findViewById(R.id.imgThumb);
        ImageView portada = (ImageView)findViewById(R.id.imgPortada);
        TextView txtRating = (TextView)findViewById(R.id.txtRating);
        progressBar = (ProgressBar) findViewById(R.id.progressThumb);
        progressBarPoster = (ProgressBar) findViewById(R.id.progress);

        String title ="Season "+String.valueOf(season.getNumber());
        getSupportActionBar().setTitle(title);

        Glide.with(this)
                .load(season.getImgThumb())
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }
                }).error(R.mipmap.serie_thumbnail_placeholder)
                .into(thumb);

        Glide.with(this)
                .load(season.getImgPoster())
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        progressBarPoster.setVisibility(View.GONE);
                        return false;
                    }
                }).error(R.mipmap.season_background_placeholder)
                .into(portada);
        txtRating.setText(String.valueOf(season.getRating()));

    }

    private void setupRequest(int id){
        mRequestQueue = CustomVolleyRequest.getInstance(this).getRequestQueue();
        EndPoints endPoints = new EndPoints(this);
        String url = endPoints.getURLEpisodes(Constants.NAME,id);
        //String url = "https://api-v2launch.trakt.tv/shows/the-x-files/seasons/"+id;
        startRequest(url);
    }

    private void startRequest(String url){
        final CustomJSONArrayRequest customJSONArrayRequest = new CustomJSONArrayRequest(Request.Method.GET,url,
                new JSONArray(),this,this);
        customJSONArrayRequest.setTag(REQUEST_TAG);
        mRequestQueue.add(customJSONArrayRequest);
    }
    @Override
    public void onResponse(Object response) {
        JSONUtils.parseEpisodes((JSONArray) response,mData);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        emptyView.setVisibility(View.VISIBLE);
        emptyView.setText(getResources().getString(R.string.error_network));
    }
}
