package org.acca.retgui.service;

import java.util.Date;
import java.util.List;

import org.acca.retgui.domainmodel.FileType;
import org.acca.retgui.domainmodel.Record;
import org.acca.retgui.domainmodel.Transaction;
import org.acca.retgui.utils.DateUtils;
import org.acca.retgui.utils.StringUtils;

public class CipFileParser extends BaseFileParser {

	private static final String EU = "eu";
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

		if (!StringUtils.isEmpty(fileName)) {
			int index = fileName.lastIndexOf(".");
			if (index > -1) {
				fileName = fileName.substring(0, index);
			}
		}

		// check long
		if (fileName.length() != 20) {
			return null;
		}

		// check end filename
		if (!fileName.endsWith("_CIP")) {
			return null;
		}

		String eu = fileName.substring(2, 4);
		if (!eu.equals(EU)) {
			return null;
		}

		// check date
		String date = fileName.substring(8, 16);
		Date parsedate = DateUtils.parseDate(date, "yyyyMMdd");
		if (parsedate == null) {
			return null;
		}

		return FileType.CIP;
	}

}
