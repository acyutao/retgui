/*
 * Copyright (c) 2011-2036 International Air Transport Association corp.
 * All Rights Reserved.
 */
package org.acca.retgui.domainmodel;

import java.util.ArrayList;
import java.util.List;
import org.acca.retgui.dish.DishConst;
import org.acca.retgui.dish.NoSuchRcidException;
import org.acca.retgui.utils.StringUtils;

/**
 * 
 * RetTransaction.
 * 
 * 
 * @version Seurat v1.0
 * @author Yu Tao, 2012-11-12
 */
public class RetTransaction extends Transaction{

	
	public RetTransaction(Long beginLineNum,List<String> transRecords, DishVersion dishVersion) {

        long lineNum=beginLineNum;
		for (int i = 0; i < transRecords.size(); i++) {
			String recordId=DishConst.IT0+StringUtils.subString(transRecords.get(i), 0, 1);
			Record retRecord = new Record(transRecords.get(i), dishVersion,recordId,DishConst.IT0+"*");
			retRecord.parseRecord();
			retRecord.setLineNum(lineNum);
			// sequential records
			sequentialRecords.add(retRecord);

			if (retRecord.isErrorRetRecord()) {
				if (noSuchRcidExceptions == null) {
					noSuchRcidExceptions = new ArrayList<NoSuchRcidException>();
				}
				noSuchRcidExceptions.add(retRecord.getNoSuchRcidException());
				continue;
			}
			lineNum++;
		}

	}



	public List<NoSuchRcidException> getNoSuchRcidExceptions() {
		return noSuchRcidExceptions;
	}

	public List<Record> getSequentialRecords() {
		return sequentialRecords;
	}







}
