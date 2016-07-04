package com.core.util;

import com.core.encryption.AesException;
import com.core.encryption.WXBizMsgCrypt;

/**
 * 消息加密解密类
 * 
 * @author Administrator
 *         url上无encrypt_type参数或者其值为raw时表示为不加密；encrypt_type为aes时，表示aes加密
 */
public class SecretUtil {

	/**
	 * 
	 * @Description:消息加密
	 * @Author:Jc-Li
	 * @param:
	 * @Date:2015-11-3
	 */
	public static String encryptMsg(String msg) {
		String timestamp = ToolUtil.create_timestamp();
		String nonce = ToolUtil.create_nonce_str();
		WXBizMsgCrypt pc;
		try {
			pc = new WXBizMsgCrypt(AppInfoUtil.getToken(),
					AppInfoUtil.getEncodingAESKey(), AppInfoUtil.getAppid());
			return pc.encryptMsg(msg, timestamp, nonce);
		} catch (AesException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 
	 * @Description:消息解密，msg_signature，timestamp,nonce从URL的参数中获得
	 * @Author:Jc-Li
	 * @param:
	 * @Date:2015-11-3
	 */
	public static String desEncryptMsg(String msg, String msg_signature,
			String timestamp, String nonce) throws Exception {
		WXBizMsgCrypt pc = new WXBizMsgCrypt(AppInfoUtil.getToken(),
				AppInfoUtil.getEncodingAESKey(), AppInfoUtil.getAppid());
		return pc.decryptMsg(msg_signature, timestamp, nonce, msg);
	}
}
