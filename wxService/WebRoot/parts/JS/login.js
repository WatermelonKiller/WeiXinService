 
document.onkeydown = function(e){
	var ev = document.all ? window.event : e;
	if(ev.keyCode==13) {
		doins($("#loginBtn"));
	}
};

function login(loginBtn){
	loginBtn.disabled=true;
	var operate=$("#operate").val();
	var loginname = $("#loginName").val();
	var password = $("#password").val();
	$("#password").val($("#password").val().replace(' ',''));
	$("#loginName").val($("#loginName").val().replace(' ',''));
	if(loginname=='' || password==''){
		alert("帐号密码都要输入！大哥！!");
		loginBtn.disabled=false;
		$("#password").val("");
		return false;
	}else if(operate='ins'){	
		$.ajax({
			type: "POST",
			dataType:"text",
			url: _path+"/login/checkuser",
			data: {
				"loginname":loginname,
				"password":password
			},
			success: function(jsondata){
				if(jsondata == "notFound"){
					alert("用户名或密码错误");
					$("#password").val("");
					loginBtn.disabled=false;
					return false;
				}else if(jsondata == "notFoundDep"){
					alert("您不属于任何部门，无法登陆");
					$("#password").val("");
					loginBtn.disabled=false;
					return false;
				}else if(jsondata == "notFoundRole"){
					alert("您没有被分配角色，请联系管理员");
					$("#password").val("");
					loginBtn.disabled=false;
					return false;
				}else if(jsondata == "notInUse"){
					alert("您的帐户是禁用状态，请联系管理员");
					loginBtn.disabled=false;
					return false;
				}else{
					document.forms[0].action += "/login/managerHtml.html";
					$("#serialNumber_liu").val(serialNumber_liu);
					$('#webForm').submit();
				}
			}
		});
	}
}


function doadd (){
	$("#loginName").val(input_name_liu);
	$("#password").val("");
}





