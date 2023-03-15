package com.itheima.pojo;


//分页查询返回的对象

import java.util.List;

public class PageBean<T> {

    //数据总数
    private int count;
    //返回的集合 泛型能够适应所有的方式

    private List<T> rows;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

}
