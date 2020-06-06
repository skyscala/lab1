package objdataopr.lang;

import com.google.gson.Gson;

public class JsonUtil {

    private JsonUtil(){}

    public static String toJsonString(Object obj) {
        Gson gson = new Gson();
        return gson.toJson(obj);
    }

    public static <E> E fromJsonString(String jsonString, Class<E> clz) {
        Gson gson = new Gson();
        return gson.fromJson(jsonString, clz);
    }
    
    
}
