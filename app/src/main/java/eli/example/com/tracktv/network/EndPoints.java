package eli.example.com.tracktv.network;

import android.content.Context;
import android.net.Uri;

import eli.example.com.tracktv.R;

/**
 * Created by Administrador on 11/3/15.
 */
public class EndPoints {

    private Context mContext;
    private String FULL = "full";
    private String IMAGES = "images";

    public EndPoints(Context mContext) {
        this.mContext = mContext;
    }

    /**
     *Seasons
     * @param nameSerie String
     * @return String url.
     */
    public String getURLSeasons(String nameSerie){
        //URL https://api-v2launch.trakt.tv/shows/{nameSerie}/seasons?extended=full,images

        String query = FULL+","+IMAGES;

        Uri.Builder builder = new Uri.Builder();
        builder.scheme(mContext.getResources().getString(R.string.url_schemme))
                .authority(mContext.getResources().getString(R.string.url_authority))
                .appendPath(mContext.getResources().getString(R.string.url_path))
                .appendPath(nameSerie)
                .appendPath(mContext.getResources().getString(R.string.url_path_seasons))
                .appendQueryParameter(mContext.getResources().getString(R.string.url_query), query);
        return builder.build().toString();

    }

    /**
     *Episodes
     * @param nameSerie String
     * @param id int
     * @return String url
     */
    public String getURLEpisodes(String nameSerie, int id){
        //https://api-v2launch.trakt.tv/shows/{nameSerie}/seasons/{id}
        Uri.Builder builder = new Uri.Builder();
        builder.scheme(mContext.getResources().getString(R.string.url_schemme))
                .authority(mContext.getResources().getString(R.string.url_authority))
                .appendPath(mContext.getResources().getString(R.string.url_path))
                .appendPath(nameSerie)
                .appendPath(mContext.getResources().getString(R.string.url_path_seasons))
                .appendPath(String.valueOf(id));
        return builder.build().toString();
    }

}
