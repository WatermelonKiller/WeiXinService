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
<title>事件管理</title>
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
function addForm(){
	$.imDialog.open("<%=path%>/weixin/eventForm",
	          "添加新事件", "900", "300","");
}

function delEvent(id){
	if(confirm("是否删除此事件？")){
		$.ajax({
			  type: "POST",
			   url: "<%=path%>/weixin/delEvent",
			   processData:true,
			   data:{"id":id},
		  		success: function(msg){
		  			if(msg=="success"){
			    		win.doindex();
			    	}else{
			    		$.imDialog.alert("添加失败！",function(){
						},"300","100","");
			    	}
		  		}
			  })
	}
}
</script>
</head>
<body>
<div id="main">
<form name="form" method="post" action="${ctx}/weixin/eventList">
<input type="button" class="btn3" value="添加新事件" onclick="addForm()"/>
<div id="tablebox"class="tablebox">
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="MainBody_table">
<tr>
	<th>事件名称</th>
	<th>事件KEY</th>
	<th>事件类型</th>
	<th>重定向地址</th>
	<th>描述</th>
	<th>创建时间</th>
	<th>创建人</th>
	<th>点击数</th>
	<th>是否已发布</th>
	<th>操作</th>
</tr>
<c:forEach items="${list}" var="e" varStatus="sta">
<tr>
	<td>${e.event_name}</td>
	<td>${e.event_key}</td>
	<td>
		<c:if test="${e.event_type eq 'com'}">
		普通事件
		</c:if>
		<c:if test="${e.event_type eq 'biz'}">
		业务事件
		</c:if>
	</td>
	<td>${e.request_mapping}</td>
	<td>${e.description}</td>
	<td>${e.create_time}</td>
	<td>${e.create_user}</td>
	<td>${e.click_num}</td>
	<td>
		<c:if test="${e.released eq 'Y'}">是</c:if>
		<c:if test="${e.released eq 'N'}">否</c:if>
	</td>
	<td>
		<c:if test="${e.released eq 'N'}"><input type="button" value="删除" onclick="delEvent('${e.id}')"/></c:if></td>
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
</form>
</div>
</body>
</html>	