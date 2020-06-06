/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objdataopr;


import objdataopr.lang.CommonLogger;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.TreeMap;


/**
 *
 * @author zlhso
 */
public class ClassAttrUtil {
    
    private static final Class<ClassAttrUtil> TAG = ClassAttrUtil.class;
    
    private ClassAttrUtil(){}
    
    public static Map<String,Field> collectFieldMap(Class<?> clz){
    
        Map<String,Field> map = new TreeMap<>();
        try{
            Field[] fields = clz.getDeclaredFields();
            for(Field field:fields){
                Class type=field.getType();
                if(!type.isPrimitive()&&!type.getName().startsWith("java.")){
                    Map<String,Field> childFields=collectFieldMap(type);
                    if(childFields.isEmpty()){
                        map.put(field.getName(), field);
                    }else{
                        for(String childField:childFields.keySet()){
                            if(!field.getName().equals(childField)){
                                map.put(field.getName()+"."+childField,childFields.get(childField));
                            }
                        }
                    }
                }else{
                    map.put(field.getName(), field);
                }
            }
            
            Class<?> superClass=clz.getSuperclass();        
            if(superClass!=null){
                map.putAll(collectFieldMap(superClass));
            }
            
        }catch(Exception ex){
            CommonLogger.log(TAG, ex);
        }
        
        
        
        return map;
    }
    
    public static Map<String, String> collectAttrKeyAndNameMap(Class<?> clz) {
        Map<String,String> map = new TreeMap<>();
        try{
            Field[] fields=clz.getDeclaredFields();
            for(Field field:fields){
                Class type=field.getType();
                if(!type.isPrimitive()&&!type.getName().startsWith("java.")){
                    Map<String,String> childFields=collectAttrKeyAndNameMap(type);
                    if(childFields.isEmpty()){
                        map.put(field.getName(), field.getName());
                    }else{
                        
                        for(String childField:childFields.keySet()){
                            if(!field.getName().equals(childField)){
                                String temp=childFields.get(childField);
                                if(temp.length()>1){
                                    temp = temp.substring(0,1).toUpperCase()+temp.substring(1);
                                }else{
                                    temp = temp.toUpperCase();
                                }
                                String parentName = field.getName();
                                String name=parentName+temp;
                                map.put(parentName+"."+childField, name);
                            }
                        }
                    }
                }else{
                    map.put(field.getName(), field.getName());
                }
            }
            
            Class superClass=clz.getSuperclass();        
            if(superClass!=null){
                map.putAll(collectAttrKeyAndNameMap(superClass));
            }            
            
        }catch(Exception ex){
            CommonLogger.log(clz, ex);
        }
        
        
        
        return map;
    }

    public static Map<String, String> collectAttrNameAndKeyMap(Class clz) {
        Map<String,String> map = new TreeMap<>();
        Map<String,String> keyAndNameMap=collectAttrKeyAndNameMap(clz);
        for(String key:keyAndNameMap.keySet()){
            String val=keyAndNameMap.get(key);
            if(val!=null){
                map.put(val, key);
            }
        }
        return map;
    }
}
