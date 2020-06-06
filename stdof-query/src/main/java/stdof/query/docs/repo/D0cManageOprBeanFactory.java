/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stdof.query.docs.repo;


import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import stdof.query.docs.opr.D0cManageOpr;

/**
 *
 * @author zeyarhtike
 */
public class D0cManageOprBeanFactory {
    
    private D0cManageOprBeanFactory(){}
    
    public static D0cManageOpr create(AnnotationConfigApplicationContext context){
        return  context.getAutowireCapableBeanFactory().createBean(D0cPersistenceAdapter.class);
    }
}