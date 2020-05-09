/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stdof.query.params;

import java.util.List;

/**
 *
 * @author zeyarh
 */
public class FilterDef {
    
    private String type;
    private List<String> expressions;

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the expressions
     */
    public List<String> getExpressions() {
        return expressions;
    }

    /**
     * @param expressions the expressions to set
     */
    public void setExpressions(List<String> expressions) {
        this.expressions = expressions;
    }
    
    
    
}
