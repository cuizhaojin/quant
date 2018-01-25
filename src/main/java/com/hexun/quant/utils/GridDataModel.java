package com.hexun.quant.utils;

import java.util.List;

/**
 * 页面查询得到的对象集合封装
 * @param <T>  泛型
 */
public class GridDataModel<T> {

    private int total = 0;    //数据总数
    private List<T> rows;    //结果集

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }
}
