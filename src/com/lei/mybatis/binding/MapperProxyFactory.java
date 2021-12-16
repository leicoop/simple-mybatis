/**
 * 
 */
package com.lei.mybatis.binding;




import com.lei.mybatis.session.sqlsession.SqlSession;

import java.lang.reflect.Proxy;

/***
 * @author lei
 * @description the proxyfatory to produce proxy instance
 */
public class MapperProxyFactory<T>
{

    private final Class<T> mapperInterface;

   /***
 * @author lei
 * @description constructor of factory
 * @param
 * @param mapperInterface
 * @return
 */
    public MapperProxyFactory(Class<T> mapperInterface)
    {
        this.mapperInterface = mapperInterface;
    }

   /***
    * @author lei
    * @description method to produce proxy instance
    * @param
    * @param sqlSession
    * @return T
    */
    public T newInstance(SqlSession sqlSession)
    {
        MapperProxy<T> mapperProxy = new MapperProxy<T>(sqlSession, this.mapperInterface);
        return newInstance(mapperProxy);
    }

    /***
     * @author lei
     * @description overload method of newInstance, to wrap proxy instance into a instance of interface such as ***Mapper
     * @param
     * @param mapperProxy
     * @return T
     */
    @SuppressWarnings("unchecked")
    protected T newInstance(MapperProxy<T> mapperProxy)
    {
        return (T)Proxy.newProxyInstance(this.mapperInterface.getClassLoader(), new Class[] {this.mapperInterface}, mapperProxy);
    }
}
