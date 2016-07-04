/*
   * 公共弹层alert方法--
   * content:弹出的消息
   * callback:是否有确认按钮,值为true或false,true,false:有，true:没有
   * width:窗口宽度
   * height:窗口高度
   * icon:图标名称,定义消息图标。可定义"parts/itemjs/artDialog/skins/icons/"目录下的图标名作为参数名（不包含后缀名）
   * 方法调用:_alert("弹出",true,'100','100','question');
*/

function _alert(content,callback,width,height,icon) {
	try{
		width = width ? width: "222";
		height = height ? height: "66";
		$.imDialog.alert(content,callback,width,height,icon);
	}catch(e){
		alert(content);
	}
}
//调用方法 参数( 要跳转的地址 , 设置层的样式 , 宽度 , 高度 )
/**
 * url:跳转的地址
 * title:对话框显示的标题
 * width:窗口宽度
 * height:窗口高度
 * id:为窗口定义别名  1、防止重复弹出  2、定义id后可以使用art.dialog.list[youID]获取扩展方法
 * 方法调用:	doQueryMess($('#ctx').val()+"/attachType.html?operate=selectFTP&flow_def_key="+$('#flow_def_key').val()+"&status="+$('#status').val());
 */
function doQueryMess(sendurl,title,width,height,id) {
	if(sendurl != ""){
 		var url = "";
 		if(sendurl.indexOf(_c)!=-1||sendurl.indexOf("http://")!=-1){
 			url = sendurl;
 		}else{
    		url = _c+sendurl;
    	}
    	$.imDialog.open(url,title,width,height,id);
    }
}
/**
   * 公共弹层_confirm方法--
   * content:弹出内容
   * yes: 点击确认时的回调函数名(不加括号)
   * no: 点击取消时的回调函数名(不加括号)
   * width:窗口宽度
   * height:窗口高度
   * 方法调用:_confirm("确定",Check,'','180','200');
*/
function _confirm(content,yes,no,width,height) {
	width = width ? width: "222";
	height = height ? height: "66";
	$.imDialog.confirm(content,yes,no,width,height);
}

//关闭按钮
function doclose(){
	$.dialog.close(); 
}

function __get($name, $dom) {
  $dom = $dom || document;
  return $dom.getElementById($name);
}

var _regLtrim = /^(\s|\u3000)*/;
var _regRtrim = /(\s|\u3000)*$/;
/**
 * trim and string
 * 全角的空格unicode为3000
 */
function __trim($text) {
  if ($text==null)
    return "";
  return $text.replace(_regLtrim, "").replace(_regRtrim, "");
};
/**
 * it's the str it's empty
 */
function __isEmpty($str) {
  return __trim($str).length==0;
}

/**
 * 不要使用客户端的时间，当前时间应该是从服务器端获得的
 */
var __serverTime;
function __setServerTime($serverTime) {
  __serverTime = $serverTime;
}

/**
 * 和当前时间进行比较
 * sOption ：比较类型 ">" | "<" | "=" | ">=" | "<="
 */
function __compareTimeWithNow(sTime,sOption) {
    var now = new Date();
    now.setTime(__serverTime);
    var sNow = __parseDateToChineseDateAuto(now,sTime);
    return __compareTime(sTime,sNow,sOption);
}

/**
 * 2个时间进行比较
 * sOption ：比较类型 ">" | "<" | "=" | ">=" | "<="
 */
function __compareTime(sTime1,sTime2,sOption){
    var index1 = sTime1.indexOf(":");
    var index2 = sTime2.indexOf(":");

    var dTime1 = new Date();
    var dTime2 = new Date();
    if(index1==-1){
        dTime1 = __parseDateS(sTime1);
    }else{
        dTime1 = __parseDate(sTime1);
    }
    if(index2==-1){
        dTime2 = __parseDateS(sTime2);
    }else{
        dTime2 = __parseDate(sTime2);
    }
    return __compareTimeD(dTime1,dTime2,sOption);
}

/**
 * 2个Date类型的时间进行比较
 */
function __compareTimeD(dTime1,dTime2,sOption) {
    if (dTime1==null || dTime2==null) {
        return false;
    }
    if(sOption==">") {
        return (dTime1>dTime2);
    }else if(sOption=="<") {
        return (dTime1<dTime2);
    }else if(sOption=="=") {
        return (dTime1==dTime2);
    }else if(sOption==">=") {
        return (dTime1>=dTime2);
    }else if(sOption=="<=") {
        return (dTime1<=dTime2);
    }else{
        alert("无效的比较类型,支持的类型:>、<、=、>=、<=");
        return false;
    }
}

/**
 * 将日期格式化为Date类型
 * 传入的日期格式如下： 2004年11月09日 10:29
 */
var regDate = /(\d*)年(\d*)月(\d*)日\s(\d*)\:(\d*)/;
function __parseDate($dateStr) {
  if ($dateStr=="") {
      return null;
  }
  var t = $dateStr.match(regDate);
  var date = new Date();
  date.setTime(__serverTime);
  date.setYear(t[1]);
  date.setMonth(t[2]*1-1);
  date.setDate(t[3]);
  date.setHours(t[4]);
  date.setMinutes(t[5]);
  return date;
}

var regDateS = /(\d*)年(\d*)月(\d*)日/;
function __parseDateS($dateStr) {
  if ($dateStr=="") {
      return null;
  }    
  var t = $dateStr.match(regDateS);
  var date = new Date();
  date.setTime(__serverTime);
  date.setYear(t[1]);
  date.setMonth(t[2]*1-1);
  date.setDate(t[3]);
  date.setHours(0);
  date.setMinutes(0);
  return date;
}

/**将标准时间转化为中文时间，根据$sample自动判断长、短日期格式*/
function __parseDateToChineseDateAuto($date,$sample) {
 var idx = $sample.indexOf(":");    //长日期
   if(idx==-1){
        return $date.getYear() + "年" + ($date.getMonth()*1+1) + "月" + $date.getDate() + "日";
   }else{
        return $date.getYear() + "年" + ($date.getMonth()*1+1) + "月" + $date.getDate() + "日" + " " + $date.getHours() + ":" + $date.getMinutes();
   }
}

/**
 * mouse over the menu
 */
function doMsOver(obj) {
  var dom = myEvent();
  dom = obj!=null?obj:dom;
  if(dom.tagName != "SPAN"){
  	dom = __getReal(dom, "tagName", "SPAN");
  	dom = dom==null?obj:dom;
  }
  if (my_style=="A"||my_style=="C") {
    dom.style.padding = "2 1 1 1";
    dom.style.color = "#663399";
    dom.style.border = "1px";
    dom.style.borderStyle = "solid";
    dom.style.backgroundColor = "#FFEECC";
  }else if (my_style=="B"){
    dom.style.color = "#FF0000";
    dom.style.borderStyle = "inset";
  }
}
/**
 * mouse out the menu
 */
function doMsOut(col,obj) {
  var dom = myEvent();
  dom = obj!=null?obj:dom;
  if(dom.tagName != "SPAN"){
  	dom = __getReal(dom, "tagName", "SPAN");
  	dom = dom==null?obj:dom;
  }
  if (my_style=="A"||my_style=="C") {
    dom.style.borderStyle = "none";
    if(col&&col!=""){
    	dom.style.color = col;
    }else{
    	dom.style.color = "#0066cc";
    }
    dom.style.padding = "1 1 1 1";
    dom.style.backgroundColor = "transparent";
  }else if (my_style=="B"){
    dom.style.color = "#000000";
    dom.style.borderStyle = "outset";
  }
}
//设置样式类别
var my_style="A";