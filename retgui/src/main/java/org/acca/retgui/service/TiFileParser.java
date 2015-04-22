package org.acca.retgui.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.acca.retgui.dish.DishConst;
import org.acca.retgui.domainmodel.DishVersion;
import org.acca.retgui.domainmodel.FileType;
import org.acca.retgui.domainmodel.Record;
import org.acca.retgui.domainmodel.TiFileUtil;
import org.acca.retgui.domainmodel.TiHelper;
import org.acca.retgui.domainmodel.TiTransaction;
import org.acca.retgui.domainmodel.Transaction;
import org.acca.retgui.utils.FileUtils;
import org.acca.retgui.utils.StringUtils;
import org.acca.retgui.utils.TextFileLineReader;

public class TiFileParser extends BaseFileParser{
	public static final String TI_VERSION = "TiVersion";

	public TiFileParser(String absolutePath, String fileName) {
		initialParam(absolutePath, fileName);
		this.dishVersion= StringUtils.subString(this.firstLine, 25, 28);

	}

	@Override
	public List<Transaction> parseTransactions() {
		TiFileUtil fileUtil = new TiFileUtil(this.absolutePath, this.fileName);
		DishVersion version = DishVersion.getInstance(TI_VERSION
				+ this.dishVersion);
		TiHelper helper = new TiHelper(version, fileUtil);

		long i = 2;

		List<Transaction> retTransList = new ArrayList<Transaction>();

		TiTransaction retTrans = helper.fetchTransRecords(i);

		while (retTrans != null) {

			retTransList.add(retTrans);
			i += retTrans.getSequentialRecords().size();
			retTrans = helper.fetchTransRecords(i);
		}

		return retTransList;
	}

	@Override
	public Record parseFileHeader() {

		String rcid = DishConst.TI
				+ StringUtils.subString(this.firstLine, 0, 1);
		DishVersion version = DishVersion.getInstance(TI_VERSION
				+ this.dishVersion);
		Record retRecord = new Record(this.firstLine, version, rcid,
				DishConst.TI + "*");
		return retRecord;
	}
	@Override
	public FileType parseFileType() {

		String firstLine = this.firstLine;
		String lastLine = null;
		String fileName = this.fileName;
		String fileSouce = this.absolutePath;
		File file = new File(fileSouce + fileName);

		if (!checkFileLineLengthForTi()) {
			return null;
		}

		if (!"1".equals(firstLine.substring(0, 1))) {
			return null;
		}

		try {
			lastLine = FileUtils.readLastLine(file);
		} catch (IOException e) {

			throw new RuntimeException(e);
		}

		if (!"9".equals(lastLine.substring(0, 1))) {
			return null;
		}

		return FileType.TI;
	}

	private boolean checkFileLineLengthForTi() {

		String fileName = this.fileName;
		String fileSouce = this.absolutePath;

		String line = null;
		InputStream inputStream = null;
		try {
			inputStream = FileUtils.openInputStream(new File(fileSouce
					+ fileName));
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		}
		TextFileLineReader reader = new TextFileLineReader(inputStream);

		try {

			while ((line = reader.readALine()) != null) {

				if (line.length() != 76) {
					return false;
				}
			}

		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e2) {
				}
			}
		}
		return true;
	}


}
