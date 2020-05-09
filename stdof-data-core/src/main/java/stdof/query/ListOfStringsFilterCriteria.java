/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stdof.query;

import java.util.List;

/**
 *
 * @author zeyarh
 */
public class ListOfStringsFilterCriteria extends FilterCriteria{
    
    private List<String> values;

    /**
     * @return the values
     */
    public List<String> getValues() {
        return values;
    }

    /**
     * @param values the values to set
     */
    public void setValues(List<String> values) {
        this.values = values;
    }
}
