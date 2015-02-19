/**
 * 
 */
package org.acca.retgui.controller;

import java.io.File;
import java.io.FileNotFoundException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author yutao
 *
 */
@Controller
public class IndexController {

	@RequestMapping(value = "/index", method = {RequestMethod.GET})
	public String index(){
		return "index";
	}
	
	/**
     * 上传文件api
     * @param response
     * @param file
     * @param localPath
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public String upload(HttpServletResponse response, 
                                 @RequestParam("file") MultipartFile file) throws Exception {
        if (!file.isEmpty()) {
            try {
                file.transferTo(new File(file.getOriginalFilename()));
                return "";
            } catch (FileNotFoundException ex) {
                throw new RuntimeException("upload.failed.errorpath");
            }
        } else {
            throw new RuntimeException("upload.failed.emptyfile");
        }
    }
}
