package com.sunfund.core.common.interceptor;


import com.sunfund.core.utils.RedisComponent;
import com.sunfund.userSys.entity.SfUser;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.Id;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.logging.Logger;

/**
 * Created by 9douyu on 2017/10/15.
 */

public class MyInterceptor implements HandlerInterceptor {

    private static final Logger logger = Logger
			.getLogger(String.valueOf(MyInterceptor.class));

    private ThreadLocal<Long> time = new ThreadLocal<Long>();

    private static final String USER_ID = "userId";

    private static final String TOKEN = "token";


    @Autowired
    private RedisComponent redis;


//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, String id,String token)
//            throws Exception {
//        try {
//            String usertoken =redis.get(id);
//
//                if (usertoken==token){
//
//                    return true;  //说明用户已经登录，直接放行
//                }
//
//        }catch (Exception e) {
//            e.printStackTrace();
//
//        }
//
//        //说明用户未登录，跳转到登录页面
//        return false;// 只有返回true才会继续向下执行，返回false取消当前请求
//    }



    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {

          //获取url地址
//        StringBuffer uri = request.getRequestURL();
//        String url = uri.toString();

           // 记录Controller响应开始时间
           long startTime = System.currentTimeMillis();

           time.set(startTime);

           String id = request.getParameter(USER_ID);

           if (id != null){

               String userToken = request.getParameter(TOKEN);

               if (userToken != null){

                   String token = redis.get(id);

                   if (userToken == token) {
                       //说明用户已经登录，直接放行

                       return true;

                   }

               }
           }

            System.out.print("jingg");
        this.writerJson(response,"用户未登录，请先登录",400);
        return false; //说明用户没有登录，跳转到登录页面。
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        System.out.println(">>>MyInterceptor2>>>>>>>请求处理之后进行调用，但是在视图被渲染之前（Controller方法调用之后）");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        System.out.println(">>>MyInterceptor2>>>>>>>在整个请求结束之后被调用，也就是在DispatcherServlet 渲染了对应的视图之后执行（主要是用于进行资源清理工作）");
    }

    /**
     * 向浏览器输出数据
     *
     * @param response
     * @param text
     * @param errorCode
     * @throws Exception
     */
    private void writerJson(HttpServletResponse response, String text,
                            int errorCode) throws Exception {

//      response.setCharacterEncoding("UTF_8");//设置Response的编码方式为UTF-8
        response.setHeader("Content-type","text/html;charset=UTF-8");//向浏览器发送一个响应头，设置浏览器的解码方式为UTF-8,其实设置了本句，也默认设置了Response的编码方式为UTF-8，但是开发中最好两句结合起来使用
        //response.setContentType("text/html;charset=UTF-8");同上句代码作用一样
        PrintWriter writer = response.getWriter();
        writer.write(text);

    }

}
