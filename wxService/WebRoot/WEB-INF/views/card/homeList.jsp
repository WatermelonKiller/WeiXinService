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
<title>卡卷信息</title>
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
	$.imDialog.open("<%=path%>/keyword/form?act=save",
	          "添加新卡卷", "900", "300","");
}
function updKeyWord(id){
	$.imDialog.open("<%=path%>/keyword/form?act=upd&id="+id,
	          "修改卡卷", "900", "300","");
}
function delKeyWord(id){
	if(confirm("是否删除此卡卷？")){
		$.ajax({
			  type: "POST",
			   url: "<%=path%>/keyword/del",
			   processData:true,
			   data:{"id":id},
		  		success: function(msg){
		  			if(msg=="success"){
			    		window.doindex();
			    	}else{
			    		$.imDialog.alert("删除失败！",function(){
						},"300","100","");
			    	}
		  		}
			  })
	}
}
</script>
</head>
<body>
<form name="form" action="${ctx}/keyword/list">
<div id="main">
<input type="button" class="btn3" value="添加新卡卷" onclick="addForm()"/>
<div id="tablebox"class="tablebox">
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="MainBody_table">
<tr>
	<th>卡卷ID</th>
	<th>类型</th>
	<th>重定向地址</th>
	<th>使用情况</th>
	<th>创建时间</th>
	<th>创建人</th>
	<th>点击数</th>
	<th>备注</th>
	<th>操作</th>
</tr>
<c:forEach items="${list}" var="ls" varStatus="sta">
<tr>
	<td>${ls.keyword}</td>
	<td>${ls.type}</td>
	<td>${ls.request_mapping}</td>
	<td><c:if test="${ls.in_use eq 'Y'}">使用中</c:if><c:if test="${ls.in_use ne 'Y'}">停用中</c:if></td>
	<td>${ls.create_time}</td>
	<td>${ls.create_user}</td>
	<td>${ls.click_num}</td>
	<td>${ls.remark}</td>
	<td>
		<input type="button" value="修改" onclick="updKeyWord('${ls.id}')"/>
		<input type="button" value="删除" onclick="delKeyWord('${ls.id}')"/>
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
</form>
</body>
</html>	