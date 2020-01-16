package com.kgc.dao;

public class UserDaoImpl implements UserDao
{
    @Override
    public void query()
    {
        System.out.println("假装查询数据库");
    }

    @Override
    public void query(String s)
    {
        System.out.println(s+"假装查询数据库");
    }

    @Override
    public String queryName(String name) {
        return name;
    }
}
