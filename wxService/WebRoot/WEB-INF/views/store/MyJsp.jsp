<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>图片上传测试</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta http-equiv="Content-Language" content="zh-CN" />
    <link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/parts/CSS/nine/style.css" /> 
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/parts/CSS/nine/isotope.css" /> 
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/parts/CSS/nine/boot.css" /> 
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/parts/CSS/nine/font-awesome.css" /> 
<script type='text/javascript' src='<%=path%>/parts/JS/jquery-1.4.4.js'></script>
<script type='text/javascript' src='<%=path%>/parts/JS/jquery.ajaxform.js'></script>
<script type='text/javascript' src='<%=path%>/parts/JS/jquery.wallform.js'></script>
<script>
//下面用于图片上传预览功能
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
		$("#complete").append("<h4>上传成功</h4>");
		var json=JSON.parse(msg);
		$.each(json.res,function(i, item) {
			if(item.result=="succ"){
	            $("#complete").append("<img src='"+item.url+"' width='150' height='150' style='display: inline; width: 150px; height: 150px;'/>");
			}else{
				$("#complete").append("<span>第"+item.seq+"张图片上传失败，请重试！请勿重复上传已成功的图片</span>");
			}
        });
        $("#localImag").empty();
	}
</script>
</head>
<body>
    <div>
    	<form enctype="multipart/form-data" method="post" target="upload" action="<%=path%>/store/uploadStorePhoto" > 
    	<div id="complete">
    	</div>
    	<div id="localImag">
    	</div>
    	<input type="hidden" name="id" id="id" value="BD7AD93F-8B33-434F-84C4-503D1479894B"/>
    	<input type="button" value="选择文件" class="btn btn-primary" onclick="$('#uploadfile').click()"/>
		<input type="file" name="uploadfile" id="uploadfile" multiple="multiple" style="display:none"  onchange="javascript:setImagePreview();" />
		<input type="submit" value="上传" id="subBtn" class="btn btn-success"/>
		<span id="msg"></span> 
		</form> 
		<iframe name="upload" style="display:none"></iframe>  
    </div>  
    
    
    
    

</body>
</html> 