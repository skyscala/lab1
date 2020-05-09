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
public class QueryResultPage<E> {
    
    private int pageNumber;
    private int totalPages;
    private boolean hasNextPage;
    private boolean hasPreviousPage;
    private int pageSize;
    private long totalNumberOfElements;
    private long numberOfElements;
    private List<E> items;
    

    /**
     * @return the pageNumber
     */
    public int getPageNumber() {
        return pageNumber;
    }

    /**
     * @param pageNumber the pageNumber to set
     */
    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    /**
     * @return the totalPages
     */
    public int getTotalPages() {
        return totalPages;
    }

    /**
     * @param totalPages the totalPages to set
     */
    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    /**
     * @return the hasNextPage
     */
    public boolean isHasNextPage() {
        return hasNextPage;
    }

    /**
     * @param hasNextPage the hasNextPage to set
     */
    public void setHasNextPage(boolean hasNextPage) {
        this.hasNextPage = hasNextPage;
    }

    /**
     * @return the hasPreviousPage
     */
    public boolean isHasPreviousPage() {
        return hasPreviousPage;
    }

    /**
     * @param hasPreviousPage the hasPreviousPage to set
     */
    public void setHasPreviousPage(boolean hasPreviousPage) {
        this.hasPreviousPage = hasPreviousPage;
    }

    /**
     * @return the pageSize
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * @param pageSize the pageSize to set
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * @return the totalNumberOfElements
     */
    public long getTotalNumberOfElements() {
        return totalNumberOfElements;
    }

    /**
     * @param totalNumberOfElements the totalNumberOfElements to set
     */
    public void setTotalNumberOfElements(long totalNumberOfElements) {
        this.totalNumberOfElements = totalNumberOfElements;
    }

    /**
     * @return the numberOfElements
     */
    public long getNumberOfElements() {
        return numberOfElements;
    }

    /**
     * @param numberOfElements the numberOfElements to set
     */
    public void setNumberOfElements(long numberOfElements) {
        this.numberOfElements = numberOfElements;
    }

    /**
     * @return the items
     */
    public List<E> getItems() {
        return items;
    }

    /**
     * @param items the items to set
     */
    public void setItems(List<E> items) {
        this.items = items;
    }

    

    
    
    
}
