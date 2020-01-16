package com.kgc.util;

import com.kgc.dao.CoustomIInvocationHandler;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class TestCustomInvocationHandler implements CoustomIInvocationHandler
{
    private Object o;

    public TestCustomInvocationHandler(Object o)
    {
        this.o=o;
    }

    @Override
    public Object invoke(Method method,Object[]args) {
        try {
            System.out.println("----");
            return method.invoke(o,args);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
}
