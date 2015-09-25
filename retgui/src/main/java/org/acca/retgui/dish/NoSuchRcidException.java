/*
 * Copyright (c) 2011-2036 International Air Transport Association corp.
 * All Rights Reserved.
 */
package org.acca.retgui.dish;

/**
 * NoSuchRcidException.
 * 
 * @version Seurat v1.0
 * @author Yu Tao, 2012-7-5
 */
public class NoSuchRcidException extends RuntimeException {

    private static final long serialVersionUID = -8676301434722597062L;

    /**
     * 未找到的rcid.
     */
    private String rcid;

    /**
     * 出错的记录.
     */
    private String recordLine;

    /**
     * Construct.
     * @param rcid String
     */
    public NoSuchRcidException(String rcid) {
        this.rcid = rcid;
    }

    /**
     * Construct.
     * @param rcid String
     * @param recordLine String
     */
    public NoSuchRcidException(String rcid, String recordLine) {
        this.rcid = rcid;
        this.recordLine = recordLine;
    }

    public String getRcid() {
        return rcid;
    }

    public String getRecordLine() {
        return recordLine;
    }
}
