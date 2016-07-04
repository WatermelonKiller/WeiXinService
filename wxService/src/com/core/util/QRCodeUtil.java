package com.core.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.core.thread.TokenThread;
import com.hd.util.InputStreamUtil;

public class QRCodeUtil {
	// 获取永久二维码ticket
	public static final String TicketUrl = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=TOKEN";
	// 获取二维码url
	public static final String getQRCodeUrl = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=TICKET";

	/**
	 * 
	 * @Description:获取临时带参二维码图片输出流
	 * @Author:Jc-Li
	 * @param: id 传递数字参数
	 * @Date:2015-10-27
	 */
	public static InputStream getTempQRCode(int scene_id) {
		String ticketUrl = TicketUrl.replace("TOKEN",
				TokenThread.accessToken.getToken());
		// 组装post所需json字符串
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> action_info = new HashMap<String, Object>();
		Map<String, Object> scene = new HashMap<String, Object>();
		scene.put("scene_id", scene_id);
		action_info.put("scene", scene);
		map.put("expire_seconds", 604800);
		map.put("action_name", "QR_SCENE");
		map.put("action_info", action_info);
		String postJson = JSONObject.fromObject(map).toString();
		// 发起请求返回结果
		String ticketInfo = WeiXinUtil.httpRequest(ticketUrl, "POST", postJson);
		// 输出结果
		System.out.println(ticketInfo);
		JSONObject ticketJson = JSONObject.fromObject(ticketInfo);
		String ticket = "";
		// 判断是否成功
		if (ticketJson.containsKey("ticket")) {
			ticket = ticketJson.getString("ticket");
		} else {
			System.out.println("获取ticket异常" + ticketJson.toString());
			return null;
		}
		// 成功则获取二维码图片流
		String QRCodeUrl = getQRCodeUrl.replace("TICKET", ticket);
		return InputStreamUtil.getInputStream(QRCodeUrl);
	}

	/**
	 * 
	 * @Description:获取永久二维码图片流
	 * @Author:Jc-Li
	 * @param:scene_str传递字符串参数
	 * @Date:2015-10-27
	 */
	public static InputStream getQRCode(int scene_id) {
		String ticketUrl = TicketUrl.replace("TOKEN",
				TokenThread.accessToken.getToken());
		// 组装post所需json字符串
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> action_info = new HashMap<String, Object>();
		Map<String, Object> scene = new HashMap<String, Object>();
		scene.put("scene_id", scene_id);
		action_info.put("scene", scene);
		map.put("action_name", "QR_LIMIT_SCENE");
		map.put("action_info", action_info);
		String postJson = JSONObject.fromObject(map).toString();
		// 发起请求返回结果
		String ticketInfo = WeiXinUtil.httpRequest(ticketUrl, "POST", postJson);
		// 输出结果
		System.out.println(ticketInfo);
		JSONObject ticketJson = JSONObject.fromObject(ticketInfo);
		String ticket = "";
		// 判断是否成功
		if (ticketJson.containsKey("ticket")) {
			ticket = ticketJson.getString("ticket");
		} else {
			System.out.println("获取ticket异常" + ticketJson.toString());
			return null;
		}
		// 成功则获取二维码图片流
		String QRCodeUrl = getQRCodeUrl.replace("TICKET", ticket);
		return InputStreamUtil.getInputStream(QRCodeUrl);
	}

	/**
	 * 
	 * @Description:获取永久二维码图片流
	 * @Author:Jc-Li
	 * @param:scene_str传递字符串参数
	 * @Date:2015-10-27
	 */
	public static InputStream getQRCode(String scene_str) {
		String ticketUrl = TicketUrl.replace("TOKEN",
				TokenThread.accessToken.getToken());
		// 组装post所需json字符串
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> action_info = new HashMap<String, Object>();
		Map<String, Object> scene = new HashMap<String, Object>();
		scene.put("scene_str", scene_str);
		action_info.put("scene", scene);
		map.put("action_name", "QR_LIMIT_STR_SCENE");
		map.put("action_info", action_info);
		String postJson = JSONObject.fromObject(map).toString();
		// 发起请求返回结果
		String ticketInfo = WeiXinUtil.httpRequest(ticketUrl, "POST", postJson);
		// 输出结果
		System.out.println(ticketInfo);
		JSONObject ticketJson = JSONObject.fromObject(ticketInfo);
		String ticket = "";
		// 判断是否成功
		if (ticketJson.containsKey("ticket")) {
			ticket = ticketJson.getString("ticket");
		} else {
			System.out.println("获取ticket异常" + ticketJson.toString());
			return null;
		}
		// 成功则获取二维码图片流
		String QRCodeUrl = getQRCodeUrl.replace("TICKET", ticket);
		return InputStreamUtil.getInputStream(QRCodeUrl);
	}

	/**
	 * 
	 * @Description:从输出流下载二维码图片
	 * @Author:Jc-Li
	 * @param:
	 * @Date:2016-1-25
	 */
	public static void downLoadQRCode(HttpServletResponse response,
			InputStream inputStream) {

		// 1.设置文件ContentType类型，这样设置，会自动判断下载文件类型
		response.setContentType("multipart/form-data");
		// 2.设置文件头：最后一个参数是设置下载文件名(假如我们叫a.pdf)
		response.setHeader("Content-Disposition", "attachment;fileName="
				+ "QRCode.jpg");
		ServletOutputStream out;

		try {

			// 3.通过response获取ServletOutputStream对象(out)
			out = response.getOutputStream();

			int b = 0;
			byte[] buffer = new byte[512];
			while (b != -1) {
				b = inputStream.read(buffer);
				// 4.写到输出流(out)中
				if (b > 0) {
					out.write(buffer, 0, b);
				}
			}
			inputStream.close();
			out.close();
			out.flush();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static InputStream getQrIsforCard() {
		return null;
	}
}
