/*
 * Copyright (c) 2011-2036 International Air Transport Association corp.
 * All Rights Reserved.
 */
package org.acca.retgui.dish;
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

//    @SuppressWarnings("unused")
//    private Log log = Logging.getLog(RetHelper.class);

    /**
     * RetFileUtil.
     * 
     * @param filePathAndName String
     * @throws DishFileException DishFileException
     */
    public RetFileUtil(String filePathAndName) throws DishFileException {
        super(filePathAndName);
        this.filePathAndName = filePathAndName;
    }

    /**
     * getLineNumber.
     * 
     * @return long
     * @throws DishFileException DishFileException
     */
    public long getLineNumber() throws DishFileException {
        return super.getTotalLineNumber();
    }

    /**
     * getFileInfo.
     * 
     * @return getLineNumber
     * @throws DishFileException DishFileException
     */
    public RetFileInfo getFileInfo() throws DishFileException {

        RetFileInfo fileInfo = new RetFileInfo();

        fileInfo.setFilePathAndName(filePathAndName);
        fileInfo.setFileSize(getFileSize());
        fileInfo.setLineWidth(RetFileUtil.RET_FILE_LINE_WIDTH);
        fileInfo.setLineFeedWidth(getLineFeedWidth());
        fileInfo.setHeader(getHeader());
        fileInfo.setTailer(getTailer());
        fileInfo.setLineNumber(getTotalLineNumber());

        fileInfo.setTransNumber(getTotalTrans());

        return fileInfo;
    }

    /**
     * Total Trans.
     * 
     * @return Long
     * @throws DishFileException DishFileException
     */
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
    

//    /**
//     * MappedByteBuffer.
//     * 
//     * @param beginLine long
//     * @param endLine long
//     * @return MappedByteBuffer
//     * @throws DishFileException DishFileException
//     */
//    public MappedByteBuffer mapFileToByteBuffer(long beginLine, long endLine)
//        throws DishFileException {
//
//        if (beginLine < 1 || endLine > getTotalLineNumber()) {
//            throw new DishFileException("Can Not Read File " + filePathAndName + " From Line "
//                    + beginLine + " To " + endLine);
//        }
//
//        if (endLine < 0) {
//            return null;
//        }
//
//        beginLine = (beginLine == 1) ? 2 : beginLine;
//        endLine = (endLine == getTotalLineNumber()) ? endLine - 1 : endLine;
//
//        // 计算将文件映射到字节数组的起始行
//        beginLine = calcBufferTopLine(beginLine);
//        endLine = calcBufferBottomLine(endLine);
//
//        log.debug("Map File Top Line: " + beginLine);
//        log.debug("Map File Bottom Line: " + endLine);
//
//        try {
//
//            RandomAccessFile rf = new RandomAccessFile(filePathAndName, "r");
//
//            // 获得
//            // 映射RET文件到虚拟内存
//            // RET文件头和尾除外
//            MappedByteBuffer mapBuffer = rf.getChannel().map(
//                    FileChannel.MapMode.READ_ONLY,
//                    (beginLine - 1) * (RetFileUtil.RET_FILE_LINE_WIDTH + getLineFeedWidth()),
//                    (endLine - beginLine + 1)
//                            * (RetFileUtil.RET_FILE_LINE_WIDTH + getLineFeedWidth()));
//            rf.close();
//            return mapBuffer;
//
//        } catch (FileNotFoundException e) {
//            throw new DishFileException(filePathAndName + " File Not Found!");
//        } catch (IOException e) {
//            LogCategory.BUSINESS.error("has errors", e);
//            throw new DishFileException(filePathAndName + " File Read Error!");
//        }
//    }
//
//    // 计算将文件映射到字节数组的起始行
//    // 1. 起始行必须为须一个交易的首行，如果起始行的位置在交易的中间，
//    // 则调整起始行到下一个交易的首行
//    private long calcBufferTopLine(long beginLine) throws DishFileException {
//
//        long topLine;
//
//        // 获得当前行和前一行的交易号
//        String currentTrnn = StringUtils.subString(getSpecificLine(beginLine), 1, 7);
//        String previousTrnn = StringUtils.subString(getSpecificLine(beginLine - 1), 1, 7);
//
//        if (StringUtils.isBlank(currentTrnn)) {
//            return beginLine;
//        }
//
//        // 当前行和前一行的交易号不同时，说明当前行为一个交易的第一行，
//        // 直接返回当前行作为映射文件的起始行
//        if (!currentTrnn.equals(previousTrnn)) {
//            topLine = beginLine;
//        } else {
//            topLine = beginLine + 1;
//            String nextTrnn = StringUtils.subString(getSpecificLine(topLine), 1, 7);
//            // 当前行依次与下一行的交易号比较，当不同时，作为新的一个交易的第一行
//            while (currentTrnn.equals(nextTrnn)) {
//                topLine++;
//                nextTrnn = StringUtils.subString(getSpecificLine(topLine), 1, 7);
//
//                // Next Line is Empty.
//                if (StringUtils.isBlank(nextTrnn)) {
//                    break;
//                }
//            }
//        }
//
//        return topLine;
//    }
//
//    // 计算将文件映射到字节数组的结束行
//    // 2. 结束行必须为须一个交易的尾行，如果结束行的位置在交易的中间，
//    // 则调整结束行到当前交易的尾行
//    private long calcBufferBottomLine(long endLine) throws DishFileException {
//
//        long bottomLine;
//
//        // 获得当前行和后一行的交易号
//        String currentTrnn = StringUtils.subString(getSpecificLine(endLine), 1, 7);
//        String nextTrnn = StringUtils.subString(getSpecificLine(endLine + 1), 1, 7);
//
//        if (StringUtils.isBlank(currentTrnn)) {
//            return endLine;
//        }
//
//        // 当前行和后一行的交易号不同时，说明当前行为一个交易的最后一行，
//        // 直接返回当前行作为映射文件的结束行
//        if (!currentTrnn.equals(nextTrnn)) {
//            bottomLine = endLine;
//        } else {
//            bottomLine = endLine + 1;
//            nextTrnn = StringUtils.subString(getSpecificLine(bottomLine), 1, 7);
//            // 当前行依次与下一行的交易号比较，当不同时，将bottomLine所在行移动到上一行
//            // 作为当前交易的最后一行
//            while (currentTrnn.equals(nextTrnn)) {
//                bottomLine++;
//                nextTrnn = StringUtils.subString(getSpecificLine(bottomLine), 1, 7);
//
//                // Next Line is Empty.
//                if (StringUtils.isBlank(nextTrnn)) {
//                    break;
//                }
//            }
//            bottomLine--;
//        }
//
//        return bottomLine;
//    }

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
     * @throws DishFileException DishFileException
     */
    protected String getHeader() throws DishFileException {

        return org.apache.commons.lang.StringUtils.rightPad(this.fileHeaderLine, RET_FILE_LINE_WIDTH);
    }

    /**
     * 获得文件尾行字符串.
     * 
     * @return String
     * @throws DishFileException DishFileException
     */
    protected String getTailer() throws DishFileException {
        return org.apache.commons.lang.StringUtils.rightPad(this.fileTailerLine, RET_FILE_LINE_WIDTH);
    
    }
}
