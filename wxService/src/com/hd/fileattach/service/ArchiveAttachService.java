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
	 * �жϵ�¼ftp
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
	 * �ϴ�����
	 * 
	 * @param request
	 * @param response
	 * @param items
	 */
	public void start(HttpServletRequest request, HttpServletResponse response,
			List<FileItem> items, Map<String, String> snMap) {
		boolean b = false;
		if (ServletFileUpload.isMultipartContent(request)) {// �����ļ��ϴ���request
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

				String browser = "";// ���IE�������ʶ
				String remotepath = FTP_PATH;// ftp������·��

				// ������ʱ�ļ��ϴ�·�� ��Ϊtomcat��fileTempPath ��webappsͬ��
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

						if (item.isFormField()) {// �Ǳ�����
							// ��ȡ�����ݵ�ֵ
							String key = item.getFieldName();
							if ("browser".equals(key)) {// ��ȡ��IE������ı�ʶ ,IE
														// ΪIE��noneΪ���������
								browser = item.getString("UTF-8");
							}
							if ("archive_type".equals(key)) {// ����Ŀ¼id
								archive_type = item.getString("UTF-8");
							}
							if ("archive_id".equals(key)) {// ����Ŀ¼id
								archive_id = item.getString("UTF-8");
							}
							if ("pp_id".equals(key)) {
								pp_id = item.getString("UTF-8");
							}
						} else {
							// ��������,�ϴ��ļ���������Ϣ
							// ����Ŀ¼id����ѯftp�����Ϣ

							String filename = item.getName();// IE�ϴ�֮���·��������ȡ��
							if (filename.contains("\\")) {
								filename = filename.substring(filename
										.lastIndexOf("\\") + 1);
							}

							String filetype = getSuffix(filename);// �����ļ�����ø���������
							if (filetype.equals("txt")) {// ��txt����ת����text���û������ã���������txt��ʽ�ļ�ֱ�Ӵ򿪣�����������
								filetype = "text";
							}
							String uuid = UUID.randomUUID().toString();
							// �����û��ϴ��ļ����ƣ�
							String file_name = snMap.get("realname").toString()
									+ "-" + snMap.get("cid").toString() + "-"
									+ uuid + "." + filetype;// ����ַ���+�ϴ��ļ��ĺ�׺��=�ϴ�����ļ�����
							/**** �������ַ�����������id ****/

							// ���ļ��ϴ���tomcat��������
							item.write(new File(localpath.toString(), file_name));
							String create_time = DateUtil.getDateTime();

							// ���ļ��ϴ���ftp��������
							Boolean flag = toftpServer(localpath.toString(),
									remotepath, file_name, ftp);
							// Boolean
							// flag=toftpServer(item.getInputStream(),file_name,item.getSize());//�����ļ���ʽд��
							while (flag == false) {// �ϴ����ɹ������ϴ���ֱ���ɹ�Ϊֹ
								flag = toftpServer(localpath.toString(),
										remotepath, file_name, ftp);
							}
							String showUrl = "http://" + ftp.getServer_ip()
									+ ":" + ftp.getHttp_port() + remotepath
									+ "/" + file_name; // ����ͼƬurl
							if (flag == true) {
								String userId = snMap.get("id"); // ��ȡ�û�ID
								String sltUrl = "D:/FTP/cindyImages-SLT/"
										+ "slt-" + file_name; // ����ͼURL
								// ����ϴ��ļ��ɹ�����ִ��������ݵ���������
								ArchiveAttachBean aab = new ArchiveAttachBean();
								aab.setFile_id(uuid);
								aab.setFile_name(file_name);
								aab.setFile_real_name(filename);
								aab.setFile_type(filetype);
								aab.setCreate_time(create_time);
								aab.setFile_path(remotepath);
								aab.setFtp_server_id(snMap.get("realname")
										.toString()); // �洢�û�����
								aab.setFile_archive_type(showUrl); // showUrl
								aab.setFile_archive_id(userId); // �û�ID����USER��
								// ��������
								b = insArchiveAttach(aab);
								// author jiyu yang ��������ͼ����
								if (true == b) {
									// ��ȡԴ�ļ�����������ͼת��;ѹ����90%
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
							// �ļ�ɾ��·��
							String deleteUrl = uuid;
							JSONObject jsono = new JSONObject();
							jsono.put("name", filename);
							jsono.put("size", item.getSize());
							jsono.put("url", showUrl);

							if (("jpg|bmp|png|jpeg|gif|tif|tiff"
									.indexOf(filetype)) != -1) {// �����ͼƬ��ʽ����ǰ̨��ʾͼƬ
								// ���������,�����IE
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
							// ƴ������ʾ���ϴ�������Ϣ
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
	 * ��ȡ�ļ���׺��
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
	 * �ϴ���ftp
	 * 
	 * @param is
	 * @param filename
	 * @param localsize
	 * @return
	 */
	public Boolean toftpServer(String localpath, String remotepath,
			String filename, HdTabFtpServer ftp) {
		Boolean flag = true;// �ϴ��ɹ���ʶ
		ContinueFTP myFtp = new ContinueFTP();
		try {
			myFtp.connect(ftp);
			myFtp.mkDir(remotepath);
			myFtp.upload(localpath + "//" + filename, remotepath + "//"
					+ filename);
			myFtp.disconnect();
		} catch (IOException e) {
			System.out.println("����FTP����" + e.getMessage());
			flag = false;// �ϴ�ʧ�ܱ�ʶ
		}
		return flag;
	}

	/**
	 * ���뵵���ĸ�����Ϣ
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
	 * �����ļ�����
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
	 * �ļ�ɾ��
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

		Boolean flag = true;// �ϴ��ɹ���ʶ
		ContinueFTP myFtp = new ContinueFTP();
		try {
			myFtp.connect(ftp);
			myFtp.deleteFile(remotepath + "/" + file_name);
		} catch (IOException e) {
			System.out.println("����FTPɾ������" + e.getMessage());
			flag = false;// �ϴ�ʧ�ܱ�ʶ
		}

		// ɾ������
		String sql = ArchiveAttachSql.DEL_DATA;
		flag = cd.executeSQL(sql, new SqlParameter(file_name));

		return flag;
	}

	/**
	 * �ļ�����
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
		String file_name = request.getParameter("file_uu_name");// �ļ���

		int reply = 0;

		FTPClient ftpClient = new FTPClient();
		ftpClient.connect(ftp.getServer_ip(),
				Integer.parseInt(ftp.getFtp_port()));
		ftpClient.login(ftp.getFtp_user_name(), ftp.getFtp_user_password());// ��¼
		reply = ftpClient.getReplyCode();
		if (!FTPReply.isPositiveCompletion(reply)) {
			ftpClient.disconnect();
		}
		ftpClient.changeWorkingDirectory(FTP_PATH);// ת�Ƶ�FTP������Ŀ¼

		InputStream iss = null;

		FTPFile[] fs = ftpClient.listFiles(); // �õ�ftp������ĳһ·���µ��ļ��б�
		for (FTPFile ff : fs) {
			if (ff.getName().equals(file_name)) {
				iss = ftpClient.retrieveFileStream(ff.getName());// �õ�ftp�ϵ��ļ���
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
