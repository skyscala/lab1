/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package springboot.war.controller;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import stdof.DbConfig;
import stdof.common.docs.entity.StdofEntity;
import stdof.common.opr.StdofManageOpr;
import stdof.common.repo.StdofManageOprBeanFactory;

import stdof.lang.JsonUtil;

/**
 *
 * @author zeyarhtike
 */


@RestController
@CrossOrigin
public class StdofController {
    
    
    @GetMapping(path = "/save")
    public ResponseEntity<Object> save(String payload){
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DbConfig.class)) {
            StdofEntity entity = JsonUtil.fromJsonString(payload, StdofEntity.class);
            StdofManageOpr saveOpr = StdofManageOprBeanFactory.create(context);
            saveOpr.save(entity);
            return ResponseEntity.ok(entity);
        }
    }
    
    
    
}
