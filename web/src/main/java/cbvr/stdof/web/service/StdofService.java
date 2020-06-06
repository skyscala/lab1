/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cbvr.stdof.web.service;

import java.util.List;
import java.util.Map;
import org.bson.types.ObjectId;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.domain.Page;
import stdof.DbConfig;
import stdof.common.docs.entity.StdofEntity;
import stdof.common.opr.StdofManageOpr;
import stdof.common.opr.StdofQueryOpr;
import stdof.common.repo.StdofManageOprBeanFactory;
import stdof.common.repo.StdofQueryOprBeanFactory;
import stdof.lang.CommonLogger;
import stdof.lang.ExternalPropUtil;
import stdof.lang.JsonUtil;
import stdof.pgb.functional.component.AbstractQueryResultFactory;
import stdof.pgb.util.QueryHelper;
import stdof.pgb.generic.component.Filter;
import stdof.pgb.generic.component.PaginationParam;
import stdof.pgb.generic.component.QueryParam;
import stdof.pgb.generic.component.SortParam;

/**
 *
 * @author zeyarhtike
 */
public class StdofService {

    static {
        ExternalPropUtil.mergeProp("db.name", "test-db");
        ExternalPropUtil.mergeProp("db.url", "mongodb://localhost:27017/test-db");
    }

    private static Class TAG = StdofService.class;

    public Object save(String payload) {
        CommonLogger.log(TAG, payload);
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DbConfig.class)) {
            StdofEntity entity = new StdofEntity();
            Map map = JsonUtil.fromJsonString(payload, Map.class);
            Object idObj = map.get("id");
            if (idObj != null) {
                entity.setId(new ObjectId(idObj.toString()));
            }
            entity.setAttributes(map);
            StdofManageOpr saveOpr = StdofManageOprBeanFactory.create(context);
            saveOpr.save(entity);            
            return findById(entity.getId().toHexString());
        }
    }

    public Object delete(String payload) {
        CommonLogger.log(TAG, payload);
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DbConfig.class)) {
            try {
                String id = JsonUtil.fromJsonString(payload, Map.class).get("id").toString();
                StdofManageOpr saveOpr = StdofManageOprBeanFactory.create(context);
                StdofEntity entity = saveOpr.delete(new ObjectId(id));
                CommonLogger.log(TAG, "Data deleted: " + JsonUtil.toJsonString(entity));
                return convertE(entity);
            } catch (Exception ex) {
                CommonLogger.log(TAG, "Erorr in delete operation:", ex);
                throw ex;
            }
        }
    }
    
    public Object findById(String id){
        CommonLogger.log(TAG, id);
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DbConfig.class)) {

            try {
                StdofQueryOpr queryOpr = StdofQueryOprBeanFactory.create(context);
                StdofEntity entity = queryOpr.findById(id);                
                CommonLogger.log(TAG, "Data found: " + JsonUtil.toJsonString(entity));
                return convertE(entity);
                
            } catch (Exception ex) {
                CommonLogger.log(TAG, "Error in query operation:", ex);
                throw ex;
            }
        }
    }

    public Object query(String payload) {

        CommonLogger.log(TAG, payload);
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DbConfig.class)) {

            try {

                QueryParam queryParam = JsonUtil.fromJsonString(payload, QueryParam.class);

                StdofQueryOpr queryOpr = StdofQueryOprBeanFactory.create(context);

                PaginationParam pageParam = queryParam.getPaginationParam();
                int pageNumber = pageParam.getPageNumber()-1;
                int pageSize = pageParam.getPageSize();
                List<SortParam> sortList = queryParam.getSortingParams();
                Filter filter = queryParam.getFilter();
                Page<StdofEntity> page = queryOpr.query(pageNumber, pageSize,
                        filter!=null?filter.getLogic():"and",
                        QueryHelper.createFilterCriteriaArray(filter, null),
                        QueryHelper.createSortCriteriaArray(sortList, null));
                AbstractQueryResultFactory<StdofEntity, Map> qrf = new AbstractQueryResultFactory<StdofEntity, Map>() {
                    @Override
                    protected Map convert(StdofEntity a) {
                        CommonLogger.log(TAG, "Record fetched: " + JsonUtil.toJsonString(a));                        
                        return convertE(a);
                    }
                };

                return qrf.create(pageParam.getPageNumber(), page);
            } catch (Exception ex) {
                CommonLogger.log(TAG, "Error in query:", ex);
                throw ex;
            }
        }
    }
    
    
    private Map convertE(StdofEntity entity){
        //entity.getAttributes().put("id", entity.getId().toHexString());

        return entity.getAttributes();
    }
}
