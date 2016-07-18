package project.isep.com.opendays;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;


/**
 * Created by Tharanipathy PC on 09-10-2015.
 */
public class RegistrationIntentService extends IntentService{

    private static final String TAG = RegistrationIntentService.class.getSimpleName();

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     */
    public RegistrationIntentService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        SharedPreferences prefs = getSharedPreferences(Config.USER_PREFERENCE, MODE_PRIVATE);
        try{
            InstanceID instanceID = InstanceID.getInstance(this);
            String token = instanceID.getToken(Config.GCM_SENDER_ID, GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);

            Log.i(TAG, "GCM Registration Token: " + token);
            prefs.edit().putString(Config.GCM_TOKEN_RECEIVED, token).apply();

            sendTokenToServer(token);

        }catch(Exception e){
            Log.d(TAG, "Failed to get GCM token");
            prefs.edit().putString(Config.GCM_TOKEN_RECEIVED, "null").apply();
        }

        Intent registrationComplete = new Intent(Config.GCM_REGISTRATION_COMPLETE);
        LocalBroadcastManager.getInstance(this).sendBroadcast(registrationComplete);
    }

    private void sendTokenToServer(String token){
        SharedPreferences prefs = getSharedPreferences(Config.USER_PREFERENCE, MODE_PRIVATE);
        try{
            //int id = prefs.getInt(Config.POST_ID_KEY, 0);
            if(token != null) {
                String url = Config.url;
                String[] keys = new String[]{Config.POST_TAG_KEY, Config.POST_GCMTOKEN_KEY};
                String[] values = new String[]{Config.POST_TAG_GCMREGISTER, token};
                ContentValues params = Requests.getRequestParam(keys, values);
                if (Config.DEBUG) {
                    Log.d(TAG, "Params: ");
                }

                processFinished(sendRequest(params));
                //new ResponseTask(this, true).execute(url, params);
            }else{
                prefs.edit().putBoolean(Config.GCM_TOKEN_SENT_TO_SERVER, false).apply();
            }
        }catch(Exception e){
            prefs.edit().putBoolean(Config.GCM_TOKEN_SENT_TO_SERVER, false).apply();
        }
    }

    private String sendRequest(ContentValues... params){
        if(params.length == 0){
            return null;
        }

        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024;

        ContentValues param = params[0];

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        BufferedWriter writer = null;

        String responseJsonStr = null;

        try{

            URL url = new URL(Config.url);
            Log.v("fahad", url.toString());

            urlConnection = (HttpURLConnection)url.openConnection();
            urlConnection.setReadTimeout(90000);
            urlConnection.setConnectTimeout(90000);
            urlConnection.setUseCaches(false);
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Accept-Encoding", "");
            //conn.setRequestProperty("Connection", "Keep-Alive");
            urlConnection.setRequestProperty("ENCTYPE", "multipart/form-data");
            urlConnection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);

            OutputStream os = urlConnection.getOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(os);

            for(Map.Entry<String, Object> entry : param.valueSet()){
                String key = entry.getKey();
                String value = String.valueOf(entry.getValue());
                dataOutputStream.writeBytes(twoHyphens + boundary + lineEnd);
                dataOutputStream.writeBytes("Content-Disposition: form-data; name=\"" + key + "\"" + lineEnd + lineEnd
                        + value + lineEnd);
                /*if(key != Config.POST_IMAGE_KEY){
                    content.append(key + "\"" + lineEnd + value);
                    content.append(lineEnd);
                    content.append(twoHyphens + boundary + lineEnd);
                }else{
                    image = true;
                    file = new File(String.valueOf(entry.getValue()));
                }*/
            }



            dataOutputStream.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

            dataOutputStream.flush();
            dataOutputStream.close();
            os.close();

            int responseCode = urlConnection.getResponseCode();

            if(responseCode == HttpURLConnection.HTTP_OK){
                InputStream inputStream = urlConnection.getInputStream();
                StringBuilder buffer1 = new StringBuilder();
                if(inputStream == null){
                    return null;
                }

                reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while((line = reader.readLine()) != null){
                    buffer1.append(line + "\n");
                }

                if(buffer1.length() == 0){
                    return null;
                }
                responseJsonStr = buffer1.toString();

                Log.v("fahad", responseJsonStr);
                //JSONObject responseObject = new JSONObject(responseJsonStr);

                //resp = responseObject.getString("success");
                //String message = responseObject.getString("message");
            }else{
                responseJsonStr = "ERROR";
            }
            return responseJsonStr;

        }
        catch(IOException e){
            Log.e("fahad", "Error:" + e);
        }
        finally{
            if(urlConnection != null){
                urlConnection.disconnect();
            }

            if(reader != null){
                try{
                    reader.close();
                }
                catch(final IOException e){
                    Log.e("fahad", "Error closing stream:" + e);
                }
            }

            if(writer != null){
                try{
                    writer.close();
                }
                catch(final IOException e){
                    Log.e("fahad", "Error closing stream:" + e);
                }
            }
        }
        return null;
    }

    private void processFinished(String response) {
        SharedPreferences prefs = getSharedPreferences(Config.USER_PREFERENCE, MODE_PRIVATE);
        try{
            if(response != null) {
                JSONObject responseObject = new JSONObject(response);
                int code = responseObject.getInt(Config.RESPONSE_SUCCESS_KEY);
                String tag = responseObject.getString(Config.POST_TAG_KEY);
                if (tag.equals(Config.POST_TAG_GCMREGISTER)) {
                    if (code == Config.RESP_SUCCESS) {
                        prefs.edit().putBoolean(Config.GCM_TOKEN_SENT_TO_SERVER, true).apply();
                    } else {
                        prefs.edit().putBoolean(Config.GCM_TOKEN_SENT_TO_SERVER, false).apply();
                    }
                }
            }else{
                prefs.edit().putBoolean(Config.GCM_TOKEN_SENT_TO_SERVER, false).apply();
            }
        }catch(Exception e){
            prefs.edit().putBoolean(Config.GCM_TOKEN_SENT_TO_SERVER, false).apply();
        }
    }
}
