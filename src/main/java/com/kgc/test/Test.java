package com.kgc.test;

import com.kgc.dao.KgcDao;
import com.kgc.dao.kgcDaoImpl;
import com.kgc.proxy.ProxyUtil;
import com.kgc.util.TestCustomInvocationHandler;

public class Test
{
    public static void main(String[] args) throws Exception {
//        UserDao targef = new UserDaoImpl();
//        UserDao proxy = (UserDao) ProxyUtil.newInstance(new UserDaoImpl());
////        proxy.query();
////        proxy.query("he");
//
//        String zhangsan = proxy.queryName("zhangsan");
//        System.out.println(zhangsan);

        kgcDaoImpl kgcDao=new kgcDaoImpl();
        TestCustomInvocationHandler h = new TestCustomInvocationHandler(kgcDao);
        KgcDao o =(KgcDao) ProxyUtil.newInstance(KgcDao.class, h);
        o.queryName();
        String query = o.query("lisi");
        System.out.println(query);

        Integer integer = o.get();
        System.out.println(integer);


    }















}
