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
<title>添加按钮</title>
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/parts/CSS/nine/style.css" > 
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/parts/CSS/nine/isotope.css" > 
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/parts/CSS/nine/boot.css" > 
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/parts/CSS/nine/font-awesome.css" > 
<script type='text/javascript' src='<%=path%>/parts/JS/jquery-1.4.4.js'></script>
<script type='text/javascript' src='<%=path%>/parts/JS/jquery-migrate.min.js'></script>
<script type='text/javascript' src='<%=path%>/parts/JS//minify-b1-modernizr-f3c35af30bf577ef89016166ec467ebe.js'></script>
<script type='text/javascript' src='<%=path%>/parts/JS/jweixin-1.0.0.js'></script>
<script>
function closeWindow(){
	$.imDialog.close();
} 
function saveButton(){
	 var formData=$("form").serialize();
		$.ajax({
			  type: "POST",
			   url: "<%=path%>/weixin/doAddButton",
			   processData:true,
			   data:formData,
		  		success: function(msg){
		  			if(msg=="success"){
			    		$.imDialog.alert("成功添加菜单！",function(){
			    			},"300","100","");
			    		window.parent.location.reload();
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
<form action="<%=path%>/weixin/doAddButton"  name="form" method="post">
	<c:if test="${last_num eq '0'}">
		添加数量已经达到上限！
	</c:if>
	<c:if test="${last_num ne '0'}">
	<input type="hidden" name="father_id" value="${father_id}"/>
	<input type="hidden" name="sequence" value="${sequence}"/>
	还能添加${last_num}个
	<c:if test="${father_id eq '00000000-0000-0000-0000-000000000000'}">一级</c:if>
	<c:if test="${father_id ne '00000000-0000-0000-0000-000000000000'}">二级</c:if>
	菜单，请输入名称
	（<c:if test="${father_id eq '00000000-0000-0000-0000-000000000000'}">4个汉字或8个字母以内</c:if>
	<c:if test="${father_id ne '00000000-0000-0000-0000-000000000000'}">8个汉字或16个字母以内</c:if>）
	<br/>
	<input type="text" name="name" id="name" />
	<br/>
	<input type="button" value="确认" onclick="saveButton()"/>
	</c:if>
	<input type="button" value="取消" onclick="closeWindow()"/>
</form>
</body>
</html>	