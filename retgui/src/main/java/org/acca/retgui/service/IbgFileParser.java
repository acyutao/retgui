package org.acca.retgui.service;

import java.util.List;

import org.acca.retgui.domainmodel.FileType;
import org.acca.retgui.domainmodel.Record;
import org.acca.retgui.domainmodel.Transaction;
import org.acca.retgui.utils.DateUtils;

public class IbgFileParser extends BaseFileParser {

	@Override
	public Record parseFileHeader() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Transaction> parseTransactions() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FileType parseFileType() {
		String fileName = this.fileName;
		String date = fileName.substring(4, 10);
		if (DateUtils.parseDate(date, "yyMMdd") == null) {
			return null;
		}

		return FileType.IBG;
	}

}
