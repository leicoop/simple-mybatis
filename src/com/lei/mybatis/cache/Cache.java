package com.lei.mybatis.cache;

/***
 * @author lei
 * @description cache interface defines basic methods should involved
 */
public interface Cache {


    String getId();

    void putObject(Object key, Object value);

    Object getObject(Object key);

    Object removeObject(Object key);

    void clear();

    int getSize();


}
