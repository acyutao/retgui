package org.acca.retgui.service;

import java.util.ArrayList;
import java.util.List;

import org.acca.retgui.dish.DishConst;
import org.acca.retgui.domainmodel.DishVersion;
import org.acca.retgui.domainmodel.FileType;
import org.acca.retgui.domainmodel.Record;
import org.acca.retgui.domainmodel.RetFileUtil;
import org.acca.retgui.domainmodel.RetHelper;
import org.acca.retgui.domainmodel.RetTransaction;
import org.acca.retgui.domainmodel.Transaction;
import org.acca.retgui.utils.DateUtils;
import org.acca.retgui.utils.StringUtils;

public class RetFileParser extends BaseFileParser {

	public static final String RET_VERSION = "RetVersion";
	
	RetFileUtil fileUtil;
	DishVersion version;
	RetHelper helper;
	long i = 2;

	public RetFileParser(String absolutePath, String fileName) {
		initialParam(absolutePath, fileName);
		this.dishVersion= StringUtils.subString(this.firstLine, 11, 14);
		
		fileUtil = new RetFileUtil(this.absolutePath, this.fileName);
		version = DishVersion.getInstance(RET_VERSION
				+ this.dishVersion);
		helper = new RetHelper(version, fileUtil);
	}

	@Override
	public List<Transaction> parseTransactions() {
		
		long i = 2;

		List<Transaction> retTransList = new ArrayList<Transaction>();

		RetTransaction retTrans = helper.fetchTransRecords();

		while (retTrans != null) {

			retTransList.add(retTrans);
//			i += retTrans.getSequentialRecords().size();
			retTrans = helper.fetchTransRecords();
		}

		return retTransList;
	}
	
	@Override
	public Transaction readATransaction() {
		RetTransaction retTrans = helper.fetchTransRecords();
		if(retTrans != null){
//			i += retTrans.getSequentialRecords().size();
			return retTrans;
		}
		return null;
	}


	@Override
	public Record parseFileHeader() {

		String rcid = DishConst.IT0
				+ StringUtils.subString(this.firstLine, 0, 1);
		DishVersion version = DishVersion.getInstance(RET_VERSION
				+ this.dishVersion);
		Record retRecord = new Record(this.firstLine, version, rcid,
				DishConst.IT0 + "*");
		return retRecord;
	}
	
	public Record parseFileHeader(String firstLine){
		String rcid = DishConst.IT0
				+ StringUtils.subString(firstLine, 0, 1);
		DishVersion version = DishVersion.getInstance(RET_VERSION
				+ this.dishVersion);
		Record retRecord = new Record(firstLine, version, rcid,
				DishConst.IT0 + "*");
		return retRecord;
	}

	@Override
	public FileType parseFileType() {

		String firstLine = this.firstLine;

		if (firstLine == null) {
			return null;
		}

		if (firstLine.length() <= 24) {
			return null;
		}

		if (!firstLine.startsWith("1")) {
			return null;
		}

		String spedDateStr = firstLine.substring(1, 7);

		String prdaDateStr = firstLine.substring(18, 24);

		if (DateUtils.parseDate(spedDateStr, "yymmdd") == null) {

			return null;
		}

		if (DateUtils.parseDate(prdaDateStr, "yymmdd") == null) {

			return null;
		}

		return FileType.RET;

	}

	
}
