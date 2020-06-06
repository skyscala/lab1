/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stdof.query.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import stdof.query.docs.converter.D0cReadConverter;
import stdof.query.docs.converter.D0cWriteConverter;
import stdof.lang.CommonLogger;
import stdof.lang.ConverterStore;

/**
 *
 * @author zeyarhtike
 */

@Configuration
public class D0c {

    
    @Autowired
    public D0c(
            MongoTemplate mongoTemplate,
            ConverterStore converterStore,
            D0cReadConverter readConverter,
            D0cWriteConverter writeConverter){
    
        
        CommonLogger.log(D0c.class, "Defining converter - "+D0cReadConverter.class);
        CommonLogger.log(D0c.class, "Defining converter - "+D0cWriteConverter.class);
        
        MappingMongoConverter mongoMapping = (MappingMongoConverter) mongoTemplate.getConverter();

        converterStore.converters().add(readConverter);
        converterStore.converters().add(writeConverter);
        List<?> converters = converterStore.converters();
        MongoCustomConversions conversions = new MongoCustomConversions(converters);
        mongoMapping.setCustomConversions(conversions);
        mongoMapping.afterPropertiesSet();
        
        CommonLogger.log(D0c.class, "Converters: "+converters);
        
    }
    
}
