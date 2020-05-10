/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stdof.common.opr;

import org.springframework.data.domain.Page;
import stdof.common.docs.entity.StdofEntity;
import stdof.query.FilterCriteria;
import stdof.query.SortCriteria;

/**
 *
 * @author zeyarhtike
 */
public interface StdofQueryOpr {
    
    Page<StdofEntity> query(int pageNumber, int pageSize,
            String searchType, FilterCriteria[] filterArray,SortCriteria[] sortArray);
    
    
    StdofEntity findById(String id);
}
