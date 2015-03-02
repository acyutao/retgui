/*
 * Copyright (c) 2011-2036 International Air Transport Association corp.
 * All Rights Reserved.
 */
package org.acca.retgui.dish;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.util.ArrayList;
import java.util.List;

import org.acca.retgui.utils.StringUtils;
import org.acca.retgui.utils.TextFileLineReader;

/**
 * RetHelper.
 * 
 * @version Seurat v1.0
 * @author Yu Tao, 2012-11-6
 */
public class RetHelper {

    //@SuppressWarnings("unused")
    //private Log log = Logging.getLog(RetHelper.class);

    private RetVersion retVersion;
    private RetFileInfo fileInfo;
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
    public RetHelper(RetVersion retVersion, RetFileUtil fileUtil) throws DishFileException {
        this.retVersion = retVersion;
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
                        return new RetTransaction(currentTrans, retVersion);
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
                        return new RetTransaction(result, retVersion);
                        
                    }            
                    
                    
                }
                
                
            } catch (IOException e) {
                throw new RuntimeException("Read line error.");
            }
            
            
        }
        return null;

//        if (mappedBuffer == null) {
//            return null;
//        }
//
//        int lineIndex = 1;
//
//        // 交易码
//        String currentTransNo = "";
//        String recordLine;
//        while (mappedBuffer.position() < mappedBuffer.limit()) {
//
//            // 获得RET行字节数组
////          log.debug("mapBuffer.position(): "+mapBuffer.position());
////          log.debug("mapBuffer.limit(): "+mapBuffer.limit());
//            mappedBuffer.get(retLineBytes);
////          log.debug(retLineBytes[0]+" "+retLineBytes[1]);
//
//            try {
//
//                recordLine = new String(retLineBytes, 0, fileInfo.getLineWidth(), "ISO8859-1");
//
//                // 若为交易第一行，初始化transRecords
//                if (lineIndex == 1) {
//                    transRecords = new ArrayList<String>();
//                    currentTransNo = recordLine.substring(1, 7);
//                }
//
//                // 若同属一交易，添加交易记录到transRecords
//                if (currentTransNo.equals(recordLine.substring(1, 7))) {
//                    transRecords.add(recordLine);
//                } else {
//                    // 若进入下一交易
//                    // 将记录指针指到下一交易起始位置，以便于下次读取下一个交易
//                    mappedBuffer.position(mappedBuffer.position()
//                            - fileInfo.getLineAndLindFeedWidth());
//                    break;
//                }
//
//                lineIndex++;
//
//            } catch (UnsupportedEncodingException e) {
//                log.error("Encoding Error When Fetch Transanctions!", e);
//            }
//        }
//        if (transRecords == null) {
//            return null;
//        }

    }

    /**
     * 从内存中读文件头，以recordLine存储.
     * 
     * @return String
     */
    public String fetchHeadRecords() {
        return fileInfo.getHeader();
    }

    /**
     * 从内存中读文件尾，以recordLine存储.
     * 
     * @return String
     */
    public String fetchEndRecords() {
        return fileInfo.getTailer();

    }

    public RetFileInfo getFileInfo() {
        return this.fileInfo;
    }

    public long getMapSize() {
        return mapSize;
    }

    public void setMapSize(long mapSize) {
        this.mapSize = mapSize;
    }

//
//  /**
//   * @param totalTrans the totalTrans to set
//   */
//  public void setTotalTrans(long totalTrans) {
//      this.totalTrans = totalTrans;
//  }

    /**
     * getTotalTrans.
     * 
     * @return long totalTrans
     */
    public long getTotalTrans() {
        return fileInfo.getTransNumber();
    }

//    // 读取完文件后, 或终止Ret文件导入时要调用
//    public void close() throws DishFileException {
//        if (rf == null)
//            return;
//
//        try {
//            rf.close();
//        } catch (IOException e) {
//            throw new DishFileException(fileInfo.getFilePathAndName() + " File Not Open!");
//        }
//    }
}
