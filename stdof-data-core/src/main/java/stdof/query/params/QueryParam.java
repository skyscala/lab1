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
public class QueryParam {

    private String className;
    private PaginationParam paginationParam;
    private Filter filter;
    private List<SortParam> sortingParams;

    /**
     * @return the paginationParam
     */
    public PaginationParam getPaginationParam() {
        return paginationParam;
    }

    /**
     * @param paginationParam the paginationParam to set
     */
    public void setPaginationParam(PaginationParam paginationParam) {
        this.paginationParam = paginationParam;
    }

    /**
     * @return the sortingPrams
     */
    public List<SortParam> getSortingParams() {
        return sortingParams;
    }

    /**
     * @param sortingPrams the sortingPrams to set
     */
    public void setSortingParams(List<SortParam> sortingPrams) {
        this.sortingParams = sortingPrams;
    }

    /**
     * @return the filter
     */
    public Filter getFilter() {
        return filter;
    }

    /**
     * @param filter the filter to set
     */
    public void setFilter(Filter filter) {
        this.filter = filter;
    }

    /**
     * @return the className
     */
    public String getClassName() {
        return className;
    }

    /**
     * @param className the className to set
     */
    public void setClassName(String className) {
        this.className = className;
    }
}
