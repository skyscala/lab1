/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objdataopr;

import java.lang.reflect.Field;
import java.util.Map;
import objdataopr.lang.CommonLogger;

/**
 *
 * @author zlhso
 */
public class DataCopy {
    
    
    private static final Class TAG = DataCopy.class;
    
    private DataCopy(){}
    
    public static <E> void perform(E from,E to,Class<E> clz,boolean skipNull){
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
                            perform(val1,val2,type,skipNull);
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
                perform(from,to,superClass,skipNull);
            }
            
        }catch(Exception ex){
            CommonLogger.log(TAG, ex);
        }
    }
    
    
    
    public static <E> void perform(Map<String,Object> map,E to,Class<E> clz,String prefix){
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
                        perform(map,val2,type,prefix+"."+field.getName());
                    }
                }else{     
                    field.set(to, val);
                    CommonLogger.log(TAG,field.getName()+"="+val);                       
                    
                }
            }
            
            Class superClass=clz.getSuperclass();        
            if(superClass!=null){
                perform(map,to,superClass,prefix);
            }
            
        }catch(Exception ex){
            CommonLogger.log(TAG, ex);
        }
    }
}
