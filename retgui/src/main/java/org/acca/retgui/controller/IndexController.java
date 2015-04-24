/**
 * 
 */
package org.acca.retgui.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.acca.retgui.config.ConfigHelper;
import org.acca.retgui.dish.DishConst;
import org.acca.retgui.domainmodel.FileInfo;
import org.acca.retgui.domainmodel.Record;
import org.acca.retgui.domainmodel.Transaction;
import org.acca.retgui.service.FileTypeParseFactory;
import org.apache.commons.configuration.XMLConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author yutao
 * 
 */
@Controller
public class IndexController {
	
	@Autowired
	private MongoTemplate mongoTemplate;

	@RequestMapping(value = "/index", method = { RequestMethod.GET })
	public String index() {
		return "index";
	}

	/**
	 * 上传文件api, file为默认名字.
	 * 
	 * @param response
	 * @param file
	 * @param localPath
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	@ResponseBody
	public String upload(HttpServletRequest request) throws Exception {

		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Map<String, List<MultipartFile>> fileMap = multipartRequest
				.getMultiFileMap();

		for (Entry<String, List<MultipartFile>> entry : fileMap.entrySet()) {
			List<MultipartFile> files = entry.getValue();
			for (MultipartFile file : files) {
				if (!file.isEmpty()) {
					try {
						String filePath = this.getFilePath();
						file.transferTo(new File(filePath
								+ file.getOriginalFilename()));
						
						// 解析文件并存储到mongodb
						List<Transaction> transactions = getTransaction(file.getOriginalFilename());
						for(Transaction t : transactions){
							mongoTemplate.insert(t, file.getOriginalFilename());
						}
						

					} catch (FileNotFoundException ex) {
						throw new RuntimeException("upload.failed.errorpath");
					}
				} else {
					throw new RuntimeException("upload.failed.emptyfile");
				}
			}
		}

		return "";
	}

	/**
	 * 得到配置的文件路径，如果路径不存在，则创建
	 * 
	 * @return
	 */
	private String getFilePath() {

		String filePath = ConfigHelper.getInstance().getString(
				ConfigHelper.UPLOAD_FILE_PATH);
		File file = new File(filePath);
		if (!file.exists()) {
			file.mkdirs();
		}
		return filePath;
	}

	/**
	 * 获得所有的文件信息.
	 * 
	 * @return
	 */
	@RequestMapping(value = "/alluploadfiles", method = RequestMethod.GET)
	@ResponseBody
	public List<FileInfo> getAllUploadfiles() {
		String filePath = this.getFilePath();
		File file = new File(filePath);
		List<FileInfo> list = new ArrayList();

		for (File f : file.listFiles()) {
			FileInfo info = new FileInfo();
			info.setFileName(f.getName());
			info.setFileSize(f.length());

			list.add(info);
		}

		return list;
	}

	@RequestMapping(value = "/detail/{fileName}/view", method = RequestMethod.GET)
	public ModelAndView viewFileDetail(@PathVariable String fileName)
			throws UnsupportedEncodingException {
		String name = java.net.URLDecoder.decode(fileName, "utf-8");
		ModelAndView mv= new ModelAndView();
		mv.setViewName( "retDetail");
		Record header=getFileHeader(fileName);
		mv.getModel().put("fileName", fileName);
		mv.getModel().put(DishConst.SPED.toLowerCase(), header.getElement(DishConst.SPED));
		mv.getModel().put(DishConst.RPSI.toLowerCase(), header.getElement(DishConst.RPSI));
		mv.getModel().put(DishConst.REVN.toLowerCase(), header.getElement(DishConst.REVN));
		mv.getModel().put(DishConst.TPST.toLowerCase(), header.getElement(DishConst.TPST));
		mv.getModel().put(DishConst.PRDA.toLowerCase(), header.getElement(DishConst.PRDA));
		mv.getModel().put(DishConst.TIME.toLowerCase(), header.getElement(DishConst.TIME));
		mv.getModel().put(DishConst.ISOC.toLowerCase(), header.getElement(DishConst.ISOC));
		mv.getModel().put(DishConst.FTYP.toLowerCase(), header.getElement(DishConst.FTYP));
		mv.getModel().put(DishConst.FTSN.toLowerCase(), header.getElement(DishConst.FTSN));
		
		return mv;
	}
    /**
     * @param fileName
     * @return
     * @throws UnsupportedEncodingException
     */
	@RequestMapping(value = "/detail/{fileName}/view", method = RequestMethod.POST,consumes = "application/json", produces = "application/json")
	@ResponseBody
	public String viewFileDetailContent(@PathVariable String fileName, @RequestBody String trnn)
			throws UnsupportedEncodingException {
		Query query = new Query(Criteria.where("sequentialRecords.elementMap.TRNN").is(trnn));
		List<Transaction> transactions = mongoTemplate.find(query, Transaction.class, fileName);
		StringBuilder res = new StringBuilder();
		res.append("{\"");
		res.append("content");
		res.append("\":[");
		// 循环显示表格中的每一行内容。
		for (Transaction u : transactions) {
			List<Record> records = u.getSequentialRecords();
			for (Record retRecord : records) {
				res.append("{\"");
				res.append("lineNum");
				res.append("\"");
				res.append(":\"");
				res.append(retRecord.getLineNum());
				res.append("\",");
				res.append("\"");
				res.append("record");
				res.append("\":[");
				Map<String, String>  maplists = retRecord.getElementMap();

				for (Map.Entry<String, String> mapEntry : maplists.entrySet()) {
					res.append("{");
					res.append("\"");
					res.append("id");
					res.append("\":\"");
					res.append(mapEntry.getKey());
					res.append("\",");
					res.append("\"");
					res.append("value");
					res.append("\":\"");
					res.append(mapEntry.getValue());
					res.append("\"");
					res.append("},");
				}
				res.delete(res.length() - 1, res.length());
                res.append("]");
                res.append("}");
                res.append(",");
			}
			res.delete(res.length() - 1, res.length());
              res.append(",");
		}
		res.delete(res.length() - 1, res.length());
		res.append("]");
		res.append("}");
		return res.toString();
	}

	private List<Transaction> getTransaction(String fileName) {
		XMLConfiguration configuration =ConfigHelper.getInstance();
		String path=configuration.getString("uploadFilePath");
		FileTypeParseFactory facotry = new FileTypeParseFactory(path, fileName);
		return facotry.getFileParserInstance().parseTransactions();
	}
	
	private Record getFileHeader(String fileName) {
		XMLConfiguration configuration =ConfigHelper.getInstance();
		String path=configuration.getString("uploadFilePath");
		FileTypeParseFactory facotry = new FileTypeParseFactory(path, fileName);
		return facotry.getFileParserInstance().parseFileHeader();
	}

	/**
	 * 删除文件.
	 * 
	 * @param fileName
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/fileName/{fileName}/remove", method = RequestMethod.DELETE)
	@ResponseBody
	public String deleteFile(@PathVariable String fileName)
			throws UnsupportedEncodingException {
		String name = java.net.URLDecoder.decode(fileName, "utf-8");

		String filePath = this.getFilePath();
		File file = new File(filePath + name);
		if (file.exists()) {
			file.delete();
		}

		return "";
	}
}
