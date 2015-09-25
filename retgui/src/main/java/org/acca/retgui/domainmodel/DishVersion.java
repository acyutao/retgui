/*
 * Copyright (c) 2011-2036 International Air Transport Association corp.
 * All Rights Reserved.
 */
package org.acca.retgui.domainmodel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.acca.retgui.dish.Dish;
import org.acca.retgui.dish.RecordIdentifier;
import org.acca.retgui.dish.RetVersion203;

/**
 * RET DISH Description.
 * 
 * @version Seurat v1.0
 * @author Yu Tao, 2012-11-6
 */
public abstract class DishVersion {
    
    public static final String RET_DISH_203 = "Ret203";
    public static final String RET_DISH_220 = "Ret220";
    public static final String HOT_DISH_203 = "Hot203";
    public static final String HOT_DISH_220 = "Hot220";
    public static final String CSI_DISH_203 = "Csi203";
    public static final String CSI_DISH_220 = "Csi220";
//    public static final String TI_DISH_203 = "Ti203";
//    public static final String TI_DISH_220 = "Ti220";   
//    public static final String CIP_DISH_203 = "Cip203";
//    public static final String CIP_DISH_220 = "Cip220";   
//    public static final String TSP_DISH_203 = "Tsp203";
//    public static final String TSP_DISH_220 = "Tsp220";   
//    public static final String NDD_DISH_203 = "Ndd203";
//    public static final String NDD_DISH_220 = "Ndd220";   
//    public static final String WAD_DISH_203 = "Wad203";
//    public static final String WAD_DISH_220 = "Wad220";   
//    public static final String IBG_DISH_203 = "Ibg203";
//    public static final String IBG_DISH_220 = "Ibg220";   
//    public static final String XRATE_DISH_203 = "Xrate203";
//    public static final String XRATE_DISH_220 = "Xrate220";   
     

    protected Map<String, RecordIdentifier> identifierLayout;

    /**
     * RetVersion.
     */
    public DishVersion() {

        identifierLayout = new ConcurrentHashMap();

        Dish ret = Dish.getDish(this.getVersion());

        for (RecordIdentifier it : ret.getRecordIdentifiers()) {
            identifierLayout.put(it.getName(), it);
        }

    }

    /**
     * 增加静态方法.
     * @param version String
     * @return DishVersion
     */
    public static DishVersion getInstance(String version) {
        try {
            Class<?> clz = Class.forName("org.acca.retgui.dish." + version);
            return (DishVersion) clz.newInstance();
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
    
    


}
