/*
 * Copyright (c) 2011-2036 International Air Transport Association corp.
 * All Rights Reserved.
 */
package org.acca.retgui.domainmodel;

import org.acca.retgui.dish.DishFileException;
import org.acca.retgui.dish.DishFileUtil;
import org.acca.retgui.domainmodel.FileInfo;


public class TiFileUtil extends DishFileUtil {

	// Ti 文件行字符长度
	public static final int TI_FILE_LINE_WIDTH = 76;

	private String filePathAndName;

	private String absolutePath;
	private String fileName;



	/**
	 * RetFileUtil.
	 * 
	 * @param filePathAndName
	 *            String
	 * @throws DishFileException
	 *             DishFileException
	 */
	public TiFileUtil(String absolutePath, String fileName) throws DishFileException {
		super(absolutePath+fileName);
		this.filePathAndName = absolutePath+fileName;
		this.absolutePath=absolutePath;
		this.fileName=fileName;
	}

	/**
	 * getLineNumber.
	 * 
	 * @return long
	 * @throws DishFileException
	 *             DishFileException
	 */
	public long getLineNumber() throws DishFileException {
		return super.getTotalLineNumber();
	}

	/**
	 * getFileInfo.
	 * 
	 * @return getLineNumber
	 * @throws DishFileException
	 *             DishFileException
	 */
	public FileInfo getFileInfo() throws DishFileException {

		FileInfo fileInfo = new FileInfo();
		fileInfo.setAbsolutePath(absolutePath);
		fileInfo.setFileName(fileName);
		fileInfo.setFileSize(getFileSize());
		fileInfo.setLineWidth(TiFileUtil.TI_FILE_LINE_WIDTH);
		fileInfo.setLineFeedWidth(getLineFeedWidth());
		fileInfo.setFirstLine(getHeader());
		return fileInfo;
	}


	@Override
	protected int getLineWidth() {
		return TiFileUtil.TI_FILE_LINE_WIDTH;
	}

	public String getFilePathAndName() {
		return filePathAndName;
	}

	public void setFilePathAndName(String filePathAndName) {
		this.filePathAndName = filePathAndName;
	}

	/**
	 * 获得文件首行字符串.
	 * 
	 * @return String
	 * @throws DishFileException
	 *             DishFileException
	 */
	protected String getHeader() throws DishFileException {

		return org.apache.commons.lang.StringUtils.rightPad(
				this.fileHeaderLine, TI_FILE_LINE_WIDTH);
	}



}
