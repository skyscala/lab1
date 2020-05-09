/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stdof.lang.util;

/**
 *
 * @author zeyarh
 */
public class StringIO {

    private StringIO(){}
    
    public static String trimStringOrNull(String org){
        if(org!=null){
            return org.trim();
        }
        return org;
    }
    
    
}
