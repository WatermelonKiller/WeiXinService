<!DOCTYPE html>
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
String path = request.getContextPath();
%>
<html lang="zh-CN">
<head></head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Untitled Document</title>
<!-- 移动设备优先 -->
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"> 
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/parts/CSS/bootstrap-3.3.5-dist/css/bootstrap.css" > 

<script type='text/javascript' src='<%=path%>/parts/JS/jquery-1.4.4.js'></script>
<script src="${pageContext.request.contextPath}/parts/JS/common/leftTree/prototype.lite.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/parts/JS/common/leftTree/moo.fx.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/parts/JS/common/leftTree/moo.fx.pack.js" type="text/javascript"></script>

<body class="tree_bg" style="margin:5px 5px 5px 5px;">
	<table  class="tree_bg_line">
  <tr>
    <td class="tree_bg">
    <div id="container">
    
    <c:forEach items="${tree}" varStatus="status" var="rowp">
	<div class="tree_title tree_title0${status.index+1}"><A href="javascript:void(0)">${rowp.name}</a></div>
	<div class="content">
	<div class="sidebar">
      <!--左侧导航-->
		<ul>
		<c:forEach items="${rowp.soninfo}" varStatus="status" var="rows">
			<c:choose>
				<c:when test="${fn:contains(rows.link, 'http://')}">
				<c:set var="link" value="${rows.link}" />
				</c:when>
				<c:otherwise>
				<c:set var="link" value="${pageContext.request.contextPath}${rows.link}" />
				</c:otherwise>
			</c:choose>
			<c:choose>
				<c:when test="${fn:contains(rows.link, '?')}">
				<c:set var="argu" value="&parentlink=${rows.pId}&sonlink=${rows.id}" />
				</c:when>
				<c:otherwise>
				<c:set var="argu" value="?parentlink=${rows.pId}&sonlink=${rows.id}" />
				</c:otherwise>
			</c:choose>
      		<li><a href="${link}${argu}" target="mainArea" onclick="trans('${rows.id}')"><span>${rows.name}</span></a></li>
		</c:forEach>
		</ul>
	</div>
	</div>
	</c:forEach>
	</div>
	</td>
  </tr>
</table>
</body>
</html>

<script type="text/javascript">
var contents = document.getElementsByClassName('content');
var toggles = document.getElementsByClassName('tree_title');
var myAccordion = new fx.Accordion(
	toggles, contents, {opacity: true, duration: 400}
);
myAccordion.showThisHideOpen(contents[0]);
</script>