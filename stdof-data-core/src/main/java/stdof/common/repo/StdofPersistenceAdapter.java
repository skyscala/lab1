/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stdof.common.repo;

import com.querydsl.core.types.Predicate;
import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import stdof.common.docs.entity.StdofEntity;
import stdof.common.opr.StdofManageOpr;
import stdof.common.opr.StdofQueryOpr;
import stdof.qdsl.QueryLogicMapper;
import stdof.query.FilterCriteria;
import stdof.query.SortCriteria;

import java.util.Optional;

/**
 *
 * @author zeyarhtike
 */

@Component
@AllArgsConstructor
class StdofPersistenceAdapter implements StdofQueryOpr,StdofManageOpr{
    
    
    private final StdofRepo repo;

    @Override
    public Page<StdofEntity> query(int pageNumber, int pageSize, String searchType, FilterCriteria[] filterArray, SortCriteria[] sortArray) {
        Class<?> clz=StdofEntity.class;        
        Pageable pageable=QueryLogicMapper.createPageable(clz, pageNumber, pageSize, sortArray);
        Predicate exp = QueryLogicMapper.createPredicate(clz, searchType, filterArray);        
        return repo.findAll(exp, pageable);
    }

    @Override
    public StdofEntity save(StdofEntity entity) {        
        return repo.save(entity);
    }

    @Override
    public StdofEntity delete(ObjectId objId) {
        Optional<StdofEntity> optional=repo.findById(objId.toHexString());
        if(optional.isPresent()) {
            StdofEntity entity = optional.get();
            repo.delete(entity);
            return entity;
        }
        throw new IllegalArgumentException("Cannot find data by given id.");
    }

    
    
    
    
    
    
    
    
    
}
