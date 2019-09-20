package com.jt.controller;


import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.jt.service.FileService;
import com.jt.vo.EasyUIImage;

@Controller
public class FileController {
	
	@Autowired 
	private FileService fileService;
	
	/**
	 * springmvc负责流操作API
	 * 1.准备文件路径
	 * 2.准备文件的名称和后缀 abc.jpg
	 * 3.利用IO流进行写盘的操作
	 * @param fileImage
	 * @return
	 * @throws Exception 
	 * @throws IllegalStateException 
	 */
	@RequestMapping("/file")
	public String file(MultipartFile fileImage) throws Exception {
		File dirFile=new File("D:/Jt/jt-image");
		if(!dirFile.exists()) {
			//如果不存在则则新建目录
			//dirFile.mkdir();//只建一级目录
			dirFile.mkdirs();
		}
		//动态获取文件名称
		String fileName=fileImage.getOriginalFilename();
		File file=new File("D:/Jt/jt-image/"+fileName);
		//文件上传
		fileImage.transferTo(file);
		//转发重定向不经过视图解析器
		return "redirect:/file.jsp";
	}
	
	@RequestMapping("/pic/upload")
	@ResponseBody
	public EasyUIImage uploadFile(MultipartFile uploadFile) {
		return  fileService.uploadFile(uploadFile);
	}
	
	
}	
