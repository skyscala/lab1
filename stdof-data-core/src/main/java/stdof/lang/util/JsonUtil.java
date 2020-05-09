package stdof.lang.util;

import com.google.gson.Gson;

public class JsonUtil {

    private JsonUtil(){}

    public static String toJsonString(Object obj) {

        if (obj != null) {
            Gson gson = new Gson();
            return gson.toJson(obj);
        }

        return null;
    }

    public static <E> E fromJsonString(String jsonString, Class<E> clz) {
        if (jsonString != null) {
            Gson gson = new Gson();
            return gson.fromJson(jsonString, clz);
        }
        return null;
    }
    
    
}
