/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stdof.common.repo;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import stdof.common.docs.entity.StdofEntity;

/**
 *
 * @author zeyarhtike
 */

@Repository
interface StdofRepo extends PagingAndSortingRepository<StdofEntity,String>,
        QuerydslPredicateExecutor<StdofEntity>{
    
    
    
    
    
}
