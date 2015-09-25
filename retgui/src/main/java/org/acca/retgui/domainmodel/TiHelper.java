/*
 * Copyright (c) 2011-2036 International Air Transport Association corp.
 * All Rights Reserved.
 */
package org.acca.retgui.domainmodel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.util.ArrayList;
import java.util.List;

import org.acca.retgui.dish.DishFileException;
import org.acca.retgui.domainmodel.FileInfo;
import org.acca.retgui.utils.TextFileLineReader;

public class TiHelper {


	private DishVersion dishVersion;
	private FileInfo fileInfo;
	@SuppressWarnings("unused")
	private MappedByteBuffer mappedBuffer;
	private long mapSize;
	@SuppressWarnings("unused")
	private byte[] retLineBytes;

	private TextFileLineReader reader;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private List<String> currentTrans = new ArrayList();


	/**
	 * RetHelper.
	 * 
	 * @param retVersion
	 *            RetVersion
	 * @param fileUtil
	 *            RetFileUtil
	 * @throws DishFileException
	 *             DishFileException
	 */
	public TiHelper(DishVersion dishVersion, TiFileUtil fileUtil)
			throws DishFileException {
		this.dishVersion = dishVersion;
		this.fileInfo = fileUtil.getFileInfo();
		retLineBytes = new byte[fileInfo.getLineAndLindFeedWidth()];

		try {
			reader = new TextFileLineReader(new FileInputStream(new File(
					fileUtil.getFilePathAndName())));
		} catch (FileNotFoundException e) {
			throw new DishFileException(fileUtil.getFilePathAndName()
					+ " File Read Error!");
		}

	}


	public TiTransaction fetchTransRecords(long beginLineNum) {

		String line = "";
		while (line != null) {

			try {
				line = reader.readALine(TiFileUtil.TI_FILE_LINE_WIDTH);

				// 读取到最后一行，将最后一个交易返回
				if (line != null && line.startsWith("9")) {
					if (currentTrans.size() > 0) {
						return new TiTransaction(beginLineNum, currentTrans,
								dishVersion);
					}
				}

				// 不是TI1和TI9
				if (!(line == null || line.startsWith("1") || line
						.startsWith("9"))) {
					currentTrans.add(line);

				}
			} catch (IOException e) {
				throw new RuntimeException("Read line error.");
			}

		}
		return null;

	}

	/**
	 * 从内存中读文件头，以recordLine存储.
	 * 
	 * @return String
	 */
	public String fetchHeadRecords() {
		return fileInfo.getFirstLine();
	}

	public FileInfo getFileInfo() {
		return this.fileInfo;
	}

	public long getMapSize() {
		return mapSize;
	}

	public void setMapSize(long mapSize) {
		this.mapSize = mapSize;
	}

}
