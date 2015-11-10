package eli.example.com.tracktv.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;

/**
 * Created by Administrador on 11/2/15.
 */
public class Utility {

    public static String TAG_EXTRAS = "extras";

    public static boolean responseStatus( JSONObject response){
        //Location status
        final String MESSAGE_CODE = "status";
        final String OWM_RESPONSE = "response";
        try{
            JSONObject status = response.getJSONObject(OWM_RESPONSE);

            if(status.has(MESSAGE_CODE)){
                int code = status.getInt(MESSAGE_CODE);

                switch (code){
                    case HttpURLConnection.HTTP_OK:
                        return true;
                    case HttpURLConnection.HTTP_UNAUTHORIZED:
                        //TODO: Handle this error
                        return false;
                    case HttpURLConnection.HTTP_NOT_FOUND:
                        //TODO: Handle this error too
                        return false;
                    default:
                        //TODO: Handle all the errors
                        return false;
                }

            }
        }catch (JSONException e){

        }
        return false;

    }

    /**
     * Verifica la conexión de datos.
     * @param context Contexto de la aplicación
     * @return boolean
     */
    public static boolean verificaConexion( Context context) {
        boolean bConectado = false;
        ConnectivityManager connec = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        // No sólo wifi, también GPRS
        NetworkInfo[] redes = connec.getAllNetworkInfo();
        for (int i = 0; i < 2; i++) {
            // ¿Tenemos conexión? ponemos a true
            if (redes[i].getState() == NetworkInfo.State.CONNECTED) {
                bConectado = true;
            }
        }
        return bConectado;
    }
}
