/*
 * 文件名：MapperRegistry.java
 * 版权：Copyright by www.huawei.com
 * 描述：
 * 修改人：ZTE
 * 修改时间：2019年3月8日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.lei.mybatis.binding;



import com.lei.mybatis.session.sqlsession.SqlSession;

import java.util.HashMap;
import java.util.Map;

/***
 * @author lei
 * @description class contains a map to store the mapping relation between interface or class entity and corresponding proxyfactory instance
 */
public class MapperRegistry
{
    /** the knownMappers */
    private final Map<Class<?>, MapperProxyFactory<?>> knownMappers = new HashMap<>();


    /***
     * @author lei
     * @description method to deposit instance of class or interface as type and its proxyfactory as value into a map
     * @param
     * @param type
     * @return void
     */
    public <T> void addMapper(Class<T> type)
    {
        this.knownMappers.put(type, new MapperProxyFactory<T>(type));
    }
    

    /***
     * @author lei
     * @description method to return a proxyfactory instance mapped by specific class or interface
     * @param
     * @param type
     * @param sqlSession
     * @return T
     */
    @SuppressWarnings("unchecked")
    public <T> T getMapper(Class<T> type, SqlSession sqlSession)
    {
      MapperProxyFactory<T> mapperProxyFactory = (MapperProxyFactory<T>)this.knownMappers.get(type);
      
      return mapperProxyFactory.newInstance(sqlSession);
    }
}
