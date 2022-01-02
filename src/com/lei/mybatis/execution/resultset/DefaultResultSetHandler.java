/**
 *
 */
package com.lei.mybatis.execution.resultset;


import com.lei.mybatis.mapping.MappedStatement;


import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


/***
 * @author lei
 * @description resulthandler class to handle result into required type
 */
public class DefaultResultSetHandler implements ResultSetHandler {

    private final MappedStatement mappedStatement;


    public DefaultResultSetHandler(MappedStatement mappedStatement) {
        this.mappedStatement = mappedStatement;
    }


    /***
     * @author lei
     * @description method to convert resultset to entity
     * @param
     * @param resultSet
     * @return java.util.List<E>
     */
    @SuppressWarnings("unchecked")
    @Override
    public <E> List<E> handleResultSets(ResultSet resultSet) {
        try {

            List<E> result = new ArrayList<>();

            if (null == resultSet) {
                return null;
            }

            while (resultSet.next()) {
                // get class instance of target class with reflection
                Class<?> entityClass = Class.forName(mappedStatement.getResultType());

                //get instance of class
                E entity = (E) entityClass.newInstance();

                //get filed instance with reflection
                Field[] declaredFields = entityClass.getDeclaredFields();


                //iterate filed entity
                for (Field field : declaredFields) {

                    field.setAccessible(true);

                    //get type instance of filed
                    Class<?> fieldType = field.getType();

                    //match filed types
                    if (String.class.equals(fieldType)) {
                        field.set(entity, resultSet.getString(field.getName()));
                    } else if (int.class.equals(fieldType) || Integer.class.equals(fieldType)) {
                        field.set(entity, resultSet.getInt(field.getName()));
                    } else {

                        field.set(entity, resultSet.getObject(field.getName()));
                    }
                }
                //add into return list
                result.add(entity);
            }

            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
