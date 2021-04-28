package com.xmlval.online.service;

import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;

/**
 * Created by usrdjp on 4/14/2021.
 */
public class IbkValidator {
    public IbkValidator(){

    }

    public String validate(String xsdPath, String xmlPath){
        String respon = "";
        try {
            //SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            SchemaFactory factory = SchemaFactory.newInstance("http://www.w3.org/XML/XMLSchema/v1.1");
            File schemaFile = new File(xsdPath);
            Schema schema = factory.newSchema(schemaFile);
            Validator validator = schema.newValidator();
            File instanceFile = new File(xmlPath);
            validator.validate(new StreamSource(instanceFile));
            respon = "Instance Document "+instanceFile.getName().replace("null","")+" is valid according to "+schemaFile.getName().replace("null","");
            System.out.println(respon);
        } catch (IOException e){
            System.out.println("Exception: "+e.getMessage());
            respon = "Error occurs : "+e.getMessage();
        }catch(SAXParseException e2){
            int line = e2.getLineNumber();
            int col = e2.getColumnNumber();
            String message = e2.getMessage();
            String prsMessage = "Error found while validating the file\n" +
                    "line: " + line + "\n" +
                    "column: " + col + "\n" +
                    "message: " + message.substring(message.indexOf(":") + 2);
            System.out.println(prsMessage);
            respon = prsMessage;
        }
        catch(SAXException e1){
            System.out.println("SAX Exception: "+e1.getMessage());
            respon = "Error occurs : SAX Exception - "+e1.getMessage();
        }
        return respon;
    }
}
