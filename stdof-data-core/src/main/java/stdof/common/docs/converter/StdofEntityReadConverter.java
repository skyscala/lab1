/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stdof.common.docs.converter;

import java.util.HashMap;
import java.util.Map;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

import org.springframework.stereotype.Component;
import stdof.common.docs.entity.StdofEntity;

/**
 *
 * @author zeyarhtike
 */

@Component
@ReadingConverter
public class StdofEntityReadConverter implements Converter<Document,StdofEntity>{

    @Override
    public StdofEntity convert(Document source) {
        StdofEntity doc = new StdofEntity();
        Map<String,Object> map=new HashMap<>();
        ObjectId objectId=(ObjectId)source.get("_id");
        doc.setId(objectId);
        map.put("id", objectId.toHexString());
        source.keySet().forEach( k -> 
           populate(map,k,source.get(k))
        );
        doc.setAttributes(map);
        return doc;
    }
    
    protected void populate(Map<String,Object> map,String key,Object val){
        if("_id".equals(key)||"version".equals(key)){
            return;
        }
        if(val instanceof Document){
            Document doc=(Document)val;
            Map<String,Object> inner = new HashMap<>();
            doc.keySet().forEach(k->
               populate(inner,k,doc.get(k))
            );
            map.put(key, inner);
        }else{
            map.put(key, val);
        }
    }

    
}

