/*
 * Copyright (c) 2011-2036 International Air Transport Association corp.
 * All Rights Reserved.
 */
package org.acca.retgui.dish;

import org.acca.retgui.utils.MathUtil;




/**
 * RetFileInfo.
 * 
 * @version Seurat v1.0
 * @author Yu Tao, 2012-11-6
 */
public class RetFileInfo {

    private String filePathAndName;

    // RET文件大小，单位是字节
    private long fileSize;

    // 一行字符数
    private int lineWidth;

    // 换行符长度, 换行符可能为\n, \r, 或\r\n
    private int lineFeedWidth;

    // 行数
    private long lineNumber;

    // 本线程取得的行数
    private long mapEndLine;

    // 交易数
    private long transNumber;
    private String header;
    private String tailer;
    public String getFilePathAndName() {
        return filePathAndName;
    }
    public void setFilePathAndName(String filePathAndName) {
        this.filePathAndName = filePathAndName;
    }
    public long getFileSize() {
        return fileSize;
    }
    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }
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
    public long getLineNumber() {
        return lineNumber;
    }
    public void setLineNumber(long lineNumber) {
        this.lineNumber = lineNumber;
    }
    public long getMapEndLine() {
        return mapEndLine;
    }
    public void setMapEndLine(long mapEndLine) {
        this.mapEndLine = mapEndLine;
    }
    public long getTransNumber() {
        return transNumber;
    }
    public void setTransNumber(long transNumber) {
        this.transNumber = transNumber;
    }
    public String getHeader() {
        return header;
    }
    public void setHeader(String header) {
        this.header = header;
    }
    public String getTailer() {
        return tailer;
    }
    public void setTailer(String tailer) {
        this.tailer = tailer;
    }
    

    public double getFileSizeInKb(){
        return MathUtil.roundDouble((double)fileSize/1024, 2);
    }
    
    public double getFileSizeInMb(){
        return MathUtil.roundDouble((double)fileSize/1024/1024, 2);
    }
    
    public int getLineAndLindFeedWidth(){
        return lineWidth + lineFeedWidth;
    }
    
    @Override
    public String toString() {
        
        StringBuilder fileInfo = new StringBuilder();
        fileInfo.append("File: ").append(getFilePathAndName()).append("\n");
        fileInfo.append("Size: ").append(getFileSize()).append("B");
        fileInfo.append("(").append(getFileSizeInMb()).append("MB)\n");
        fileInfo.append("Line Number: ").append(getLineNumber()).append("\n");
        fileInfo.append("Trans Number: ").append(getTransNumber()).append("\n");
        fileInfo.append("Header: ").append(getHeader()).append("\n");
        fileInfo.append("Trailer: ").append(getTailer()).append("\n");
        return fileInfo.toString();
    }

    
    
    
}
