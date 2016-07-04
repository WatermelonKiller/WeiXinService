package com.hd.util;

import java.io.IOException;

import net.coobird.thumbnailator.Thumbnails;

public class ThumbnailsUtil {
	
	/**
	 * @author Jiyu Yang
	 * @time 2015-08-18 
	 * @param Strin url ��Ҫת����ͼƬ��URL
	 * @return  void
	 * ��������: ͨ������ͼƬ������FTP·��ʹ�ù����ཫ���ļ�������ת��������ͼ����
	 * 				  ͳһת����jpg��ʽ���洢�������Ŀ¼��
	 */
	public void thumbnailsUtil(String url) throws IOException{
		String outUrl = ""; //��������ͼ��·��;
		try {
			Thumbnails.of(url).scale(0.25f).outputFormat("jpg").toFile(outUrl);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
	}
}
