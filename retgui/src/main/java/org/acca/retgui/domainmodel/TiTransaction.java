/*
 * Copyright (c) 2011-2036 International Air Transport Association corp.
 * All Rights Reserved.
 */
package org.acca.retgui.domainmodel;

import java.util.List;

/**
 * 
 * RetTransaction.
 * 
 * 
 * @version Seurat v1.0
 * @author Yu Tao, 2012-11-12
 */
public class TiTransaction extends Transaction{

	
	public TiTransaction(Long beginLineNum,List<String> transRecords, DishVersion dishVersion) {

//        long lineNum=beginLineNum;
//		for (int i = 0; i < transRecords.size(); i++) {
//			String recordId=DishConst.TI+StringUtils.subString(transRecords.get(i), 0, 1);
//			Record retRecord = new Record(transRecords.get(i), dishVersion,recordId,DishConst.TI+"*");
//			retRecord.parseRecord();
//			retRecord.setLineNum(lineNum);
//			// sequential records
//			sequentialRecords.add(retRecord);
//
//			if (retRecord.isErrorRetRecord()) {
//				if (noSuchRcidExceptions == null) {
//					noSuchRcidExceptions = new ArrayList<NoSuchRcidException>();
//				}
//				noSuchRcidExceptions.add(retRecord.getNoSuchRcidException());
//				continue;
//			}
//			lineNum++;
//		}

	}


	public List<Record> getSequentialRecords() {
		return sequentialRecords;
	}







}
