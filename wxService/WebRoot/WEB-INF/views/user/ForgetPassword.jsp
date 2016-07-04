<!DOCTYPE html>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="hello" uri="http://org.springframework.web.tld"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<html lang="zh-CN">

<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<title>找回密码</title>

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

<script type="text/javascript">
var username_validate=false;
var validate=false;
       function SendButton(){
              var username  = $("#userName").val();
              var mail = $("#mail").val();
              if(username==""||username==null||mail==""||mail==null){
                 alert("注册名称或邮箱不能为空！！");
                 return false;
              }
              $.ajax({
                type : 'post',
		        dataType : 'json',
		        url : "<%=path%>/psw/queryUserMail",
		        data:{"username":username,"mail":mail} ,
				success: function (msg){
	  				if(msg.message=="error"){
	  					username_validate=true;
	  					alert("您输入信息你有误！请核实您的信息");
	  					$("#mail").attr("data-content","请认真核实你所填的信息是否正确！");
	  			 	}else{
	  			 	    window.location.href="<%=path%>/password/ResetPassword?username=" + username+"&id="+ msg.id;
	  			 		$("#mail").attr("data-content","邮箱已存在！请使用其他账号");
	  			 		$("#mail").focus();
		    		}
	   			 }
              
              });
       }
  function  closeWindow(){
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
	<form name="form" method="post" style="padding-left: 50px">
		<div class="form-group" >
			<label>UserName：</label> 
			<input type="text" name="userName" id="userName" class="form-control" placeholder="注册用户名" />
		</div>
			
		<div class="form-group">
			<label> mail：</label> 
			<input type="text" name="mail" id="mail" class="form-control" placeholder="注册邮箱 "/>
		</div>
	
		<input type="button" value="确定" class="btn btn-primary btn-lg" onclick="SendButton()" />
	    <input type="button" value="取消" class="btn btn-default btn-lg" onclick="closeWindow()" />
	</form>
</body>
</html>
