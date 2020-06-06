/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objdataopr;

import java.lang.reflect.Field;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author zlhso
 */

@Getter @Setter
public class GenericObject {
    
    private String key;
    private GenericObject parent;
    private List<GenericObject> children;
    private Object value;
    private Field field;

    @Override
    public String toString() {
        return key+" - "+value;
    }
    
    
}
