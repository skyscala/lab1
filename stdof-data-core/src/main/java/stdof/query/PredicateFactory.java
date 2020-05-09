/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stdof.query;


import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.DatePath;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.core.types.dsl.SimplePath;
import com.querydsl.core.types.dsl.StringPath;
import stdof.lang.util.CommonLogger;


import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


/**
 *
 * @author zeyarh
 */
public class PredicateFactory {
    
    public static final String TEXT_FILTER="text";
    public static final String TEXT_ARRAY_FILTER="textArray";
    public static final String DATE_RANGE_FILTER="dateRange";
    public static final String DATE_FILTER="date";
    public static final String NUMBER_FILTER="number";
    public static final String OBJECT_FILTER="object";

    private PredicateFactory(){}
    
    public static BooleanExpression createBooleanExpression(FilterCriteria criteria, Class<?> clz){
        
        if(criteria==null||criteria.getKey()==null||criteria.getOperation()==null){
            throw new IllegalArgumentException("Invalid filter object!");
        }
        
        PathBuilder<?> entityPath = new PathBuilder<>(clz,clz.getSimpleName());
        
        if(criteria instanceof ObjectFilterCriteria){
            return createBooleanExpressionForObject(entityPath, (ObjectFilterCriteria)criteria);
        }
        
        if(criteria instanceof StringFilterCriteria){
            return createBooleanExpressionForString(entityPath, (StringFilterCriteria)criteria);
        }
        
        if(criteria instanceof ListOfStringsFilterCriteria){
            return createBooleanExpressionForListOfStrings(entityPath, (ListOfStringsFilterCriteria)criteria);
        }
        
        if(criteria instanceof DateFilterCriteria){
            return createBooleanExpressionForDate(entityPath, (DateFilterCriteria)criteria);
        }
        
        if(criteria instanceof DateRangeFilterCriteria){
            return createBooleanExpressionForDateRange(entityPath, (DateRangeFilterCriteria)criteria);
        }
        
        if(criteria instanceof NumberFilterCriteria){

            return createBooleanExpressionForNumber(entityPath, (NumberFilterCriteria)criteria);
        }
        
        return null;
        
    }
    
    public static Map<String,List<String>> expressionMap(){
        Map<String,List<String>> map = new TreeMap<>();
        String[] textFilterExpressions={
            "startsWithIgnoreCase","endsWithIgnoreCase","containsIgnoreCase",
            "equalsIgnoreCase","startsWith","endsWith","contains","equals","ne"
        };
        map.put(TEXT_FILTER, Arrays.asList(textFilterExpressions));
        
        String[] textArrayFilterExpressions = {
            "in","notIn"
        };
        map.put(TEXT_ARRAY_FILTER, Arrays.asList(textArrayFilterExpressions));
        
        String[] dateRangeFilterExpressions = {
            "between","notBetween"
        };
        map.put(DATE_RANGE_FILTER, Arrays.asList(dateRangeFilterExpressions));
        
        String[] dateExpressions = {
            "before","after"  
        };
        map.put(DATE_FILTER, Arrays.asList(dateExpressions));
        
        String[] numExpressions = {
            "gt","lt","eq","goe","loe"
        };
        map.put(NUMBER_FILTER, Arrays.asList(numExpressions));
        
        String[] objExpressions = {
            "eq","ne"
        };
        map.put(OBJECT_FILTER, Arrays.asList(objExpressions));
        
        
        return map;
    }
    
    public static BooleanExpression createBooleanExpressionForObject(PathBuilder<?> entityPath,ObjectFilterCriteria criteria){
        String key = criteria.getKey();
        String operation = criteria.getOperation();
        SimplePath<Object> path = entityPath.getSimple(key, Object.class);
        Object value=criteria.getValue();
        if("ne".equalsIgnoreCase(operation)){
            if(value==null){
                return path.isNotNull();
            }
            return path.ne(value);
        }else if("eq".equalsIgnoreCase(operation)){
            return path.eq(value);
        }else{
            throw new IllegalArgumentException("Invalid operator for object filter.");
        }
    }
    
    
    
    public static BooleanExpression createBooleanExpressionForString(PathBuilder<?> entityPath,StringFilterCriteria criteria){
        String key = criteria.getKey();
        String operation = criteria.getOperation();
        StringPath path = entityPath.getString(key);
        String value=criteria.getValue();
        
        if("startsWithIgnoreCase".equalsIgnoreCase(operation)){
            return path.startsWithIgnoreCase(value);
        }else if("endsWithIgnoreCase".equalsIgnoreCase(operation)){
            return path.endsWithIgnoreCase(value);
        }else if("containsIgnoreCase".equalsIgnoreCase(operation)){
            return path.containsIgnoreCase(value);
        }else if("equalsIgnoreCase".equalsIgnoreCase(operation)){
            return path.equalsIgnoreCase(value);
        }else if("startsWith".equalsIgnoreCase(operation)){
            return path.startsWith(value);
        }else if("endsWith".equalsIgnoreCase(operation)){
            return path.endsWith(value);
        }else if("contains".equalsIgnoreCase(operation)){
            return path.contains(value);
        }else if("equals".equalsIgnoreCase(operation)){
            return path.matches(value);
        }else if("ne".equalsIgnoreCase(operation)){
            if(value==null){
                return path.isNotNull();
            }
            return path.ne(value);
        }else{
            throw new IllegalArgumentException("Invalid operator for text filter.");
        }
    }
    
    public static BooleanExpression createBooleanExpressionForListOfStrings(PathBuilder<?> entityPath,ListOfStringsFilterCriteria criteria){
        String key = criteria.getKey();
        String operation = criteria.getOperation();
        StringPath path = entityPath.getString(key);
        List<String> values=new ArrayList<>();
        values.addAll(criteria.getValues());
        
        if(!values.isEmpty()){
            if("in".equalsIgnoreCase(operation)){
                if(values.size()==1){
                    values.add("");
                }
                return path.in(values);
            }else if("notIn".equalsIgnoreCase(operation)){
                if(values.size()==1){
                    values.add("");
                }
                return path.notIn(values);
            }else{
                throw new IllegalArgumentException("Invalid operator for text array filter");
            }
        }else{
            throw new IllegalArgumentException("Empty text array is not allowed.");
        }
        
        
        
    }
    
    
    public static BooleanExpression createBooleanExpressionForDate(PathBuilder<?> entityPath,DateFilterCriteria criteria){
        String key = criteria.getKey();
        String operation = criteria.getOperation();
        DatePath<Date> path = entityPath.getDate(key,Date.class);
        Date date = criteria.getDate();
        
        if(date !=null){
            if("before".equalsIgnoreCase(operation)){
                return path.before(date);
            }else if("after".equalsIgnoreCase(operation)){
                return path.after(date);
            }else {
                throw new IllegalArgumentException("Invalid operator for date filter.");
            }
        }
        
        throw new IllegalArgumentException("Invalid date.");
    }
    
    
    public static BooleanExpression createBooleanExpressionForDateRange(PathBuilder<?> entityPath,DateRangeFilterCriteria criteria){
        String key = criteria.getKey();
        String operation = criteria.getOperation();
        DatePath<Date> path = entityPath.getDate(key,Date.class);
        
        Date start = criteria.getStart();
        Date end = criteria.getEnd();
        
        if(start !=null && end !=null){
            if("between".equalsIgnoreCase(operation)){
                return path.between(start, end);
            }else if("notBetween".equalsIgnoreCase(operation)){
                return path.between(start, end).not();
            }else{
                throw new IllegalArgumentException("Invalid operator for date range filter.");
            }
        }
        
        
        throw new IllegalArgumentException("Invalid start date or end date");
    }
    
    public static BooleanExpression createBooleanExpressionForNumber(PathBuilder<?> entityPath, NumberFilterCriteria criteria){
        
        String key = criteria.getKey();
        String operation = criteria.getOperation();
        
        Long valNum=criteria.getValue();
        
        if(valNum==null){
            throw new IllegalArgumentException("Invalid value for number filter.");            
        }

        Class<Long> clz=Long.class;
        NumberPath<?> path=null;
        path = entityPath.getNumber(key, clz);
        
        if("gt".equalsIgnoreCase(operation)){
            return path.gt(valNum);
        }else if("goe".equalsIgnoreCase(operation)){
            return path.goe(valNum);
        }else if("eq".equalsIgnoreCase(operation)){
            return path.in(valNum);
        }else if("lt".equalsIgnoreCase(operation)){
            return path.lt(valNum);
        }else if("loe".equalsIgnoreCase(operation)){
            return path.loe(valNum);
        }
        
        throw new IllegalArgumentException("Invalid operator for number filter.");
    }
    
    protected static <E extends Number & Comparable<Number>> NumberPath<E> defineNumberPath(String key,Class<E> cls,  PathBuilder<?> entityPath){
        return entityPath.getNumber(key, cls);
    }
    
    protected static boolean isNumeric(Object object){
        try{
            new BigInteger(object.toString());
            return true;
        }catch(Exception ex){
            CommonLogger.log(PredicateFactory.class,"Error converting to BigInteger",ex);
        }
        
        try{
            new BigDecimal(object.toString());
            return true;
        }catch(Exception ex){
            CommonLogger.log(PredicateFactory.class,"Error converting to BigDecimal.",ex);
        }
        
        return false;
    }
    
    protected static boolean isDate(Object object){
        return object instanceof Date;
    }
    
    
}
