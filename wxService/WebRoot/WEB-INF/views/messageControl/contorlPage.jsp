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
<title>消息管理</title>
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
<div id="main">
<div id="tablebox"class="tablebox">
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="MainBody_table">
<tr>
	<th>发送人</th>
	<th>发送时间</th>
	<th>消息类型</th>
	<th>操作</th>
</tr>
<c:forEach items="${list}" var="ls" varStatus="sta">
<tr>
	<td width="25%">${ls.from_user_name}</td>
	<td width="15%">${ls.create_time}</td>
	<td  width="15%">${ls.msg_type}</td>
	<td width="15%">
		<input type="button" value="查看详情" onclick="detailMessage('${ls.id}')"/>
		<input type="button" value="删除" onclick="delMessage('${ls.id}')"/>
	</td>
</tr>
</c:forEach>
<c:if test="${empty list}">
	<tr><td colspan="10">暂无数据</td></tr>
</c:if>
</table>
</div>
<c:if test="${ !empty list}">
<div class="page">
	<hello:AllPage/>
</div>
</c:if>
</div>
</body>
</html>	