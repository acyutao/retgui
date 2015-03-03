/*
 * Copyright (c) 2011-2036 International Air Transport Association corp.
 * All Rights Reserved.
 */
package org.acca.retgui.dish;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Dish Version.
 * 
 * @version Seurat v1.0
 * @author Yu Tao, 2012-7-5
 */
public abstract class DishVersion {

    private Map<String, List<RecordElement>> retLayout;

    /**
     * Dish Version.
     */
    public DishVersion() {
        retLayout = new HashMap<String, List<RecordElement>>();
    }

    public Map<String, List<RecordElement>> getRetLayout() {
        return retLayout;
    }

    /**
     * // RCID, Eg, 2-IT02, 5-IT05...
     * @param rcid String
     * @return List<RecordElement>
     * @throws NoSuchRcidException NoSuchRcidException
     */
    public List<RecordElement> getRecordLayoutByRcid(String rcid) throws NoSuchRcidException {
        // TODO lcg 如果
        try {
            List<RecordElement> elements = retLayout.get(getRecordIdentifier() + rcid);
            if (elements == null || elements.size() == 0) {
                throw new NoSuchRcidException(rcid);
            } else {
                return elements;
            }
        } catch (Exception e2) {
            throw new NoSuchRcidException(rcid);
        }
    }

    /**
     * RecordIdentifier.
     * @return String
     */
    protected abstract String getRecordIdentifier();

    /**
     * VersionNum.
     * @return String
     */
    public abstract String getVersionNum();
}
