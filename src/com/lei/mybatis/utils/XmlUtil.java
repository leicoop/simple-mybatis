/**
 * 
 */
package com.lei.mybatis.utils;



import com.lei.mybatis.constants.Constant;
import com.lei.mybatis.mapping.MappedStatement;
import com.lei.mybatis.session.Configuration;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/***
 * @author lei
 * @description class of utils used to parse xml files
 */
public final class XmlUtil
{


    /***
     * @author lei
     * @description method to read a xxxmapper.xml file and store its element info into configuration
     * @param
     * @param fileName
     * @param configuration
     * @return void
     */
    public static void readMapperXml(File fileName, Configuration configuration)
    {

        try
        {

            // get instance of reader
            SAXReader saxReader = new SAXReader();
            saxReader.setEncoding(Constant.CHARSET_UTF8);
            
            // execute reading and return a document instance
            Document document = saxReader.read(fileName);

            // get rootelement of this document
            Element rootElement = document.getRootElement();

            // if the suffix of this doc is not "xml"
            if (!Constant.XML_ROOT_LABEL.equals(rootElement.getName()))
            {
                System.err.println("only xml file can be read!");
                return;
            }
            //get namespace of doc
            String namespace = rootElement.attributeValue(Constant.XML_SELECT_NAMESPACE);

            //generate a list to store statments
            List<MappedStatement> statements = new ArrayList<>();

            //iterate each section
            for (Iterator iterator = rootElement.elementIterator(); iterator.hasNext();)
            {

                Element element = (Element)iterator.next();

                //get name of operation
                String eleName = element.getName();

                //produce a instance of statement
                MappedStatement statement = new MappedStatement();

                //match operation type and put info into statement entity
                if (Constant.SqlType.SELECT.value().equals(eleName))
                {
                    String resultType = element.attributeValue(Constant.XML_SELECT_RESULTTYPE);
                    statement.setResultType(resultType);
                    statement.setSqlCommandType(Constant.SqlType.SELECT);
                }
                else if (Constant.SqlType.UPDATE.value().equals(eleName))
                {
                    String paramType = element.attributeValue(Constant.XML_SELECT_PARAMTYPE);
                    statement.setSqlCommandType(Constant.SqlType.UPDATE);
                    statement.setParameterType(paramType);
                }
                else if(Constant.SqlType.INSERT.value().equals(eleName))
                {
                    String paramType = element.attributeValue(Constant.XML_SELECT_PARAMTYPE);
                      statement.setSqlCommandType(Constant.SqlType.INSERT);
                    statement.setParameterType(paramType);
                }
                else if(Constant.SqlType.DELETE.value().equals(eleName))
                {
                         statement.setSqlCommandType(Constant.SqlType.DELETE);
                }

                //generate sqlId as unique identification of each statement entity in which contains all info of operation
                String sqlId = namespace + "." + element.attributeValue(Constant.XML_ELEMENT_ID);

                //inject all info
                statement.setSqlId(sqlId);
                statement.setNamespace(namespace);
                statement.setSql(CommonUtis.stringTrim(element.getStringValue()));
                statements.add(statement);


                System.out.println(statement);

                //put sqlid and statement into configuration
                configuration.addMappedStatement(sqlId, statement);

                //put type instance and proxyFactory into configuration
                configuration.addMapper(Class.forName(namespace));
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

}
