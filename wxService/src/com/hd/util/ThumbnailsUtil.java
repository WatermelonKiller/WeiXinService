package com.hd.util;

import java.io.IOException;

import net.coobird.thumbnailator.Thumbnails;

public class ThumbnailsUtil {
	
	/**
	 * @author Jiyu Yang
	 * @time 2015-08-18 
	 * @param Strin url 需要转换的图片的URL
	 * @return  void
	 * 方法功能: 通过传递图片服务器FTP路径使用工具类将大文件按比例转换成缩略图，并
	 * 				  统一转换成jpg格式并存储到另外的目录；
	 */
	public void thumbnailsUtil(String url) throws IOException{
		String outUrl = ""; //保存缩略图的路径;
		try {
			Thumbnails.of(url).scale(0.25f).outputFormat("jpg").toFile(outUrl);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
	}
}
