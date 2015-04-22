package org.acca.retgui.service;

import java.util.Date;
import java.util.List;

import org.acca.retgui.domainmodel.FileType;
import org.acca.retgui.domainmodel.Record;
import org.acca.retgui.domainmodel.Transaction;
import org.acca.retgui.utils.DateUtils;
import org.acca.retgui.utils.NumberUtils;
import org.acca.retgui.utils.StringUtils;

public class WadFileParser extends BaseFileParser{

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
		if (!fileName.substring(0, 3).equals("PAS")) {

			return null;
		}

		Date parsedate = null;

		// Fix Bug index out of String.
		int lengthOfFileName = fileName.contains(".") ? fileName
				.lastIndexOf(".") : fileName.length();

		if (fileName.substring(5, 6).equals("M")) {
			String date = fileName.substring(7, 15);
			parsedate = DateUtils.parseDate(date, "yyyyMMdd");
			if (parsedate == null) {
				return null;
			}

			String sequence = fileName.substring(16, lengthOfFileName);
			if (!NumberUtils.isDigits(sequence)) {
				return null;
			}

		} else if (fileName.substring(5, 7).equals("CH")) {
			String date = fileName.substring(8, 16);
			parsedate = DateUtils.parseDate(date, "yyyyMMdd");
			if (parsedate == null) {
				return null;
			}

			String sequence = fileName.substring(17, lengthOfFileName);
			if (!NumberUtils.isDigits(sequence)) {
				return null;
			}

		} else {
			return null;
		}
		return FileType.WAD;
	}

}
