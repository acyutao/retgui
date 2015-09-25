/*
 * Copyright (c) 2011-2036 International Air Transport Association corp.
 * All Rights Reserved.
 */
package org.acca.retgui.dish;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Record Element. Dish Element.
 * 
 * @version Seurat v1.0
 * @author Yu Tao, 2012-11-6
 */
@XmlRootElement(name = "element")
@XmlAccessorType(XmlAccessType.FIELD)
public class RecordElement {

    @XmlTransient
    public static final String STATUS_C = "C";

    @XmlTransient
    public static final String STATUS_M = "M";

    @XmlTransient
    public static final String STATUS_NA = "N/A";

    @XmlTransient
    public static final String ATTRIBUTE_A = "A";

    @XmlTransient
    public static final String ATTRIBUTE_AN = "AN";

    @XmlTransient
    public static final String ATTRIBUTE_N = "N";
    
    @XmlTransient
    public static final String BLANK = " ";

    @XmlAttribute
    private String name;

    @XmlAttribute
    private int position;

    @XmlAttribute
    private int length;

    @XmlAttribute
    private String attribute;

    @XmlAttribute
    private String status;

    @XmlAttribute
    private String desc;

    @XmlElementWrapper(name = "trncs")
    @XmlElement(name = "trnc")
    private List<TrncStatus> trncs = new ArrayList();

    @XmlTransient
    private RecordIdentifier identifier;

    /**
     * RecordElement.
     */
    public RecordElement() {

    }

    /**
     * Constructor.
     * 
     * @param name String
     * @param position int
     * @param length int
     * @param attribute String
     * @param status String
     */
    public RecordElement(String name, int position, int length, String attribute, String status) {
        this.name = name;
        this.position = position;
        this.length = length;
        this.attribute = attribute;
        this.status = status;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public List<TrncStatus> getTrncs() {
        return trncs;
    }

    public void setTrncs(List<TrncStatus> trncs) {
        this.trncs = trncs;
    }

    /**
     * isMandatory. Firstly query TRNCs maintained in current element, return the TRNC`s status. If
     * not maintain TRNC, then return current element`s status. Mandatory : true Not Mandatory :
     * false
     * 
     * @param trnc String
     * @return boolean
     */
    public boolean isMandatory(String trnc) {
        if (trncs.size() > 0) {

            for (TrncStatus t : trncs) {

                if (STATUS_M.equals(t.getStatus())) {
                    String trncs = t.getTrncs();

                    if (trncs != null && trncs.contains(trnc)) {
                        return true;
                    }
                }

            }
        }

        return STATUS_M.equals(status);
    }

    /**
     * isNotApplicable. N/A : ture not N/A : false
     * 
     * @param trnc String
     * @return boolean
     */
    public boolean isNotApplicable(String trnc) {
        if (trncs.size() > 0) {

            for (TrncStatus t : trncs) {

                if (STATUS_NA.equals(t.getStatus())) {
                    String trncs = t.getTrncs();

                    if (trncs != null && trncs.contains(trnc)) {
                        return true;
                    }
                }

            }
        }

        return false;
    }

    /**
     * isAlphabetic. A : True Other : False
     * 
     * @return boolean
     */
    public boolean isAlphabetic() {
        return ATTRIBUTE_A.equals(attribute);
    }

    /**
     * isNumeric. N : True Other : False
     * 
     * @return boolean
     */
    public boolean isNumeric() {
        return ATTRIBUTE_N.equals(attribute);
    }

    /**
     * isAlphaNumeric. AN : True Other : False
     * 
     * @return boolean
     */
    public boolean isAlphaNumeric() {
        return ATTRIBUTE_AN.equals(attribute);
    }

    /**
     * Default Value.
     * 
     * @return String
     */
    public String getDefaultValue() {

        if (isNumeric()) {
            return "0";
        } else {

            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < this.length; i++) {
                sb.append(BLANK);
            }
            return sb.toString();
        }
    }

    public RecordIdentifier getIdentifier() {
        return identifier;
    }

    public void setIdentifier(RecordIdentifier identifier) {
        this.identifier = identifier;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Element : Name = ");
        sb.append(name);
        return sb.toString();
    }

    /**
     * 
     * Element Status for all TRNC.
     * 
     * @version Seurat v1.0
     * @author Yu Tao, 2012-12-27
     */
    @XmlRootElement(name = "trnc")
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class TrncStatus {

        @XmlAttribute
        private String status;

        @XmlAttribute
        private String trncs;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getTrncs() {
            return trncs;
        }

        public void setTrncs(String trncs) {
            this.trncs = trncs;
        }

    }

}
