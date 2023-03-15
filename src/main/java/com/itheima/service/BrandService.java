package com.itheima.service;

import com.itheima.pojo.Brand;
import com.itheima.pojo.PageBean;

import javax.management.MBeanServerDelegateMBean;
import java.util.List;

public interface BrandService {


    List<Brand> selectAll();

    void add(Brand brand);

    void delete(Brand brand);

    void deleteByIds(int[] ids);

    PageBean<Brand> selectByPage(int pageBegin,int pagenum);

    public PageBean<Brand> selectByPageAndCondition(int currentPage, int pageSize, Brand brand);

}
