/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stdof.lang.util;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

/**
 *
 * @author zeyarh
 */
public class DateUtil {


    private DateUtil(){}

    public static SimpleDateFormat createDefaultDateFormatter(){
        String datePattern = "yyyy-MM-dd HH:mm:ss.SSS";
        return new SimpleDateFormat(datePattern);

    }
    
    public static SimpleDateFormat createyyyyDashMMDashddDollarSignHHmmssFormatter(){
        String datePattern = "yyyy-MM-dd$HH:mm:ss";
        return new SimpleDateFormat(datePattern);

    }
    
    public static SimpleDateFormat createyyyyDashMMMDashddSpaceHHmmssFormatter(){
        String datePattern = "yyyy-MMM-dd HH:mm:ss";
        return new SimpleDateFormat(datePattern);
    }
    
    public static SimpleDateFormat createHHmmssSSSFormatter(){
        String datePattern = "HH:mm:ss.SSS";
        return new SimpleDateFormat(datePattern);
    }
    
    public static SimpleDateFormat createHHmmssFormatter(){
        String datePattern = "HH:mm:ss";
        return new SimpleDateFormat(datePattern);
    }
    
    public static SimpleDateFormat createyyyyDashMMMDashddFormatter(){
        String datePattern = "yyyy-MMM-dd";
        return new SimpleDateFormat(datePattern);
    }
    
    public static SimpleDateFormat createyyyyMMddFormatter(){
        String datePattern = "yyyyMMdd";
        return new SimpleDateFormat(datePattern);
    }
    
    public static SimpleDateFormat createyyyyMMddHHmmssFormatter(){
        String datePattern = "yyyyMMddHHmmss";
        return new SimpleDateFormat(datePattern);
    }
    
    
    public static void defineTimeZone(String timezone,SimpleDateFormat... sdfs){
        if(timezone!=null){
            for(SimpleDateFormat sdf:sdfs){            
                sdf.setTimeZone(TimeZone.getTimeZone(timezone));            
            }
        }
    }
}
