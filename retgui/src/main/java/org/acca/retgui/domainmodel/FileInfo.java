/**
 * 
 */
package org.acca.retgui.domainmodel;

import java.io.Serializable;

/**
 * @author yutao
 * 
 */
public class FileInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String fileName;

	private String absolutePath;

	private String firstLine;

	private long fileSize; // M

	// 一行字符数
	private int lineWidth;

	// 换行符长度, 换行符可能为\n, \r, 或\r\n
	private int lineFeedWidth;

	public int getLineWidth() {
		return lineWidth;
	}

	public void setLineWidth(int lineWidth) {
		this.lineWidth = lineWidth;
	}

	public int getLineFeedWidth() {
		return lineFeedWidth;
	}

	public void setLineFeedWidth(int lineFeedWidth) {
		this.lineFeedWidth = lineFeedWidth;
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

	public String getFirstLine() {
		return firstLine;
	}

	public void setFirstLine(String firstLine) {
		this.firstLine = firstLine;
	}

	public long getFileSize() {
		return fileSize;
	}

	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}
    public int getLineAndLindFeedWidth(){
        return lineWidth + lineFeedWidth;
    }

}
