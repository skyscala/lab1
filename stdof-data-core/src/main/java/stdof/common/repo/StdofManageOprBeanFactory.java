/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stdof.common.repo;


import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import stdof.common.opr.StdofManageOpr;

/**
 *
 * @author zeyarhtike
 */
public class StdofManageOprBeanFactory {
    
    private StdofManageOprBeanFactory(){}
    
    public static StdofManageOpr create(AnnotationConfigApplicationContext context){
        return  context.getAutowireCapableBeanFactory().createBean(StdofPersistenceAdapter.class);
    }
}