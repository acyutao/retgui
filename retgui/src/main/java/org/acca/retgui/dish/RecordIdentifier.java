/*
 * Copyright (c) 2011-2036 International Air Transport Association corp.
 * All Rights Reserved.
 */
package org.acca.retgui.dish;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * RecordIdentifier.
 *
 * @version Seurat v1.0
 * @author Yu Tao, 2012-11-12
 */
@XmlRootElement(name = "identitier")
@XmlAccessorType(XmlAccessType.FIELD)
public class RecordIdentifier {
    
    @XmlElementWrapper(name = "elements") 
    @XmlElement(name = "element") 
    private List<RecordElement> elements = new ArrayList();
    
    @XmlAttribute
    private String name;
    
    @XmlTransient
    private Map<String, RecordElement> map;
    
    /***
     * RecordIdentifier.
     */
    public RecordIdentifier(){
        
    }
    
    /**
     * RecordIdentifier.
     * 
     * @param identifier String
     * @param elements List<RecordElement>
     */
    public RecordIdentifier(String identifier, List<RecordElement> elements) {
        this.name = identifier;

        if (elements != null) {
            this.elements = elements;

            for (RecordElement e : elements) {               
                map.put(e.getName(), e);
            }
        }
    }

    public List<RecordElement> getElements() {
        return elements;
    }

    public void setElements(List<RecordElement> elements) {
        this.elements = elements;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * getElement.
     * 
     * @param name String
     * @return RecordElement
     */
    public RecordElement getElement(String name) {
        
        if(map == null){
            map = new HashMap();
            
            for(RecordElement e : elements){
                e.setIdentifier(this);
                map.put(e.getName(), e);
            }
        }        
        
        return map.get(name);
    }
    
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Identifier : Name = ");
        sb.append(name);

        return sb.toString();
    }
    
    

    
    
}
