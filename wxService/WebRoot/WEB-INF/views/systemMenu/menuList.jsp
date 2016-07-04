<!DOCTYPE html>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%String path = request.getContextPath();%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="hello" uri="http://org.springframework.web.tld"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html lang="zh-CN">
<title>系统栏目</title>

<!-- 移动设备优先 -->
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"> 

<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/parts/CSS/bootstrap-3.3.5-dist/css/bootstrap.css" >


<script charset="utf-8" src="<%=path%>/parts/JS/jquery-1.4.4.js"></script>
<script charset="utf-8" src="<%=path%>/parts/JS/artDialog/artDialog.js"></script>
<script charset="utf-8" src="<%=path%>/parts/JS/artDialog/customDialog.js"></script>
<script charset="utf-8" src="<%=path%>/parts/JS/artDialog/jquery.artDialog.js?skin=black"></script>
<script charset="utf-8" src="<%=path%>/parts/JS/artDialog/artDialog.iframeTools.js"></script>

<script type='text/javascript' src='<%=path%>/parts/JS/jweixin-1.0.0.js'></script>

<script>
//添加菜单
function addMenu(){
	$.imDialog.open("<%=path%>/menu/addMenu", "添加菜单", "768", "400","");
}

//修改菜单
function updMenu(id){
	$.imDialog.open("<%=path%>/menu/updPage?cid="+id, "修改菜单", "500", "400","");
}

//删除按钮事件
function delMenu(id,type){
	if(confirm("是否确定删除此按钮？")){
		$.ajax({
			  type: "POST",
			   url: "<%=path%>/menu/delMenuEvent",
			   processData:true,
			   dataType: "json",
			   data:{
			   		"id":id,
			   		"type":type
			   },
		  		success: function(result){
		  			if(result.message=="success"){
		  			 $.imDialog.alert("删除菜单成功",function(){ 
		  			 	window.location.reload();
						},"300","100","");
			    	}else if(result.message=="isF"){
			    	   $.imDialog.alert("存在未删除的子菜单，请优先删除子菜单后再进行父菜单删除！",function(){
			    	   		window.location.reload();
						},"300","100","");
			    	}else{
			    		$.imDialog.alert("删除失败！",function(){
			    			window.location.reload();
						},"300","100","");
			    	}
		  		}
			  });
	}
}
</script>
</head>
<body>

<div id="main" class="container-fluid;"  style="margin:5px 5px 5px 5px;">

<form name="form" method="post" action="${ctx}/weixin/eventList">

<input type="button" class="btn btn-primary"  value="添加按钮" onclick="addMenu();"/>

<table class="table table-bordered table-hover table-striped ">
<tr>
	<th>按钮名称</th>
	<th>按钮URL</th>
	<th>系统ID</th>
	<th>排序</th>
	<th>类别</th>
	<th>标识</th>
	<th>操作</th>
</tr>
<c:forEach items="${list}" var="list" varStatus="sta">
<tr>
	<td>${list.node_name}</td>
	<td>${list.link}</td>
	<td>${list.system_id}</td>
	<td>${list.sequence}</td>
	<td>${list.type}</td>
	<td>${list.flag}</td>
	<td>
	<input type="button" class="btn btn-info"  value=" 修改"  onclick="updMenu('${list.column_id}')"/>
	<input type="button"  class="btn btn-danger"  value=" 删除"  onclick="delMenu('${list.column_id}','${list.type}');"/>
	</td>
</tr>
</c:forEach>
<c:if test="${empty list}">
	<tr><td colspan="10">暂无数据</td></tr>
</c:if>
</table>

<c:if test="${ !empty list}">
<div class="page">
	<hello:AllPage/>
</div>
</c:if>
</form>
</div>
</body>
</html>	