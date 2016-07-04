<!DOCTYPE html>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%String path = request.getContextPath();%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="hello" uri="http://org.springframework.web.tld"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html lang="zh-CN">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<title>修改系统栏目</title>

<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/parts/CSS/bootstrap-3.3.5-dist/css/bootstrap.css" >

<script charset="utf-8" src="<%=path%>/parts/JS/jquery-1.4.4.js"></script>
<script charset="utf-8" src="<%=path%>/parts/JS/artDialog/artDialog.js"></script>
<script charset="utf-8" src="<%=path%>/parts/JS/artDialog/customDialog.js"></script>
<script charset="utf-8" src="<%=path%>/parts/JS/artDialog/jquery.artDialog.js?skin=black"></script>
<script charset="utf-8" src="<%=path%>/parts/JS/artDialog/artDialog.iframeTools.js"></script>
<script type='text/javascript' src='<%=path%>/parts/JS/jweixin-1.0.0.js'></script> 
<script>
var system_id = $(map.system_id);

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

function closeWindow(){ 
	$.imDialog.close();
} 
 
function updMenu(){
//页面验证，必须选择所属级别；
	if(""==$("#system_id option:selected").val()){
		alert("请选择可控权限部门");
		return false;
	}else{
		var formData=$("form").serialize();
		$.ajax({
			  type: "POST",
			   url: "<%=path%>/menu/updEvent",
			   processData:true,
			   data:formData,
		  		success: function(result){
		  			if(result.message=="success"){
			    		$.imDialog.alert("修改菜单成功！",function(){
			    			},"300","100","");
			    		window.parent.location.reload();
			    	}else{
			    		$.imDialog.alert("修改菜单失败！",function(){
						},"300","100","");
			    	}
		  		}
			  })
	}
}

function showFather(flag){
  if("F"==flag){
  	$("#fatherSelect").show();;	
  }else{
  	$("#fatherSelect").hide();
  }
}

</script>
</head>
<body>
<form action="<%=path%>/menu/ajax/createLeftMenu"  name="form" method="post">

<input type="hidden" value="${map.column_id}" id="column_id" name="column_id" />

	请输入按钮名称：<input type="text" name="node_name" id="node_name"  value="${map.node_name}" />
	<br/>
    按钮url：<input type="text" name="link" id="link"  value="${map.link}"/>
	<br/>
	 排序：<input type="text" name="sequence" id="sequence"  value="${map.sequence}"/>
	<br/>
	是否为父级菜单：
	<input  type="radio"  name="type" id="type"  value="J"  onclick="showFather('T');" ${map.type eq 'J' ? 'checked':''}/>是 
	<input  type="radio"  name="type" id="type"  value="B"  onclick="showFather('F')" ${map.type eq 'B' ? 'checked':''}/>否
	<br/>
	<div id="fatherSelect">
		请选择所属父菜单：<select id="parent_id" name="parent_id"> 
			<c:forEach var="pList"  items="${parList}" varStatus="status">
				<option value="${pList.column_id}"  <c:if test="${pList.column_id eq map.parent_id}" >selected="selected"</c:if>>${pList.node_name}</option> 
	</c:forEach>
	                 </select>
	                 		<br/>
	</div>
	可控部门：<select id="system_id" name="system_id"> 
	<c:forEach var="list"  items="${sysList}" varStatus="status">
				<option value="${list.system_id}"  <c:if test="${list.system_id eq map.system_id}" >selected="selected"</c:if>>${list.system_name}</option> 
	</c:forEach>
	                 </select>
	<br/>
	<input type="button" value="确认" onclick="updMenu()"/>
	<input type="button" value="取消" onclick="closeWindow()"/>
</form>
</body>
</html>	