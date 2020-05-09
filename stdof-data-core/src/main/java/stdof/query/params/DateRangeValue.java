/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stdof.query.params;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author zeyarh
 */

@Getter
@Setter
public class DateRangeValue {
    
    private String datePattern;
    private String start;
    private String end;

    
    
}
