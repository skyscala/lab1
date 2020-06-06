/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stdof.query.docs.repo;

import com.querydsl.core.types.Predicate;
import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import stdof.query.docs.entity.D0cEntity;
import stdof.qdsl.QueryLogicMapper;
import stdof.pgb.functional.component.FilterCriteria;
import stdof.pgb.functional.component.SortCriteria;

import java.util.Optional;
import stdof.query.docs.opr.D0cManageOpr;
import stdof.query.docs.opr.D0cQueryOpr;

/**
 *
 * @author zeyarhtike
 */

@Component
@AllArgsConstructor
class D0cPersistenceAdapter implements D0cQueryOpr,D0cManageOpr{
    
    
    private final D0cRepo repo;

    @Override
    public Page<D0cEntity> query(int pageNumber, int pageSize, String searchType, FilterCriteria[] filterArray, SortCriteria[] sortArray) {
        Class<?> clz=D0cEntity.class;        
        Pageable pageable=QueryLogicMapper.createPageable(clz, pageNumber, pageSize, sortArray);
        Predicate exp = QueryLogicMapper.createPredicate(clz, searchType, filterArray);        
        return repo.findAll(exp, pageable);
    }

    @Override
    public D0cEntity findById(String id) {
        Optional<D0cEntity> optional=repo.findById(id);
        if(optional.isPresent()){
            return optional.get();
        }
        throw new IllegalArgumentException("Cannot find data by given id.");
    }
    
    

    @Override
    public D0cEntity save(D0cEntity entity) {        
        return repo.save(entity);        
    }

    @Override
    public D0cEntity delete(ObjectId objId) {
        Optional<D0cEntity> optional=repo.findById(objId.toHexString());
        if(optional.isPresent()) {
            D0cEntity entity = optional.get();
            repo.delete(entity);
            return entity;
        }
        throw new IllegalArgumentException("Cannot delete data by given id.");
    }

    
    
    
    
    
    
    
    
    
}
