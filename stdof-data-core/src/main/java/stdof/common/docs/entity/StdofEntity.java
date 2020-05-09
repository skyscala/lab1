/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stdof.common.docs.entity;

import java.util.Map;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 *
 * @author zeyarhtike
 */

@Setter
@Getter
@EqualsAndHashCode
@Document(collection = "stdofentity")
public class StdofEntity {
 
    @Id
    protected ObjectId id;
    protected Map<String,Object> attributes;
    
}
