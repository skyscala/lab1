/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stdof.common.docs.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import stdof.common.docs.converter.StdofEntityReadConverter;
import stdof.common.docs.converter.StdofEntityWriteConverter;
import stdof.lang.CommonLogger;
import stdof.lang.ConverterStore;

/**
 *
 * @author zeyarhtike
 */

@Configuration
public class StdofEntityConfig {

    
    @Autowired
    public StdofEntityConfig(
            MongoTemplate mongoTemplate,
            ConverterStore converterStore,
            StdofEntityReadConverter readConverter,
            StdofEntityWriteConverter writeConverter){
    
        
        CommonLogger.log(StdofEntityConfig.class, "Defining converter - "+StdofEntityReadConverter.class);
        CommonLogger.log(StdofEntityConfig.class, "Defining converter - "+StdofEntityWriteConverter.class);
        
        MappingMongoConverter mongoMapping = (MappingMongoConverter) mongoTemplate.getConverter();

        converterStore.converters().add(readConverter);
        converterStore.converters().add(writeConverter);
        List<?> converters = converterStore.converters();
        MongoCustomConversions conversions = new MongoCustomConversions(converters);
        mongoMapping.setCustomConversions(conversions);
        mongoMapping.afterPropertiesSet();
        
        CommonLogger.log(StdofEntityConfig.class, "Converters: "+converters);
        
    }
    
}
