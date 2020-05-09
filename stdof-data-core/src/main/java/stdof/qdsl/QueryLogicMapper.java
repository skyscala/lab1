/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stdof.qdsl;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import stdof.query.BooleanExpressionHelper;
import stdof.lang.CommonLogger;
import stdof.query.FilterCriteria;
import stdof.query.PageableFactory;
import stdof.query.SortCriteria;

/**
 *
 * @author zeyarhtike
 */
public class QueryLogicMapper {
    
    private QueryLogicMapper(){}
    
    
    public static Pageable createPageable(Class<?> clz, int pageNumber, int pageSize,
            SortCriteria[] sortArray){
        
        int offsetPage = pageNumber;
        int size = pageSize;
        
        if(offsetPage<0){
            throw new IllegalArgumentException("Invalid page number.");
        }
        
        if(size < 1){
            throw new IllegalArgumentException("Invalid page size.");
        }
        
        CommonLogger.log(QueryLogicMapper.class, "page_offset:"+offsetPage);
        CommonLogger.log(QueryLogicMapper.class, "page_size:"+size);
        
        Pageable pageable;
        if(sortArray == null || sortArray.length <=0){
            
            pageable = PageRequest.of(offsetPage, size);
        }else{
            pageable = PageableFactory.createPageRequest(offsetPage, size, clz, sortArray);
        }
        CommonLogger.log(QueryLogicMapper.class,"Pageable:"+pageable);
        return pageable;
        
        
    }
    
    
    public static Predicate createPredicate(Class<?> clz,String searchType,FilterCriteria[] filterArray){
        
        String type="and";
        if(searchType!=null && "or".equalsIgnoreCase(searchType)){
            type = "or";
        }
        
        BooleanExpression exp = null;
        
        if(filterArray!=null&&filterArray.length>0){
            if("or".equalsIgnoreCase(type)){
                exp = BooleanExpressionHelper.preocessExpOr(filterArray, clz);
            }else{
                exp = BooleanExpressionHelper.preocessExpAnd(filterArray, clz);
            }
        }
        CommonLogger.log(QueryLogicMapper.class,"Predicate:"+exp);
        
        return exp;
        
        
    }
}
