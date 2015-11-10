package eli.example.com.tracktv;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;

import java.util.ArrayList;

import eli.example.com.tracktv.adapters.AdapterGrid;
import eli.example.com.tracktv.entities.Season;
import eli.example.com.tracktv.network.CustomJSONArrayRequest;
import eli.example.com.tracktv.network.CustomVolleyRequest;
import eli.example.com.tracktv.network.EndPoints;
import eli.example.com.tracktv.utils.Constants;
import eli.example.com.tracktv.utils.JSONUtils;
import eli.example.com.tracktv.utils.Utility;

public class MainFragment extends Fragment implements Response.Listener,Response.ErrorListener {


    private TextView emptyView;
    private AdapterGrid adapter;
    private ArrayList<Season> mDataset;


    private RequestQueue mRequestQueue;
    public static final String REQUEST_TAG = "TrackTV";
    public String TAG_LOG = MainFragment.class.getSimpleName();

    private boolean conection = true;
    private OnFragmentInteractionListener mListener;

    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Utility.verificaConexion(getActivity())) {
            mDataset = new ArrayList<>();
            setupRequest();
        }else{
            conection=false;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View layoutView = inflater.inflate(R.layout.fragment_main, container,
                false);

        GridView gridView = (GridView) layoutView.findViewById(R.id.grid_view);
        adapter = new AdapterGrid(getActivity(), mDataset);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Season season = (Season) parent.getItemAtPosition(position);
                onButtonPressed(season);
            }
        });

        emptyView = (TextView) layoutView.findViewById(R.id.emptyView);

        if(!conection) {
            emptyView.setVisibility(View.VISIBLE);
        }

        return layoutView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Season season) {
        if (mListener != null) {
            mListener.onFragmentInteraction(season);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void setupRequest(){
        mRequestQueue = CustomVolleyRequest.getInstance(getActivity().getApplicationContext()).getRequestQueue();
        EndPoints endPoints = new EndPoints(getActivity());
        String url = endPoints.getURLSeasons(Constants.NAME);
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

        JSONUtils.parseSeasons((JSONArray) response, mDataset);
        adapter.notifyDataSetChanged();
        Log.i(TAG_LOG, response.toString());
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        emptyView.setVisibility(View.VISIBLE);
        emptyView.setText(getResources().getString(R.string.error_response));
    }

    /**
     * Interface to communicate with the Activity.
     */
    public interface OnFragmentInteractionListener {
         void onFragmentInteraction(Season season);
    }

}
