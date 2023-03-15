package com.itheima.web.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

//替换http servlet 根据请求的最后一段路径进行方法的分发
public class BaseServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //获取连接
        String uri = req.getRequestURI();

        //通过斜杆截取后面一段路径
        int index = uri.lastIndexOf('/');

        //使其能够转发，因为获取的后面一端包括/所以需要加1

        String substring = uri.substring(index + 1);

        //this是  谁调用我（方法），我代表谁（对象）
        Class<? extends BaseServlet> cls = this.getClass();

        //获取方法名字
        try {
            Method method = cls.getMethod(substring, HttpServletRequest.class, HttpServletResponse.class);
            //执行方法
            method.invoke(this,req,resp);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }


    }

}
