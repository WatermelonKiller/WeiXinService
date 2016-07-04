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
<title>关键字修改</title>
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
function saveKeyWord(){
	var formData=$("form").serialize();
	$.ajax({
		  type: "POST",
		   url: "<%=path%>/keyword/${act}",
		   processData:true,
		   data:formData,
	  		success: function(msg){
	  			if(msg=="success"){
		    		$.imDialog.alert("保存成功！",function(){
		    		win.doindex();
		    			},"300","100","");
		    	}else{
		    		$.imDialog.alert("保存失败！",function(){
					},"300","100","");
		    	}
	  		}
		  })
}
</script>
</head>
<body>
<form name="form" action="" method="POST">
<input type="hidden" name="id" value="${kw.id}"/>
<input type="hidden" name="create_time" value="${kw.create_time}"/>
<input type="hidden" name="create_user" value="${kw.create_user}"/>
<input type="hidden" name="click_num" value="${kw.click_num}"/>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="MainBody_table">
<tr>
	<th width="15%">关键字</th>
	<td width="15%"><input name="keyword" id="keyword" type="text" value="${kw.keyword}"/></td>
	<th width="15%">回复类型</th>
	<td width="15%">
		<select name="type" id="type">
			<option value="文字" <c:if test="${kw.type eq '文字'}">selected="selected"</c:if>>文字</option>
			<option value="模板消息" <c:if test="${kw.type eq '模板消息'}">selected="selected"</c:if>>模板消息</option>
			<option value="图文消息" <c:if test="${kw.type eq '图文消息'}">selected="selected"</c:if>>图文消息</option>
			<option value="其他" <c:if test="${kw.type eq '其他'}">selected="selected"</c:if>>其他</option>
		</select>
	</td>
	<th width="15%">状态</th>	
	<td width="15%">
		<select name="in_use" id="in_use">
			<option value="Y" <c:if test="${kw.in_use eq 'Y'}">selected="selected"</c:if>>使用中</option>
			<option value="N" <c:if test="${kw.in_use eq 'N'}">selected="selected"</c:if>>停用中</option>
		</select>
	</td>
</tr>
<tr>
	<th>重定向地址</th>
	<td colspan="5"><textarea name="request_mapping" id="request_mapping" cols="60" row="5">${kw.request_mapping}</textarea></td>
</tr>
<tr>
	<th>备注</th>
	<td colspan="5"><textarea name="remark" id="remark" cols="60" row="3">${kw.remark}</textarea></td>
</tr>
</table>
<div style="text-align:center">
<input type="button" value="提交" onclick="saveKeyWord()"/>
</div>
</form>
</body>
</html>	