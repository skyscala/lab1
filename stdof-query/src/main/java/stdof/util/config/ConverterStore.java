/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stdof.util.config;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 *
 * @author zlhso
 */

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class ConverterStore {
    
    final List<Converter> converters = new ArrayList<>();
    
    public List<Converter> converters(){
        return converters;
    }
    
}
