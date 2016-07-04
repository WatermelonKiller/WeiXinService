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
<script type="text/javascript" src="http://api.map.baidu.com/api?v2.0&ak=vp7OkjLDil4DUAFYxchkpETT"></script>
<script type="text/javascript">
	
 $(function(){ 
 //初始化加载一级类目 
	changeTypes(1);
	 // 百度地图API功能
 	var map = new BMap.Map("allmap");
	var point = new BMap.Point(116.331398,39.897445);
	map.centerAndZoom(point,11);
	map.enableScrollWheelZoom(true);
	var top_left_control = new BMap.ScaleControl({anchor: BMAP_ANCHOR_TOP_LEFT});// 左上角，添加比例尺
	var top_left_navigation = new BMap.NavigationControl();  //左上角，添加默认缩放平移控件
	map.addControl(top_left_control);        
	map.addControl(top_left_navigation); 
	
	
	
	function myFun(result){//根据ip获取设置城市
		var cityName = result.name;
		map.setCenter(cityName);
	}
	
	var myCity = new BMap.LocalCity();
	myCity.get(myFun);
	
	//添加点标注
	function addMark(){
		map.clearOverlays();
		var marker = new BMap.Marker(map.getCenter());  // 创建标注
		map.addOverlay(marker);              // 将标注添加到地图中
		marker.addEventListener("click",getAttr);
		marker.enableDragging();
		marker.addEventListener("dragend", function() {
			var sp = marker.getPosition();       //获取marker的位置
		   	decrypt(sp);
		   });
		var p = marker.getPosition();       //获取marker的位置
	   	decrypt(p);
	}
	
	//添加选择城市控件
	var size = new BMap.Size(10, 20);
	map.addControl(new BMap.CityListControl({
	    anchor: BMAP_ANCHOR_TOP_RIGHT,
	    offset: size,
	}));
	
	var menu = new BMap.ContextMenu();
	var txtMenuItem = [
		{
			text:'添加定位',
			callback:function(){addMark();}
		}
	];
	for(var i=0; i < txtMenuItem.length; i++){
		menu.addItem(new BMap.MenuItem(txtMenuItem[i].text,txtMenuItem[i].callback,100));
	}
	map.addContextMenu(menu);
 }); 
    
	
function getAttr(){
	var p = marker.getPosition();       //获取marker的位置
	alert("marker的位置是" + p.lng + "," + p.lat);   
}
//解密百度坐标为火星坐标
function decrypt(p){
	getAddress(p);
	$.ajax({
 	 	type: "POST",
	   url: "<%=path%>/store/decryptZb",
	   data:{"lon":p.lng.toFixed(6),"lat":p.lat.toFixed(6)},
		success: function(msg){
			if(msg!=""&&msg!="null"){
			var obj= $.parseJSON(msg);
				$("#longitude").val(obj.lon);
		   		$("#latitude").val(obj.lat);
			}
		}
	});
}

function getAddress(p){
	$.ajax({
 	 	type: "GET",
	   url: "<%=path%>/store/getAddressByPoint",
	   data:{"lon":p.lng.toFixed(6),"lat":p.lat.toFixed(6)},
		success: function(msg){
			var data=JSON.parse(msg);
			$("#district").val(data.district);
			$("#address").val(data.address);
			$("#city").val(data.city);
			$("#province").val(data.province);
		}
	});
}

function changeTypes(level){
	var last_type="";
	if(level!=1){
		last_type=$("#categories"+(level-1)).val();
	}
	$.ajax({
		  type: "POST",
		   url: "<%=path%>/store/getTypes",
		   data:{"last_type":last_type,"level":level},
  			success: function(msg){
  				//清空变更类目
  				$("#categories"+level).empty();
  				if(msg!=""&&msg!="null"){
	  				if(level!=1){
  						$("#categories"+level).removeClass("hid");
  						$("#categories"+level).addClass("type");
	  				}else{
	  					$("#categories"+level).append("<option value='error'>请选择</option>");
	  				}
	  				if(level==2){
  						$("#categories3").empty();
  						$("#categories3").addClass("hid");
  						$("#categories3").removeClass("type");
  					}
	  				var obj= $.parseJSON(msg);
	  				$.each(obj, function(index, json) { 
				 		$("#categories"+level).append("<option value='"+json.type+"'>"+json.type+"</option>");
					}); 
  				}else{
  					$("#categories"+level).empty();
  					$("#categories"+level).removeClass("type");
  					$("#categories"+level).addClass("hid");
  					if(level==2){
  						$("#categories3").empty();
  						$("#categories3").removeClass("type");
  						$("#categories3").addClass("hid");
  					}
  					
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
	
	function addStore(){
		$.ajax({
			        type : 'POST',
			        dataType : 'text',
			        url : "<%=path%>/store/auditingStore",
			        data : $('#storeForm').serialize(),
					success: function (msg){
		   				if(msg=="success"){
		   					$.imDialog.alert("新建成功！",function(){
		   					window.location.href="<%=path%>/store/storeList";
		   						},"300","100","");
		   			 	}else{
		   			 		$.imDialog.alert("新建失败！",function(){
								},"300","100","");
					    	}
		   			 }
	      		});
	}
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
 <div id="storeImg">
 		<h4>门店图片</h4>
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
<form action="<%=path%>/store/auditingStore" class="" id="storeForm" name="form" method="post">
	<input type="hidden" name="offset_type" value="1"/>
	<input type="hidden" name="sid" value="${sid}"/>
	<div id="map">
		<div id="r-result">
			<h4>门店定位</h4>
			<hr/>
		<div class="form-group">
			<label for="exampleInputName2">省</label><input type="text" class="form-control type" name="province" id="province" readonly="readonly"/>
			<label for="exampleInputName2">市</label><input type="text" class="form-control type" name="city" id="city"  readonly="readonly"/>
			<label for="exampleInputName2">区</label><input type="text" class="form-control type" name="district" id="district"  readonly="readonly"/><br/>
		</div>
		<div class="form-group">
			<label for="exampleInputName2">地址</label>
			<input type="text" style="width:300px;display:inline" class="form-control"  name="address" id="address" /><br/>
		</div>
			<label for="exampleInputName2">经度</label>
  				<input type="text" class="form-control type" name="longitude" id="longitude" placeholder="经度" readonly="readonly"/>
  				<label for="exampleInputName2">纬度</label>
  				<input type="text" class="form-control type" name="latitude" id="latitude" placeholder="纬度" readonly="readonly"/>
		</div>
		<div class="form-group">
		   <label for="exampleInputName2">定位&nbsp;&nbsp;</label>
		   <span style="color:#8D8D8D">找到您的门店大致位置后，右键点击地图“添加定位”后，将标注拖动到您的门店位置</span>
		   <div id="allmap">
			</div>
		</div>
		
	</div>
	<div id="baseInfo">
		<div class="form-group">
		   <label for="exampleInputName2">门店名</label>
		   <input type="text" class="form-control" name="business_name" id="business_name" placeholder="门店名不得含有区域地址信息（如，北京市XXX公司）">
		</div>
		<div class="form-group">
		   <label for="exampleInputName2">分店名</label>
		   <input type="text" class="form-control" name="branch_name" id="branch_name" placeholder="分店名不得含有区域地址信息（如，“北京国贸店”中的“北京”）">
		</div>
		<div class="form-group">
		   <label for="exampleInputName2">类目</label>
		   <select class="form-control  type" name="categories" id="categories1" onchange="changeTypes('2')">
		   </select>
		   <select class="form-control hid" name="categories" id="categories2" onchange="changeTypes('3')">
		   </select>
		   <select class="form-control hid" name="categories" id="categories3">
		   </select>
		</div>
		<br/>
	</div>
	
	<div class="form-group">
	   <h4>服务信息</h4>
	  	<span style="color:#8D8D8D">该部分为公共编辑信息，每个添加了该门店的商户均可提交修改意见，并在审核后选择性采纳</span>
	   <hr/>
	</div>
			
	<div id="serviceInfo">
			<div class="form-group">
			   <label for="exampleInputName2">电话</label>
			   <input type="text" class="form-control" name="telephone" id="telephone" placeholder="固定电话需加区号；区号、分机号均用“-”连接">
			</div>
			<div class="form-group">
			   <label for="exampleInputName2">人均价格</label>
			   <input type="text" class="form-control" name="avg_price" id="avg_price" placeholder="大于零的整数，须如实填写，默认单位为人民币/元">
			</div>
			<div class="form-group">
			   <label for="exampleInputName2">营业时间</label>
			   <input type="text" class="form-control" name="open_time" id="open_time" placeholder="如，10:00-21:00">
			</div>
			<div class="form-group">
			   <label for="exampleInputName2">推荐</label>
				<textarea class="form-control" name="recommend" id="recommend"   placeholder="如，推荐菜，推荐景点，推荐房间" rows="3"></textarea>
			</div>
			<div class="form-group">
			   <label for="exampleInputName2">特色服务</label>
				<textarea class="form-control" name="special" id="special"   placeholder="如，免费停车，WiFi" rows="3"></textarea>
			</div>
			<div class="form-group">
			   <label for="exampleInputName2">简介</label>
				<textarea class="form-control" name="introduction" id="introduction"   placeholder="对品牌或门店的简要介绍" rows="3"></textarea>
			</div>
			<!-- 
			<div class="form-group">
			   <label for="exampleInputName2">门店签名</label>
			   <input type="text" class="form-control" name="signature" id="signature" placeholder="在“附近的人”展示，不超过6个字，如上方截图示例中的“满99送咖啡”字样。">
			</div> 
			-->
	</div>
	<div class="form-group" style="text-align:center">
		<input type="button" class="btn btn-primary" onclick="addStore()" value="提交"/>
		<input type="button" class="btn  btn-warning" onclick="cancelAdd()" value="取消"/>
	</div>
</form>
</div>
</body>
</html>
