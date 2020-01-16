package com.kgc.dao;

public class kgcDaoImpl implements KgcDao {
    @Override
    public void queryName()throws Exception {
        System.out.println("queryName");
    }

    @Override
    public String query(String name) throws Exception {
        return name;
    }

    @Override
    public Integer get() throws Exception {
        return 2;
    }
}
