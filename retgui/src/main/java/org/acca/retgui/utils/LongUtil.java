/*
 * Copyright (c) 2011-2036 International Air Transport Association corp.
 * All Rights Reserved.
 */
package org.acca.retgui.utils;

/**
 * LongUtil.
 * 
 * @version Seurat v1.0
 * @author Yu Tao, 2012-12-4
 */
public class LongUtil {
    
    private LongUtil(){
        
    }
    /**
     * getLong. If l is null, return 0L.
     * 
     * @param l Long
     * @return Long
     */
    public static Long getLong(Long l) {
        return l == null ? 0L : l;
    }
    
    /**
     * Parse String to Long. If error, return null.
     * @param str String.
     * @return Long
     */
    public static Long valueOf(String str){
        
        try{
            return Long.valueOf(str.trim());
        }catch(Exception ex){
            return null;
        }
    }
    
    
    
    /**
     * Parse String to Long. If error, return 0L.
     * @param str String.
     * @return Long
     */
    public static Long defaultValueOf(String str){
        
        try{
            return Long.valueOf(str.trim());
        }catch(Exception ex){
            return 0L;
        }
    }
}
