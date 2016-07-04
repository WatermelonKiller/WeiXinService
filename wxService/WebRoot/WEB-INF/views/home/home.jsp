<!DOCTYPE html>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%String path = request.getContextPath();%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="hello" uri="http://org.springframework.web.tld"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html lang="zh-CN">
<head>
<title>多点互联微信服务系统</title>
<meta charset="utf-8">
<script type='text/javascript' src='<%=path%>/parts/JS/jquery-1.4.4.js'></script>
<!-- 移动设备优先 -->
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"> 
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/parts/CSS/bootstrap-3.3.5-dist/css/bootstrap.css" > 
</head>
<frameset  rows="96,*, 18, 0" border="0">
<frame name="topArea" src="<%=path %>/login/head" scrolling="NO" noresize frameborder="0" framespacing="0">
<frameset cols="180,0,*" framespacing="0" id=framesetBottom border=0> 
<frame name="leftArea" src="<%=path %>/login/left" noresize scrolling="no" id=DomLeftFrame />
<frame name="midframe" src="#<%-- <%=path %>/login/midframe --%>" noresize scrolling="no" /> 
<frame name="mainArea" src="<%=path %>/login/main" id="mainArea"/>
</frameset>

<frame name="btArea" src="<%=path %>/login/footer " noresize="yes" />    
<frame name="postArea" src="about:blank" noresize="yes"/>
</frameset>
<noframes> 
<body>
<p>此网页使用了框架，但您的浏览器不支持框架。</p>
</body>
</noframes>

</html>