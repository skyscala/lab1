/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objdataopr;

import objdataopr.lang.CommonLogger;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

/**
 *
 * @author zlhso
 */
public class DataStructureGen {

    private static final Class TAG = DataStructureGen.class;

    private DataStructureGen(){}

    public static Map<String, Object> perform(Object e, Class<?> clz) {
        Map<String, Object> map = new TreeMap<>();
        try {
            Field[] fields = clz.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                Class type = field.getType();
                if (!type.isPrimitive()
                        && !type.getName().startsWith("java.")) {
                    collectNestedDataMap(map, field, type, e);
                } else {
                    Object obj = field.get(e);
                    if (obj instanceof List) {
                        collectList(map, field, obj);
                    } else if (obj instanceof Map) {
                        collectMap(map, field, obj);
                    } else {
                        map.put(field.getName(), field.get(e));
                    }
                }
            }
            Class<?> superClass = clz.getSuperclass();
            if (superClass != null) {
                map.putAll(perform(e, superClass));
            }
        } catch (Exception ex) {
            CommonLogger.log(TAG, ex);
        }

        return map;
    }

    private static void collectNestedDataMap(Map<String, Object> map, 
            Field field, Class type, Object e) {
        try {
            if (!type.isPrimitive()
                    && !type.getName().startsWith("java.")) {
                Map<String, Object> childrenMap = perform(field.get(e), type);
                map.put(field.getName(), childrenMap);
            }
        } catch (Exception ex) {
            CommonLogger.log(TAG, ex);
        }
    }

    private static void collectList(Map<String, Object> map, 
            Field field, Object obj) {

        List<Map<String, Object>> list = new ArrayList<>();
        List<?> l = (List) obj;
        l.stream()
                .map(itm -> perform(itm, itm.getClass()))
                .filter(m -> (m != null && !m.isEmpty()))
                .forEach(m -> list.add(m)
                );
        map.put(field.getName(), list);

    }

    private static void collectMap(Map<String, Object> map, 
            Field field, Object obj) {

        Map<String, Object> m = new TreeMap<>();
        Map m1 = ((Map) obj);
        Set<Entry> set = m1.entrySet();

        set.forEach(e -> {
            Object k = e.getKey();
            Object v = e.getValue();
            m.put(k.toString(), perform(v, v.getClass()));
        });

        map.put(field.getName(), m);

    }

}
