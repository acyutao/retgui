/*
 * Copyright (c) 2011-2036 International Air Transport Association corp.
 * All Rights Reserved.
 */
package org.acca.retgui.dish;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * Dish Record.
 * 
 * @version Seurat v1.0
 * @author Yu Tao, 2012-11-6
 */
public class RetRecord {

    private Map<String, String> elementMap;
    private Map<String, String> elementUpdateMap;
    private NoSuchRcidException noSuchRcidException;
    private String recordLine;
    
    private String rcid;
    private RecordIdentifier identifier;
    

    // IT02,IT05,IT06... has multiple Record.
    private int sequence = 1;

    // Sequence In RetTransaction. Begin with 0.
    private int sequenceInRet = 0;
    
    private long ticketSequence=1L;

    // Attribute Errors in Current Record.
//    private List<RetErrorVO> errors = new ArrayList();

    /**
     * RetRecord.
     * 
     * @param recordLine String
     * @param retVersion RetVersion
     */
    public RetRecord(String recordLine, RetVersion retVersion) {
        elementMap = new HashMap<String, String>();
        elementUpdateMap =  new HashMap<String, String>();
        this.recordLine = recordLine;
        
        this.rcid = StringUtils.substring(recordLine, 0, 1);

        this.identifier = retVersion.getRecordIdentifier(DishRetConst.IT0 + rcid);

        if (identifier == null) {
            this.identifier = retVersion.getRecordIdentifier(DishRetConst.IT0 + "*");
        }
    }

    /**
     * Parse Record according Identifier Elements.
     */
    public void parseRecord() {

        String rcid = StringUtils.substring(recordLine, 0, 1);
        List<RecordElement> elements = this.identifier.getElements();

        if (elements == null || elements.size() == 0) {
            noSuchRcidException = new NoSuchRcidException(rcid, recordLine);
        } else {
            for (RecordElement element : elements) {
                String value = StringUtils.substring(recordLine, element.getPosition() - 1,
                        element.getPosition() - 1 + element.getLength());

                elementMap.put(element.getName(), value);
            }
        }
    }

    /**
     * get element value.
     * 1.修改后的值。
     * 2、如果验证有属性错误，返回默认值
     * 3、返回真正默认的值。
     * 
     * @param key String
     * @return String
     */
    public String getElement(String key) {

        RecordElement element = this.identifier.getElement(key);
        
        // Some case, element value will be updated some value. This case will priori return.
        String result = elementUpdateMap.get(key);
        if(result != null){
            return result;
        }
        
        // If has error, return default.
//        if (checkAttributeError(key)) {
//            return element.getDefaultValue();
//        }

        // not trim, check whether trim in validators.
        result = getOriginalElement(key);

        return result;
    }
    
    /**
     * get original element value. equals RET value.
     * 
     * @param key String
     * @return String
     */
    public String getOriginalElement(String key) {
        
        RecordElement element = this.identifier.getElement(key);
        
        // not trim, check whether trim in validators.
        String result = elementMap.get(key);

        if (result == null) {           

            if (element != null) {
                result = StringUtils.substring(recordLine, element.getPosition() - 1, element.getPosition() - 1
                        + element.getLength());

                elementMap.put(element.getName(), result);
            } else {
            	   System.out.println("No such element, " + identifier.getName() + ", " + key);
            
            }
        }

        return result;
    }

//    /**
//     * checkAttributeError. True : has Error. False : No Error.
//     * 
//     * @param elementName String
//     * @return
//     */
//    private boolean checkAttributeError(String elementName) {
//
//        if (errors != null) {
//            for (RetErrorVO vo : errors) {
//
//                if (elementName.equals(vo.getErrorField())) {
//                    return true;
//                }
//            }
//        }
//
//        return false;
//
//    }

    /**
     * set element value.
     * 
     * @param key String
     * @param value String
     */
    public void setElement(String key, String value) {
        if ((value != null) && (key != null)) {
            elementMap.put(key, value);
        }
    }
    
    /**
     * set element value.
     * 
     * @param key String
     * @param value String
     */
    public void setUpdateElement(String key, String value) {
        if ((value != null) && (key != null)) {
            elementUpdateMap.put(key, value);
        }
    }

    /**
     * Record ID. IT0*.
     * 
     * @return String
     */
    public String getRecordId() {
        return "IT0" + getElement("RCID");
    }

    public NoSuchRcidException getNoSuchRcidException() {
        return noSuchRcidException;
    }

    public boolean isErrorRetRecord() {
        return noSuchRcidException != null;
    }

    public String getRecordLine() {
        return recordLine;
    }

    public Map<String, String> getElementMap() {
        return elementMap;
    }

    public void setElementMap(Map<String, String> elementMap) {
        this.elementMap = elementMap;
    }

    public RecordIdentifier getIdentifier() {
        return identifier;
    }


    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    /**
     * RCID, 2,3,4,A,B,C,D...
     * 
     * @return String
     */
    public String getRcid() {
        return rcid;
    }

    public void setRcid(String rcid) {
        this.rcid = rcid;
    }

    public int getSequenceInRet() {
        return sequenceInRet;
    }

    public void setSequenceInRet(int sequenceInRet) {
        this.sequenceInRet = sequenceInRet;
    }

    public long getTicketSequence() {
        return ticketSequence;
    }

    public void setTicketSequence(long ticketSequence) {
        this.ticketSequence = ticketSequence;
    }



}
