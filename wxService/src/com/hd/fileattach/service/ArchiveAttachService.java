package com.hd.fileattach.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.coobird.thumbnailator.Thumbnails;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hd.SystemInterface.SystemInterFace;
import com.hd.fileattach.bean.ArchiveAttachBean;
import com.hd.fileattach.sql.ArchiveAttachSql;
import com.hd.img.ContinueFTP;
import com.hd.img.HdTabFtpServer;
import com.hd.util.DateUtil;
import com.hd.util.SqlParameter;
import com.hd.util.dao.CommonDao;

@Service
public class ArchiveAttachService implements SystemInterFace {

	@Autowired
	CommonDao cd;

	/**
	 * 判断登录ftp
	 * 
	 * @param menu_id
	 * @param flow_def_key
	 * @return
	 */
	public Boolean testConnect() {
		boolean flag = false;

		HdTabFtpServer ftp = new HdTabFtpServer();
		ftp.setServer_ip(FTP_INIP);
		ftp.setFtp_port(FTP_INPORT);
		ftp.setFtp_user_name(FTP_USERNAME);
		ftp.setFtp_user_password(FTP_PASSWORD);

		ContinueFTP myFtp = new ContinueFTP();
		flag = myFtp.getconnect(ftp);

		try {
			myFtp.disconnect();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return flag;
	}

	/**
	 * 上传附件
	 * 
	 * @param request
	 * @param response
	 * @param items
	 */
	public void start(HttpServletRequest request, HttpServletResponse response,
			List<FileItem> items, Map<String, String> snMap) {
		boolean b = false;
		if (ServletFileUpload.isMultipartContent(request)) {// 是流文件上传的request
			ServletFileUpload uploadHandler = new ServletFileUpload(
					new DiskFileItemFactory());
			response.setContentType("text/plain;charset=UTF-8");
			PrintWriter writer = null;
			try {
				writer = response.getWriter();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			JSONArray json = new JSONArray();
			try {
				HdTabFtpServer ftp = new HdTabFtpServer();
				ftp.setServer_ip(FTP_INIP);
				ftp.setFtp_port(FTP_INPORT);
				ftp.setFtp_user_name(FTP_USERNAME);
				ftp.setFtp_user_password(FTP_PASSWORD);
				ftp.setHttp_port(FTP_OUTPORT);

				String browser = "";// 获得IE浏览器标识
				String remotepath = FTP_PATH;// ftp服务器路径

				// 设置临时文件上传路径 改为tomcat下fileTempPath 与webapps同级
				String tempPath = CommonDao.getFileTempPath(request);
				File localpath = new File(tempPath + "\\tempPath");
				if (!localpath.exists()) {
					localpath.mkdirs();
				}

				String archive_type = "";
				String archive_id = "";
				String pp_id = "";
				if (!items.isEmpty()) {
					for (FileItem item : items) {

						if (item.isFormField()) {// 是表单数据
							// 获取表单数据的值
							String key = item.getFieldName();
							if ("browser".equals(key)) {// 获取是IE浏览器的标识 ,IE
														// 为IE，none为其他浏览器
								browser = item.getString("UTF-8");
							}
							if ("archive_type".equals(key)) {// 附件目录id
								archive_type = item.getString("UTF-8");
							}
							if ("archive_id".equals(key)) {// 附件目录id
								archive_id = item.getString("UTF-8");
							}
							if ("pp_id".equals(key)) {
								pp_id = item.getString("UTF-8");
							}
						} else {
							// 是流数据,上传文件的数据信息
							// 根据目录id，查询ftp相关信息

							String filename = item.getName();// IE上传之后带路径名，截取掉
							if (filename.contains("\\")) {
								filename = filename.substring(filename
										.lastIndexOf("\\") + 1);
							}

							String filetype = getSuffix(filename);// 根据文件名获得附件的类型
							if (filetype.equals("txt")) {// 把txt类型转换成text，用户下载用，否则下载txt格式文件直接打开，而不是下载
								filetype = "text";
							}
							String uuid = UUID.randomUUID().toString();
							// 定义用户上传文件名称，
							String file_name = snMap.get("realname").toString()
									+ "-" + snMap.get("cid").toString() + "-"
									+ uuid + "." + filetype;// 随机字符串+上传文件的后缀名=上传后的文件名称
							/**** 获得随机字符串，当主键id ****/

							// 把文件上传到tomcat服务器下
							item.write(new File(localpath.toString(), file_name));
							String create_time = DateUtil.getDateTime();

							// 把文件上传到ftp服务器上
							Boolean flag = toftpServer(localpath.toString(),
									remotepath, file_name, ftp);
							// Boolean
							// flag=toftpServer(item.getInputStream(),file_name,item.getSize());//以流文件形式写入
							while (flag == false) {// 上传不成功继续上传，直至成功为止
								flag = toftpServer(localpath.toString(),
										remotepath, file_name, ftp);
							}
							String showUrl = "http://" + ftp.getServer_ip()
									+ ":" + ftp.getHttp_port() + remotepath
									+ "/" + file_name; // 回显图片url
							if (flag == true) {
								String userId = snMap.get("id"); // 获取用户ID
								String sltUrl = "D:/FTP/cindyImages-SLT/"
										+ "slt-" + file_name; // 缩略图URL
								// 如果上传文件成功，则执行添加数据到附件表中
								ArchiveAttachBean aab = new ArchiveAttachBean();
								aab.setFile_id(uuid);
								aab.setFile_name(file_name);
								aab.setFile_real_name(filename);
								aab.setFile_type(filetype);
								aab.setCreate_time(create_time);
								aab.setFile_path(remotepath);
								aab.setFtp_server_id(snMap.get("realname")
										.toString()); // 存储用户姓名
								aab.setFile_archive_type(showUrl); // showUrl
								aab.setFile_archive_id(userId); // 用户ID关联USER表
								// 插入数据
								b = insArchiveAttach(aab);
								// author jiyu yang 增加缩略图功能
								if (true == b) {
									// 读取源文件并进行缩略图转换;压缩率90%
									BufferedImage image = ImageIO
											.read(new File(
													"D:/FTP/cindyImages/"
															+ file_name));
									Thumbnails
											.of(image)
											.scale(0.90f)
											.toFile("D:/FTP/cindyImages-SLT/"
													+ "slt-" + file_name);
								}

							}
							String deletePath = remotepath + "/" + file_name;
							// 文件删除路径
							String deleteUrl = uuid;
							JSONObject jsono = new JSONObject();
							jsono.put("name", filename);
							jsono.put("size", item.getSize());
							jsono.put("url", showUrl);

							if (("jpg|bmp|png|jpeg|gif|tif|tiff"
									.indexOf(filetype)) != -1) {// 如果是图片格式才在前台显示图片
								// 浏览器类型,如果是IE
								if ("none".equals(browser)) {
									jsono.put("thumbnail_url", showUrl);
								}
							}

							jsono.put("delete_url", deleteUrl);
							jsono.put("delete_type", "GET");
							jsono.put("archive_id", uuid);
							jsono.put("archive_type", archive_type);
							jsono.put("filename", filename);
							jsono.put("file_uu_name", file_name);
							jsono.put("url", showUrl);
							jsono.put("showurl", showUrl);
							// 拼接树显示的上传附件信息
							json.put(jsono);
						}
					}
				}
			} catch (FileUploadException e) {
				throw new RuntimeException(e);
			} catch (Exception e) {
				throw new RuntimeException(e);
			} finally {
				if (writer != null) {
					writer.write(json.toString());
					writer.close();
				}
			}
		}
	}

	/**
	 * 获取文件后缀名
	 * 
	 * @param filename
	 * @return
	 */
	public String getSuffix(String filename) {
		String suffix = "";
		int pos = filename.lastIndexOf('.');
		if (pos > 0 && pos < filename.length() - 1) {
			suffix = filename.substring(pos + 1);
		}
		suffix = suffix.toLowerCase();
		return suffix;
	}

	/**
	 * 上传到ftp
	 * 
	 * @param is
	 * @param filename
	 * @param localsize
	 * @return
	 */
	public Boolean toftpServer(String localpath, String remotepath,
			String filename, HdTabFtpServer ftp) {
		Boolean flag = true;// 上传成功标识
		ContinueFTP myFtp = new ContinueFTP();
		try {
			myFtp.connect(ftp);
			myFtp.mkDir(remotepath);
			myFtp.upload(localpath + "//" + filename, remotepath + "//"
					+ filename);
			myFtp.disconnect();
		} catch (IOException e) {
			System.out.println("连接FTP出错：" + e.getMessage());
			flag = false;// 上传失败标识
		}
		return flag;
	}

	/**
	 * 插入档案的附件信息
	 * 
	 * @param aab
	 * @return
	 */
	public Boolean insArchiveAttach(ArchiveAttachBean aab) {
		boolean b = false;
		String sql = ArchiveAttachSql.INS_ARCHIVE_ATTACH;

		b = cd.executeSQL(
				sql,
				new SqlParameter(aab.getFile_id(), aab.getFile_name(), aab
						.getFile_real_name(), aab.getFile_type(), aab
						.getCreate_time(), aab.getFile_path(), aab
						.getFile_archive_type(), aab.getFtp_server_id(), aab
						.getFile_archive_id()));
		return b;
	}

	/**
	 * 查找文件数据
	 * 
	 * @param file_id
	 * @return
	 */
	public List<Map<String, String>> queryFileMes(String file_id) {
		String sql = ArchiveAttachSql.SEL_FILE_MES;
		List<Map<String, String>> list = cd.queryListByPS(sql,
				new SqlParameter(file_id));
		return list;

	}

	/**
	 * 文件删除
	 * 
	 * @return
	 */
	public Boolean delAttach(String file_name) {
		HdTabFtpServer ftp = new HdTabFtpServer();
		ftp.setServer_ip(FTP_INIP);
		ftp.setFtp_port(FTP_INPORT);
		ftp.setFtp_user_name(FTP_USERNAME);
		ftp.setFtp_user_password(FTP_PASSWORD);
		String remotepath = FTP_PATH;

		Boolean flag = true;// 上传成功标识
		ContinueFTP myFtp = new ContinueFTP();
		try {
			myFtp.connect(ftp);
			myFtp.deleteFile(remotepath + "/" + file_name);
		} catch (IOException e) {
			System.out.println("连接FTP删除出错：" + e.getMessage());
			flag = false;// 上传失败标识
		}

		// 删除数据
		String sql = ArchiveAttachSql.DEL_DATA;
		flag = cd.executeSQL(sql, new SqlParameter(file_name));

		return flag;
	}

	/**
	 * 文件下载
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */

	public InputStream downFile(HttpServletRequest request) throws Exception {
		HdTabFtpServer ftp = new HdTabFtpServer();
		ftp.setServer_ip(FTP_INIP);
		ftp.setFtp_port(FTP_INPORT);
		ftp.setFtp_user_name(FTP_USERNAME);
		ftp.setFtp_user_password(FTP_PASSWORD);
		String file_name = request.getParameter("file_uu_name");// 文件名

		int reply = 0;

		FTPClient ftpClient = new FTPClient();
		ftpClient.connect(ftp.getServer_ip(),
				Integer.parseInt(ftp.getFtp_port()));
		ftpClient.login(ftp.getFtp_user_name(), ftp.getFtp_user_password());// 登录
		reply = ftpClient.getReplyCode();
		if (!FTPReply.isPositiveCompletion(reply)) {
			ftpClient.disconnect();
		}
		ftpClient.changeWorkingDirectory(FTP_PATH);// 转移到FTP服务器目录

		InputStream iss = null;

		FTPFile[] fs = ftpClient.listFiles(); // 得到ftp服务上某一路径下的文件列表
		for (FTPFile ff : fs) {
			if (ff.getName().equals(file_name)) {
				iss = ftpClient.retrieveFileStream(ff.getName());// 得到ftp上的文件流
			}
		}

		return iss;
	}

	public List<Map<String, String>> getInfo(String file_id) {
		String sql = "select file_id,file_title,file_addr from tab_archive_attach where file_id=?";
		List<Map<String, String>> list = cd.queryListByPS(sql,
				new SqlParameter(file_id));
		return list;
	}

	public boolean updateInfo(String file_id, String file_title,
			String file_addr) {
		String sql = "update tab_archive_attach set file_title=?,"
				+ "file_addr=? where file_id=? ";
		return cd.executeSQL(sql, new SqlParameter(file_title, file_addr,
				file_id));
	}
}
