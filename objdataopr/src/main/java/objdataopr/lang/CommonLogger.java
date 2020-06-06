/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objdataopr.lang;

import java.util.logging.Level;

/**
 *
 * @author zeyarh
 */
public class CommonLogger {
    
    
    private CommonLogger(){}

    public static void replayLog(Class<?> clz,String msg){
        java.util.logging.Logger.getLogger(clz.getName()).log(Level.INFO, msg);
    }
    
    public static void log(Class<?> clz,String msg){
        replayLog(clz, msg);
    }
    
    public static void log(Class<?> clz,String msg,Exception ex){
        java.util.logging.Logger.getLogger(clz.getName()).log(Level.INFO,msg, ex);
    }
    
    public static void log(Class<?> clz,Exception ex){
        java.util.logging.Logger.getLogger(clz.getName()).log(Level.INFO,"Error - ", ex);
    }
    
}
