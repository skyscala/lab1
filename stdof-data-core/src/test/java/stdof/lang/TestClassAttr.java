/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stdof.lang;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author zlhso
 */
public class TestClassAttr {
    
    private static Class<?> TAG = TestClassAttr.class;
    
    @Test
    public void run1(){
   
        DummyA a1=new DummyA();
        a1.attrA1="a.attrA";
        DummyB b1=new DummyB();
        
        
        DummyA a2=new DummyA();
        a2.attrA1="a2.attrA1";
        DummyB b2=new DummyB();
        b2.attrB1="b2.attrB1";
        
        DummyD d= new DummyD();
        d.a=b1;
        d.list1=Arrays.asList(a1,b2);
        Map<String,DummyA> map=new HashMap<>();
        map.put("1", a2);
        map.put("2", b2);
        d.map1=map;
        
        ClassAttrUtil.copy(a1, b1, DummyA.class, true);
        
        CommonLogger.log(TAG, b1.attrA1);
        assertTrue(b1.attrA1!=null);
        
        Map<String,Object> mapTest=DataMapUtil.defineDataMap(d, DummyD.class);
        printMap("", mapTest);
        
        
        System.out.println(JsonUtil.toJsonString(mapTest));
        
    }
    
    private void printMap(String prefix,Map<String,Object> map){
        for(String k:map.keySet()){
            Object v=map.get(k);
            if(v instanceof Map){                
                printMap(prefix+k+".",(Map)v);                
            }else if(v instanceof List){
                for(Object o:(List)v){
                    if(o instanceof Map){
                        printMap(k+".",(Map)o);         
                    }else{
                        System.out.println( prefix+k+" = "+v);  
                    }
                }
            }else{
                System.out.println( prefix+k+" = "+v);
            }
        }
    }
    
}
