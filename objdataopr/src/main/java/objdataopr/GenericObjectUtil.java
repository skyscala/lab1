/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objdataopr;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import objdataopr.lang.CommonLogger;

/**
 *
 * @author zlhso
 */
public class GenericObjectUtil {

    private static final Class TAG = GenericObjectUtil.class;
    

    private GenericObjectUtil() {
    }
    
    
    public static <T> GenericObject createGenericObject(Class<T> clz,T t){
        return GenericObjectUtil.createGenericObject(null, null, clz, t, null);
    }

    public static GenericObject createGenericObject(String key, Field field,
            Class<?> type, Object value, GenericObject parent) {

        GenericObject genericObj = new GenericObject();
        genericObj.setKey(key);
        genericObj.setField(field);
        genericObj.setParent(parent);
        genericObj.setValue(value);

        List<Field> allFields=new ArrayList<>();
        
        Field[] fields = type.getDeclaredFields();
        allFields.addAll(Arrays.asList(fields));
        
        Class<?> superClass=type.getSuperclass(); 
        if(superClass!=null){
            Field[] superFields =superClass.getDeclaredFields();
            allFields.addAll(Arrays.asList(superFields));
        }
        
        
        
        List<GenericObject> children = new ArrayList<>();

        for (Field f : allFields) {
            String k = f.getName();
            if (key != null) {
                k = key + "." + k;
            }
            Class<?> typ = f.getType();
            Object val = null;
            if (value != null) {
                try {
                    f.setAccessible(true);
                    val = f.get(value);
                } catch (Exception ex) {
                    CommonLogger.log(TAG, ex);
                }
            }

            if (!typ.isPrimitive() && !typ.getName().startsWith("java.")) {
                children.add(GenericObjectUtil.createGenericObject(k, f, typ, val, genericObj));
            } else {
                GenericObject childObj = new GenericObject();
                childObj.setKey(k);
                childObj.setField(f);
                childObj.setParent(genericObj);
                childObj.setValue(val);
                children.add(childObj);
            }
            
            
        }
        
               
        
        genericObj.setChildren(children);
        
        
        return genericObj;

    }

    public static GenericObject findByKey(GenericObject obj, String key) {
        if (obj == null || key == null) {
            return null;
        } else {
            boolean keyMatch = key.equals(obj.getKey());
            if (!keyMatch) {
                String nKey1 = key.toLowerCase().replace("\\.", "");
                String temp = obj.getKey();
                if (temp != null) {
                    String nKey2 = temp.toLowerCase().replace("\\.", "");
                    keyMatch = nKey1.equals(nKey2);
                }
            }
            
            return keyMatch ? obj : findInChildrenByKey(obj.getChildren(), key);
        }
    }

    private static GenericObject findInChildrenByKey(List<GenericObject> list, String key) {
        if (list != null) {
            for (GenericObject c : list) {
                GenericObject result = findByKey(c, key);
                if (result != null) {
                    return result;
                }
            }
        }
        return null;
    }
    
    public static <T> GenericObject defineValue(GenericObject genObj,String key,Object value){
        GenericObject searchObj = findByKey(genObj, key);
        if(searchObj!=null){
            searchObj.setValue(value);
            GenericObject parent = searchObj.getParent();
            createParent(parent, searchObj);
        }
        return searchObj;
    }
    
    public static void createParent(GenericObject parent,GenericObject child){
        if(parent!=null){
            try{
                
                Object parentVal = parent.getValue();
                Object childVal = child.getValue();
                
                Field f = child.getField();
                f.setAccessible(true);
                
                if(parentVal==null){
                    Class typ = parent.getField().getType();
                    parentVal = typ.getDeclaredConstructor().newInstance();
                    parent.setValue(parentVal);
                    f.set(parentVal, childVal);
                    createParent(parent.getParent(), parent);
                }else{
                    if(childVal!=null){
                        
                        Object obj = childVal;
                        /*
                        Class typ = child.getField().getType();
                        String childType = typ.getName();
                        String childValType =childVal.getClass().getName();                        
                        if(!childValType.equals(childType)){
                            if("java.util.Date".equals(childType)){
                                obj = dateFormat.parse(childVal.toString());
                            }
                        } 
                        */
                        f.set(parentVal, obj);
                    }
                }
                
            }catch(Exception ex){
                CommonLogger.log(TAG, ex);
            }
        }
    }
}
