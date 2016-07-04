//清除页面里所有INPUT的 单引号 双引号 和
function addEvent() {
	var ipall = $(":input[disabled!=true]");
	if(ipall)
	ipall.blur(function(e){ckeckInputAll(e)}).keyup(function(e){ckeckInputAll(e)});
}
function ckeckInputAll(e) {
	//调用commontier.js 中的修改兼容性方法 兼容 window.event.srcElement
	var dom = e.target;
	if(dom.tagName=='INPUT'){
		if(dom.type=='text' || dom.type=='password'){
			var text = dom.value.replace(/['"]/g,'');
			if(dom.value.length > text.length)
				dom.value = text;
		}
	}else if(dom.tagName=='TEXTAREA') {
		var text = dom.value.replace(/['"]/g,'');
		if(dom.value.length > text.length)
		dom.value = text;
	}
}
if (window.addEventListener != undefined ){
    window.addEventListener("load", addEvent, false); 
} else if (window.attachEvent != undefined){
    window.attachEvent("onload", addEvent);
}
//结束

//去空格
function trim(s) {
  return s.replace( /^"s*/, "" ).replace( /"s*$/, "" );
}
//日期验证
function vDate(value) {
	if(trim(value).length!=0){
		var date = value.split("-");
		var yyyy = date[0], mm = date[1], dd = date[2];
		var d=new Date(yyyy,mm-1,dd),
		year=d.getYear()+"",
		mon=d.getMonth()+1,
		day=d.getDate();
		if(year.length<4){
			year="19"+year;
		}
		if ( year!=yyyy || mon!=mm || day!=dd ||!isValidDate(dd, mm, yyyy) )
		    return true;
	}
}
//日期验证
function isValidDate(day, month, year) {
        if (month < 1 || month > 12) {
            return false;
        }
        if (day < 1 || day > 31) {
            return false;
        }
        if ((month == 4 || month == 6 || month == 9 || month == 11) &&
            (day == 31)) {
            return false;
        }
        if (month == 2) {
            var leap = (year % 4 == 0 &&
               (year % 100 != 0 || year % 400 == 0));
            if (day>29 || (day == 29 && !leap)) {
                return false;
            }
        }
        return true;
}
//数字验证
function vInt(value) {
    if(value.search(/^[-\+]?\d+$/) == -1){
    	return true;
    }
}
//为一个作用域下的输入元素进行验证
function checkf(scopes){
	var isV = false;
	//判断是否是jQuery对象
	if(!(scopes instanceof jQuery)){
		scopes = $(scopes); //转换
	}
	var allelements = scopes.find(":input[check][disabled!=true]");
		for(i=0;i<allelements.length;i++){
		   	var checkType = allelements.eq(i).attr("check").split(",");
		    for(l=0;l<checkType.length;l++){
			    valiType =  checkType[l] ;
			    switch(valiType) {
			       case "checkBlank":isV = blankCheck(allelements[i]) ;break;
			       case "checkNaN": isV = NaNCheck(allelements[i]) ;break;
			       case "checkInt":isV = intCheck(allelements[i]) ;break;	 	       
			       case "checkDate":isV = dateCheck(allelements[i]) ;break;	 
			       case "checkChar":isV = charCheck(allelements[i]) ;break;
			       case "checkEmail":isV = isEmail(allelements[i]) ;break;
			    }
			    if(isV)return false;
		    }
		}
	return true;
}
//为单独一个控件进行验证
function checkd(obj){
	var isV = false;
	var inputs = $(obj);
   	if((!inputs.attr("check") ) || (inputs.disabled)) return true;
   	var checkType = inputs.attr("check").split(",");
	    for(l=0;l<checkType.length;l++){
		    valiType =  checkType[l] ;
		    switch(valiType) {
		       case "checkBlank":isV = blankCheck(obj) ;break;	       
		       case "checkNaN": isV = NaNCheck(obj) ;break;	 	       
		       case "checkInt":isV = intCheck(obj) ;break;	 	       
		       case "checkDate":isV = dateCheck(obj) ;break;	 
		       case "checkChar":isV = charCheck(obj) ;break;
		       case "checkEmail":isV = isEmail(obj) ;break;
		    }
		    if(isV)return false;
	    }
	return true;
}
//为全局控件进行验证
function check(){
	var isV = false;
	var allelements = $(":input[check][disabled!=true]");
		for(i=0;i<allelements.length;i++){
		   	var checkType = allelements.eq(i).attr("check").split(",");
		    for(l=0;l<checkType.length;l++){
			    valiType =  checkType[l] ;
			    switch(valiType) {
					case "checkBlank":isV = blankCheck(allelements[i]) ;break;
					case "checkNaN": isV = NaNCheck(allelements[i]) ;break;
					case "checkInt":isV = intCheck(allelements[i]) ;break;	 	       
					case "checkDate":isV = dateCheck(allelements[i]) ;break;	 
					case "checkChar":isV = charCheck(allelements[i]) ;break;
					case "checkEmail":isV = isEmail(allelements[i]) ;break;
					case "checkChinese":isV = isChinese(allelements[i]) ;break;
			    }
			    if(isV)return false;
		    }
		}
	/*
	var selects = document.getElementsByTagName("SELECT");
		for(i=0;i<selects.length;i++){
		   	if((!selects[i].check ) || (selects[i].disabled)) continue;
		   	var checkType = selects[i].check.split(",");
			    for(l=0;l<checkType.length;l++){
				    valiType =  checkType[l] ;
				    switch(valiType) {
				       case "checkBlank":isV = blankCheck(selects[i]) ;break;	       
				    }
				    if(isV)return false;
			    }
		}
	var inputs = document.getElementsByTagName("INPUT");
		for(i=0;i<inputs.length;i++){
		   	if((!inputs[i].check ) || (inputs[i].disabled)) continue;
		   	var checkType = inputs[i].check.split(",");
		    
			    for(l=0;l<checkType.length;l++){
				    valiType =  checkType[l] ;
				    switch(valiType) {
				       case "checkBlank":isV = blankCheck(inputs[i]) ;break;	       
				       case "checkNaN": isV = NaNCheck(inputs[i]) ;break;	 	       
				       case "checkInt":isV = intCheck(inputs[i]) ;break;	 	       
				       case "checkDate":isV = dateCheck(inputs[i]) ;break;	 
				       case "checkChar":isV = charCheck(inputs[i]) ;break;
				       case "checkEmail":isV = isEmail(inputs[i]) ;break;
				    }
				    if(isV)return false;
			    }
		}
	var textareas = document.getElementsByTagName("TEXTAREA");
		for(i=0;i<textareas.length;i++){
		   	if((!textareas[i].check ) || (textareas[i].disabled)) continue;
		   	var checkType = textareas[i].check.split(",");
			    for(l=0;l<checkType.length;l++){
				    valiType =  checkType[l] ;
				    switch(valiType) {
				       case "checkBlank":isV = blankCheck(textareas[i]) ;break;	       
				    }
				    if(isV)return false;
			    }
		}*/
	return true;
}

function blankCheck(field){
		var str = field.value;
		str = str.replace(/^\s+\s+$/g,"");
		if( str  == "") {
			_alert(field.title+"不能为空")	;
			try{field.focus();}catch(e){}
			return true;
		}
}
 
function NaNCheck(field){
		if(trim(field.value)!=""&&isNaN(field.value)){
			_alert(field.title+"应该为数字类型")
			try{field.focus();}catch(e){}
			return true;
		}
}
function intCheck(field){ 
		if(trim(field.value)!=""&& (field.value.indexOf("-")!=-1 || vInt(field.value) ) ){
			_alert(field.title+"应该为正整数类型")
			try{field.focus();}catch(e){}
			return true;
		}
}
function dateCheck(field){
		if(field.value!=""&&vDate(field.value)){
			_alert(field.title+"应该为规定日期类型 yyyy-mm-dd")
			try{field.focus();}catch(e){}
			return true;
		}
}

function charCheck(field){
		var reg = "^[A-Za-z]+$";   
        var reg_obj = new RegExp(reg);
		if(field.value!=""&&!field.value.match(reg_obj)){
			_alert(field.title+"应该为字母类型")
			try{field.focus();}catch(e){}
			return true;
		}
}
/**检查邮件地址合法性*/
function isEmail(field){
	if(field.value!=""){
	    if(field.value.search(/^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/) == -1) {
	    	_alert(field.title+"格式不正确");
	    	try{field.focus();}catch(e){}
	        return true;
	    }
    }
}
//只能为汉字
function isChinese(field) {
	var regex  = /^[\u4E00-\u9FA5\uF900-\uFA2D]+$/;
	if(field.value!=""){
		if(!regex.test(jQuery.trim(field.value))){
	    	_alert(field.title+"只能为汉字");
	    	//field.value = "";
	    	try{field.focus();}catch(e){}
	        return true;
	    }
    }
}
