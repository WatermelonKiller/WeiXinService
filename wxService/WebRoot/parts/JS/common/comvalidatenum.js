//验证数量
function doVnum(obj)
{
	if(!isNaN(obj.value)&&obj.value.indexOf(".")==-1){
		if(obj.value.substring(0,1)=='0'&&obj.value.length!=1)
		{
			for(var num = 0 ; num < obj.value.length; num++)
			{
				if(obj.value.substring(0,1)=='0'&&obj.value.length!=1)
				{
				obj.value=obj.value.substring(1)
				}
				doVnum(obj)
			}
		}
		return true;
	}
	else{
				obj.value="";
				obj.focus();
				alert(obj.title + "应该填写相应数字！")
				return false;
		}
}
//验证单价
function doVprice(obj) {	
	if(obj.value!=""){
	if(!isNaN(obj.value)){
	//	if(obj.value.substring(0,1)=='0'&&obj.value.indexOf(".")!=1)
	//	{
	//		obj.value=obj.value.substring(1)
	//	}
		if(obj.value.substring(0,1)=='0'&&obj.value.length!=1&&obj.value.indexOf(".")!=1)
		{
			for(var num = 0 ; num < obj.value.length; num++)
			{
				if(obj.value.substring(0,1)=='0'&&obj.value.length!=1&&obj.value.indexOf(".")!=1)
				{
				obj.value=obj.value.substring(1)
				doVprice(obj)
				}
			}
		}
		return true;
	}else
	{
			obj.value="";
			obj.focus();
			alert(obj.title + "应该填写相应数字！")
			return false;
	}
	}else{
		return false;
		//obj.value=0;
		//doVprice(obj)
	}
}
//验证 百分数
function doVpercent(obj){	
	if(obj.value*1>100||obj.value*1<0){
		obj.value=0;
		obj.focus();
		alert(obj.title + "不能超过100%");
		return false;
	}
	if(obj.value!=""){
	if(!isNaN(obj.value)){
		if(obj.value.substring(0,1)=='0'&&obj.value.length!=1&&obj.value.indexOf(".")!=1)
		{

			for(var num = 0 ; num < obj.value.length; num++)
			{
				if(obj.value.substring(0,1)=='0'&&obj.value.length!=1&&obj.value.indexOf(".")!=1)
				{
				obj.value=obj.value.substring(1)
				doVpercent(obj)
				}
			}
		}
		return true;
	}else
	{
			obj.value=0;
			obj.focus();
			alert(obj.title + "应该填写实数类型！")
			return false;
	}
	}else{
		obj.value=0;
		doVpercent(obj)
	}
}
//将数字转化成大写汉字
function BillSign(obj){
	try{
		var v =obj.value;
		//String xx=String.valueOf(v);
		if(v!=""){
			if(!isNaN(v)){
				var billSigns = ['零', '壹', '贰', '叁', '肆', '伍', '陆', '柒', '捌', '玖', '拾'];
				obj.value=billSigns[v];
			}
		}
	} catch(e){
		for(var v in e){
			alert(e.lineNumber);
		}
	}
}
/*
	* 获得焦点和失去焦点的操作
	* 数字控件的内容操作 -- 选中时 控件内容为0 变为空  失去焦点时控件内容为空 变为0
*/
function isActive(obj){
	var aobj=document.activeElement;
	if(obj == aobj){
		if(obj.value!="" && new Number(obj.value)==0){
			obj.value = "";
		}
	}else{
		if(obj.value==""){
			obj.value = "0.0";
		}
	}
}
function doFocus(obj){
	if(obj.value == "0"){
		obj.value ="";
	}
}
//四舍五入
function round(x,y){
	var p = Math.pow(10,y)
	x*=p;
	x=Math.round(x);
	return x/p;
}
