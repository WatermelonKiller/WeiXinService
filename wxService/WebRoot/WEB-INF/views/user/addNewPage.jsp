<!DOCTYPE html>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%String path = request.getContextPath();
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="hello" uri="http://org.springframework.web.tld"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html lang="en">
<head>
<script charset="utf-8" src="<%=path%>/parts/JS/jquery-1.4.4.js"></script>
<!--   bootstrap -->
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/parts/CSS/main.css" > 
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/parts/CSS/bootstrap-3.3.5-dist/css/bootstrap.css" >

<script charset="utf-8"
	src="<%=path%>/parts/bootstrap-3.3.5-dist/js/bootstrap.js"></script>
<script charset="utf-8" src="<%=path%>/parts/JS/carousel.js"></script>
<script charset="utf-8" src="<%=path%>/parts/JS/holder.js"></script>
<!-- artDialog -->
<!-- artDialog -->
<script charset="utf-8" src="<%=path%>/parts/JS/artDialog/artDialog.js"></script>
<script charset="utf-8"
	src="<%=path%>/parts/JS/artDialog/customDialog.js"></script>
<script charset="utf-8"
	src="<%=path%>/parts/JS/artDialog/jquery.artDialog.js?skin=black"></script>
<script charset="utf-8"
	src="<%=path%>/parts/JS/artDialog/artDialog.iframeTools.js"></script>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>注册账号</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">
<style type="text/css">
body {
	padding-top: 40px;
	padding-bottom: 40px;
	background-color: #f5f5f5;
}

.lb{
	width:80px;
	text-align:left;	
}
</style>
<link href="../assets/css/bootstrap-responsive.css" rel="stylesheet">


<link rel="apple-touch-icon-precomposed" sizes="144x144"
	href="../assets/ico/apple-touch-icon-144-precomposed.png">
<link rel="apple-touch-icon-precomposed" sizes="114x114"
	href="../assets/ico/apple-touch-icon-114-precomposed.png">
<link rel="apple-touch-icon-precomposed" sizes="72x72"
	href="../assets/ico/apple-touch-icon-72-precomposed.png">
<link rel="apple-touch-icon-precomposed"
	href="../assets/ico/apple-touch-icon-57-precomposed.png">
<link rel="shortcut icon" href="../assets/ico/favicon.png">
<script type="text/javascript">
$(document).ready(function(){
  $("#fatherSelect").hide();
  		$.ajax({
			dataType:"json",
			type:"post",
			url:"<%=path%>/menu/ajax/loadAllSystem",
			cache:false,
			async:false,
			data:{},
			success:function(data){
				if(data != undefined){
				var json = eval(data);
						$.each(json,function(i,o){
			     				$("#system_id").append("<option value='"+o.system_id+"'>"+o.system_name+"</option>");
						});  
					}		
			}
		});	
});

function loadAllDept(){
	$("#dept_id").empty();
	var sysId = $("#system_id option:selected").val();
	if(""!=sysId){
		$.ajax({
			dataType:"json",
			type:"post",
			url:"<%=path%>/menu/ajax/loadAllDept",
			cache:false,
			async:false,
			data:{
				"sysId" : sysId
			},
			success:function(data){
				if(data != undefined){
					var json = eval(data);
						$.each(json,function(i,o){
			     				$("#dept_id").append("<option value='"+o.dept_id+"'>"+o.dept_name+"</option>");
						});  
					}		
			}
		});	
	}else{
		$("#dept_id").empty();
	}
}

    var regCode="";
    var validate=false;
    var countdown=60; 
    var username_validate=false;
	function showTips(kj){
		$("#"+kj).popover('show');
	}
	
<%-- 	function getCode(){
		var regPhon=/^1\d{10}$/;
		var tel=$("#tel").val();
		if(!regPhon.test(tel)){
				$.imDialog.alert("手机号码格式有误，请重新输入！",function(){
							$("#phone").focus();
						},"300","100","");
				return false;
		}
		 $.ajax({
		        type : 'post',
		        dataType : 'text',
		        url : "<%=path%>/user/getCode",
		        data:{"tel":tel} ,
				success: function (msg){
	  				if(msg!=""){
	  					regCode=msg;
	  					runCodeTime();
	  			 	}else{
	  			 		$.imDialog.alert("获取验证码失败！",function(){
					},"300","100","");
		    		}
	   			 }
      		});
		
		
		
	}
	
	function runCodeTime(){
	var b=document.getElementById("getCodeButton");
		if (countdown == 0) { 
			b.removeAttribute("disabled");    
			b.value="获取验证码"; 
			countdown = 60;
			return false;
		} else { 
			b.setAttribute("disabled", true); 
			b.value="重新发送(" + countdown + ")"; 
			countdown--; 
		} 
		setTimeout(function() { runCodeTime();},1000); 
	}
	 --%>
	
	//验证用户名是否存在
	function checkUserName(){
		var username=$("#username").val();
		if(username==null||username==""){
			return false;
		}
		  $.ajax({
		        type : 'post',
		        dataType : 'text',
		        url : "<%=path%>/userInfo/checkUsername",
		        data:{"username":username} ,
				success: function (msg){
	  				if(msg=="success"){
	  					username_validate=true;
	  			 	}else{
	  			 	    alert("账号已存在！请使用其他账号");
	  			 		$("#username").val("");
		    		}
	   			 }
      		});
	}
	//验证邮箱号是否存在
	function checkMail(){
	    var mail =$("#mail").val();
	    if(mail == null || mail ==""){
	       alert("邮箱不能为空");
	       return false;
	    }	
	    
	    $.ajax({
	        type : 'post',
		        dataType : 'text',
		        url : "<%=path%>/mail/queryMail",
		        data:{"mail":mail} ,
				success: function (msg){
	  				if(msg=="success"){
	  					username_validate=true;
	  					$("#mail").attr("data-content","该邮箱未被注册！");
	  			 	}else{
	  			 	    alert("邮箱已存在！请使用其他邮箱");
	  			 		$("#mail").val("");
		    		}
	   			 }
	    
	    });
	
	}
	function addUser(){
		if(!username_validate){
			$("#username").attr("data-content","账号已存在！请使用其他账号");
 			$("#username").focus();
			return false;
		}
		var username=$("#username").val();
		var newPassword=$("#password").val();
		/* var cid=$("#cid").val();*/		
		
		var mail=$("#mail").val();
		var tel=$("#tel").val();
		var checkPassword=$("#checkPassword").val();
		var regPhon=/^1\d{10}$/; 
		var regCid=/^(\d{15}|\d{17}[\dx])$/;
		var regmail  = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;//正则表达式验证邮箱格式
		var regUsername=/^[a-zA-Z0-9_]{6,20}$/;
	/* 	if($("#code").val()==""){
			$.imDialog.alert("请验证手机！",function(){
					},"300","100","");
			return false;
		} */
		if(!regUsername.test(username)){
			$.imDialog.alert("用户名不符合规范！",function(){
						$("#username").attr("data-content","账号长度在6~12位之间，请不要包含标点符号");
						$("#username").focus();
					},"300","100","");
			return false;
		}
		if(newPassword==""||newPassword==null||newPassword!=checkPassword){
			$.imDialog.alert("两次输入密码不一致！",function(){
						$("#password").val("");
						$("#checkPassword").val("");
						$("#password").focus();
					},"300","100","");
			return false;
		}
		if(newPassword.length<6||newPassword.length>20){
			$.imDialog.alert("密码长度请大于6位并小于20位！",function(){
						$("#password").val("");
						$("#checkPassword").val("");
						$("#password").focus();
			},"300","100","");
			return false;
		}
		/* if(!regCid.test(cid)){
			$.imDialog.alert("身份证号有误，请重新输入！",function(){
						$("#cid").focus();
					},"300","100","");
			return false;
		} */
		if(!regmail.test(mail)){
			$.imDialog.alert("邮箱输入有误，请重新输入！",function(){
						$("#mail").focus();
					},"300","100","");
			return false;
		}
		
		if(!regPhon.test(tel)){
			$.imDialog.alert("手机号码格式有误，请重新输入！",function(){
						$("#tel").focus();
					},"300","100","");
			return false;
		}
		/* if($("#code").val()!=(regCode)){
					$.imDialog.alert("验证码错误！",function(){
								$("#code").focus();
							},"300","100","");
					return false;
				} */
	 	$.ajax({
		        type : 'post',
		        dataType : 'text',
		        url : "<%=path%>/userInfo/saveNew",
		        data : $('#addUserForm').serialize(),
				success: function (msg){
   				if(msg=="success"){
   					$.imDialog.alert("注册成功！",function(){
   					
   					window.parent.location.href="<%=path%>";
   						},"300","100","");
   			 	}else{
   			 		$.imDialog.alert("注册失败！",function(){
						},"300","100","");
			    	}
		   			 }
      		});
	}
    </script>
    <style type="text/css">
    .text{
       width: 25%;
       margin: 0 auto;
       text-align: left;
    }
    .container{
       width:100%;
       margin-top:-30px;
    }
    .select.input-lg{
        height: 30px;
    }
    .input-lg{
      font-size: 12px
    }
    </style>
</head>

<body>

	<div class="container" style="width:100%;margin-top:-30px;text-align:center;">
      <div class="text" >
			<form class="form-inline" name="addUserForm" id="addUserForm">
				<h2>注册账号</h2>
				
				<div class="form-group">
				<label  class="lb">用户名&nbsp;&nbsp;&nbsp;</label> 
				<input type="text"
					class="form-control" onfocus="showTips('username')"
					onblur="checkUserName()" data-toggle="popover" data-trigger="focus"
					data-content="账号长度在6~12位之间，请不要包含标点符号" placeholder="用户名"
					name="username" id="username">
					</div><br/><br/>
				
				<div class="form-group">
				<label class="lb">密码&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label> 
				<input type="password"
					class="form-control" placeholder="密码" name="password"
					id="password" onfocus="showTips('password')" data-toggle="popover"
					data-trigger="focus" data-content="密码长度在6~20位之间，建议使用复杂密码"/> 
					</div><br/><br/>
				
				<div class="form-group">
				<label  class="lb">确认密码</label>
				 <input type="password"
					class="form-control" placeholder="确认密码" name="checkPassword"
					id="checkPassword" onfocus="showTips('checkPassword')"
					data-toggle="popover" data-trigger="focus" data-content="两次密码输入需一致">
					</div><br/><br/>
					
				<!-- <div class="form-group">
				<label  class="lb">身份证号</label> 
				<input type="text"
					class="form-control" placeholder="身份证号" name="cid" id="cid"
					onfocus="showTips('cid')" data-toggle="popover" data-trigger="focus"
					data-content="请正确填写身份证号">
					</div><br/><br/> -->
					
				<div class="form-group">
					<label>可使用系统&nbsp;&nbsp;&nbsp;</label> 
					<select id="system_id" name="system_id" class="form-control input-lg" onchange="loadAllDept();">
						<option value="">请选择</option>
					</select>
				</div><br/><br/>
				<div class="form-group">
					<label>可使用部门&nbsp;&nbsp;&nbsp;</label> <select id="dept_id" name="dept_id" class="form-control input-lg">
					<option value="00000000-0000-0000-0000-000000000000" id="dept_all">全部门</option>
					</select>
				</div><br/><br/>
				<div class="form-group">
					<label class="lb">邮箱&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label> 
					<input type="text"
						class="form-control" onfocus="showTips('mail')"
						data-toggle="popover" data-trigger="focus" onblur="checkMail()"
						data-content="邮箱格式不正确" placeholder="邮箱"
						name="mail" id="mail">
				</div><br/><br/>
					
				<div class="form-group">
					<label class="lb">手机号&nbsp;&nbsp;&nbsp;</label>
					<input type="text" class="form-control" placeholder="手机号" name="tel" id="tel" onfocus="showTips('tel')" data-toggle="popover" data-trigger="focus"
						data-content="请正确填写手机号码"> 
				</div><br/><br/>
				
				<!-- <div class="form-group">
					<label class="lb">验证码&nbsp;&nbsp;&nbsp;</label>
					<input type="text" class="form-control" name="code" id="code" value="" style="width:100px;">
					&nbsp;&nbsp;&nbsp;
					<input  class="btn btn-default" type="button" id="getCodeButton" onclick="getCode()" value="获取验证码"/>
			    </div><br/><br/> -->
				
				<input  class="btn btn-primary" type="button" onclick="addUser()" value="注册"/>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			    <input class="btn btn-default" type="button" name="back" value="取消注册" onclick="javascript:history.back(-1);"/>
			</form>
		</div>
	</div>
	<!-- /container -->

</body>
</html>
