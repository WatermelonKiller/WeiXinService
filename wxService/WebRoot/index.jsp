<!DOCTYPE html>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%String path = request.getContextPath();
%>
<html lang="en">
  <head>
<!-- 移动设备优先 -->
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"> 

<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/parts/CSS/main.css" > 
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/parts/CSS/bootstrap-3.3.5-dist/css/bootstrap.css" >

<script charset="utf-8" src="<%=path%>/parts/JS/jquery-1.4.4.js"></script>
<script charset="utf-8" src="<%=path%>/parts/bootstrap-3.2.0-dist/js/bootstrap.js"></script>

<script charset="utf-8" src="<%=path%>/parts/JS/artDialog/artDialog.js"></script>
<script charset="utf-8" src="<%=path%>/parts/JS/artDialog/customDialog.js"></script>
<script charset="utf-8" src="<%=path%>/parts/JS/artDialog/jquery.artDialog.js?skin=black"></script>
<script charset="utf-8" src="<%=path%>/parts/JS/artDialog/artDialog.iframeTools.js"></script>

<!-- 微信JS -->
<script charset="utf-8" src="<%=path%>/parts/JS/jweixin-1.0.0.js"></script>
<script charset="utf-8" src="<%=path%>/parts/JS/wxJSSDK.js"></script>


<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>用户登录</title>
<style type="text/css">
.container{
  padding-top: 50px;
}
.form-control{
    width: 20%;

}
</style>

<script type="text/javascript">
function ForgetPassword(){
    $.imDialog.open("<%=path%>/password/ForgetPassword", "找回密码", "550", "300","");
}
function signin(){
	var ln = $("#loginname").val();
	var pw =$("#password").val();
	if( ln==""  ||  pw == ""){
		alert("请输入完整的帐号和密码！");
		return false;
	}else{
				$.ajax({
				type: "POST",
				url: "<%=path%>/login/checkuser",
				 data: {"loginname":ln,"password":pw},
				   success: function(msg){
					   if("success"==msg){
						    $("#signinForm").submit();
					   }else{
						   alert("帐号或密码错误!");
						   window.location.reload();
					   }
				   }
			});
	}
}

function register(){
	window.location.href="<%=path%>/userInfo/addNewPage";
}
</script>

 </head>
 <body>

		<form class="form-horizontal" id="signinForm"   action="<%=path %>/jump/index" method="post">
			<div class="container">
			
				  <div class="form-group">
				    <label for="inputEmail3" class="col-sm-2 control-label">Username：</label>
				    <div class="col-sm-10">
				       <input type="text" class="form-control"  id="loginname" name="loginname" placeholder="User Name" required autofocus >
				    </div>
				  </div>
			  
				  <div class="form-group">
				    <label for="inputPassword3" class="col-sm-2 control-label">Password：</label>
				    <div class="col-sm-10">
				       <input type="password" class="form-control" id="password"  name="password" placeholder="Password" required>
				    </div>
				  </div>
				  
				  <div class="form-group">
				    <div class="col-sm-offset-2 col-sm-10">
				      <button type="button" class="btn btn-link" onclick="ForgetPassword();">忘记密码</button>
				    </div>
				  </div>
				  
			  <div class="form-group">
			    <div class="col-sm-offset-2 col-sm-10">
			    <button class="btn  btn-primary"  type="button"  onclick="signin();">登录 </button>
			    &nbsp;&nbsp;&nbsp;
			    <button class="btn btn-warning"  type="button"  onclick="register();">注册 </button>
			    </div>
			  </div>
			 </div>
		</form>
</body>
</html>
