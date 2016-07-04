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
<title>模板消息添加</title>
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
function saveTemplate(){
	 var formData=$("form").serialize();
		$.ajax({
			  type: "POST",
			   url: "<%=path%>/templateMsg/saveTemplate",
			   processData:true,
			   data:formData,
		  		success: function(msg){
		  			if(msg=="success"){
			    		$.imDialog.alert("成功添加模板！",function(){
			    			},"300","100","");
			    		window.parent.location.reload();
			    	}else{
			    		$.imDialog.alert("添加失败！",function(){
						},"300","100","");
			    	}
		  		}
			  })
}
function addLine(){
	var count=$("#count").val()*1+1;
	$("#count").val(count);
	$("#parameterTable").append("<tr id=\"parameter"+count+"\">"+
			"<th>参数名</th><td><input name=\"data_name\" id=\"data_name\" type=\"text\"/></td>"+
			"<th >显示颜色</th>"+
			"<td ><input name=\"color\" id=\"color\" type=\"text\"/></td>"+
			"<th>参数顺序</th>"+
			"<td><input name=\"sequence\" id=\"sequence\" type=\"text\" value=\""+count+"\"/></td>"+
			"<th><input type=\"button\" value=\"移除此行\" onclick=\"removeLine('"+count+"')\"></th></tr>");
}
function removeLine(seq){
	$("#parameter"+seq).remove();
	var count=$("#count").val()-1;
	$("#count").val(count);
}
</script>
</head>
<body>
<form name="form" action="" method="POST">
<input type="hidden" name="count" id="count" value="1"/>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="MainBody_table" id="mdinTable">
<tr>
	<th >模板ID</th>
	<td ><input name="template_id" id="template_id" type="text"/></td>
	<th >模板名称</th>
	<td ><input name="template_name" id="template_name" type="text"/></td>
	<th>顶部颜色</th>
	<td><input name="top_color" id="top_color" type="text"/></td>
</tr>
<tr>
	<th>跳转地址</th>
	<td colspan="3"><textarea name="template_url" id="template_url" cols="30" row="5"></textarea></td>
	<th>备注</th>
	<td><input type="text" name="remark" id="remark" /></td>
</tr>
</table>
<table id="parameterTable" class="MainBody_table">
<tr class="parameter1" id="parameter1">
	<th >参数名</th>
	<td ><input name="data_name" id="data_name" type="text"/></td>
	<th >显示颜色</th>
	<td ><input name="color" id="color" type="text"/></td>
	<th>参数顺序</th>
	<td><input name="sequence" id="sequence" type="text" value="1"/></td>
	<th><input type="button" value="移除此行" onclick="removeLine('1')"></th>
</tr>
</table>
<div style="text-align:center">
<input type="button" value="添加参数" onclick="addLine()">
<input type="button" value="保存" onclick="saveTemplate()"/>
</div>
</form>
</body>
</html>	