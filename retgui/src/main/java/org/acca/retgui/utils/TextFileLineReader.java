/*
 * Copyright (c) 2011-2036 International Air Transport Association corp.
 * All Rights Reserved.
 */
package org.acca.retgui.utils;

import java.io.BufferedInputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.lang.StringUtils;

/**
 * 
 * Read text file by line, and reports the current offset.
 * 
 * @version Seurat v1.0
 * @author Li Liangjie, 2013-12-24
 */
public class TextFileLineReader implements Closeable {

    private static final int DEFAULT_BUF_SIZE = 16 * 1024;
    private final InputStream inputStream;

    // the offset of the end of the current line.
    private long offsetInFile;

    // current position, has already returned the previous contents
    private int offsetInBuffer;

    // max index in buffer
    private int limit;

    // buffer to hold the block of file content
    private byte[] buf;
    private StringBuilder stringBuilder = new StringBuilder();

    /**
     * TextFileLineReader.
     * 
     * @param inputStream inputStream
     */
    public TextFileLineReader(InputStream inputStream) {
        this(inputStream, DEFAULT_BUF_SIZE);
    }

    /**
     * 
     * TextFileLineReader.
     * 
     * @param inputStream inputStream
     * @param maxLineLen maxLineLen
     */
    public TextFileLineReader(InputStream inputStream, int maxLineLen) {
        this.inputStream = new BufferedInputStream(inputStream);
        buf = new byte[maxLineLen];
        offsetInFile = 0;
        offsetInBuffer = 0;
        limit = 0;
    }

    @Override
    public void close() throws IOException {
        inputStream.close();
    }

    /**
     * readALine.
     * 
     * @return String
     * @throws IOException IOException
     */
    public String readALine() throws IOException {
        return readALine(false);
    }
    
    /**
     * 读取一行，按照长度要求使用空格补齐String.
     * @param lineSize
     * @return
     * @throws IOException
     */
    public String readALine(int length) throws IOException {
        String str = readALine();
        if(str == null){
            return str;
        }else{
            return StringUtils.rightPad(str, length);
        }
    }

    /**
     * readALine.
     * 
     * @param includeEndOfLine includeEndOfLine
     * @return next line which include the \r, \r\n or \n.
     * @throws IOException IOException
     */
    public String readALine(boolean includeEndOfLine) throws IOException {
        stringBuilder.setLength(0);
        readDataIfNeeded();

        if (limit == -1) {
            return null;
        }

        int endOfLineLength;
        int maxLineSize = buf.length;
        int lineLen = 0;

        while (offsetInBuffer < limit) {
            byte b = buf[offsetInBuffer];
            offsetInBuffer++;

            switch (b) {
                case '\r':
                    endOfLineLength = appendCarriageReturn(includeEndOfLine, (char) b);
                    increaseOffset(includeEndOfLine, endOfLineLength);
                    return stringBuilder.toString();

                case '\n':
                    endOfLineLength = appendLineFeed(includeEndOfLine, (char) b);
                    increaseOffset(includeEndOfLine, endOfLineLength);
                    return stringBuilder.toString();

                default:
                    stringBuilder.append((char) b);
                    break;
            }

            // if the line len is bigger than buf size
            lineLen++;
            if (lineLen == maxLineSize) {
                throw new IOException("this line is too long!");
            }

            readDataIfNeeded();
        }

        offsetInFile += stringBuilder.length();
        return stringBuilder.toString();
    }

    private void increaseOffset(boolean includeEndOfLine, int lengthOfEOL) {
        offsetInFile += stringBuilder.length() + (includeEndOfLine ? 0 : lengthOfEOL);
    }

    /**
     * appendCarriageReturn.
     * 
     * @param includeEndOfLine includeEndOfLine
     * @param b b
     * @return the length of new line, which could be 1 (\r or \n) or 2 (\r\n)
     * @throws IOException IOException
     */
    private int appendCarriageReturn(boolean includeEndOfLine, char b) throws IOException {
        int endOfLineLength = 1;

        if (includeEndOfLine) {
            stringBuilder.append(b);
        }

        readDataIfNeeded();
        if (buf[offsetInBuffer] == '\n') {
            endOfLineLength += appendLineFeed(includeEndOfLine, (char) buf[offsetInBuffer]);
            offsetInBuffer++;
        }

        return endOfLineLength;
    }

    /**
     * appendLineFeed.
     * 
     * @param includeEndOfLine includeEndOfLine
     * @param b b
     * @return length of the \n which always 1
     * @throws IOException IOException
     */
    private int appendLineFeed(boolean includeEndOfLine, char b) throws IOException {
        if (includeEndOfLine) {
            stringBuilder.append(b);
        }

        return 1;
    }

    private void readDataIfNeeded() throws IOException {
        if (offsetInBuffer == limit) {
            int readCount = inputStream.read(buf);
            offsetInBuffer = 0;
            limit = readCount;
        }
    }

    public long getOffset() {
        return offsetInFile;
    }
}
