/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stdof.query.docs.opr;

import org.bson.types.ObjectId;
import stdof.query.docs.entity.D0cEntity;

/**
 *
 * @author zeyarhtike
 */
public interface D0cManageOpr {    
    
    D0cEntity save(D0cEntity entity);    
    D0cEntity delete(ObjectId objId);
        
}
