package org.acca.retgui.service;

import java.util.List;

import org.acca.retgui.dish.DishConst;
import org.acca.retgui.domainmodel.DishVersion;
import org.acca.retgui.domainmodel.FileType;
import org.acca.retgui.domainmodel.Record;
import org.acca.retgui.domainmodel.Transaction;
import org.acca.retgui.utils.StringUtils;

public class CsiFileParser extends BaseFileParser{

	public static final String CSI_VERSION = "CsiVersion";

	public CsiFileParser(String absolutePath, String fileName) {
		initialParam(absolutePath, fileName);
		this.dishVersion= StringUtils.subString(this.firstLine, 77, 80);
	}

	@Override
	public List<Transaction> parseTransactions() {

		return null;
	}

	@Override
	public Record parseFileHeader() {

		String rcid = DishConst.CFH;
		DishVersion version = DishVersion.getInstance(CSI_VERSION
				+ this.dishVersion);
		Record retRecord = new Record(this.firstLine, version, rcid,
				DishConst.CFH);
		return retRecord;
	}

	@Override
	public FileType parseFileType() {

		String firstLine = this.firstLine;

		if (firstLine == null) {
			return null;
		}


//		if (!firstLine.startsWith("CFH00000001")) {
//			return null;
//		}

		return FileType.CSI;

	}

	@Override
	public Transaction readATransaction() {
		// TODO Auto-generated method stub
		return null;
	}


}
