/*
 * Copyright (c) 2011-2036 International Air Transport Association corp.
 * All Rights Reserved.
 */
package org.acca.retgui.domainmodel;

import org.acca.retgui.dish.DishFileException;
import org.acca.retgui.dish.DishFileUtil;
import org.acca.retgui.domainmodel.FileInfo;
import org.acca.retgui.utils.LongUtil;
import org.acca.retgui.utils.StringUtils;

/**
 * RetFileUtil.
 * 
 * @version Seurat v1.0
 * @author Yu Tao, 2012-11-6
 */
public class RetFileUtil extends DishFileUtil {

	// RET文件行字符长度
	public static final int RET_FILE_LINE_WIDTH = 255;

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
	public RetFileUtil(String absolutePath, String fileName) throws DishFileException {
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
		fileInfo.setLineWidth(RetFileUtil.RET_FILE_LINE_WIDTH);
		fileInfo.setLineFeedWidth(getLineFeedWidth());
		fileInfo.setFirstLine(getHeader());
		return fileInfo;
	}


	@Override
	protected int getLineWidth() {
		return RetFileUtil.RET_FILE_LINE_WIDTH;
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
				this.fileHeaderLine, RET_FILE_LINE_WIDTH);
	}
	
    public Long getTotalTrans() throws DishFileException {
        // 读取文件倒数第二行，获得RET文件交易数
        String line = this.secondLastLine;

        if (line == null || line.startsWith("1") || line.startsWith("Z")) {
//            throw new DishFileException("RET File is Not DISH Fomat!");
            return 0L;
        }

        if (LongUtil.valueOf(StringUtils.subString(line, 1, 7)) == null) {
            return 0L;
        }

        // 获取交易数
        return LongUtil.valueOf(StringUtils.subString(line, 1, 7));
    }   
    



}
