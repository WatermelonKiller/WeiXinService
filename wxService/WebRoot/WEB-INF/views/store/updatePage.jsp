<!DOCTYPE html>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="hello" uri="http://org.springframework.web.tld"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html lang="zh-CN">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<title>修改门店</title>
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/parts/CSS/nine/style.css" > 
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/parts/CSS/nine/isotope.css" > 
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/parts/CSS/nine/boot.css" > 
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/parts/CSS/nine/font-awesome.css" > 
<script type='text/javascript' src='<%=path%>/parts/JS/jquery-1.4.4.js'></script>
<script type='text/javascript' src='<%=path%>/parts/JS/jquery-migrate.min.js'></script>
<script type='text/javascript' src='<%=path%>/parts/JS//minify-b1-modernizr-f3c35af30bf577ef89016166ec467ebe.js'></script>
<script type='text/javascript' src='<%=path%>/parts/JS/jweixin-1.0.0.js'></script>
<script type="text/javascript" src="http://api.map.baidu.com/api?v2.0&ak=vp7OkjLDil4DUAFYxchkpETT"></script>
<script type="text/javascript">
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
<script>
function cancelAdd(){
		window.location.href="<%=path%>/store/storeList";
	}
function updStore(){
		$.ajax({
			        type : 'POST',
			        dataType : 'text',
			        url : "<%=path%>/store/updateStore",
			        data : $('#storeForm').serialize(),
					success: function (msg){
		   				if(msg=="success"){
		   					$.imDialog.alert("修改成功！",function(){
		   					window.location.href="<%=path%>/store/storeList";
		   						},"300","100","");
		   			 	}else{
		   			 		$.imDialog.alert("修改失败！",function(){
								},"300","100","");
					    	}
		   			 }
	      		});
	}
	
	function setImagePreview(avalue) {
		var docObj = document.getElementById("uploadfile");
		$("#localImag").empty();
		for(var i=0;i<docObj.files.length;i++){
			var imgObjPreview = document.getElementById("preview");
			if (docObj.files && docObj.files[i]) {
				$("#localImag").append("<img id='preview"+i+"' src='"+window.URL.createObjectURL(docObj.files[i])+"' width='150' height='150' style='display: inline; width: 150px; height: 150px;'/>");
				//火狐下，直接设img属性
			
				//imgObjPreview.src = docObj.files[0].getAsDataURL();
	
				//火狐7以上版本不能用上面的getAsDataURL()方式获取，需要一下方式
				//imgObjPreview.src = window.URL.createObjectURL(docObj.files[0]);
			} else {
			/*
				//IE下，使用滤镜
				docObj.select();
				var imgSrc = document.selection.createRange().text;
				var localImagId = document.getElementById("localImag");
				//必须设置初始大小
				var a=150*docObj.files.length;
				localImagId.style.width = a+"px";
				localImagId.style.height = "150px";
				//图片异常的捕捉，防止用户修改后缀来伪造图片
				try {
					localImagId.style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale)";
					localImagId.filters
							.item("DXImageTransform.Microsoft.AlphaImageLoader").src = imgSrc;
				} catch (e) {
					alert("您上传的图片格式不正确，请重新选择!");
					return false;
				}
				imgObjPreview.style.display = 'none';
				document.selection.empty();
				*/
			}
		}
		return true;
	}
	

	function callback(msg) {
		$("#complete").append("<h4 style='color:red'>上传成功</h4>");
		var json=JSON.parse(msg);
		$.each(json.res,function(i, item) {
			if(item.result=="succ"){
			}else{
				$("#complete").append("<span>第"+item.seq+"张图片上传失败，请重试！请勿重复上传已成功的图片</span>");
			}
        });
	}
</script>
</head>
<body>
<div class="container">
<div class="form-group">
	   <h3>${store.business_name}(${store.branch_name})</h3>
	   <hr/>
	</div>
	<div id="storeImgs">
 		<h4>门店图片</h4>
 		<hr/>
 		<c:forEach items="${photos}" var="p" varStatus="sta">
 			<img src="${p.photo_url}" />
 		</c:forEach>
    </div> 
<div id="storeImg">
 		<h5>添加图片</h5>
 		<hr/>
		 <span style="color:#8D8D8D">像素要求必须为640*340像素，支持.jpg .jpeg .bmp .png格式，大小不超过5M</span>
    	<form enctype="multipart/form-data" method="post" target="upload" action="<%=path%>/store/uploadStorePhoto?id=${sid}" > 
    	<div id="complete">
    	</div>
    	<div id="localImag">
    	</div>
    	<input type="button" value="选择文件" class="btn btn-primary" onclick="$('#uploadfile').click()"/>
		<input type="file" name="uploadfile" id="uploadfile" multiple="multiple" style="display:none"  onchange="javascript:setImagePreview();" />
		<input type="submit" value="上传" id="subBtn" class="btn btn-success"/>
		</form> 
		<iframe name="upload" style="display:none"></iframe>  
    </div> 
<form action="" class="" id="storeForm" name="form" >
	<input type="hidden" name="available_state" id="available_state" value="${store.available_state}"/>
	<input type="hidden" name="poi_id" id="poi_id" value="${store.poi_id}"/>
	<input type="hidden" name="province" id="province" value="${store.province}"/>
	<input type="hidden" name="city" id="city"  value="${store.city}"/>
	<input type="hidden" name="district" id="district" value="${store.district}"/>
	<input type="hidden" name="address" id="address" value="${store.address}"/>
	<input type="hidden" name="sid" value="${store.sid}"/>
	<input type="hidden" name="offset_type" value="1"/>
	<input type="hidden" value="${store.longitude}" name="longitude" id="longitude" />
	<input type="hidden"  name="latitude" id="latitude" value="${store.latitude}"/>
   <input type="hidden"  name="business_name" id="business_name" value="${store.business_name}" />
   <input type="hidden" name="branch_name" id="branch_name" value="${store.branch_name}" />
   <input type="hidden"  name="categories" id="categories"  value="${store.categories}"/>
	
	
    
	<div id="serviceInfo">
			<div class="form-group">
			   <label for="exampleInputName2">电话</label>
			   <input type="text" class="form-control" name="telephone" id="telephone"  value="${store.telephone}" placeholder="固定电话需加区号；区号、分机号均用“-”连接">
			</div>
			<div class="form-group">
			   <label for="exampleInputName2">人均价格</label>
			   <input type="text" class="form-control" name="avg_price" id="avg_price" value="${store.avg_price}" placeholder="大于零的整数，须如实填写，默认单位为人民币/元">
			</div>
			<div class="form-group">
			   <label for="exampleInputName2">营业时间</label>
			   <input type="text" class="form-control" name="open_time" id="open_time" value="${store.open_time}" placeholder="如，10:00-21:00">
			</div>
			<div class="form-group">
			   <label for="exampleInputName2">推荐</label>
				<textarea class="form-control" name="recommend" id="recommend"   placeholder="如，推荐菜，推荐景点，推荐房间" rows="3">${store.recommend}</textarea>
			</div>
			<div class="form-group">
			   <label for="exampleInputName2">特色服务</label>
				<textarea class="form-control" name="special" id="special"   placeholder="如，免费停车，WiFi" rows="3">${store.special}</textarea>
			</div>
			<div class="form-group">
			   <label for="exampleInputName2">简介</label>
				<textarea class="form-control" name="introduction" id="introduction"   placeholder="对品牌或门店的简要介绍" rows="3">${store.introduction}</textarea>
			</div>
	</div>
	<div class="form-group" style="text-align:center">
		<input type="button" class="btn btn-success" onclick="updStore()" value="提交"/>
		<input type="button" class="btn  btn-warning" onclick="cancelAdd()" value="取消"/>
	</div>
</form>
</div>
</body>
</html>
