/**
 * *****************************************************************************
 *               Copyright(c) 2010 by 哈尔滨华泽数码科技有限公司.
 *                       All rights reserved.
 *******************************************************************************
 *     Description(说明)	:  各种辅助功能js集合 包含 分页、checkbox操作、区域内获取元素键值对、页面键盘操作
 * -----------------------------------------------------------------------------
 *     No.        Date              Revised by           Description	  
 *     1		2012-10-31				韩飞	              Created
 *****************************************************************************
 */
//分页 ---------↓
function dostartend(obj){	
	document.getElementById("npage").name="action";
	document.getElementById("npage").value="index";
	var ys=1;
	if(obj.title=="尾页") {
		ys=document.getElementById("allPageNumber").value;
	}
	document.getElementById("leafNumber").value=ys;
	doSel();
}
function nextPage(){
	document.getElementById("npage").name="action";
	document.getElementById("npage").value="nextPage";
	doSel();
}
function previousPage(){
	document.getElementById("npage").name="action";
	document.getElementById("npage").value="previousPage";
	doSel();
}
function doindex(){
	try{	
	document.getElementById("npage").name="action";
	document.getElementById("npage").value="index";
	document.getElementById("leafNumber").value = document.getElementById("page").value;
	}catch(e){
	}
	doSel();
}
//默认查询 有变动请在页面上重写此方法
function doSel(){	
	document.forms[0].submit();
}
//页面排序方法如有变动请在页面上重写此方法
function doOrder(order) {
	if(document.getElementById("order")){
		if(document.getElementById("taxis").value=="" || document.getElementById("taxis").value=='asc'){
			document.getElementById("taxis").value="desc";
		}else{
			document.getElementById("taxis").value="asc";
		}
		document.getElementById("order").value=order;
		doindex();
	}
}
//分页 ---------↑
//选择对应的checkbox进行初始化 obj 全选的checkbox， checkname要初始化的checkbox
function setCheck(obj,checkname){
	if(obj.checked){
		doChecktrue(checkname);
	}else{
		doCheckfalse(checkname);
	}
}
//初始化所有checkbox为false
function doCheckfalse(checkName){
	var gramaCkecks = document.getElementsByName(checkName);
	//var gramaCkecks = document.all[checkName];
	if(gramaCkecks && gramaCkecks.length){
		for(var j=0;j<gramaCkecks.length;j++){
			gramaCkecks[j].checked = false;
		}
	}else{
		gramaCkecks.checked = false;
	}
}
//初始化所有checkbox为true
function doChecktrue(checkName){
	var gramaCkecks = document.getElementsByName(checkName);
	//var gramaCkecks = document.all[checkName];
	if(gramaCkecks && gramaCkecks.length){
		for(var j=0;j<gramaCkecks.length;j++){
			gramaCkecks[j].checked = true;
		}
	}else{
		gramaCkecks.checked = true;
	}
}
//上下键盘 input 获取焦点  弃用 obj控件对象  seat所在位置
function checkKey(obj,seat){
	var tr = obj.parentNode.parentNode;
	var trs;
	if(getEvent().keyCode==38){
		//上
		trs = $(tr).prev();
		if(trs.get(0)&& trs.children("td").length ){
			var rs = trs.children("td");
			if(rs[seat].firstChild.nodeName=="INPUT"){
				rs[seat].firstChild.focus();
			}
		}
	}else if(getEvent().keyCode==40){
		//下
		trs = $(tr).next();
		if(trs.get(0)&& trs.children("td").length){
			var rs = trs.children("td");
			rs[seat].firstChild.focus();
		}
	}
}
//左右键选择页面控件 使用方法 在body 的onload 事件调用
function keydown() {
	if(getEvent().keyCode == 13){ //enter 回车下一个输入对象
		keycheck(1);
	}else if (getEvent().keyCode == 37) { //backspace 回退键 当前内容为空时回退上衣输入对象
		keycheck(-1);
	}
    /*if (getEvent().keyCode == 37) { //←
        keycheck(-1);
    }else if (getEvent().keyCode == 39) { //→
        keycheck(1);
    }*/
}
//左右键调用函数 scoping 范围 
function keycheck(step) {
	try{
		var $dom = document.forms[0];
		var ips = $dom.elements;
		var scoping = "text,select-one,textarea,checkbox,radio";
		var tmp = [];
		if(ips.length) 
		for(var j=0; j<ips.length; j++){
			if(scoping.indexOf(ips[j].type)!=-1 && ips[j].disabled==false){
				tmp.push(ips[j]);
			}
		}
		if(tmp.length)
		for (var i=0; i<tmp.length; i++) {
			if(tmp[i] == document.activeElement){
				try{
					if(tmp[i].value!="" && step==-1){
						return;
					}
					if((i + step)>=tmp.length){
						tmp[0].focus();
						return false;
					}else if((i + step)<0){
						
						tmp[tmp.length-1].focus();
						return false;
					}else{
						tmp[i + step].focus();
			  			return false;
					}
		  		}catch(e){
		  		}
		  	}
		}
	}catch(e){
	}
}
//获取指定域内需要的控件的值生成键值对
function cellvalue($dom,names) {
	var ips = $dom.find(":input[type!='button'][disabled!=true]");
  	//var ips = $dom.all.tags("INPUT");
  	//var txts = $(":textarea[check][disabled!=true]");
  	//var txts = $dom.all.tags("TEXTAREA");
  	//var txts = $(":select[check][disabled!=true]");
  	//var sls = $dom.all.tags("SELECT");
  var content={};
  for (var i=0; i<ips.length; i++) {
  	if(ips.get(i).name!=null&&ips.get(i).name!=""){
	  	if(names.indexOf(ips.get(i).name)==-1){
	  		continue;
	  	}else{
	  		if(checkd(ips.get(i))){
	  			content[ips.get(i).name]=ips.get(i).value;
	  		}else{
	  			return false;
	  		}
	  	}
	}
  }
  /*for (var i=0; i<txts.length; i++) {
  	if(txts.get(i).name!=null&&txts.get(i).name!=""){
  	if(names.indexOf(txts.get(i).name)==-1){
  		continue;
  	}else{
  		if(checkd(txts.get(i))){
  			content[txts.get(i).name]=txts.get(i).value;
  		}else{
  			return false;
  		}
  	}
  	}
  }
  for (var i=0; i<sls.length; i++) {
  	if(sls.get(i).name!=null&&sls.get(i).name!=""){
  	if(names.indexOf(sls.get(i).name)==-1){
  		continue;
  	}else{
  		if(checkd(sls.get(i))){
  			content[sls.get(i).name]=sls.get(i).value;
  		}else{
  			return false;
  		}
  	}
  	}
  }*/
  return content;
}
 //限制添加快捷内容的字数  
var maxLength =30; 
function MaxInput(fo,len) {
	if(len!=""){
		maxLength=len;
	}
	if (fo.value.length > maxLength) {
		_alert("输入内容过多！");
		fo.value = fo.value.substring(0, maxLength);
	}
}
function orderByTerm(termName,thId){
	$("#order").val(termName);
	if($("#taxis").val()=="asc"){
		$("#taxis").val("desc");
	}else{
		$("#taxis").val("asc");
	}
	doindex();
}
$(function(){
	var ascSrc="<img src=\""+_path+"/parts/images/up.png\" width=\"9\" height=\"9\" style=\"border:none;margin-left:5px;line-height:16px; vertical-align: middle;\" />";
	var descSrc="<img src=\""+_path+"/parts/images/down.png\" width=\"9\" height=\"9\" style=\"border:none;margin-left:5px;line-height:16px; vertical-align: middle;\" />";
	if(""!=$("#order").val()){
		var thId="th_"+$("#order").val();
		var th_obj=$(window.document).find("#"+thId);
		if($("#taxis").val()=="asc"){
			th_obj.append(ascSrc);
		}else{
			th_obj.append(descSrc);
		}
	}
});