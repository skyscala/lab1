/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cbvr.stdof.web;

import cbvr.stdof.web.service.StdofService;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import stdof.lang.CommonLogger;
import stdof.lang.JsonUtil;

/**
 *
 * @author zeyarhtike
 */

@WebServlet(urlPatterns = "/api/*",name="StdofServlet")
public class StdofServlet extends HttpServlet{

    private static final Class<?> TAG = StdofServlet.class;
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp,false); 
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req,resp,true);
    }
    
    protected void process(HttpServletRequest req,HttpServletResponse resp,boolean isPost) throws ServletException, IOException{
        
        CommonLogger.log(TAG, req.getPathInfo());
        CommonLogger.log(TAG, req.getContextPath());
        PrintWriter out = resp.getWriter();
        
        if(isPost){
            String pathInfo = req.getPathInfo();
            StringBuilder sb=new StringBuilder();

            while(true){
                String line = req.getReader().readLine();
                if(line==null){
                    break;
                }
                sb.append(line);
            }

            StdofService ctrl=new StdofService();
            resp.setContentType("application/json");

            if("/save".equals(pathInfo)){            
                out.write(JsonUtil.toJsonString(ctrl.save(sb.toString())));
            }else if("/delete".equals(pathInfo)){
                out.write(JsonUtil.toJsonString(ctrl.delete(sb.toString())));
            }else if("/query".equals(pathInfo)){
                out.write(JsonUtil.toJsonString(ctrl.query(sb.toString())));
            }
        }else{
            out.println(req.getPathInfo());
        }
    }
    
    
    
    
    
}
