package org.acca.retgui.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.acca.retgui.domainmodel.FileType;
import org.acca.retgui.domainmodel.Record;
import org.acca.retgui.domainmodel.Transaction;
import org.acca.retgui.utils.FileUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * BaseInputFileService.
 * 
 * @author Zhang Xue
 * 
 */
public abstract class BaseFileParser {


	protected String firstLine;
	protected String fileName;
	protected String absolutePath;
	protected String dishVersion;

	public abstract Record parseFileHeader();

	public abstract List<Transaction> parseTransactions();

	public abstract FileType parseFileType();
	
	public String getFirstLine() {
		return firstLine;
	}

	public void setFirstLine(String firstLine) {
		this.firstLine = firstLine;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getAbsolutePath() {
		return absolutePath;
	}

	public void setAbsolutePath(String absolutePath) {
		this.absolutePath = absolutePath;
	}

	public String getDishVersion() {
		return dishVersion;
	}

	public void setDishVersion(String dishVersion) {
		this.dishVersion = dishVersion;
	}
	
	protected void initialParam(String absolutePath, String fileName){
		this.absolutePath=absolutePath;
		this.fileName=fileName;
		
		File inputFile = new File(this.absolutePath+this.fileName);
		try {
			this.firstLine = FileUtils.readFirstLine(inputFile);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
