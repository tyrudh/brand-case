package com.itheima.web.servlet;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.itheima.pojo.Brand;
import com.itheima.pojo.PageBean;
import com.itheima.service.BrandService;
import com.itheima.service.impl.BrandServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.List;

@WebServlet("/brand/*")
public class BrandServlet extends BaseServlet{

    private BrandService brandService = new BrandServiceImpl();



    public void selectAll(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //调用查询
        List<Brand> brands = brandService.selectAll();

        //转格式
        String jsonString = JSON.toJSONString(brands);

        //输出
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(jsonString);
    }



    public void Add(HttpServletRequest request, HttpServletResponse response) throws Exception {


        BufferedReader br = request.getReader();

        //按照行来读取

        String readLine = br.readLine();

        //转化为json对象

        Brand brand = JSON.parseObject(readLine, Brand.class);
        //调用方法
        brandService.add(brand);

        //相应成功的标志

        response.getWriter().write("success");

    }


    public void deleteID(HttpServletRequest request, HttpServletResponse response) throws Exception {
        BufferedReader br = request.getReader();

        //按照行来读取

        String readLine = br.readLine();

        //转化为json对象

        Brand brand = JSON.parseObject(readLine, Brand.class);

        brandService.delete(brand);

        response.getWriter().write("success");

    }


    public void deleteByIds(HttpServletRequest request, HttpServletResponse response) throws Exception {
        BufferedReader br = request.getReader();

        //按照行来读取

        String readLine = br.readLine();

        //转化为json对象

        int[] ints = JSON.parseObject(readLine, int[].class);

        brandService.deleteByIds(ints);


        response.getWriter().write("success");

    }

    public void selectByPages(HttpServletRequest request, HttpServletResponse response) throws Exception {

        //currentPage=1&pageSize=5

        String currentPage = request.getParameter("currentPage");
        String pageSize = request.getParameter("pageSize");

        int pageBegin = Integer.parseInt(currentPage);
        int pagenum = Integer.parseInt(pageSize);

        PageBean<Brand> pageBean = brandService.selectByPage(pageBegin, pagenum);

        //转格式
        String jsonString = JSON.toJSONString(pageBean);

        //输出
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(jsonString);


    }

    public void selectByPagesAndCondition(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setCharacterEncoding("utf-8");
        //currentPage=1&pageSize=5
        //1.获取当前页码handleCurrentChange，和每页展示条数handleSizeChange url?currentPage=1&pageSize=5,把参数放到请求之后
        String _currentPage = request.getParameter("currentPage");
        String _pageSize = request.getParameter("pageSize");

        //2.把接收的数据，转换成Integer
        int currentPage = Integer.parseInt(_currentPage);
        int pageSize = Integer.parseInt(_pageSize);


        //1.获取请求行数据（获取json格式的独特方法JavaWeb）
        BufferedReader reader = request.getReader();

        //2.读取请求行数据（json字符串）
        String s = reader.readLine();

        //3.把json格式转为java对象
        Brand brand = JSONObject.parseObject(s, Brand.class);


        //3.调用service进行查询
        PageBean<Brand> brandPageBean = brandService.selectByPageAndCondition(currentPage, pageSize, brand);

        //4.把service实现类中返回值改成json格式，
        String s2 = JSON.toJSONString(brandPageBean);

        //5.别忘了编码问题，从数据库出来，改成json的格式,并设置data的结果值
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(s2);
}

}
