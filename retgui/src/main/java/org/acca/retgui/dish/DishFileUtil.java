/*
 * Copyright (c) 2011-2036 International Air Transport Association corp.
 * All Rights Reserved.
 */
package org.acca.retgui.dish;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.acca.retgui.utils.TextFileLineReader;
import org.apache.log4j.Logger;

/**
 * Dish File Util.
 * 
 * @version Seurat v1.0
 * @author Yu Tao, 2012-11-6
 */
public abstract class DishFileUtil {
    
    protected String fileHeaderLine;
    
    protected String fileTailerLine;
    
    // 倒数第二行
    protected String secondLastLine;
    
    protected long fileTotalNumber;

    private String filePathAndName;

    // 文件行字符长度
    private int lineWidth;

    // 文件换行符长度
    private int lineFeedWidth;

    // 文件大小，以字节为单位
    private long fileSize;

    // DISH文件编码集
    private String encoding = "ISO8859-1";


    /**
     * DishFileUtil.
     * 
     * @param filePathAndName String
     * @throws DishFileException DishFileException
     */
    protected DishFileUtil(String filePathAndName) throws DishFileException {

        File file = new File(filePathAndName);
        if (file == null) {
            throw new DishFileException(filePathAndName + " File Not Found");
        }

        this.filePathAndName = filePathAndName;
        fileSize = file.length();
        lineWidth = getLineWidth();

        // 设算文件换行符长度
        calcLineFeedWidth(filePathAndName);
        
        //保存2行数据
        List<String> cache = new ArrayList(2);
        
        TextFileLineReader rf = null;
        try {
            rf = new TextFileLineReader(new FileInputStream(new File(filePathAndName)));

            long currentLine = 0;
            String cursor = "";
            // 按行顺序读取文件
            while(true){
                String line = rf.readALine();
                
                if(line == null){
                    break;
                }
                
                cursor = line;
                
                currentLine++;
                
                if( currentLine == 1){
                    this.fileHeaderLine = line;
                }
                
                // 新加的数据在数组头
                if(cache.size() == 2){
                    cache.remove(0);
                }
                cache.add(line);
                
            }
            
            this.fileTotalNumber = currentLine;
            
            
            
            if(this.fileTotalNumber > 0){
                this.fileTailerLine = cursor;
                
                if(fileTotalNumber == 1){
                    this.secondLastLine = this.fileTailerLine;
                }else{
                    this.secondLastLine = cache.get(0);
                }
            } 
        } catch (FileNotFoundException e) {
            throw new DishFileException("File Read Error!", e);
        } catch (IOException e) {
            throw new DishFileException("File Read Error!", e);
        }finally{
            try {
                rf.close();
            } catch (IOException e) {
                throw new DishFileException("File Read Error!", e);
            }
        }
    }

    /**
     * 获得文件总行数.文件最后一行要有换行，否则报不符合DISH文件格式.
     * 
     * @return long
     * @throws DishFileException DishFileException
     */
    protected long getTotalLineNumber() throws DishFileException {

//        double totalLine = fileSize * 1.0d / (lineWidth + lineFeedWidth);
//        long totalLines = Math.round(totalLine);
//        long calcFileSize = totalLines * (lineWidth + lineFeedWidth);
//
//        return Math.round(totalLine);
        return this.fileTotalNumber;

//      if(fileSize == calcFileSize) 
//          return fileSize/(lineWidth+lineFeedWidth);
//      else
//          throw new DishFileException(filePathAndName + " File Is Not DISH Format!" + 
//                  " Reason: There are lines which are less than "+getLineWidth()+" character.");
    }

    // 获得文件总字节数
    protected long getFileSize() {
        return fileSize;
    }

    protected int getLineFeedWidth() {
        return lineFeedWidth;
    }

    /**
     * 获得文件首行字符串.
     * 
     * @return String
     * @throws DishFileException DishFileException
     */
    protected String getHeader() throws DishFileException {

        return this.fileHeaderLine;
    }

    /**
     * 获得文件尾行字符串.
     * 
     * @return String
     * @throws DishFileException DishFileException
     */
    protected String getTailer() throws DishFileException {
        return this.fileTailerLine;
    }

    /**
     * 获得文件指定行字符串, 不存在返回空字符串.
     * 
     * @param lineNumber long
     * @return String
     * @throws DishFileException DishFileException
     */
    protected String getSpecificLine(long lineNumber) throws DishFileException {

        if (lineNumber <= 0 || lineNumber > getTotalLineNumber()) {
            return "";
        }

        TextFileLineReader rf = null;
        try {
            rf = new TextFileLineReader(new FileInputStream(new File(filePathAndName)));

            long currentLine = 1;
            // 按行顺序读取文件
            while(true){
                String line = rf.readALine();
                
                if(line == null){
                    break;
                }
                
                if( currentLine == lineNumber){
                    return line;
                }
                currentLine++;
            }
            return null;
        } catch (FileNotFoundException ex) {
            throw new DishFileException("File Not Found!", ex);
        } catch (IOException ex) {
            throw new DishFileException("File Read Error!", ex);
        } finally{
            try {
                rf.close();
            } catch (IOException ex) {
                throw new DishFileException("File Read Error!", ex);
            }
        }
    }

    // 计算文件换行符长度
    private void calcLineFeedWidth(String filePathAndName) throws DishFileException {

        // 换行符
        char lineFeed = '\n';

        // 回车符
        char carriageReturn = '\r';

        // 读出包括'\n'或'\r'，'\r'+'\n'两个字符在内的一行字符的字节数组
        byte[] bytes = new byte[lineWidth + 2];
        try {
            FileInputStream fis = new FileInputStream(filePathAndName);
            fis.read(bytes);
            fis.close();
        } catch (FileNotFoundException ex) {
            throw new DishFileException("File Not Found!", ex);
        } catch (IOException ex) {
            throw new DishFileException("File Read Error!", ex);
        }

        // 判断最后一个字节,若是'\r'或'\n', 则表示文件换行符长度为2
        if (carriageReturn == bytes[bytes.length - 1] || lineFeed == bytes[bytes.length - 1]) {
            lineFeedWidth = 2;
        } else if (carriageReturn == bytes[bytes.length - 2] || lineFeed == bytes[bytes.length - 2]) {
            // 判断倒数第二个字节,若是'\r'或'\n', 则表示文件换行符长度为1
            lineFeedWidth = 1;
        } else {
            // 否则,表示文件格式错误
            // throw new DishFileException(getFileName(filePathAndName) +
            // " File Is Not DISH Format!");
        }

        System.out.println("Line Width: " + lineWidth);
        System.out.println("Line Feed Width: " + lineFeedWidth);
        try {
            String header = new String(bytes, 0, lineWidth + lineFeedWidth, encoding);
            System.out.println("Header: " + header);
        } catch (UnsupportedEncodingException e) {
        	 System.out.println("calcLineFeedWidth errors");
        }
    }

    private String getFileName(String filePathAndName) {

        int position = filePathAndName.lastIndexOf('/');
        if (position == -1) {
            position = filePathAndName.lastIndexOf('\\');
        }
        return filePathAndName.substring(position + 1);
    }

    /**
     * getLineWidth.
     * 
     * @return int
     */
    protected abstract int getLineWidth();
}
