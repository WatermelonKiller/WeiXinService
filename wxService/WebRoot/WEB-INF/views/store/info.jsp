<!DOCTYPE html>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="hello" uri="http://org.springframework.web.tld"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html lang="zh-CN">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<title>添加门店</title>
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/parts/CSS/nine/style.css" > 
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/parts/CSS/nine/isotope.css" > 
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/parts/CSS/nine/boot.css" > 
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/parts/CSS/nine/font-awesome.css" > 
<script type='text/javascript' src='<%=path%>/parts/JS/jquery-1.4.4.js'></script>
<script type='text/javascript' src='<%=path%>/parts/JS/jweixin-1.0.0.js'></script>
<script type="text/javascript">
	function cancelAdd(){
		window.location.href="<%=path%>/store/storeList";
	}
</script>
<style type="text/css">
.hid{
	display:none;
}
.type{
	width:150px;
	display:inline;
}
#allmap {width:700px;height:400px;overflow: hidden;margin:0;font-family:"微软雅黑";}
#serviceInfo{
	width:50%;
}
#baseInfo{
	width:50%;
}
</style>
</head>
<body>
<div class="container">

<form action="<%=path%>/store/auditingStore" class="" id="storeForm" name="form" method="post">
	<input type="hidden" name="offset_type" value="1"/>
	<input type="hidden" name="sid" value="${store.sid}"/>
	<div id="map">
			<h3>门店信息</h3>
		 <div id="storeImg">
	 		<h4>门店图片</h4>
	 		<hr/>
	 		<c:forEach items="${photos}" var="p" varStatus="sta">
	 			<img src="${p.photo_url}" />
	 		</c:forEach>
	    </div> 
	</div>
	<div id="baseInfo">
		<div class="form-group">
		   <label for="exampleInputName2">门店名:${store.business_name}</label>
		</div>
		<div class="form-group">
		   <label for="exampleInputName2">分店名:${store.branch_name}</label>
		</div>
		<div class="form-group">
		   <label for="exampleInputName2">类目:${store.categories}</label>
		</div>
		<br/>
	</div>
	
	<div class="form-group">
	   <h4>服务信息</h4>
	   <hr/>
	</div>
			
	<div id="serviceInfo">
			<div class="form-group">
			   <label for="exampleInputName2">电话:${store.telephone}</label>
			</div>
			<div class="form-group">
			   <label for="exampleInputName2">人均价格:${store.avg_price}</label>
			</div>
			<div class="form-group">
			   <label for="exampleInputName2">营业时间:${store.open_time}</label>
			</div>
			<div class="form-group">
			   <label for="exampleInputName2">推荐:${store.recommend}</label>
			</div>
			<div class="form-group">
			   <label for="exampleInputName2">特色服务:${store.special}</label>
			</div>
			<!-- 
			<div class="form-group">
			   <label for="exampleInputName2">门店签名</label>
			   <input type="text" class="form-control" name="signature" id="signature" placeholder="在“附近的人”展示，不超过6个字，如上方截图示例中的“满99送咖啡”字样。">
			</div> 
			-->
	</div>
	<div class="form-group" style="text-align:center">
		<input type="button" class="btn  btn-warning" onclick="cancelAdd()" value="返回"/>
	</div>
</form>
</div>
</body>
</html>
