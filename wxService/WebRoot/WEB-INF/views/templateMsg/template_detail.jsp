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
<title>模板设定</title>
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
</head>
<body>
<form name="form" action="" method="POST">
<input type="hidden" name="count" id="count" value="1"/>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="MainBody_table" id="mdinTable">
<tr>
	<th >模板ID</th>
	<td>${template.template_id}</td>
	<th >模板名称</th>
	<td >${template.template_name}</td>
	<th>顶部颜色</th>
	<td>${template.top_color}</td>
</tr>
<tr>
	<th>跳转地址</th>
	<td colspan="3">${template.template_url}</td>
	<th>备注</th>
	<td>${template.remark}</td>
</tr>
</table>
<table id="parameterTable" class="MainBody_table">
<c:forEach items="${tempData}" var="td" varStatus="sta">
<tr class="parameter">
	<th >参数名</th>
	<td >${td.data_name}</td>
	<th >显示颜色</th>
	<td><span style="color:${td.color}">${td.color}</span></td>
	<th>参数顺序</th>
	<td>${td.sequence}</td>
</tr>
</c:forEach>
</table>
<div style="text-align:center">
<input type="button" value="关闭" onclick="javascript:window.close()"/>
</div>
</form>
</body>
</html>	