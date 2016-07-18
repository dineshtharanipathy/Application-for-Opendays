package project.isep.com.opendays;

import android.content.ContentValues;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by Tharanipathy PC on 09-10-2015.
 */
public class Requests {

    private static String getParams(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        try {
            boolean first = true;
            for (Map.Entry<String, String> entry : params.entrySet()) {
                if (first) {
                    first = false;
                } else {
                    result.append("&");
                }

                result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
                result.append("=");
                result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
            }
        }catch (Exception e){
            return "null";
        }
        return result.toString();
    }

    public static ContentValues getRequestParam(String[] keys, String[] values){
        try {
            ContentValues contentValues = new ContentValues();

            //HashMap<String, String> pair = new HashMap<String, String>();
            if (keys.length == values.length) {
                int len = keys.length;
                for (int i = 0; i < len; i++) {
                    contentValues.put(keys[i], values[i]);
                }
            }
            return contentValues;
            //return getParams(pair);
        }catch (Exception e){
            return null;
        }
    }


}
