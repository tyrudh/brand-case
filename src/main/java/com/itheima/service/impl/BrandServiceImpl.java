package com.itheima.service.impl;

import com.itheima.mapper.BrandMapper;
import com.itheima.pojo.Brand;
import com.itheima.pojo.PageBean;
import com.itheima.service.BrandService;
import com.itheima.util.SqlSessionFactoryUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;



public class BrandServiceImpl implements BrandService {
//获取工厂连接
    SqlSessionFactory factory = SqlSessionFactoryUtils.getSqlSessionFactory();

    @Override
    public List<Brand> selectAll() {
    //2.1 获取sqlsession对象
        SqlSession sqlSession = factory.openSession();

        //获取BrandMapper
        BrandMapper mapper = sqlSession.getMapper(BrandMapper.class);


        //调用方法

        List<Brand> brands = mapper.selectAll();

        //释放资源
        sqlSession.close();


        //返回变量
        return brands;
    }

    @Override
    public void add(Brand brand) {
        //2.1 获取sqlsession对象
        SqlSession sqlSession = factory.openSession();

        //获取BrandMapper
        BrandMapper mapper = sqlSession.getMapper(BrandMapper.class);


        //调用方法
        mapper.add(brand);
        sqlSession.commit();
        //释放资源
        sqlSession.close();


    }

    @Override
    public void delete(Brand brand) {
        //2.1 获取sqlsession对象
        SqlSession sqlSession = factory.openSession();

        //获取BrandMapper
        BrandMapper mapper = sqlSession.getMapper(BrandMapper.class);
        mapper.delete(brand.getId());
        sqlSession.commit();
        //释放资源
        sqlSession.close();
    }

    @Override
    public void deleteByIds(int[] ids) {
        //2.1 获取sqlsession对象
        SqlSession sqlSession = factory.openSession();

        //获取BrandMapper
        BrandMapper mapper = sqlSession.getMapper(BrandMapper.class);
        //对应的方法
        mapper.deleteByIds(ids);

        sqlSession.commit();

        sqlSession.close();



    }

    @Override
    public PageBean<Brand> selectByPage(int pageBegin, int pagenum) {
        //2.1 获取sqlsession对象
        SqlSession sqlSession = factory.openSession();

        //获取BrandMapper
        BrandMapper mapper = sqlSession.getMapper(BrandMapper.class);
        //对应的方法

        int begin = (pageBegin-1) * pagenum;
        int size = pagenum;

        List<Brand> brand = mapper.selectByPage(begin,size);
        int count = mapper.selectTotalCount();

        PageBean<Brand> pageBean = new PageBean<>();
        pageBean.setRows(brand);
        pageBean.setCount(count);

        sqlSession.close();

        return pageBean;

    }

    @Override
    public PageBean<Brand> selectByPageAndCondition(int currentPage, int pageSize, Brand brand) {
        //1.获取sqlsession的对象
        SqlSession sqlSession = factory.openSession();
        //2.获取BrandMapper映射文件
        BrandMapper mapper = sqlSession.getMapper(BrandMapper.class);

        //3.计算,,处理一下brand条件，模糊表达式
        int begin = (currentPage - 1) * pageSize;
        int size = pageSize;

        //处理brand条件，模糊表达式
        String brandName = brand.getBrandName();
        if(brandName != null  && brandName.length()>0){
            brand.setBrandName("%"+brandName+"%");
        }
        String companyName = brand.getCompanyName();
        if(companyName != null  && companyName.length()>0){
            brand.setCompanyName("%"+companyName+"%");
        }

        //4.查询当前页的数据
        List<Brand> rows= mapper.selectByPageAndCondition(begin, size,brand);

        //5.查询总记录数
        int totalCount = mapper.selectTotalCountByCondition(brand);

        //6.把rows与totalCount封装成一个PageBean对象
        PageBean<Brand> pageBean = new PageBean<>();
        pageBean.setRows(rows);
        pageBean.setCount(totalCount);

        //7.释放资源
        sqlSession.close();

        //8.返回值
        return pageBean;
    }



}
