/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stdof.query.docs.repo;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import stdof.query.docs.entity.D0cEntity;

/**
 *
 * @author zeyarhtike
 */

@Repository
interface D0cRepo extends PagingAndSortingRepository<D0cEntity, String>, 
        QuerydslPredicateExecutor<D0cEntity>{
    
    
    
    
    
}
