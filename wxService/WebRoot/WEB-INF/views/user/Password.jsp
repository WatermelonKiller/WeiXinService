<!DOCTYPE html>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="hello" uri="http://org.springframework.web.tld"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html lang="en">
<head>

<title>My JSP 'Password.jsp' starting page</title>


<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">

<link type="text/css" rel="stylesheet"
	href="<%=request.getContextPath()%>/parts/CSS/bootstrap-3.3.5-dist/css/bootstrap.css">

<script charset="utf-8" src="<%=path%>/parts/JS/jquery-1.4.4.js"></script>
<script charset="utf-8" src="<%=path%>/parts/JS/artDialog/artDialog.js"></script>
<script charset="utf-8"
	src="<%=path%>/parts/JS/artDialog/customDialog.js"></script>
<script charset="utf-8"
	src="<%=path%>/parts/JS/artDialog/jquery.artDialog.js?skin=black"></script>
<script charset="utf-8"
	src="<%=path%>/parts/JS/artDialog/artDialog.iframeTools.js"></script>
<script type='text/javascript' src='<%=path%>/parts/JS/jweixin-1.0.0.js'></script>
<script charset="utf-8" src="<%=path%>/parts/JS/carousel.js"></script>
<script charset="utf-8" src="<%=path%>/parts/JS/holder.js"></script>
<script type="text/javascript">
   var validate=false;
   function SendButton(){
        var username = $("#username").val();
        var password = $("#pass").val();
        var password2 = $("#pass2").val();
        var id = $("#id").val();
        if(password == "" || password == null || password != password2){
            $.imDialog.alert("两次输入密码不一致！",function(){
						$("#pass").val("");
						$("#pass2").val("");
						$("#pass").focus();
					},"300","100","");
			  
			
			return false;
        }
        if(password.length<6 || password.length>20){
            $.imDialog.alert("密码长度请大于6位并小于20位！",function(){
						$("#pass").val("");
						$("#pass2").val("");
						$("#pass").focus();
					},"300","100","");
			return false;
        }
        $.ajax({
		        type : 'post',
		        dataType : 'text',
		        url : "<%=path%>/password/updatePassword",
			    data : {"id":id,"password" : password},
      			success : function(msg) {
				if (msg == "success") {
				   $.imDialog.alert("找回成功！",function(){
   					
   					window.parent.location.href="<%=path%>";
   						},"300","100","");
				} else {
				   $.imDialog.alert("找回失败！",function(){
   					
   						},"300","100","");
				}
			}
		});

	}

	function closeWindow() {
		$.imDialog.close();
	}
</script>
<style type="text/css">
.form-control{
   width: 50%;
}
</style>
</head>

<body>
	<form style="padding-left: 50px" >

		<input type="hidden" name="id" id="id" value="${id}" />
		<p>用户名：</p>
		<input type="text" name="username" id="username" class="form-control" value="${user}" placeholder="注册用户名" />

		<p>新密码：</p>
		<input type="password" name="pass" id="pass" value="" class="form-control" placeholder="新密码 " />

		<p>确认密码：</p>
		<input type="password" name="pass2" id="pass2" class="form-control" placeholder="确认密码 " /> 
		</br>
		<input type="button" value="确定" class="btn btn-primary btn-lg" onclick="SendButton();" />
		<input type="button" value="取消" class="btn btn-default btn-lg" onclick="closeWindow();" />
	</form>
</body>
</html>
