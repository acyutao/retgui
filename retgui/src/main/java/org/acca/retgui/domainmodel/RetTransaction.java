/*
 * Copyright (c) 2011-2036 International Air Transport Association corp.
 * All Rights Reserved.
 */
package org.acca.retgui.domainmodel;

import java.util.List;

import org.acca.retgui.dish.DishConst;
import org.acca.retgui.utils.StringUtils;

/**
 * 
 * RetTransaction.
 * 
 * 
 * @version Seurat v1.0
 * @author Yu Tao, 2012-11-12
 */
public class RetTransaction extends Transaction {

	private String trnn;
	private String tdnr;

	/**
	 * default.
	 */
	public RetTransaction() {

	}

	public RetTransaction(/*Long beginLineNum, */List<String> transRecords,
			DishVersion dishVersion) {

//		long lineNum = beginLineNum;
		for (int i = 0; i < transRecords.size(); i++) {
			String recordId = DishConst.IT0
					+ StringUtils.subString(transRecords.get(i), 0, 1);
			Record retRecord = new Record(transRecords.get(i), dishVersion,
					recordId, DishConst.IT0 + "*");
//			retRecord.setLineNum(lineNum);
			if (recordId.equals(DishConst.IT02)) {
				trnn = retRecord.getElement(DishConst.TRNN);
				tdnr = retRecord.getElement(DishConst.TDNR).trim();
			}
			// sequential records
			sequentialRecords.add(retRecord);

//			lineNum++;
		}

	}

	public String getTrnn() {
		return trnn;
	}


	public String getTdnr() {
		return tdnr;
	}

	public List<Record> getSequentialRecords() {
		return sequentialRecords;
	}

}
