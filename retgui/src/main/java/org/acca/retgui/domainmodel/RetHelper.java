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
import org.acca.retgui.utils.StringUtils;
import org.acca.retgui.utils.TextFileLineReader;


public class RetHelper {


    private DishVersion dishVersion;
    private FileInfo fileInfo;
    @SuppressWarnings("unused")
    private MappedByteBuffer mappedBuffer;
    private long mapSize;
    @SuppressWarnings("unused")
    private byte[] retLineBytes;
    
    private TextFileLineReader reader;
    private String currentTrnn;
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    private List<String> currentTrans = new ArrayList();
    
    //读取交易时，需要判断当前行是否同一交易，需要读取下一行，先缓存下来。
    @SuppressWarnings("unused")
    private String cacheLine;

    /**
     * RetHelper.
     * 
     * @param retVersion RetVersion
     * @param fileUtil RetFileUtil
     * @throws DishFileException DishFileException
     */
    public RetHelper(DishVersion dishVersion, RetFileUtil fileUtil) throws DishFileException {
        this.dishVersion = dishVersion;
        this.fileInfo = fileUtil.getFileInfo();
        retLineBytes = new byte[fileInfo.getLineAndLindFeedWidth()];
        
        try {
            reader = new TextFileLineReader(new FileInputStream(new File(fileUtil.getFilePathAndName())));
        } catch (FileNotFoundException e) {
            throw new DishFileException(fileUtil.getFilePathAndName() + " File Read Error!");
        }

    }    

    /**
     * 读取下一交易的所有Records.若下一交易不存在，返回空.
     * 
     * @return List<String>
     */
    public RetTransaction fetchTransRecords() {

        String line = "";        
        while(line != null){
            
            try {
                line = reader.readALine(RetFileUtil.RET_FILE_LINE_WIDTH);
                
                //读取到最后一行，将最后一个交易返回
                if(line != null && line.startsWith("Z")){
                    if(currentTrans.size() > 0){
                        return new RetTransaction(/*beginLineNum,*/ currentTrans, dishVersion);
                    }
                }
                
                
                // 不是IT01，IT0Z
                if (!(line == null || line.startsWith("1") || line.startsWith("Z"))) { 
                    
                    String trnn = StringUtils.subString(line, 1, 7);
                    
                    if(StringUtils.isBlank(currentTrnn)){
                        currentTrnn = trnn;
                    }
                    
                    if(trnn.equals(currentTrnn)){
                        currentTrans.add(line);
                    }else{
                        
                        currentTrnn = trnn;
                        List<String> result = currentTrans;
                        currentTrans = new ArrayList();
                        currentTrans.add(line);
                        return new RetTransaction(/*beginLineNum, */result, dishVersion);
                        
                    }            
                    
                    
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
