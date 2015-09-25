/*
 * Copyright (c) 2011-2036 International Air Transport Association corp.
 * All Rights Reserved.
 */
package org.acca.retgui.dish;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;


/**
 * DishRet.
 * 
 * @version Seurat v1.0
 * @author Yu Tao, 2012-12-5
 */
@XmlRootElement(name = "dish")
@XmlAccessorType(XmlAccessType.FIELD)
public class Dish {

    @XmlTransient
    private static Map<String, Dish> dishMap;

    @XmlAttribute
    private String version;

    @XmlElementWrapper(name = "identifiers")
    @XmlElement(name = "identifier")
    private List<RecordIdentifier> recordIdentifiers;

    static {
        String[] versions = { "Ret203", "Ret220"/*, "Ti203", "Ti220", "Cip203","Cip220", "Tsp203","Tsp220","Ndd203","Ndd220","Wad203","Wad220",
        		"Ibg203","Ibg220","Xrate203","Xrate220"*/
        		};
        dishMap = new HashMap();
        for (String str : versions) {
            try {
                String fileName = "dish" + str + ".xml";
                InputStream is = Thread.currentThread().getContextClassLoader()
                        .getResourceAsStream(fileName);

                if (is == null) {
                    continue;
                }

                Dish ret = JAXBUtils.readObjectFromXml(Dish.class, is);
                dishMap.put(str, ret);

            } catch (JAXBException e) {
            }
        }

    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public List<RecordIdentifier> getRecordIdentifiers() {
        return recordIdentifiers;
    }

    public void setRecordIdentifiers(List<RecordIdentifier> recordIdentifiers) {
        this.recordIdentifiers = recordIdentifiers;
    }

    /**
     * Dish XML Description.
     * 
     * @param version String
     * @return DishRet
     */
    public static Dish getDish(String version) {
    	Dish ret = dishMap.get(version);

        if (ret == null) {
            throw new RuntimeException("Current System do not support the DISH version!!");
        }
        return ret;

    }
    


}
