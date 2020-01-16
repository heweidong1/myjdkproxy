package com.kgc.proxy;

import com.kgc.dao.CoustomIInvocationHandler;

import javax.tools.JavaCompiler;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

/*
* 动态代理
* */

public class ProxyUtil
{
    /*
    * content -->string
    * file java
    * class
    * new----反射
    *
    * 完全模仿jdk动态代理
    * */

    public static Object newInstance(Class targetInt, CoustomIInvocationHandler h) throws NoSuchMethodException, ClassNotFoundException {
        //通过目标对象获取到接口
        //Class targetInt = target.getClass().getInterfaces()[0];

        //得到接口中的所有的方法
        Method method[] = targetInt.getDeclaredMethods();
        String line="\n";
        String tab="\t";
        //获取接口的名字
        String infName = targetInt.getSimpleName();
        String content="";
        String packageContent="package com.kgc.google;"+line;
        String importContent = "import "+targetInt.getName()+";"+line
                               +"import com.kgc.dao.CoustomIInvocationHandler;"+line
                               +"import java.lang.reflect.Method;"+line;
        String clazzFirstLineContent="public class $Proxy implements "+infName+"{"+line;
        //String filedContent=tab+"private "+infName+" dao;"+line;
        String filedContent=tab+"private CoustomIInvocationHandler  h;"+line;

        String construatatContent=tab+"public $Proxy(CoustomIInvocationHandler h){" +line+
                tab+tab+"this.h= h;" +
                line+tab+"}"+line;

        String methodContent="";
        //遍历接口中的方法
        for (Method method1 : method) {
            //获取方法的返回类型
            String returnTypeName = method1.getReturnType().getSimpleName();
            //如果不是void 则返回
            String isreturn="";
            if(!returnTypeName.equals("void"))
            {
                isreturn="return ("+returnTypeName+") ";
            }
            //获取方法名
            String methodName = method1.getName();
            //获取方法的参数
            //比如是 两个String，那么拿到的就是String.class string.class
            Class args[]=method1.getParameterTypes();
            //复写接口方法时候传的参
            String argsContent="";
            //invoke中的args参数
            String paramargsContent="";
            //ifargs 封装是否有传递的参数
            String ifargs="";
            //封装参数类型
            String agrsclass="";
            int flag=0;
            for (Class arg : args) {
                //这样拿到的就是String
                String temp= arg.getSimpleName();
                //String i,String k
                argsContent+=temp+" p"+flag+",";
                paramargsContent+=" p"+flag+",";
                agrsclass+=","+temp+".class";
                flag++;

            }
            //去除最后的，
            if(argsContent.length()>0)
            {
                argsContent=argsContent.substring(0,argsContent.lastIndexOf(","));
            }
            if(paramargsContent.length()>0)
            {
                paramargsContent=paramargsContent.substring(0,paramargsContent.lastIndexOf(","));
                ifargs="Object[] args =new Object[]{"+paramargsContent+"};";
            }else
            {
                ifargs="Object[] args = null;";
            }
            //不能写在这，因为写在这，在写在类中是已字符串的形式写进去的
            //Method method2 = Class.forName(targetInt.getName()).getMethod(methodName);

            methodContent+=tab+"public "+returnTypeName+" "+methodName+"("+argsContent+") throws Exception"+"{"+line
                           +tab+tab+ifargs+line
                           //获取到该方法的Method，因为invoke中有一个参数时method
                           +tab+tab+"Method method2 =  Class.forName(\""+targetInt.getName()+"\").getMethod(\""+methodName+"\""+agrsclass+");"+line
                           +tab+tab+isreturn+"h.invoke(method2,args);"+line
                           +tab+"}"+line;

        }

        //拼接在一起
        content=packageContent+importContent+clazzFirstLineContent+filedContent+construatatContent+methodContent+"}";


        File file =new File("D:\\com\\kgc\\google\\$Proxy.java");
        Object o=null;
        try {
            if(!file.exists())
            {
                file.createNewFile();
            }
            FileWriter fileWriter=new FileWriter(file);
            //写文件
            fileWriter.write(content);
            fileWriter.flush();
            fileWriter.close();




            //将java文件编译为class文件
            JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

            StandardJavaFileManager fileMgr = compiler.getStandardFileManager(null, null, null);
            Iterable units = fileMgr.getJavaFileObjects(file);

            JavaCompiler.CompilationTask t = compiler.getTask(null, fileMgr, null, null, null, units);
            t.call();
            fileMgr.close();

            //记载外部的class文件
            URL[] urls=new URL[]{new URL("file:D:\\\\")};
            URLClassLoader urlClassLoader= new URLClassLoader(urls);
            Class<?> aClass = urlClassLoader.loadClass("com.kgc.google.$Proxy");
            //得到构造方法,通过构造参数拿到具体的构造方法
            Constructor<?> constructor = aClass.getConstructor(CoustomIInvocationHandler.class);
            //利用拿到的构造方法，创建对象
            o = constructor.newInstance(h);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return o;
    }


















}
