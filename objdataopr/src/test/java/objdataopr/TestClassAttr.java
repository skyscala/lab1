/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objdataopr;

import objdataopr.lang.JsonUtil;
import objdataopr.lang.CommonLogger;
import java.util.Arrays;
import java.util.Calendar;
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
        
        DataCopy.perform(a1, b1, DummyA.class, true);
        
        CommonLogger.log(TAG, b1.attrA1);
        assertTrue(b1.attrA1!=null);
        
        Map<String,Object> mapTest=DataStructureGen.perform(d, DummyD.class);
        printDataStructure("", mapTest);
        
        
        System.out.println(JsonUtil.toJsonString(mapTest));
        
        print(ClassAttrUtil.collectAttrKeyAndNameMap(DummyD.class));
        
        print(ClassAttrUtil.collectAttrNameAndKeyMap(DummyD.class));        
        
        print(ClassAttrUtil.collectFieldMap(DummyD.class));
        
        
        DummyD d1=new DummyD();
        GenericObject genObj =GenericObjectUtil.createGenericObject(DummyD.class, d1);
        System.out.println(genObj.getChildren());
        GenericObjectUtil.defineValue(genObj, "a.attrA1", "test");
        GenericObjectUtil.defineValue(genObj, "date", Calendar.getInstance().getTime());
        GenericObjectUtil.defineValue(genObj, "list1", Arrays.asList("1","2","3"));
        GenericObjectUtil.defineValue(genObj, "map1", mapTest);
        System.out.println(JsonUtil.toJsonString(d1));
    }
    
    private void print(Map map){ 
        System.out.println("--- Print Map ---");
        for(Object k:map.keySet()){
            System.out.println(k+" - "+map.get(k));
        }
    }
    
    private void printDataStructure(String prefix,Map<String,Object> map){
        for(String k:map.keySet()){
            Object v=map.get(k);
            if(v instanceof Map){                
                printDataStructure(prefix+k+".",(Map)v);                
            }else if(v instanceof List){
                for(Object o:(List)v){
                    if(o instanceof Map){
                        printDataStructure(k+".",(Map)o);         
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
