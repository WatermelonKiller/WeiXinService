package com.hd.shareSubscribe.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.core.util.PropertiesUtil;
import com.core.util.QRCodeUtil;
import com.hd.shareSubscribe.bean.QrCode;
import com.hd.shareSubscribe.bean.ShareSubscribe;
import com.hd.shareSubscribe.sql.ShareSubscribeSql;
import com.hd.util.DateUtil;
import com.hd.util.FTPUtils;
import com.hd.util.SqlParameter;
import com.hd.util.dao.CommonDao;

@Service
@Transactional
public class ShareSubscribeService {
	@Autowired
	private CommonDao cd;

	/**
	 * 
	 * @Description:锟芥储专锟斤拷锟斤拷维锟斤拷锟斤拷息
	 * @Author:Jc-Li
	 * @param:
	 * @Date:2015-10-31
	 */
	public boolean saveQrCode(QrCode qrcode) {
		String sql = ShareSubscribeSql.SAVEQRCODESQL;
		return cd.executeSQL(
				sql,
				new SqlParameter(qrcode.getId(), qrcode.getOpen_id(), qrcode
						.getCode_path(), qrcode.getCreate_time()));
	}

	/**
	 * 
	 * @Description:锟芥储通锟斤拷扫锟斤拷锟轿拷锟斤拷注锟铰硷拷
	 * @Author:Jc-Li
	 * @param:
	 * @Date:2015-11-2
	 */
	public boolean saveShareSubcribe(ShareSubscribe ss) {
		String sql = ShareSubscribeSql.SAVESHAREQRCODE;
		return cd.executeSQL(
				sql,
				new SqlParameter(ss.getId(), ss.getFrom_open_id(), ss
						.getTo_open_id(), ss.getCreate_time()));
	}

	/**
	 * 
	 * @Description:通锟斤拷open_id锟斤拷枚锟轿拷锟斤拷址
	 * @Author:Jc-Li
	 * @param:
	 * @Date:2015-11-2
	 */
	public String getCodePathByOpenId(String open_id) {
		String sql = ShareSubscribeSql.SELQRCODEPATH;
		sql += "and open_id ='" + open_id + "'";
		return cd.getOneValue(sql);
	}

	/**
	 * 
	 * @Description:锟斤拷取专锟矫讹拷维锟诫并锟芥储锟斤拷Ftp
	 * @Author:Jc-Li
	 * @param:
	 * @throws IOException
	 * @Date:2015-10-31
	 */
	public boolean savePrivateQrCode(String open_id) throws IOException {
		// 判断是否已存在
		String str = "from_open_id:" + open_id;
		String path = getCodePathByOpenId(open_id);
		// 锟叫讹拷锟角凤拷锟斤拷锟�
		if (null != path && !path.equals("")) {
			return true;
		}
		// 从服务器获取二维码
		InputStream inputStream = QRCodeUtil.getQRCode(str);
		// 缓存图片至本地
		String id = UUID.randomUUID().toString().toUpperCase();
		File tempPath = new File("C://tempPicturePath/");
		if (!tempPath.exists()) {
			tempPath.mkdir();
		}
		File file = new File("C://tempPicturePath/" + id + ".jpg");
		OutputStream os = new FileOutputStream(file);
		int bytesRead = 0;
		byte[] buffer = new byte[8192];
		while ((bytesRead = inputStream.read(buffer, 0, 8192)) != -1) {
			os.write(buffer, 0, bytesRead);
		}
		os.close();
		inputStream.close();
		// 链接ftp并上传图片
		PropertiesUtil prop = new PropertiesUtil();
		FTPUtils ftp = new FTPUtils(prop.getProperties("ftp.inip"),
				prop.getProperties("ftp.username"),
				prop.getProperties("ftp.password"));
		boolean flag = ftp.putFile(file, "/cgj_test/privateQrCode/");
		// 如果上传成功，将数据存入数据库
		if (flag) {
			file.delete();
			QrCode code = new QrCode();
			code.setId(id);
			code.setOpen_id(open_id);
			code.setCreate_time(DateUtil.getDateTime());
			code.setCode_path("/cgj_test/privateQrCode/" + id + ".jpg");
			ftp.disconnect();
			return saveQrCode(code);
		} else {
			ftp.deleteFile("/cgj_test/privateQrCode/" + id + ".jpg");
			ftp.disconnect();
			return false;
		}
	}
}
