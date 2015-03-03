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
import org.acca.retgui.domainmodel.FileInfo;
import org.acca.retgui.utils.DesEncrypt;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

/**
 * @author yutao
 * 
 */
@Controller
public class IndexController {

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
	public String viewFileDetail(@PathVariable String fileName) throws UnsupportedEncodingException{
		String name = java.net.URLDecoder.decode(
                fileName,
                "utf-8");
		System.out.println(name);
		return "retDetail";
	}
	
	/**
	 * 删除文件.
	 * @param fileName
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/fileName/{fileName}/remove", method = RequestMethod.DELETE)
	@ResponseBody
	public String deleteFile(@PathVariable String fileName) throws UnsupportedEncodingException{
		String name = java.net.URLDecoder.decode(
                fileName,
                "utf-8");
		
		String filePath = this.getFilePath();
		File file = new File(filePath + name);
		if(file.exists()){
			file.delete();
		}
		
		return "";
	}
}
