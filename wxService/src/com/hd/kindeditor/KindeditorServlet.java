package com.hd.kindeditor;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.hd.util.FTPUtils;
import com.hd.util.JsonMapper;

public class KindeditorServlet extends HttpServlet {
	private static JsonMapper mapper = JsonMapper.nonDefaultMapper();


	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);

		List items = null;
		try {
			items = upload.parseRequest(request);
		} catch (FileUploadException e1) {
			// TODO Auto-generated catch block `
			e1.printStackTrace();
		}
		Iterator itr = items.iterator();
		ServletContext application = request.getSession().getServletContext();
		String savePath = "C:/attached/";
		// 文件保存目录URL
		String saveUrl = request.getContextPath() + "/attached/";
		// 定义允许上传的文件扩展名
		HashMap<String, String> extMap = new HashMap<String, String>();
		extMap.put("image", "gif,jpg,jpeg,png,bmp");
		extMap.put("flash", "swf,flv");
		extMap.put("media", "swf,flv,mp3,wav,wma,wmv,mid,avi,mpg,asf,rm,rmvb");
		extMap.put("file", "doc,docx,xls,xlsx,ppt,htm,html,txt,zip,rar,gz,bz2");
		// 最大文件大小
		long maxSize = 1024 * 1024 * 20;
		String dirName = request.getParameter("dir");
		if (dirName == null) {
			dirName = "image";
		}
		if (!extMap.containsKey(dirName)) {
			out.println(getError("目录名不正确。"));
			out.flush();
			out.close();
			return;
		}
		// 创建文件夹
		savePath += dirName + "/";
		saveUrl += dirName + "/";
		File saveDirFile = new File(savePath);
		System.out.println("saveDirFile"+saveDirFile);
		if (!saveDirFile.exists()) {
			saveDirFile.mkdirs();
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String ymd = sdf.format(new Date());
		savePath += ymd + "/";
		saveUrl += ymd + "/";
		File dirFile = new File(savePath);
		System.out.println("dirFile:"+dirFile);
		if (!dirFile.exists()) {
			dirFile.mkdirs();
		}

		if (!ServletFileUpload.isMultipartContent(request)) {
			out.println(getError("请选择文件。"));
			out.flush();
			out.close();
			return;
		}
		// 检查目录
		File uploadDir = new File(savePath);
		System.out.println("uploadDir:"+uploadDir);
		if (!uploadDir.isDirectory()) {
			out.println(getError("上传目录不存在。"));
			out.flush();
			out.close();
			return;
		}
		// 检查目录写权限
		if (!uploadDir.canWrite()) {
			out.println(getError("上传目录没有写权限。"));
			out.flush();
			out.close();
			return;
		}
		while (itr.hasNext()) {
			FileItem item = (FileItem) itr.next();
			String fileName = item.getName();
			if (!item.isFormField()) {
				// 检查文件大小
				if (item.getSize() > maxSize) {
					out.println(getError("上传文件大小超过限制。"));
					out.flush();
					out.close();
					return;
				}
				// 检查扩展名
				String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
				if (!Arrays.<String> asList(extMap.get(dirName).split(",")).contains(fileExt)) {
					out.println("上传文件扩展名是不允许的扩展名。\n只允许" + extMap.get(dirName) + "格式。");
					out.flush();
					out.close();
					return;
				}
				SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
				String newFileName = df.format(new Date()) + "_" + new Random().nextInt(1000) + "." + fileExt;
				String temStr = "";
				String temName="";
				try {
					File uploadedFile = new File(savePath, newFileName);
					temStr = uploadedFile.getAbsolutePath();
					temName = uploadedFile.getName();
					System.out.println(temStr);
					item.write(uploadedFile);
				} catch (Exception e) {
					out.println("上传文件失败。");
					out.flush();
					out.close();
					return;
				}
				
				Properties pro=new Properties();
				pro.load(KindeditorServlet.class.getClassLoader().getResourceAsStream("jdbc.properties"));
				Integer inport = Integer.parseInt(pro.getProperty("ftp.inport"));
				
				FTPUtils ftpUtils = new FTPUtils(pro.getProperty("ftp.inip"),inport,pro.getProperty("ftp.username"),pro.getProperty("ftp.password"));
				ftpUtils.putFile(temStr, pro.getProperty("ftp.help"));
				
				String showUrl = "http://"+pro.getProperty("ftp.outip")+":"+pro.getProperty("ftp.outport")+pro.getProperty("ftp.help")+"/"+temName;
				
				Map<String, Object> msg = new HashMap<String, Object>();
				msg.put("error", 0);
				msg.put("url", showUrl);
				String mapString = mapper.toJson(msg);
				out.println(mapString);
				out.flush();
				out.close();
			}
		}
	}

	private String getError(String message) {
		Map<String, Object> msg = new HashMap<String, Object>();
		msg.put("error", 1);
		msg.put("message", message);
		String mapString = mapper.toJson(msg);
		return mapString;
	}

	/**
	 * The doPost method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		this.doGet(request, response);
	}

}
