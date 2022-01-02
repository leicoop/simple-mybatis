package com.lei.mybatis.cache;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lei
 * @description CacheKey class contains methods to generate unique key for each mappedStatement
 */
public class CacheKey implements Cloneable, Serializable {

    /**
     * Default_Multiplier and Default_Hashcode are both prime numbers.
     * <p>
     * They are used to do calculations with input elements' hashcode to generate unique identification number of each element
     */
    private static final int DEFAULT_MULTIPLIER = 37;

    private static final int DEFAULT_HASHCODE = 17;

    private final int multiplier;

    private int hashcode;


    /**
     * CheckSum and count are factors of whole calculation process
     */
    private long checksum;
    private int count;

    /**
     * UpdateList will store elements added
     */
    private List<Object> updateList;


    public CacheKey() {
        this.multiplier = DEFAULT_MULTIPLIER;
        this.hashcode = DEFAULT_HASHCODE;
        this.count = 0;
        this.updateList = new ArrayList<>();
    }


    /**
     * @param
     * @param object
     * @return void
     * @author lei
     * @description This method is key method to generate unique id for each cacheKey entity
     */
    public void update(Object object) {

        /**
         * If input is null, the baseHashCode will be 1.
         * Otherwise, call getHashCode method to get its baseHashCode.
         */
        int baseHashCode = 0;
        try {
            baseHashCode = object == null ? 1 : getHashCode(object);
        } catch (Exception e) {
            e.printStackTrace();
        }

        /**
         * After having baseHashCode, following four steps will be performed to create very unique hashcode for this entity
         */
        count++;
        checksum += baseHashCode;
        baseHashCode *= count;
        hashcode = multiplier * hashcode + baseHashCode;

        /**
         * Add object into updateList
         */
        updateList.add(object);
    }


    @Override
    public int hashCode() {
        return this.hashcode;
    }


    /**
     * @param
     * @param object
     * @return int
     * @author lei
     * @description method to return hashcode of inputs
     */
    private int getHashCode(Object object) throws Exception {


        if (object == null) return 0;

        return object.hashCode();


    }


    /***
     * @author lei
     * @description override the equals method, compare each filed of two object to make sure cacheKey instance A equals instance B
     * @param
     * @param object
     * @return boolean
     */
    @Override
    public boolean equals(Object object) {
        if (object == this) return true;
        if (!(object instanceof CacheKey)) {
            return false;
        } else {
            CacheKey cacheKey = (CacheKey) object;

            if (this.hashcode != cacheKey.hashcode) {
                return false;
            } else if (this.checksum != cacheKey.checksum) {
                return false;
            } else if (this.count != cacheKey.count) {
                return false;
            } else {
                for (int i = 0; i < this.updateList.size(); ++i) {

                    Object thisObject = this.updateList.get(i);
                    Object thatObject = cacheKey.updateList.get(i);


                    if (thisObject == null) {
                        if (thatObject != null) {
                            return false;
                        }
                    } else if (!thisObject.equals(thatObject)) {
                        return false;
                    }
                }

                return true;
            }
        }


    }


}
