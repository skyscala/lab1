/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stdof.common.opr;

import org.bson.types.ObjectId;
import stdof.common.docs.entity.StdofEntity;

/**
 *
 * @author zeyarhtike
 */
public interface StdofManageOpr {
    
    
    StdofEntity save(StdofEntity entity);
    
    StdofEntity delete(ObjectId objId);
    
    
    
    
   
    
}
