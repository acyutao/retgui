package org.acca.retgui.service;

import java.util.List;

import org.acca.retgui.domainmodel.FileType;
import org.acca.retgui.domainmodel.Record;
import org.acca.retgui.domainmodel.Transaction;

public class XrateFileParser extends BaseFileParser{

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

		if (fileName.matches("\\d{4}_XRATES.csv")) {

			return FileType.XRATE;
		}

		return null;
	}

}
