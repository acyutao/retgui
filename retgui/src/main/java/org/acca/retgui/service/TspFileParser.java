package org.acca.retgui.service;

import java.util.Date;
import java.util.List;

import org.acca.retgui.domainmodel.FileType;
import org.acca.retgui.domainmodel.Record;
import org.acca.retgui.domainmodel.Transaction;
import org.acca.retgui.utils.DateUtils;
import org.acca.retgui.utils.StringUtils;

public class TspFileParser extends BaseFileParser {

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

		if (StringUtils.isBlank(fileName)) {
			return null;
		}

		String date = fileName.substring(8, 16);
		Date parsedate = DateUtils.parseDate(date, "yyyyMMdd");
		if (parsedate == null) {
			return null;
		}

		String end = fileName.substring(16, 20);
		if (!end.equals("_TSP")) {

			return null;
		}
		return FileType.TSP;
	}

}
