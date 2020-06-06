/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stdof.query.docs.opr;

import org.springframework.data.domain.Page;
import stdof.query.docs.entity.D0cEntity;
import stdof.pgb.functional.component.FilterCriteria;
import stdof.pgb.functional.component.SortCriteria;

/**
 *
 * @author zeyarhtike
 */
public interface D0cQueryOpr {
    
    Page<D0cEntity> query(
            int pageNumber, 
            int pageSize,
            String searchType, 
            FilterCriteria[] filterArray,
            SortCriteria[] sortArray);
    
    D0cEntity findById(String id);
    
}
