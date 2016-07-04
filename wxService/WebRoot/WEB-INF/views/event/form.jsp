<!DOCTYPE html>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="hello" uri="http://org.springframework.web.tld"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html lang="zh-CN">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<title>添加事件</title>
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/parts/CSS/nine/style.css" > 
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/parts/CSS/nine/isotope.css" > 
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/parts/CSS/nine/boot.css" > 
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/parts/CSS/nine/font-awesome.css" > 
<script type='text/javascript' src='<%=path%>/parts/JS/jquery-1.4.4.js'></script>
<script type='text/javascript' src='<%=path%>/parts/JS/jquery-migrate.min.js'></script>
<script type='text/javascript' src='<%=path%>/parts/JS//minify-b1-modernizr-f3c35af30bf577ef89016166ec467ebe.js'></script>
<script type='text/javascript' src='<%=path%>/parts/JS/jweixin-1.0.0.js'></script>
<style>
#menu{
width:30%;
height:500px;
float:left;
}
#right{
width:69%;
float:right;
height:500px;
}
#right_common{
margin-left:5px;
width:69%;
float:right;
height:500px;
}
.hid{
	display: none;
}
.title_h3{
	background-color: #ccc;
}
.right_title_h3{
	background-color: #ccc;
}
</style>
<script>
var win = art.dialog.open.origin; //来源页面
function saveEvent(){
	var formData=$("form").serialize();
	$.ajax({
		  type: "POST",
		   url: "<%=path%>/weixin/saveEvent",
		   processData:true,
		   data:formData,
	  		success: function(msg){
	  			if(msg=="success"){
		    		$.imDialog.alert("成功添加事件！",function(){
		    		win.doindex();
		    			},"300","100","");
		    	}else{
		    		$.imDialog.alert("添加失败！",function(){
					},"300","100","");
		    	}
	  		}
		  })
}
</script>
</head>
<body>
<form name="form" action="" method="POST">
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="MainBody_table">
<tr>
	<th width="25%">事件名称</th>
	<td width="25%"><input name="event_name" id="event_name" type="text"/></td>
	<th width="25%">事件类型</th>
	<td width="25%">
		<select name="event_type" id="event_type">
			<option value="com">普通事件</option>
			<option value="biz">业务事件</option>
		</select>
	</td>
</tr>
<tr>
	<th>重定向地址</th>
	<td colspan="3"><textarea name="request_mapping" id="request_mapping" cols="30" row="5"></textarea></td>
</tr>
<tr>
	<th>描述</th>
	<td colspan="3"><textarea name="description" id="description" cols="30" row="3"></textarea></td>
</tr>
</table>
<div style="text-align:center">
<input type="button" value="保存" onclick="saveEvent()"/>
</div>
</form>
</body>
</html>	