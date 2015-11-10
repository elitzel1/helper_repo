package eli.example.com.tracktv.utils;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import eli.example.com.tracktv.entities.Season;

/**
 * Created by Administrador on 11/2/15.
 */
public class JSONUtils {

    static String TAG_LOG = JSONUtils.class.getName();

    static String OWM_TRACK="trakt";
    static String TAG_IMAGES="images";
    static String TAG_POSTER = "poster";
    static String TAG_IDS = "ids";
    static String OWM_THUMB ="thumb";
    static String OWM_FULL = "full";
    static String TAG_THUMB = "thumb";
    static String OWM_NUMBER = "number";
    static String OWM_RATING = "rating";
    static String OWM_TITLE = "title";

    /**
     * Metodo para realizar el parseo
     * @param response JSONArray
     */
    public static void parseSeasons(JSONArray response, ArrayList<Season> mDataset){

        try{
            for(int i = 0;i<response.length();i++){
                JSONObject object = response.getJSONObject(i);
                int number = object.getInt(OWM_NUMBER);
                int id = object.getJSONObject(TAG_IDS).getInt(OWM_TRACK);
                String urlThumb = object.getJSONObject(TAG_IMAGES).getJSONObject(TAG_POSTER).getString(OWM_THUMB);
                String urlBanner = object.getJSONObject(TAG_IMAGES).getJSONObject(TAG_THUMB).getString(OWM_FULL);
                int rating = object.getInt(OWM_RATING);

                if(!object.getJSONObject(TAG_IMAGES).getJSONObject(TAG_POSTER).isNull(OWM_THUMB)) {
                    mDataset.add(new Season(number, id, urlThumb, urlBanner, rating));
                }
            }
        }catch (JSONException e){
            Log.e(TAG_LOG,e.toString());
        }

    }

    /**
     * Metodo para realizar el parseo
     * @param response JSONArray
     */
    public static void parseEpisodes(JSONArray response, ArrayList<String> mDataset){

        try{
            for(int i = 0;i<response.length();i++){
                JSONObject object = response.getJSONObject(i);
                String title = object.getString(OWM_TITLE);
                mDataset.add(i,title);
            }
        }catch (JSONException e){
            Log.e(TAG_LOG,e.toString());
        }

    }

}