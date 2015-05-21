/*
 * Copyright (c) 2011-2036 International Air Transport Association corp.
 * All Rights Reserved.
 */
package org.acca.retgui.domainmodel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.acca.retgui.dish.RecordElement;
import org.acca.retgui.dish.RecordIdentifier;
import org.apache.commons.lang.StringUtils;

public class Record {

	private Map<String, String> elementMap;
	private List<String> keys;
//	private long lineNum;
	private String rcid;

	/**
	 * default.
	 */
	public Record(){
		
	}
	/**
	 * RetRecord.
	 * 
	 * @param recordLine
	 *            String
	 * @param retVersion
	 *            RetVersion
	 */
	public Record(String recordLine, DishVersion dishVersion, String recordId,
			String notRecordId) {
		elementMap = new HashMap<String, String>();
		keys = new ArrayList<String>();
		RecordIdentifier identifier = dishVersion.getRecordIdentifier(recordId);

		if (identifier == null) {
			identifier = dishVersion.getRecordIdentifier(notRecordId);
		}

		parseRecord(recordLine, identifier);
	}

	/**
	 * Parse Record according Identifier Elements.
	 * 
	 * @param identifier
	 * @param recordLine
	 */
	public void parseRecord(String recordLine, RecordIdentifier identifier) {

		rcid = StringUtils.substring(recordLine, 0, 1);
		List<RecordElement> elements = identifier.getElements();

		for (RecordElement element : elements) {
			String value = StringUtils.substring(recordLine,
					element.getPosition() - 1, element.getPosition() - 1
							+ element.getLength());
			elementMap.put(element.getName(), value);
			keys.add(element.getName());
		}
	}

	/**
	 * get element value. equals RET value.
	 * 
	 * @param key
	 *            String
	 * @return String
	 */
	public String getElement(String key) {

		return this.elementMap.get(key);
	}

	/**
	 * set element value.
	 * 
	 * @param key
	 *            String
	 * @param value
	 *            String
	 */
	public void setElement(String key, String value) {
		if ((value != null) && (key != null)) {
			elementMap.put(key, value);
		}
	}

	

	public Map<String, String> getElementMap() {
		return elementMap;
	}

	public void setElementMap(Map<String, String> elementMap) {
		this.elementMap = elementMap;
	}

	

//	public long getLineNum() {
//		return lineNum;
//	}
//
//	public void setLineNum(long lineNum) {
//		this.lineNum = lineNum;
//	}
	public List<String> getKeys() {
		return keys;
	}
	public String getRcid() {
		return rcid;
	}

	

}
