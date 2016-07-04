package com.core.util;

import java.io.File;

import net.sf.json.JSONObject;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.WritableResource;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.core.thread.TokenThread;

//�����ϴ�������
public class AttachmentUploadUtil {

	/**
	 * @author Yang Jiyu
	 * @date 2015-11-01
	 * @param null
	 * @return ����ͼƬ�ϴ���url ͼƬ�ϴ�
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static String imageUpload(String filePath) {
		String access_token = TokenThread.accessToken.getToken();
		String url = "https://api.weixin.qq.com/cgi-bin/media/uploadimg?access_token=ACCESS_TOKEN";
		url = url.replace("ACCESS_TOKEN", access_token);
		WritableResource resource = new FileSystemResource(new File(filePath));
		MultiValueMap data = new LinkedMultiValueMap();
		data.add("buffer", resource);
		RestTemplate restTemplate = new RestTemplate();
		String result = restTemplate.postForObject(url, data, String.class);
		JSONObject resultJson = JSONObject.fromObject(result);
		if (resultJson.containsKey("url")) {
			return resultJson.getString("url").toString();
		} else {
			System.out.println(resultJson.getString("errmsg"));
			return null;
		}
	}

	public static String imageUpload(File file) {
		String access_token = TokenThread.accessToken.getToken();
		String url = "https://api.weixin.qq.com/cgi-bin/media/uploadimg?access_token=ACCESS_TOKEN";
		url = url.replace("ACCESS_TOKEN", access_token);
		WritableResource resource = new FileSystemResource(file);
		MultiValueMap data = new LinkedMultiValueMap();
		data.add("buffer", resource);
		RestTemplate restTemplate = new RestTemplate();
		String result = restTemplate.postForObject(url, data, String.class);
		JSONObject resultJson = JSONObject.fromObject(result);
		if (resultJson.containsKey("url")) {
			return resultJson.getString("url").toString();
		} else {
			System.out.println(resultJson.getString("errmsg"));
			return null;
		}
	}
}
