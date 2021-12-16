

package com.lei.mybatis.constants;

/***
 * @author lei
 * @description class contains various constants such as mapper location and other necessary info
 */

public interface Constant
{

    String CHARSET_UTF8 = "UTF-8";

    String MAPPER_LOCATION = "mapper.location";
    
    String DB_DRIVER_CONF = "db.driver";

    String DB_URL_CONF = "db.url";

    String DB_USERNAME_CONF = "db.username";

    String db_PASSWORD = "db.password";


    String MAPPER_FILE_SUFFIX = ".xml";
    
    String XML_ROOT_LABEL = "mapper";
    
    String XML_ELEMENT_ID = "id";
    
    String XML_SELECT_NAMESPACE = "namespace";
    
    String XML_SELECT_RESULTTYPE = "resultType";

    String XML_SELECT_PARAMTYPE = "paramType";
    
  /***
   * @author lei
   * @description enum class of operation to database
   */
    public enum SqlType 
    {
        SELECT("select"),
        INSERT("insert"),
        UPDATE("update"),
        DEFAULT("default"),
        DELETE("delete");

        private String value;

        private SqlType(String value)
        {
            this.value = value;
        }

        public String value()
        {
            return this.value;
        }
    }

}
