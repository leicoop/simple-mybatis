/**
 * 
 */
package com.lei.mybatis.binding;


import com.lei.mybatis.mapping.MappedStatement;
import com.lei.mybatis.session.sqlsession.SqlSession;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Collection;




/***
 * @author lei
 * @description proxy class
 */
public class MapperProxy<T> implements InvocationHandler, Serializable
{

    private static final long serialVersionUID = -7861758496991319661L;

    private final SqlSession sqlSession;

    private final Class<T> mapperInterface;



    /***
 * @author lei
 * @description constructor of proxy class
 * @param
 * @param sqlSession
 * @param mapperInterface
 * @return
 */
    public MapperProxy(SqlSession sqlSession, Class<T> mapperInterface)
    {

        this.sqlSession = sqlSession;
        this.mapperInterface = mapperInterface;
    }

  /***
   * @author lei
   * @description override invoke method of interface InvocationHandler
   * @param
   * @param proxy
   * @param method
   * @param args
   * @return java.lang.Object
   */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args)
        throws Throwable
    {
        try
        {

            // this step is to determine if the called method belongs to xxxMapper class(interface)
            // or the Object class.


            //example:
            // if the method is xxxMapper.getClass(),what we can expect? the result is a Proxy or a xxxMapper?

            //answer is a Proxy class.
            if (Object.class.equals(method.getDeclaringClass()))
            {
                return method.invoke(this, args);
            }
           
            return  this.execute(method, args);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;

    }

  /***
   * @author lei
   * @description actual method will be executed
   * @param
   * @param method
   * @param args
   * @return java.lang.Object
   */
    private Object execute(Method method,  Object[] args)
    {
        String statementId = this.mapperInterface.getName() + "." + method.getName();
        MappedStatement ms = this.sqlSession.getConfiguration().getMappedStatement(statementId);
        
        Object result = null;
        switch (ms.getSqlCommandType())
        {
            case SELECT:
            {
                Class<?> returnType = method.getReturnType();
                
                if (Collection.class.isAssignableFrom(returnType))
                {
                    result = sqlSession.selectList(ms, args);
                }
                else
                {
                    result = sqlSession.selectOne(ms, args);
                }
                break;
            }
            case UPDATE:
            {
                sqlSession.update(ms, args);
                break;
            }
            case INSERT:
            {


                sqlSession.insert(ms, args);


                break;
            }

            case DELETE:{

                sqlSession.delete(ms, args);

            }
        }
        
        return result;
    }
    
}
