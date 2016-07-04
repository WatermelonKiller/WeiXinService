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
<title>门店管理</title>
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/parts/CSS/nine/style.css" > 
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/parts/CSS/nine/isotope.css" > 
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/parts/CSS/nine/boot.css" > 
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/parts/CSS/nine/font-awesome.css" > 
<script type='text/javascript' src='<%=path%>/parts/JS/jquery-1.4.4.js'></script>
<script type='text/javascript' src='<%=path%>/parts/JS/jquery-migrate.min.js'></script>
<script type='text/javascript' src='<%=path%>/parts/JS//minify-b1-modernizr-f3c35af30bf577ef89016166ec467ebe.js'></script>
<script type='text/javascript' src='<%=path%>/parts/JS/jweixin-1.0.0.js'></script>
<script>
function addForm(){
	window.location.href="<%=path%>/store/addForm";
}

function refreshTypes(){
	$.ajax({
		  type: "GET",
		   url: "<%=path%>/store/refreshStoreType",
		   processData:true,
	  		success: function(msg){
	  			if(msg=="success"){
		    		alert("更新成功");
		    	}else{
					alert("更新失败");
		    	}
	  		}
		  });
}
function refreshStore(){
	if(confirm("是否从服务器同步门店信息，这将会删除本地记录！")){
		$.ajax({
		  type: "GET",
		   url: "<%=path%>/store/refreshStore",
		   processData:true,
	  		success: function(msg){
	  			if(msg=="success"){
		    		alert("同步成功");
		    	}else{
					alert("同步失败");
		    	}
	  		}
		  });
	}
}
function delStore(sid,poi_id){
	if(confirm("是否从服务器删除门店信息，删除后将不能恢复！")){
		$.ajax({
		  type: "POST",
		   url: "<%=path%>/store/delStore",
		   data:{"sid":sid,"poi_id":poi_id},
	  		success: function(msg){
	  			if(msg=="success"){
		    		alert("删除成功");
		    	}else{
					alert("删除失败");
		    	}
	  		}
		  });
	}
}
function updateStore(sid){
	window.location.href="<%=path%>/store/updatePage?sid="+sid;
}
function showDetails(sid){
	window.location.href="<%=path%>/store/info?sid="+sid;
}
</script>
</head>

<body>
<div id="main">
<input type="button" class="btn btn-primary" value="新建门店" onclick="addForm()"/>
<input type="button" class="btn" value="更新门店类目" onclick="refreshTypes()"/>
<input type="button" class="btn btn-warning" value="同步门店" onclick="refreshStore()">
<form name="form" method="post" action="${ctx}/weixin/eventList">
<table class="table table-hover">
<tr>
	<th width="30%">门店名称</th>
	<th width="42%">门店地址</th>
	<th width="11%"><select name="available_state" class="form-control"><c:if test="${available_state eq ''}">selected="selected"</c:if>
		<option value="">全部</option>
		<option value="2" <c:if test="${available_state eq ''}">selected="selected"</c:if>>审核中</option>
		<option value="3" <c:if test="${available_state eq ''}">selected="selected"</c:if>>生效</option>
		<option value="4" <c:if test="${available_state eq ''}">selected="selected"</c:if>>失败</option>
		</select>
	</th>
	<th width="17%">操作</th>
</tr>
<c:forEach items="${list}" var="ls" varStatus="sta">
<tr>
	<td>${ls.business_name}<c:if test="${!empty ls.branch_name}">（${ls.branch_name}）</c:if></td>
	<td>${ls.province}${ls.city}${ls.district}${ls.address}</td>
	<td>
		<c:if test="${ls.available_state eq 1}"><span style="color:red">系统错误</span></c:if>
		<c:if test="${ls.available_state eq 2}"><span style="color:orange">审核中</span></c:if>
		<c:if test="${ls.available_state eq 3}"><span style="color:green">审核通过</span></c:if>
		<c:if test="${ls.available_state eq 4}"><span style="color:red">审核驳回</span></c:if>
	</td>
	<td><input type="button" onclick="showDetails('${ls.sid}')"  class="btn btn-info"value="详情"/>
	<input type="button" onclick="delStore('${ls.sid}','${ls.poi_id}')" class="btn btn-danger" value="删除"/>
	<input type="button" onclick="updateStore('${ls.sid}')" value="修改" class="btn btn-warning"/>
	</td>
</tr>
</c:forEach>
</table>
<c:if test="${!empty list}">
<div class="page">
	<hello:AllPage/>
</div>
</c:if>
</form>
</div>
</body>
</html>	