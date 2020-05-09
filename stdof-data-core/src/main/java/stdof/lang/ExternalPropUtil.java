/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stdof.lang;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

/**
 *
 * @author zeyarhtike
 */


public class ExternalPropUtil {

    
    
    private static final Properties PROP=new Properties();
    
    private ExternalPropUtil(){
        
    }
    
    public static final void definePath(String path){
        if(path!=null){            
            try(InputStream is=new FileInputStream(path)){
                PROP.clear();
                PROP.load(is);
            }catch(Exception ex){
                CommonLogger.log(ExternalPropUtil.class, "External properties reading error -", ex);
            }
        }
    }
    
    public static final Properties retrieveProp(){    
        return PROP;
    }
    
    public static final Properties mergeProp(Properties properties){        
        PROP.putAll(properties);        
        return PROP;
    }
    public static final Properties mergeProp(String key,String value){        
        PROP.put(key, value);
        return PROP;
    }
}
