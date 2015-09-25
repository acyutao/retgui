/*
 * Copyright (c) 2011-2036 International Air Transport Association corp.
 * All Rights Reserved.
 */
package org.acca.retgui.utils;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;

/**
 * PublicUtils.
 *
 * @version Seurat v1.0
 * @author Ju Yuhuan, 2012-6-26
 */
public class PublicUtils {
   
    /**
     * PublicUtils.
     */
    private PublicUtils(){
    }
    /**
     * get extension name.
     * 
     * @param filePathAndName String
     * @return String
     */
    public static String getExtName(String filePathAndName) {
        String extName = "";
        if (StringUtils.isNotEmpty(filePathAndName)) {
            int index = filePathAndName.lastIndexOf(".");
            if (index > -1) {
                extName = filePathAndName.substring(index + 1);
            }
        }
        return extName;
    }

    /**
     * get file name .
     * 
     * @param filePathAndName String
     * @return String
     */
    public static String getFileName(String filePathAndName) {
        filePathAndName = getFileNameAndExtName(filePathAndName);
        String fileName = "";
        if (StringUtils.isNotEmpty(filePathAndName)) {
            int index = filePathAndName.lastIndexOf(".");
            if (index > -1) {
                fileName = filePathAndName.substring(0, index);
            }
        }
        return fileName;
    }

    /**
     * get file name and extension name.
     * 
     * @param filePathAndName String
     * @return String
     */
    public static String getFileNameAndExtName(String filePathAndName) {
        String fileNameAndExtName = "";
        if (StringUtils.isNotEmpty(filePathAndName)) {
            int index = filePathAndName.lastIndexOf("\\");
            int index1 = filePathAndName.lastIndexOf("/");
            if (index1 > index) {
                index = index1;
            }
            if (index > -1) {
                fileNameAndExtName = filePathAndName.substring(index + 1);
            }
        }
        return fileNameAndExtName;
    }

    /**
     * isFile.
     * @param filename String
     * @return boolean
     */
    public static boolean isFile(String filename) {
        File file = new File(filename);
        boolean ret = file.isFile();
        return ret;
    }

    /**
     * isDirectory.
     * @param filename String
     * @return boolean
     */
    public static boolean isDirectory(String filename) {
        File file = new File(filename);
        boolean ret = file.isDirectory();
        return ret;
    }

    /**
     * is slash end.
     * @param filename String
     * @return boolean
     */
    public static boolean isSlashEnd(String filename) {
        boolean ret = false;
        if (filename.endsWith("\\") || filename.endsWith("/")) {
            ret = true;
        }
        return ret;
    }

    /**
     * if file exists.
     * @param filename String
     * @return boolean
     */
    public static boolean exists(String filename) {
        File file = new File(filename);
        boolean ret = file.exists();
        return ret;
    }

    /**
     * get config by plugIn.
     * @param property String
     * @param key String
     * @return String
     */
    public static String getConfigByPlugIn(String property, String key) {
        Properties p = (Properties) buildPropFromFile(property);
        String value = "";
        if (p != null) {
            value = (String) p.get(key);
        }
        return value;
    }

    /**
     * build property from file.
     * @param property String
     * @return Properties
     */
    public static Properties buildPropFromFile(String property) {
        Properties pro = new Properties();
        try {
            FileInputStream f = new FileInputStream(property);
            pro.load(f);
            f.close();
            return pro;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * get path.
     * @return String
     */
    public static String getPath() {
        URL url = PublicUtils.class.getResource("/");
        return url.getPath();
    }
}
