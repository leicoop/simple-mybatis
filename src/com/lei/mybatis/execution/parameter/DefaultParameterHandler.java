/**
 *
 */
package com.lei.mybatis.execution.parameter;


import com.lei.mybatis.constants.Constant;
import com.lei.mybatis.mapping.MappedStatement;
import com.sun.org.apache.xpath.internal.SourceTree;


import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.util.*;


/***
 * @author lei
 * @description class contains methods to process input parameters of sql sentence
 */
public class DefaultParameterHandler implements ParameterHandler {


    private MappedStatement ms;
    private Object parameter;

    public DefaultParameterHandler(Object parameter, MappedStatement ms) {

        this.parameter = parameter;
        this.ms = ms;
    }

    /***
     * @author lei
     * @description set params into preparedstatement instance
     * @param
     * @param ps
     * @return void
     */
    @Override
    public void setParameters(PreparedStatement ps) {

        try {

            if (null != parameter) {
                if (parameter.getClass().isArray()) {
                    Object[] params = (Object[]) parameter;

                    //if the param is an entity ?

                    fillParams(ps, params);

                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /***
     * @author lei
     * @description tha actual method to execute params into preparedstament when param is a entity instead of primitive variables
     * @param
     * @param ps
     * @param params
     * @return void
     */
    private void fillParams(PreparedStatement ps, Object[] params) throws Exception {


        String parameterType = ms.getParameterType();
        Constant.SqlType commandType = ms.getSqlCommandType();

        if (null != parameterType && commandType.equals(Constant.SqlType.INSERT)
                || commandType.equals(Constant.SqlType.UPDATE)) {

            //reflection

            if (params.length == 1) {

                //get param entity
                Object sourceInsatnce = params[0];

                //get all filed entities of this param entity
                Field[] fields = sourceInsatnce.getClass().getDeclaredFields();

                //create a map to store relation between name of filed and the value mapped
                HashMap<String, Object> map = new HashMap<>();

                for (Field field : fields) {
                    field.setAccessible(true);
                    String name = field.getName();
                    Object value = field.get(sourceInsatnce);
                    map.put(name, value);
                }

                // call helper method  returning  all filed names presenting in sql sentence in the order in which the parameters appear
                List<String> paramNames = getParamNames(commandType, map);

                // get param names in order and return mapping value
                //insert value into location of preparedstatement
                for (int i = 0; i < paramNames.size(); i++) {
                    String name = paramNames.get(i);
                    ps.setObject(i + 1, map.get(name));
                }

            }


        } else {
            for (int i = 0; i < params.length; i++) {

                ps.setObject(i + 1, params[i]);
            }
        }

    }

    /***
     * @author lei
     * @description method to get all filed names of param entity showing in sql sentence and store into a list in a order of first presenting
     * @param
     * @param commandType
     * @param map
     * @return java.util.List<java.lang.String>
     */
    private List<String> getParamNames(Constant.SqlType commandType, HashMap<String, Object> map) throws Exception {

        String sql = ms.getSql();


        List<String> paramNames = new ArrayList<>();


        if (commandType.equals(Constant.SqlType.UPDATE) || commandType.equals(Constant.SqlType.INSERT)) {

            // get all param names in map

            Set<String> names = map.keySet();

            List<Integer> indexList = new ArrayList<>();
            for (String name : names) {

                // api of String class to get first idx of specific filed name in sql
                int idx = sql.indexOf(name);

                indexList.add(idx);

            }

            //sort idxs
            Collections.sort(indexList);

            //to get the actual string of filed names
            for (Integer idx : indexList) {


                int left = idx;

                int right = left;

                while (right < sql.length() && Character.isLetter(sql.charAt(right))) {
                    right++;
                }


                paramNames.add(sql.substring(left, right));

            }
            return paramNames;

        } else {
            throw new Exception("Non-primitive type parameter do not match current sql type");
        }


    }


}
