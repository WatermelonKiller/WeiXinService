package com.hd.fileattach.web;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hd.SystemInterface.SystemInterFace;
import com.hd.fileattach.service.ArchiveAttachService;

@Controller
public class ArchiveAttachController implements SystemInterFace {

	@Autowired
	ArchiveAttachService archiveservice;

	/**
	 * ��ת�����ϴ���¼ҳ��
	 * 
	 * @return
	 */
	@RequestMapping(value = "/archiveattach/touppage")
	public ModelAndView toUpAttachPage(HttpServletRequest request,
			HttpServletResponse response) {
		String archive_type = request.getParameter("archive_type") + "";
		String archive_id = request.getParameter("archive_id") + "";
		ModelAndView view = new ModelAndView();
		view.setViewName("/attach/archiveattach");
		view.addObject("archive_type", archive_type);
		view.addObject("archive_id", archive_id);
		return view;
	}

	/**
	 * �ļ��ϴ�
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/archiveattach/uploadfile", method = RequestMethod.POST)
	public ModelAndView uploadFile(HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		ModelAndView view = new ModelAndView();
		try {
			ServletFileUpload uploadHandler = new ServletFileUpload(
					new DiskFileItemFactory());
			String type = "";
			String id = "";
			List<FileItem> items = null;
			try {
				items = uploadHandler.parseRequest(request);
				if (!items.isEmpty()) {
					for (FileItem item : items) {
						if (item.isFormField()) {// �Ǳ?���
							// ��ȡ�?��ݵ�ֵ
							String key = item.getFieldName();
							if ("type".equals(type)) {// ����Ŀ¼id
								type = item.getString("UTF-8");
							}
							if ("id".equals(id)) {// ����Ŀ¼id
								id = item.getString("UTF-8");
							}
						}
					}
				}
			} catch (Exception e) {
			}
			boolean tst = archiveservice.testConnect();
			if (tst) {
				Map<String, String> snMap = (Map<String, String>) session
						.getAttribute(USER_CONTEXT);
				archiveservice.start(request, response, items, snMap);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/archiveattach/deletefile")
	public void deleteArchiveAttrach(HttpServletRequest request,
			HttpServletResponse response) {
		String id = request.getParameter("id") + "";
		List<Map<String, String>> list = archiveservice.queryFileMes(id);
		String file_name = list.get(0).get("file_name") + "";
		Boolean b = archiveservice.delAttach(file_name);
		try {
			response.getWriter().print(b);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * �ļ�����
	 * 
	 * @param response
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping(value = "/archiveattach/downfile")
	public void downloadFile(HttpServletResponse response,
			HttpServletRequest request) throws Exception {
		response.reset();
		response.setCharacterEncoding("utf-8");
		String file_id = request.getParameter("file_id");
		String filename = request.getParameter("file_uu_name");

		response.setHeader("Content-Disposition", "attachment;fileName="
				+ java.net.URLEncoder.encode(filename, "UTF-8"));
		response.setContentType("application/x-msdownload");
		try {
			InputStream inputStream = archiveservice.downFile(request);
			OutputStream os = response.getOutputStream();
			byte[] b = new byte[1024];
			int length;
			while ((length = inputStream.read(b)) > 0) {
				os.write(b, 0, length);
			}
			inputStream.close();
			os.flush();
			os.close();
			os = null;
			response.flushBuffer();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/archiveattach/infoPage")
	public ModelAndView showUpdateInfoPage(HttpServletRequest request) {
		ModelAndView view = new ModelAndView();
		String file_id = request.getParameter("file_id");
		List<Map<String, String>> list = archiveservice.getInfo(file_id);
		view.addObject("aab", list.get(0));
		view.setViewName("/imageMana/imageInfo");
		return view;
	}

	@RequestMapping(value = "/archiveattach/updateInfo")
	@ResponseBody
	public String updateInfo(HttpServletRequest request) {
		String file_id = request.getParameter("file_id");
		String file_title = request.getParameter("file_title");
		String file_addr = request.getParameter("file_addr");
		if (archiveservice.updateInfo(file_id, file_title, file_addr)) {
			return "success";
		} else {
			return "error";
		}
	}
}
