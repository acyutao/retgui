/*
 * Copyright (c) 2011-2036 International Air Transport Association corp.
 * All Rights Reserved.
 */
package org.acca.retgui.dish;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * RET DISH Description.
 * 
 * @version Seurat v1.0
 * @author Yu Tao, 2012-11-6
 */
public abstract class RetVersion {
    
    public static final String DISH_203 = "203";
    public static final String DISH_220 = "220";   
     

    protected Map<String, RecordIdentifier> identifierLayout;

    /**
     * RetVersion.
     */
    public RetVersion() {

        identifierLayout = new ConcurrentHashMap();

        DishRet ret = DishRet.getDishRet(this.getVersion());

        for (RecordIdentifier it : ret.getRecordIdentifiers()) {
            identifierLayout.put(it.getName(), it);
        }

    }

    /**
     * 增加静态方法.
     * @param retVersion String
     * @return RetVersion
     */
    public static RetVersion getInstance(String retVersion) {
        try {
            Class<?> clz = Class.forName("org.iata.seurat.app.ticket.dish.RetVersion" + retVersion);
            return (RetVersion) clz.newInstance();
        } catch (Exception e) {
            return new RetVersion203();
        }
    }

    /**
     * Get Version.
     * 
     * @return String
     */
    protected abstract String getVersion();

    /**
     * RecordIdentifier.
     * 
     * @param identifier String IT01,IT02...
     * @return RecordIdentifier
     */
    public RecordIdentifier getRecordIdentifier(String identifier) {
        return identifierLayout.get(identifier);
    }
    
    
    
    /**
     * 判断是否是203版本的ret. 
     * TRUE：is 203； FALSE：not 203.
     * @param revn String
     * @return boolean
     */
    public static boolean isDish203(String revn){
        
        if(DISH_203.equals(revn)){
            return true;
        }else{
            return false;
        }
    }
    
    /**
     * 判断是否是220版本的ret. 
     * TRUE：is 220； FALSE：not 220.
     * @param revn String
     * @return boolean
     */
    public static boolean isDish220(String revn){
        
        if(DISH_220.equals(revn)){
            return true;
        }else{
            return false;
        }
    } 

}
