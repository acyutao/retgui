/*
 * Copyright (c) 2011-2036 International Air Transport Association corp.
 * All Rights Reserved.
 */
package org.acca.retgui.dish;

import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

/**
 * JAXB Utils.
 * 
 * @version Seurat v1.0
 * @author Yin Yanjun, 2013-2-6
 */
public class JAXBUtils {

    private JAXBUtils() {

    }

    @SuppressWarnings("rawtypes")
    private static JAXBContext newJAXBContext(Class jaxbBindClass) throws JAXBException {
        return JAXBContext.newInstance(jaxbBindClass);
    }

    /**
     * read java object from XML.
     * 
     * @param <T> - return parameter type
     * @param jaxbBindClass - JAXB bind class
     * @param xmlFileName - XML filename
     * @return JAXB bing object
     * 
     * @throws JAXBException if read XML faild
     */
    public static <T> T readObjectFromXml(Class<T> jaxbBindClass, String xmlFileName) throws JAXBException {
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(xmlFileName);
        return readObjectFromXml(jaxbBindClass, is);
    }

    /**
     * read java object from XML.
     * 
     * @param <T> - return parameter type
     * @param jaxbBindClass - JAXB bind class
     * @param is - XML input stream
     * @return JAXB bing object
     * @throws JAXBException if read XML faild
     */
    @SuppressWarnings("unchecked")
    public static <T> T readObjectFromXml(Class<T> jaxbBindClass, InputStream is) throws JAXBException {
        Unmarshaller unmarshaller = createUnmarshaller(jaxbBindClass);
        return (T) unmarshaller.unmarshal(is);
    }

    @SuppressWarnings("rawtypes")
    private static Unmarshaller createUnmarshaller(Class jaxbBindClass) throws JAXBException {
        Unmarshaller unmarshaller = newJAXBContext(jaxbBindClass).createUnmarshaller();
        return unmarshaller;
    }

    /**
     * write java object to XML.
     * 
     * @param jaxbElement - JAXB bind class's object
     * @param os - XML output stream
     * @throws JAXBException if write XML faild
     */
    public static void writeObjectToXml(Object jaxbElement, OutputStream os) throws JAXBException {
        Marshaller marshaller = createMarshaller(jaxbElement);
        marshaller.marshal(jaxbElement, os);
    }

    private static Marshaller createMarshaller(Object jaxbElement) throws JAXBException {
        Marshaller marshaller = newJAXBContext(jaxbElement.getClass()).createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FRAGMENT, false);
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        return marshaller;
    }
}
