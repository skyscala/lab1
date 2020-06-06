/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stdof.lang;


import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


/**
 *
 * @author zlhso
 */
public class ClassAttrUtil {
    
    private static final Class<ClassAttrUtil> TAG = ClassAttrUtil.class;
    
    private ClassAttrUtil(){}
        
    public static <E> void copy(E from,E to,Class<E> clz,boolean skipNull){
        try{
            
            Field[] fields = clz.getDeclaredFields();
            for(Field field:fields){
                field.setAccessible(true);
                Class type=field.getType();
                if(!type.isPrimitive()&&!type.getName().startsWith("java.")){                    
                    Object val1=field.get(from);                    
                    if(val1!=null){
                        Object val2=field.get(to);
                        if(val2==null){
                            field.set(to, val1);
                        }else{
                            copy(val1,val2,type,skipNull);
                        }
                    }else{
                        if(!skipNull){
                            field.set(to, val1);
                        }
                    }
                }else{                    
                    Object val=field.get(from);
                    
                    if(val!=null){
                        field.set(to, val);
                        CommonLogger.log(TAG,field.getName()+"="+val);                       
                    }else{
                        if(!skipNull){
                            field.set(to, val);
                            CommonLogger.log(TAG,field.getName()+"="+val);
                        }
                    }
                }
            }
            
            Class superClass=clz.getSuperclass();        
            if(superClass!=null){
                copy(from,to,superClass,skipNull);
            }
            
        }catch(Exception ex){
            CommonLogger.log(TAG, ex);
        }
    }
    
    
    
    public static <E> void copy(Map<String,Object> map,E to,Class<E> clz,String prefix){
        try{
            
            
            String lookup=prefix.trim().isEmpty()?"":prefix+".";
            for(Field field:clz.getDeclaredFields()){
                
                field.setAccessible(true);
                Class type=field.getType();
                Object val=map.get(lookup+field.getName());
                if(val==null){
                    continue;
                }
                if(!type.isPrimitive()&&!type.getName().startsWith("java.")){
                    Object val2=field.get(to);
                    if(val2==null){
                        field.set(to, val);
                    }else{
                        copy(map,val2,type,prefix+"."+field.getName());
                    }
                }else{     
                    field.set(to, val);
                    CommonLogger.log(TAG,field.getName()+"="+val);                       
                    
                }
            }
            
            Class superClass=clz.getSuperclass();        
            if(superClass!=null){
                copy(map,to,superClass,prefix);
            }
            
            
        }catch(Exception ex){
            CommonLogger.log(TAG, ex);
        }
    }
    
    
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
