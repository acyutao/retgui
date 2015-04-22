package org.acca.retgui.service;

import java.util.List;

import org.acca.retgui.dish.DishConst;
import org.acca.retgui.domainmodel.DishVersion;
import org.acca.retgui.domainmodel.FileType;
import org.acca.retgui.domainmodel.Record;
import org.acca.retgui.domainmodel.Transaction;
import org.acca.retgui.utils.StringUtils;

public class HotFileParser extends BaseFileParser{
	
	public static final String HOT_VERSION = "HotVersion";

	public HotFileParser(String absolutePath, String fileName) {
		initialParam(absolutePath, fileName);
		this.dishVersion= StringUtils.subString(this.firstLine, 21, 24);
	}

	@Override
	public List<Transaction> parseTransactions() {

		return null;
	}

	@Override
	public Record parseFileHeader() {

		String rcid = DishConst.BFH01;
		DishVersion version = DishVersion.getInstance(HOT_VERSION
				+ this.dishVersion);
		Record retRecord = new Record(this.firstLine, version, rcid,
				DishConst.BFH01);
		return retRecord;
	}

	@Override
	public FileType parseFileType() {

		String firstLine = this.firstLine;

		if (firstLine == null) {
			return null;
		}


		if (!firstLine.startsWith("BFH00000001")) {
			return null;
		}

		return FileType.HOT;

	}

}
