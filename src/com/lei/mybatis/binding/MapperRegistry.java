
package com.lei.mybatis.binding;


import com.lei.mybatis.session.sqlsession.SqlSession;

import java.util.HashMap;
import java.util.Map;

/***
 * @author lei
 * @description class contains a map to store the mapping relation between interfaces or class entities and corresponding proxyfactory instances
 */
public class MapperRegistry {
    /**
     * the knownMappers
     */
    private final Map<Class<?>, MapperProxyFactory<?>> knownMappers = new HashMap<>();


    /***
     * @author lei
     * @description method to deposit a type instance of class or interface  as a key and its proxyfactory as a value into a map
     * @param
     * @param type
     * @return void
     */


    public <T> void addMapper(Class<T> type) {
        this.knownMappers.put(type, new MapperProxyFactory<T>(type));
    }


    /***
     * @author lei
     * @description method to return a proxyfactory instance mapped by specific type instance of a class or interface
     * @param
     * @param type
     * @param sqlSession
     * @return T
     */
    @SuppressWarnings("unchecked")
    public <T> T getMapper(Class<T> type, SqlSession sqlSession) {
        MapperProxyFactory<T> mapperProxyFactory = (MapperProxyFactory<T>) this.knownMappers.get(type);

        return mapperProxyFactory.newInstance(sqlSession);
    }
}
