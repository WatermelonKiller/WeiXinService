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
<title>系统栏目</title>
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/parts/CSS/bootstrap-3.3.5-dist/css/bootstrap.css">
<script charset="utf-8" src="<%=path%>/parts/JS/jquery-1.4.4.js"></script>
<script charset="utf-8" src="<%=path%>/parts/JS/artDialog/artDialog.js"></script>
<script charset="utf-8" src="<%=path%>/parts/JS/artDialog/customDialog.js"></script>
<script charset="utf-8" src="<%=path%>/parts/JS/artDialog/jquery.artDialog.js?skin=black"></script>
<script charset="utf-8" src="<%=path%>/parts/JS/artDialog/artDialog.iframeTools.js"></script>
<script type='text/javascript' src='<%=path%>/parts/JS/jweixin-1.0.0.js'></script>
<script>
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

function loadAllDept(){
$("input[name='dept_id']").remove();
$(".deptSpan").remove();
	var sysId = $("#system_id option:selected").val();
	if(""!=sysId){
		$.ajax({
			dataType:"json",
			type:"post",
			url:"<%=path%>/menu/ajax/loadAllDept",
			cache:false,
			async:false,
			data:{
				"sysId" : sysId
			},
			success:function(data){
				if(data != undefined){
					var json = eval(data);
						$.each(json,function(i,o){
						$("#checkb").append("<input type='checkbox' name='dept_id' value='"+o.dept_id+"'><span class='deptSpan'>"+o.dept_name+"</span>");
						});  
					}		
			}
		});	
	}else{
		$("input[name='dept_id']").remove();
		$(".deptSpan").remove();
	}
}
  		
function closeWindow(){ 
	$.imDialog.close();
} 
 
function saveButton(){
	if(""==$("#system_id option:selected").val()){
		alert("请选择可控权限部门");
		return false;
	}else{
		var formData=$("form").serialize();
		$.ajax({
			  type: "POST",
			   url: "<%=path%>/menu/ajax/createLeftMenu",
			   processData:true,
			   data:formData,
			   dataType:"json",
		  		success: function(result){
		  			if(result.message=="success"){
			    		$.imDialog.alert("成功添加菜单！",function(){
			    		window.parent.location.reload();
			    			},"300","100","");
			    	}else{
			    		$.imDialog.alert("添加失败！",function(){
			    		window.parent.location.reload();
						},"300","100","");
			    	}
		  		}
			  });
	}
}

function showFather(flag){
  if("F"==flag){
  	$("#fatherSelect").show();
  		$.ajax({
			dataType:"json",
			type:"post",
			url:"<%=path%>/menu/ajax/loadAllFatherMenu",
				cache : false,
				async : false,
				data : {},
				success : function(data) {
					if (data != undefined) {
						var json = eval(data);
						$.each(json, function(i, o) {
							$("#parent_id").append(
									"<option value='"+o.column_id+"'  id='"+o.route+"' name='"+o.route+"'>"
											+ o.node_name + "</option>");
						});
					}
				}
			});
		} else {
			$("#fatherSelect").hide();
			$("#parent_id").empty();
		}
	}

//跳转选择部门页面
function selectDept(sysId){

}
</script>
</head>
<body>
	<form action="<%=path%>/menu/ajax/createLeftMenu" name="form" class="form-inline"
		method="post">
		<div class="form-group">
			<label>请输入按钮名称：</label> <input type="text" name="node_name"
				id="node_name" class="form-control" /></div>
		<div class="form-group">
			<label> 按钮Url：</label> <input type="text" name="link" id="link"
				class="form-control" />
		</div>
<br/><br/>
		<div class="form-group">
			<label> 排序：</label> <input type="text" name="sequence" id="sequence"
				class="form-control" />
		</div>

		<div class="form-group">
			<label>是否为父级菜单：</label> <label class="radio-inline"> <input
				type="radio" name="type" id="type" value="J"
				onclick="showFather('T');" />是 </label> <label class="radio-inline">
				<input type="radio" name="type" id="type" value="B"
				onclick="showFather('F')" />否 </label>
		</div>
<br/><br/>
		<div id="fatherSelect" class="form-group">
			<label>请选择所属父菜单：</label> <select id="parent_id" name="parent_id" class="form-control">
			</select>
		</div>
		<div class="form-group">
			<label>可使用系统：</label> 
			<select id="system_id" name="system_id" class="form-control" onchange="loadAllDept();">
				<option value="">请选择</option>
			</select>
		</div>
		<div id="checkb"></div>
		<!-- 	<input type="button" value="选择可用部门" class="btn btn-primary "onclick="selectDept()" />  -->
<!-- 		<div class="form-group">
			<label>可使用部门：</label> <select id="dept_id" name="dept_id" class="form-control">
			<option value="00000000-0000-0000-0000-000000000000" id="dept_all">全部门</option>
			</select>
		</div> -->
		<br/><br/><br/>
		<input type="button" value="确认" class="btn btn-primary "onclick="saveButton()" /> <input type="button" value="取消" class="btn btn-default"
			onclick="closeWindow()" />
	</form>
</body>
</html>
