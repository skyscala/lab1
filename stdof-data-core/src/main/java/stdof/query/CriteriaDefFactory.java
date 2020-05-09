/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stdof.query;



import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.data.mongodb.core.query.Criteria;

/**
 *
 * @author zeyarh
 */
public class CriteriaDefFactory {

    private CriteriaDefFactory(){}


    public static Criteria createBooleanExpression(FilterCriteria criteria){
        if(criteria==null||criteria.getKey()==null||criteria.getOperation()==null){
            throw new IllegalArgumentException("Invalid filter object!");
        }

        if(criteria instanceof ObjectFilterCriteria){
            return createCdefForObject((ObjectFilterCriteria)criteria);
        }
        
        if(criteria instanceof StringFilterCriteria){
            return createCdefForString((StringFilterCriteria)criteria);
        }
        
        if(criteria instanceof ListOfStringsFilterCriteria){
            return createCdefForListOfStrings((ListOfStringsFilterCriteria)criteria);
        }
        
        if(criteria instanceof DateFilterCriteria){
            return createCdefForDate((DateFilterCriteria)criteria);
        }
        
        if(criteria instanceof DateRangeFilterCriteria){
            return createCdefForDateRange((DateRangeFilterCriteria)criteria);
        }
        
        if(criteria instanceof NumberFilterCriteria){
            return createCdefForNumber((NumberFilterCriteria)criteria);
        }
        
        return null;
    }
    
    public static Criteria createCdefForString(StringFilterCriteria criteria){
        
        String key = criteria.getKey();
        String operation = criteria.getOperation();
        String value=criteria.getValue();
        
        
        if("startsWithIgnoreCase".equalsIgnoreCase(operation)){
            return new Criteria(key).regex("(?i)^"+value+".*");
        }else if("endsWithIgnoreCase".equalsIgnoreCase(operation)){
            return new Criteria(key).regex("(?i).*"+value+"$");
        }else if("containsIgnoreCase".equalsIgnoreCase(operation)){
            return new Criteria(key).regex("(?i).*"+value+".*");
        }else if("equalsIgnoreCase".equalsIgnoreCase(operation)){
            return new Criteria(key).regex("(?i)^"+value+"$");
        }else if("startsWith".equalsIgnoreCase(operation)){
            return new Criteria(key).regex("^"+value+".*");
        }else if("endsWith".equalsIgnoreCase(operation)){
            return new Criteria(key).regex(".*"+value+"$");
        }else if("contains".equalsIgnoreCase(operation)){
            return new Criteria(key).regex(".*"+value+".*");
        }else if("equals".equalsIgnoreCase(operation)){
            return new Criteria(key).regex("^"+value+"$");
        }else if("ne".equalsIgnoreCase(operation)){            
            return new Criteria(key).ne(value);
        }
            
        throw new IllegalArgumentException("Invalid operator for text filter.");
        
    } 
    
    public static Criteria createCdefForObject(ObjectFilterCriteria criteria){
        String key = criteria.getKey();
        String operation = criteria.getOperation();
        
        Object value=criteria.getValue();
        if("ne".equalsIgnoreCase(operation)){
            return new Criteria(key).ne(value);
        }else if("eq".equalsIgnoreCase(operation)){
            return new Criteria(key).is(value);
        }else{
            throw new IllegalArgumentException("Invalid operator for object filter.");
        }
    }
    
    public static Criteria createCdefForListOfStrings(ListOfStringsFilterCriteria criteria){
        String key = criteria.getKey();
        String operation = criteria.getOperation();        
        List<String> values=new ArrayList<>();
        values.addAll(criteria.getValues());
        
        if(!values.isEmpty()){
            if("in".equalsIgnoreCase(operation)){
                if(values.size()==1){
                    values.add("");
                }
                return new Criteria(key).in(values);
                
            }else if("notIn".equalsIgnoreCase(operation)){
                if(values.size()==1){
                    values.add("");
                }
                return new Criteria(key).in(values).not();
            }else{
                throw new IllegalArgumentException("Invalid operator for text array filter");
            }
        }else{
            throw new IllegalArgumentException("Empty text array is not allowed.");
        }
        
    }
    
    
    public static Criteria createCdefForDate(DateFilterCriteria criteria){
        String key = criteria.getKey();
        String operation = criteria.getOperation();        
        Date date = criteria.getDate();
        
        if(date !=null){
            if("before".equalsIgnoreCase(operation)){
                return new Criteria(key).lte(date);                
            }else if("after".equalsIgnoreCase(operation)){
                return new Criteria(key).gte(date);
            }else {
                throw new IllegalArgumentException("Invalid operator for date filter.");
            }
        }
        
        throw new IllegalArgumentException("Invalid date.");
    }
    
    public static Criteria createCdefForDateRange(DateRangeFilterCriteria criteria){
        String key = criteria.getKey();
        String operation = criteria.getOperation();        
        
        Date start = criteria.getStart();
        Date end = criteria.getEnd();
        
        if(start !=null && end !=null){
            if("between".equalsIgnoreCase(operation)){
                return new Criteria(key).gte(start).andOperator(new Criteria(key).lte(end));
                
            }else if("notBetween".equalsIgnoreCase(operation)){
                return new Criteria(key).gte(end).andOperator(new Criteria(key).lte(start)).not();
            }else{
                throw new IllegalArgumentException("Invalid operator for date range filter.");
            }
        }
        
        
        throw new IllegalArgumentException("Invalid start date or end date");
    }
    
    public static Criteria createCdefForNumber(NumberFilterCriteria criteria){
        
        String key = criteria.getKey();
        String operation = criteria.getOperation();
        
        Number valNum=criteria.getValue();
        
        if(valNum==null){
            throw new IllegalArgumentException("Invalid value for number filter.");            
        }

        if("gt".equalsIgnoreCase(operation)){
            return new Criteria(key).gt(valNum);
        }else if("goe".equalsIgnoreCase(operation)){
            return new Criteria(key).gte(valNum);            
        }else if("eq".equalsIgnoreCase(operation)){
            return new Criteria(key).is(valNum);            
        }else if("lt".equalsIgnoreCase(operation)){
            return new Criteria(key).lt(valNum);
        }else if("loe".equalsIgnoreCase(operation)){
            return new Criteria(key).lte(valNum);
        }
        
        throw new IllegalArgumentException("Invalid operator for number filter.");
    }
    
}
