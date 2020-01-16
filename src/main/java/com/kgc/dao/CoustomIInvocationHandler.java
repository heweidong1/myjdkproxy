package com.kgc.dao;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public interface CoustomIInvocationHandler
{
    public Object invoke(Method method, Object[] args) throws InvocationTargetException, IllegalAccessException;
}
