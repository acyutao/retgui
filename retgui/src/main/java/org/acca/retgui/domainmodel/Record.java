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

import org.acca.retgui.dish.NoSuchRcidException;
import org.acca.retgui.dish.RecordElement;
import org.acca.retgui.dish.RecordIdentifier;
import org.apache.commons.lang.StringUtils;

public class Record {

	private Map<String, String> elementMap;
	private List<Map.Entry<String, String>> elementList;
	private NoSuchRcidException noSuchRcidException;
	private String recordLine;

	private RecordIdentifier identifier;

	private long lineNum;


	/**
	 * RetRecord.
	 * 
	 * @param recordLine
	 *            String
	 * @param retVersion
	 *            RetVersion
	 */
	public Record(String recordLine, DishVersion dishVersion, String recordId, String notRecordId) {
		elementMap = new HashMap<String, String>();
		elementList = new ArrayList<Map.Entry<String, String>>();
		this.recordLine = recordLine;


		this.identifier = dishVersion.getRecordIdentifier(recordId);

		if (identifier == null) {
			this.identifier = dishVersion.getRecordIdentifier(notRecordId);
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
				String value = StringUtils.substring(recordLine,
						element.getPosition() - 1, element.getPosition() - 1
								+ element.getLength());

				Set<Map.Entry<String, String>> origianl = new HashSet<Map.Entry<String, String>>();
				origianl.addAll(elementMap.entrySet());
				elementMap.put(element.getName(), value);
				Set<Map.Entry<String, String>> current = new HashSet<Map.Entry<String, String>>();
				current.addAll(elementMap.entrySet());
				current.removeAll(origianl);
				elementList.addAll(current);
			}
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

		RecordElement element = this.identifier.getElement(key);

		// not trim, check whether trim in validators.
		String result = elementMap.get(key);

		if (result == null) {

			if (element != null) {
				result = StringUtils.substring(recordLine,
						element.getPosition() - 1, element.getPosition() - 1
								+ element.getLength());

				elementMap.put(element.getName(), result);
			} else {
				System.out.println("No such element, " + identifier.getName()
						+ ", " + key);

			}
		}

		return result;
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




	public long getLineNum() {
		return lineNum;
	}

	public void setLineNum(long lineNum) {
		this.lineNum = lineNum;
	}

	public List<Map.Entry<String, String>> getElementList() {
		return elementList;
	}

	public void setElementList(List<Map.Entry<String, String>> elementList) {
		this.elementList = elementList;
	}

}
