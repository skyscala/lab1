/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stdof.pgb.generic.component;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author zeyarh
 */

@Getter
@Setter
public class QueryResultPage<E> {
    
    private int pageNumber;
    private int totalPages;
    private boolean hasNextPage;
    private boolean hasPreviousPage;
    private int pageSize;
    private long totalNumberOfElements;
    private long numberOfElements;
    private List<E> items;
    

    
    
    
    
}
