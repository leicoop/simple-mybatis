/**
 *
 */
package com.lei.mybatis.utils;


import java.util.Collection;
import java.util.Map;


/***
 * @author lei
 * @description utils class
 */
public final class CommonUtis {

    /***
     * @author lei
     * @description determine if a string is null
     * @param
     * @param src
     * @return boolean
     */
    public static boolean isNotEmpty(String src) {
        return src != null && src.trim().length() > 0;
    }

    /***
     * @author lei
     * @description determine if a collection is null or empty
     * @param
     * @param collection
     * @return boolean
     */
    public static boolean isNotEmpty(Collection<?> collection) {
        return collection != null && !collection.isEmpty();
    }


    /***
     * @author lei
     * @description determine if a arr is null or empty
     * @param
     * @param arr
     * @return boolean
     */
    public static boolean isNotEmpty(Object[] arr) {
        return arr != null && arr.length > 0;
    }

    /***
     * @author lei
     * @description trim a string input
     * @param
     * @param src
     * @return java.lang.String
     */
    public static String stringTrim(String src) {
        return (null != src) ? src.trim() : null;
    }
}
