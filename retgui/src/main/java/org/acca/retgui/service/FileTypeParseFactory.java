package org.acca.retgui.service;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

import org.acca.retgui.domainmodel.FileType;

/**
 * FileTypeParser.
 * @author ZhangXue.
 * 
 */
public class FileTypeParseFactory {
	private String fileName;
	private String absolutePath;
	
	   public static final Map<FileType, String> PARSER_MAP = new HashMap();
	    public static final FileType[] FILE_TYPES = { /*FileType.TI, FileType.CIP, FileType.TSP,
	        FileType.NDD, FileType.WAD,  FileType.IBG, FileType.XRATE,*/FileType.RET, FileType.HOT,FileType.CSI};


	    static {
//	    	PARSER_MAP.put(FileType.TI, "org.acca.retgui.service.TiFileParser");
//	    	PARSER_MAP.put(FileType.CIP, "org.acca.retgui.service.CipFileParser");
//	    	PARSER_MAP.put(FileType.TSP, "org.acca.retgui.service.TspFileParser");
//	    	PARSER_MAP.put(FileType.NDD, "org.acca.retgui.service.NddFileParser");
//	    	PARSER_MAP.put(FileType.WAD, "org.acca.retgui.service.WadFileParser");
//	        PARSER_MAP.put(FileType.IBG, "org.acca.retgui.service.IbgFileParser");
//	        PARSER_MAP.put(FileType.XRATE, "org.acca.retgui.service.XrateFileParser");
	    	PARSER_MAP.put(FileType.RET, "org.acca.retgui.service.RetFileParser");
	    	PARSER_MAP.put(FileType.HOT, "org.acca.retgui.service.HotFileParser");
	    	PARSER_MAP.put(FileType.CSI, "org.acca.retgui.service.CsiFileParser");
	    }


	public FileTypeParseFactory(String absolutePath, String fileName) {
		this.fileName = fileName;
		this.absolutePath = absolutePath;
	}

	public BaseFileParser getFileParserInstance() {
		BaseFileParser  inputFileParser = null;
        for (FileType fileType : FILE_TYPES) {
            try {
                String parser = PARSER_MAP.get(fileType);
                Class<?> clz = Class.forName(parser);
                Class[] pType = new Class[]{String.class, String.class};
                Constructor ctor  =  clz.getConstructor(pType);
                Object[] obj = new Object[]{absolutePath, fileName};
                inputFileParser= (BaseFileParser) ctor.newInstance(obj);
                FileType type = inputFileParser.parseFileType();
                if (type != null) {
                    return inputFileParser;
                }
            } catch (Exception ex) {
            }
        }
		return inputFileParser;

	}
	
	
}
