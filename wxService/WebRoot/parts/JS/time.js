// JavaScript Document
function clockon() {
	var thistime= new Date();
	var year=thistime.getFullYear() ;
	var sty=thistime.getDay();
	var month=thistime.getMonth()+1;
	var day=thistime.getDate();
	var hours=thistime.getHours();
	var minutes=thistime.getMinutes();
	var seconds=thistime.getSeconds();
	if (eval(hours) <10) {hours="0"+hours;}
	if (eval(minutes) < 10) {minutes="0"+minutes;}
	if (seconds < 10) {seconds="0"+seconds;}
	if(sty==0){
		sty="日";
	}else if(sty==1){
		sty="一";
	}else if(sty==2){
		sty="二";
	}else if(sty==3){
		sty="三";
	}else if(sty==4){
		sty="四";
	}else if(sty==5){
		sty="五";
	}else if(sty==6){
		sty="六";
	}
	thistime = year+"年"+month+"月"+day+"日 星期"+sty+"  " + hours+":"+minutes+":"+seconds;
	$("#bgclocknoshade").html(thistime);
	var timer=setTimeout("clockon()",200);
}
  //给控件赋值
  //name为要赋值的控件的name属性
function doDate(name){
	var date=new Date();
	var month=date.getMonth()+1;
	var day=date.getDate();
	if(month<10){
		month="0"+month;
	}
	if(day<10){
		day="0"+day;
	}
	document.all[name].value=date.getYear()+"-"+month+"-"+day;
}

//返回完整时间
function doDate(){
	var now="";
	var date=new Date();
	var month=date.getMonth()+1;
	var day=date.getDate();
	var hour=date.getHours();
	var minute=date.getMinutes();
	var second=date.getSeconds();
	if(month<10){
		month="0"+month;
	}
	if(day<10){
		day="0"+day;
	}
	if(hour<10){
		hour="0"+hour;
	}
	if(minute<10){
		minute="0"+minute;
	}
	if(second<10){
		second="0"+second;
	}
	now=date.getYear()+"/"+month+"/"+day+" "+hour+":"+minute+":"+second;
	return now;
}