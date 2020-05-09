/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stdof.lang.util;

import stdof.query.params.QueryResultPage;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Page;

/**
 *
 * @author zeyarh
 */
public abstract class AbstractWebQueryResultFactory<A,B> {
    
    protected abstract B convert(A a);
    
    public QueryResultPage<B> create(int pageNumber,Page<A> page){
    
        if(page!=null){
            QueryResultPage<B> result=new QueryResultPage<>();
            List<A> content=page.getContent();
            List<B> list=new ArrayList<>();
            
            for(A a:content){                
                list.add(convert(a));
            }
            
            result.setItems(list);
            result.setPageNumber(pageNumber);
            result.setPageSize(page.getSize());
            result.setHasNextPage(page.hasNext());
            result.setHasPreviousPage(page.hasPrevious());
            result.setNumberOfElements(page.getNumberOfElements());
            result.setTotalPages(page.getTotalPages());
            result.setTotalNumberOfElements(page.getTotalElements());
            return result;
        }
        
        return null;
    }
}
