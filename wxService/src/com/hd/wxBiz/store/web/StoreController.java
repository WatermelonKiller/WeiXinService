package com.hd.wxBiz.store.web;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;

import com.core.util.AttachmentUploadUtil;
import com.core.util.PropertiesUtil;
import com.core.util.StoreUtil;
import com.hd.SystemAction.BaseAction;
import com.hd.util.FTPUtils;
import com.hd.wxBiz.store.bean.StoreInfo;
import com.hd.wxBiz.store.bean.StorePhoto;
import com.hd.wxBiz.store.service.StoreService;

@Controller
public class StoreController extends BaseAction {
	@Autowired
	private StoreService ss;

	/**
	 * 
	 * @Description:门店列表
	 * @Author:Jc-Li
	 * @param:
	 * @Date:2015-11-13
	 */
	@RequestMapping(value = "/store/storeList")
	public ModelAndView storeList(HttpServletRequest request) {
		ModelAndView view = new ModelAndView();
		String parameter = request.getParameter("parameter");
		String available_state = request.getParameter("available_state");
		doAllSearchList(request, "list",
				ss.selStoreSql(parameter, available_state), "jdbc", 10);
		view.addObject("parameter", parameter);
		view.setViewName("/store/StoreList");
		return view;
	}

	/**
	 * 
	 * @Description:门店添加
	 * @Author:Jc-Li
	 * @param:
	 * @Date:2015-11-13
	 */
	@RequestMapping(value = "/store/addForm")
	public ModelAndView addStoreForm() {
		ModelAndView view = new ModelAndView();
		view.addObject("sid", UUID.randomUUID().toString().toUpperCase());
		view.setViewName("/store/addStore");
		return view;
	}

	/**
	 * 
	 * @Description:添加门店申请，此处不产生id！！！！！！！！！！
	 * @Author:Jc-Li
	 * @param:
	 * @Date:2015-11-7
	 */
	@RequestMapping(value = "/store/auditingStore")
	@ResponseBody
	public String addStore(StoreInfo si, HttpServletRequest request) {
		// 设置类目
		String[] categories = request.getParameterValues("categories");
		StringBuffer categorie = new StringBuffer();
		for (String str : categories) {
			if (str != null && !"".equals(str)) {
				categorie.append(str + ",");
			}
		}
		si.setCategories(categorie.toString().substring(0,
				categorie.length() - 1));
		if (ss.addStore(si)) {
			return "success";
		} else {
			return "error";
		}
	}

	/**
	 * 
	 * @Description:刷新门店类目表
	 * @Author:Jc-Li
	 * @param:
	 * @Date:2015-11-13
	 */
	@RequestMapping(value = "/store/refreshStoreType")
	@ResponseBody
	public String updateStoreType(HttpServletRequest request) {
		List<String> list = StoreUtil.getStoreTypeList();
		if (list != null) {
			if (ss.updateStoreType(list)) {
				return "success";
			} else {
				return "error";
			}
		} else {
			return "error";
		}
	}

	/**
	 * 
	 * @Description:异步获取门店类型
	 * @Author:Jc-Li
	 * @param:
	 * @Date:2015-12-10
	 */
	@RequestMapping(value = "/store/getTypes", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String getTypes(HttpServletRequest request) {
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String last_type = request.getParameter("last_type");
		String level = request.getParameter("level");
		return ss.getStoreType(last_type, level);
	}

	/**
	 * 
	 * @Description:解密百度坐标为火星坐标
	 * @Author:Jc-Li
	 * @param:
	 * @Date:2015-11-17
	 */
	@RequestMapping(value = "/store/decryptZb")
	@ResponseBody
	public String bd_decrypt(HttpServletRequest request) {
		String lat = request.getParameter("lat");
		String lon = request.getParameter("lon");
		return ss.bd_decrypt(Double.valueOf(lat), Double.valueOf(lon));
	}

	/**
	 * 
	 * @Description:同步门店信息
	 * @Author:Jc-Li
	 * @param:
	 * @Date:2015-11-17
	 */
	@RequestMapping(value = "/store/refreshStore")
	@ResponseBody
	public String refreshStore() throws Exception {
		if (ss.refreshStore()) {
			return "success";
		} else {
			return "error";
		}
	}

	/**
	 * 
	 * @Description:删除门店
	 * @Author:Jc-Li
	 * @param:
	 * @Date:2015-11-17
	 */
	@RequestMapping(value = "/store/delStore")
	@ResponseBody
	public String delStore(HttpServletRequest request) {
		String poi_id = request.getParameter("poi_id");
		String sid = request.getParameter("sid");
		if (ss.delStore(poi_id, sid)) {
			return "success";
		} else {
			return "error";
		}
	}

	/**
	 * 
	 * @Description:修改门店信息
	 * @Author:Jc-Li
	 * @param:
	 * @Date:2015-11-18
	 */
	@RequestMapping(value = "/store/updateStore")
	@ResponseBody
	public String updateStore(StoreInfo si) {
		if (ss.updateStore(si)) {
			return "success";
		} else {
			return "error";
		}
	}

	/**
	 * 
	 * @Description:修改页面
	 * @Author:Jc-Li
	 * @param:
	 * @Date:2015-11-18
	 */
	@RequestMapping(value = "/store/updatePage")
	public ModelAndView updatePage(HttpServletRequest request) {
		ModelAndView view = new ModelAndView();
		String sid = request.getParameter("sid");
		view.addObject("store", ss.getOneStore(sid));
		view.addObject("photos", ss.getPic_url(sid));
		view.setViewName("/store/updatePage");
		return view;
	}

	/**
	 * 
	 * @Description:添加门店图片
	 * @Author:Jc-Li
	 * @param:
	 * @Date:2015-12-10
	 */
	@RequestMapping(value = "/store/uploadStorePhoto")
	@ResponseBody
	public String uploadStorePhoto(HttpServletRequest request) {
		// 获取门店id
		String id = request.getParameter("id");
		// 获取文件request
		MultipartResolver resolver = new CommonsMultipartResolver(request
				.getSession().getServletContext());
		MultipartHttpServletRequest multipartRequest = resolver
				.resolveMultipart(request);
		// 获取图片文件集合
		List<MultipartFile> list = multipartRequest.getFiles("uploadfile");
		// 封装回调函数数据
		JSONArray jsonArray = new JSONArray();
		// 初始化图片序号
		int i = 1;
		// 获得流里面的第一个文件，也可以迭代，将文件缓存至本地服务器
		for (MultipartFile mtf : list) {
			String fileLocalPath = getProject_path(request) + "tempImg/";
			File dirFile = new File(fileLocalPath);
			if (!dirFile.exists()) {
				dirFile.mkdir();
			}
			String temp = mtf.getOriginalFilename();
			String[] tempStr = temp.split("\\.");
			String filePath = "";
			if (tempStr.length > 1) {
				temp = UUID.randomUUID().toString() + "."
						+ tempStr[(tempStr.length - 1)];
			}
			filePath = fileLocalPath + temp;
			File file = new File(filePath);
			try {
				file.createNewFile();
				InputStream inputStream = mtf.getInputStream();
				// 文件流写到服务器端
				FileOutputStream outputStream = new FileOutputStream(filePath);
				byte buffer[] = new byte[1024];
				// 判断输入流中的数据是否已经读完的标识
				int len = 0;
				// 循环将输入流读入到缓冲区当中，(len=in.read(buffer))>0就表示in里面还有数据
				while ((len = inputStream.read(buffer)) > 0) {
					// 使用FileOutputStream输出流将缓冲区的数据写入到指定的目录(savePath + "\\" +
					// filename)当中
					outputStream.write(buffer, 0, len);
				}
				inputStream.close();
				outputStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// 将图片上传至微信服务器
			String url = AttachmentUploadUtil.imageUpload(filePath);
			PropertiesUtil prop = new PropertiesUtil();
			FTPUtils ftp = new FTPUtils(prop.getProperties("ftp.inip"),
					prop.getProperties("ftp.username"),
					prop.getProperties("ftp.password"));
			if (!ftp.ftpFileExists("/storeImg")) {
				ftp.createDirs("/storeImg");
			}
			ftp.putFile(filePath, "/storeImg/" + temp);
			// 删除本地缓存
			file.delete();
			if (url != null && !"".equals(url)) {
				// 如果上传成功，则将信息存入数据库
				StorePhoto storePhoto = new StorePhoto();
				storePhoto.setId(UUID.randomUUID().toString().toUpperCase());
				storePhoto.setPhoto_url(url);
				storePhoto.setSequence(i);
				i++;
				storePhoto.setStore_id(id);
				storePhoto.setFtp_name(temp);
				storePhoto.setFtp_url("/storeImg/" + temp);
				if (ss.addPicUrl(storePhoto)) {
					// 封装此图片的结果
					Map<String, String> map = new HashMap<String, String>();
					map.put("seq", (i - 1) + "");
					map.put("result", "succ");
					map.put("url", url);
					jsonArray.add(map);
				} else {
					Map<String, String> map = new HashMap<String, String>();
					map.put("seq", (i - 1) + "");
					map.put("result", "err");
					map.put("url", "");
					jsonArray.add(map);
				}
			} else {
				Map<String, String> map = new HashMap<String, String>();
				map.put("seq", i + "");
				map.put("result", "err");
				map.put("url", "");
				jsonArray.add(map);
			}
		}
		// 拼装json字符串传至前台
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("res", jsonArray);
		String result = "<script>parent.callback('" + jsonObject.toString()
				+ "')</script>";
		return result;
	}

	/**
	 * 
	 * @Description:获取本地tomcat地址
	 * @Author:Jc-Li
	 * @param:
	 * @Date:2015-12-10
	 */
	public String getProject_path(HttpServletRequest request) {
		String path = request.getSession().getServletContext().getRealPath("/");
		path = path.replaceAll("\\\\", "/");
		return path;
	}

	@RequestMapping(value = "/store/getAddressByPoint", method = RequestMethod.GET, produces = "text/html;charset=GB2312")
	@ResponseBody
	public String getAddressByPoint(HttpServletRequest request) {
		String lat = request.getParameter("lat");
		String lon = request.getParameter("lon");
		return ss.getAddressByPoint(lon, lat);
	}

	@RequestMapping(value = "/store/info")
	public ModelAndView getStoreInfo(HttpServletRequest request) {
		ModelAndView view = new ModelAndView();
		String sid = request.getParameter("sid");
		view.addObject("store", ss.getOneStore(sid));
		view.addObject("photos", ss.getPic_url(sid));
		view.setViewName("/store/info");
		return view;
	}
}
