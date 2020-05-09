/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stdof.common.docs.converter;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.stereotype.Component;
import stdof.common.docs.entity.StdofEntity;

/**
 *
 * @author zeyarhtike
 */


@Component
@WritingConverter
public class StdofEntityWriteConverter implements Converter<StdofEntity,Document>{

    @Override
    public Document convert(StdofEntity s) {
        Document document = new Document();
        ObjectId oId=s.getId();
        document.put("_id", oId);
        if(s.getAttributes()!=null){
            document.putAll(s.getAttributes());
        }
        document.remove("_class");
        return  document;
    }
    
    
}
