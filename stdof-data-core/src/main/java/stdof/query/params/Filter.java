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
public class Filter {
    private String logic;
    private List<FilterParam> filterParams;

    /**
     * @return the logic
     */
    public String getLogic() {
        return logic;
    }

    /**
     * @param logic the logic to set
     */
    public void setLogic(String logic) {
        this.logic = logic;
    }

    /**
     * @return the filterParams
     */
    public List<FilterParam> getFilterParams() {
        return filterParams;
    }

    /**
     * @param filterParams the filterParams to set
     */
    public void setFilterParams(List<FilterParam> filterParams) {
        this.filterParams = filterParams;
    }
}
