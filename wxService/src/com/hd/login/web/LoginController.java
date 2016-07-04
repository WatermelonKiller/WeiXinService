package com.hd.login.web;

import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hd.SystemAction.BaseAction;
import com.hd.login.bean.User;
import com.hd.login.service.LoginService;
import com.hd.util.MD5Encoder;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;

@Controller
public class LoginController extends BaseAction {

	@Autowired
	private LoginService ls;

	/**
	 * @author Edwin
	 * @Date 2015-08-20
	 * @param Request
	 * @return Response
	 * @description 主页跳转
	 */
	@RequestMapping(value = "/jump/index")
	public ModelAndView jumpIndex(HttpServletRequest request,
			HttpSession session) {
		ModelAndView mav = new ModelAndView();
		Map<String, Object> snMap = (Map<String, Object>) session.getAttribute(USER_CONTEXT);
		mav.addObject("id", snMap.get("id").toString());
		mav.setViewName("/home/home");
		return mav;
	}

	/**
	 * @author Edwin
	 * @Date 2014-10-28
	 * @param Request
	 * @return Response
	 * @description 用户信息验证
	 */
	@RequestMapping(value = "/login/checkuser", method = RequestMethod.POST, produces = "appliction/json;charset=UTF-8")
	public void systemLogin(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String loginname = request.getParameter("loginname");
		String password = request.getParameter("password");
		String state = "";

		Map<String, String> map = ls.checkUser(loginname, password);

		if (map == null) {
			state = "error";
			response.getWriter().print(state);
		} else {
			state = "success";
			HttpSession session = request.getSession(true);

			session.setAttribute(USER_CONTEXT, map);
			ServletContext context = request.getSession().getServletContext();
			context.setAttribute(request.getSession().getId(),
					request.getSession());
			response.setCharacterEncoding("UTF-8");
			response.getWriter().print(state);
		}
	}

	/**
	 * 
	 * @Description: 修改用户信息
	 * @Author:Jc-Li
	 * @param:
	 * @Date:2015-8-18
	 */
	@RequestMapping(value = "/userInfo/updateUserPage")
	public ModelAndView updateUserInfoPage(HttpServletRequest request) {
		ModelAndView view = new ModelAndView();
		String id = request.getParameter("id");
		User user = ls.getOneUser(id);
		view.addObject("user", user);
		view.setViewName("/user/updatePage");
		return view;
	}

	/**
	 * 
	 * @Description:显示用户信息
	 * @Author:Jc-Li
	 * @param:
	 * @Date:2015-8-18
	 */
	@RequestMapping(value = "/userInfo/showUserInfo")
	public ModelAndView showUserInfo(HttpServletRequest request) {
		ModelAndView view = new ModelAndView();
		String id = request.getParameter("id");
		User user = ls.getOneUser(id);
		view.addObject("user", user);
		view.setViewName("/user/showInfoPage");
		return view;
	}




	/**
	 * 
	 * @Description:注册页面
	 * @Author:Jc-Li
	 * @param:
	 * @Date:2015-8-18
	 */
	@RequestMapping(value = "/userInfo/addNewPage")
	public ModelAndView addNewUserPage() {
		ModelAndView view = new ModelAndView();
		view.setViewName("/user/addNewPage");
		return view;
	}



	/**
	 * 
	 * @Description:保存新用户
	 * @Author:Jc-Li
	 * @param:
	 * @Date:2015-8-18
	 */
	@RequestMapping(value = "/userInfo/saveNew")
	@ResponseBody
	public String saveNewUser(HttpServletRequest request  ,User user) {
		if (ls.saveUser(user)) {
			return "success";
		} else {
			return "error";
		}
	}

	/**
	 * 
	 * @Description:修改密码页面
	 * @Author:Jc-Li
	 * @param:
	 * @Date:2015-8-18
	 */
	@RequestMapping(value = "/userInfo/changePasswordPage")
	public ModelAndView changePasswordPage() {
		ModelAndView view = new ModelAndView();
		view.setViewName("/user/changePassword");
		return view;
	}

	/**
	 * 
	 * @Description:修改密码方法
	 * @Author:Jc-Li
	 * @param:
	 * @Date:2015-8-18
	 */
	@RequestMapping(value = "/userInfo/changeUser")
	@ResponseBody
	public String firstChangePassword(HttpServletRequest request) {
		String passWord = request.getParameter("password");
		String userName = "";
		Map<String, String> map = ls.checkUser(userName, passWord);
		if (!map.get("id").isEmpty()) {
			return "id";
		} else {
			return null;
		}
	}

	/**
	 * 
	 * @Description:设置新密码
	 * @Author:Jc-Li
	 * @param:
	 * @Date:2015-8-18
	 */
	@RequestMapping(value = "/userInfo/setNewPasswordPage")
	public ModelAndView setNewPasswordPage(HttpServletRequest request) {
		ModelAndView view = new ModelAndView();
		String id = request.getParameter("id");
		view.addObject("id", id);
		view.setViewName("/userInfo/setNewPassword");
		return view;
	}

	/**
	 * 
	 * @Description:充值密码
	 * @Author:Jc-Li
	 * @param:
	 * @Date:2015-8-18
	 */
	@RequestMapping(value = "/userInfo/setPassword")
	public String setPassword(HttpServletRequest request) {
		String id = request.getParameter("id");
		String newPassword = request.getParameter("newPassword");
		if (ls.changePassword(id, newPassword)) {
			return "success";
		} else {
			return "error";
		}

	}

	@RequestMapping(value = "/userInfo/forgetPassword")
	public ModelAndView forgetPassword() {
		ModelAndView view = new ModelAndView();
		view.setViewName("/user/forgetPassword");
		return view;
	}

	@RequestMapping(value = "/userInfo/changePassword")
	@ResponseBody
	public String changePassword(HttpServletRequest request) {
		String id = request.getParameter("id");
		String newPassword = request.getParameter("newPassword");
		if (ls.changePassword(id, newPassword)) {
			return "success";
		} else {
			return "error";
		}
	}


	/**
	 * @author Edwin
	 * @Date 2014-11-19
	 * @param none
	 * @return login Page
	 * @description 退出登录
	 */
	@RequestMapping(value = "/login/exitBye")
	public String exitBye(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.setAttribute(USER_CONTEXT, null);
		return "../../index";
	}

	@RequestMapping(value = "/userInfo/checkUsername")
	@ResponseBody
	public String checkUserExist(HttpServletRequest request) {
		String username = request.getParameter("username");
		String id = ls.getIdByUsername(username);
		if (id != null && !"".equals(id)) {
			return "error";
		} else {
			return "success";
		}
	}

	@RequestMapping(value = "/userInfo/login")
	public ModelAndView login() {
		ModelAndView view = new ModelAndView();
		view.setViewName("/user/login");
		return view;
	}

	// ---------------登陆用户名成功后主页4块ifram拼合 begin--------------------//
	@RequestMapping(value = "/login/head")
	public ModelAndView addHead() {
		ModelAndView mav = new ModelAndView("/home/top");
		return mav;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/login/left")
	public ModelAndView addLeftbar(HttpServletRequest request,HttpSession session) {
		ModelAndView mav = new ModelAndView("/home/leftbar");
		Map<String, String> map = (Map<String, String>) session.getAttribute(USER_CONTEXT);
		//根据当前登陆人的id和系统
		mav.addObject("tree", ls.getMenuTree(map.get("dept_id"),map.get("system_id"), request));
		return mav;
	}

	
	@RequestMapping(value = "/login/footer")
	public ModelAndView addFooter() {
		ModelAndView mav = new ModelAndView("/home/footer");
		return mav;
	}

	@RequestMapping(value = "/login/main")
	public ModelAndView addMain() {
		ModelAndView mav = new ModelAndView("/home/main");
		return mav;
	}

	// ---------------娑登陆用户名成功后主页4块ifram拼合 end--------------------//
	/**
	 * 
	 * @Description:发送短信验证码
	 * @Author:Jc-Li
	 * @param:
	 * @Date:2015-12-7
	 */
	@RequestMapping(value = "/user/getCode")
	@ResponseBody
	public String getCode(HttpServletRequest request) {
		String url = "http://gw.api.taobao.com/router/rest";
		String appkey = "23279775";
		String secret = "b7b13061a4fdd5299a98fd720cc43f1f";
		String tel = request.getParameter("tel");
		if (tel == null || tel.equals("")) {
			return null;
		}
		TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
		AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
		req.setExtend(UUID.randomUUID().toString().toUpperCase());
		req.setSmsType("normal");
		req.setSmsFreeSignName("注册验证");
		Double temp = Math.random() * 1000000;
		String tempCode = temp.toString().substring(0, 6);
		req.setSmsParamString("{\"code\":\":" + tempCode+ "\",\"product\":\"[多点互联公众号管理系统]\"}");
		req.setRecNum(tel);
		req.setSmsTemplateCode("SMS_2960363");
		AlibabaAliqinFcSmsNumSendResponse rsp;
		Boolean flag = false;
		try {
			rsp = client.execute(req);
			flag = rsp.isSuccess();
			System.out.println(rsp.getBody());
		} catch (ApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (flag) {
			return tempCode;
		} else {
			return null;
		}
	}

	/**
	 * 
	 * @author : T-rq
	 * @date : 2016-2-29下午2:39:23
	 * @param :
	 * @return :
	 * @description :找回密码
	 */

	@RequestMapping(value = "/password/ForgetPassword")
	public String ForgetPassword() {
		return "/user/ForgetPassword";
	}
	/**
	 * 
	 * @author : T-rq
	 * @date : 2016-2-29下午5:11:30
	 * @param :
	 * @return :
	 * @description :验证Email是否存在
	 */

	@RequestMapping(value = "/mail/queryMail")
	@ResponseBody
	public String queryMail(HttpServletRequest request){
		String mail = request.getParameter("mail");
		String id = ls.getIdByMail(mail);
		if (id != null && !"".equals(id)) {
			return "error";
		} else {
			return "success";
		}
	}

	@RequestMapping(value = "/psw/queryUserMail")
	@ResponseBody
	public String queryUserMail(HttpServletRequest request){
		String username = request.getParameter("username");
		String mail = request.getParameter("mail");
		String id = ls.getIdByUserMail(username,mail);
		String result = "{\"message\":\"error\"}";
		if(id != null && !"".equals(id)){		
			result= "{\"message\":\"success\",\"id\":\""+id+"\"}";
			return result;
		}else{
			return result;
		}
	}

	/**
	 * 
	 * @author : T-rq
	 * @date : 2016-3-1下午2:00:48
	 * @param :
	 * @return :
	 * @description :重置密码并更新数据库
	 */
	@RequestMapping(value = "/password/ResetPassword")
	public ModelAndView ResetPassword(String username,String id, HttpServletRequest request) {
		ModelAndView view = new ModelAndView("/user/Password");
		view.addObject("user", username);
		view.addObject("id", id);
		return view;
	}

	@RequestMapping(value = "/password/updatePassword")
	@ResponseBody
	public String updatePassword(HttpServletRequest request) {
		String id = request.getParameter("id");
		String password = request.getParameter("password");
		if(ls.UpdatePassword(id, password)){
			return "success";
		}else{
			return "error";
		}
	}

}
