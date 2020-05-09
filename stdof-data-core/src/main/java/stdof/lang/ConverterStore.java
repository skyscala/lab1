/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stdof.lang;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import org.springframework.stereotype.Component;

/**
 *
 * @author zeyarhtike
 */

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class ConverterStore {
    
    private final List<Object> converters=new ArrayList<>();
    
    public List<Object> converters(){
        return converters;
    }
}
