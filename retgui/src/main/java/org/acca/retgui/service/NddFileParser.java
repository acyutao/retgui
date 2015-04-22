package org.acca.retgui.service;

import java.util.List;

import org.acca.retgui.domainmodel.FileType;
import org.acca.retgui.domainmodel.Record;
import org.acca.retgui.domainmodel.Transaction;
import org.acca.retgui.utils.NumberUtils;
import org.acca.retgui.utils.PublicUtils;

public class NddFileParser extends BaseFileParser{
	private static final String FILENAME_EXT = "BSP";

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

		// 1. check A
		if (!fileName.startsWith("A")) {
			return null;
		}

		// 2. check n
		String minName = fileName.substring(4, 5);
		if (!minName.equals("n")) {
			return null;
		}

		// 3. check number
		String numberFile = fileName.substring(5, 8);
		if (!NumberUtils.isDigits(numberFile)) {
			return null;
		}

		// 4. check file suffix
		String suffix = PublicUtils.getExtName(fileName);
		if (!FILENAME_EXT.equals(suffix)) {
			return null;
		}
		return FileType.NDD;
	}

}
