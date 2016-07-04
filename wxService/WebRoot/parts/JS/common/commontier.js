//兼容火狐浏览器 insert方法
if(typeof HTMLElement!="undefined" && !HTMLElement.prototype.insertAdjacentElement) {
     HTMLElement.prototype.insertAdjacentElement = function(where,parsedNode) {
        switch (where) {
            case 'beforeBegin':
                this.parentNode.insertBefore(parsedNode,this)
                break;
            case 'afterBegin':
                this.insertBefore(parsedNode,this.firstChild);
                break;
            case 'beforeEnd':
                this.appendChild(parsedNode);
                break;
            case 'afterEnd':
                if (this.nextSibling) this.parentNode.insertBefore(parsedNode,this.nextSibling);
                    else this.parentNode.appendChild(parsedNode);
                break;
         }
     }
     HTMLElement.prototype.insertAdjacentHTML = function (where,htmlStr) {
         var r = this.ownerDocument.createRange();
         r.setStartBefore(this);
         var parsedHTML = r.createContextualFragment(htmlStr);
         this.insertAdjacentElement(where,parsedHTML)
     }
     HTMLElement.prototype.insertAdjacentText = function (where,txtStr) {
         var parsedText = document.createTextNode(txtStr)
         this.insertAdjacentElement(where,parsedText)
     }
}

/**
   * 公共弹层alert方法--韩飞
*/
function _alert($msg, $time) {
	try{
		if (typeof(top.MessageMgr)!="undefined") {
		  $time = $time || 3;
		  top.__popup($msg, $time, window);
		}else {
		  alert($msg);
		}
	}catch(e){
		alert($msg);
	}
}
/**
   * 公共弹层显示信息方法--韩飞
*/
var _c="/hz_bms";
var _image = "/parts/images";
var __global_top = 1000;
if (typeof(_MY_SET)=="undefined") {
  _MY_SET = [];
  _MY_SET['Style'] = "A";
  _MY_SET['ToolBar'] = "A";
}
var _Uuid = new Uuid();
//调用方法 参数( 要跳转的地址 , 设置层的样式 , 宽度 , 高度 )
function doQueryMess(sendurl,setting,width,height) {
 	doCloseWin();
 	if(sendurl!=""){
 		var url = "";
 		if(sendurl.indexOf(_c)!=-1||sendurl.indexOf("http://")!=-1){
 			url = sendurl;
 		}else{
    		url = _c+sendurl;
    	}
    	if(setting.indexOf("drag")==-1){
    		setting= "drag=true;"+setting
    	}
    	if($("body").height()>height && $("body").width()>width){
    		$("body").css("overflow","hidden");
    	}
    	__open(url, "width="+width+";height="+height+";center=true;fresh=true;"+setting);
    }
}
//调用方法 节日弹窗( 要跳转的地址 , 设置层的样式 , 宽度 , 高度 )
function doQueryMessC(sendurl,where,width,height) {
 	doCloseWin();
 	if(sendurl!=""){
 		var url = "";
 		if(sendurl.indexOf(_c)!=-1||sendurl.indexOf("http://")!=-1){
 			url = sendurl + where;
 		}else{
    		url = _c+sendurl + where;
    	}
    	__open(url, "width="+width+";height="+height+";center=true;fresh=true;drag=true;");
    }
}
//关闭按钮
function doclose(){
	window.parent.__close();
}
function doCloseWin() {
     __closes();
}
/**
 * close all the inner window
 */
function __closes() {
  if (window.STATICCLASS_JWINDOW!=null) {
    window.STATICCLASS_JWINDOW.close();
    if (window.JWINDOW_AFTERCLOSE!=null) {
      window.JWINDOW_AFTERCLOSE();
      window.JWINDOW_AFTERCLOSE = null;
    }
  }
  if (window.STATICCLASS_XWINDOW!=null) {
    window.STATICCLASS_XWINDOW.close();
    if (window.XWINDOW_AFTERCLOSE!=null) {
      window.XWINDOW_AFTERCLOSE();
      window.XWINDOW_AFTERCLOSE = null;
    }
  }
}
/**
 * close the inner JWINDOW
 */
function __close() {
	$("body").css("overflow","auto");
	if (window.STATICCLASS_JWINDOW!=null) {
		window.STATICCLASS_JWINDOW.close();
		__unglass();   //显示select
		if (window.JWINDOW_AFTERCLOSE!=null) {
		  window.JWINDOW_AFTERCLOSE();
		}
	}
	closecallback();
}
function closecallback() {

}
/**
 * hide the glass
 */
function __unglass() {
  if (window.__glassNode!=null) {
    __glassNode.style.display = "none";
    //用jquery 修改兼容性
    var sels = $("SELECT");
    for (var i=0,len=sels.length;i<len;i++) {
      sels.get(i).style.visibility  = "visible";
    }
  }
}
/**
 * open an inner window
 */
function __open($sUrl, $params, $fct) {
  if (window.STATICCLASS_JWINDOW==null) {
    window.STATICCLASS_JWINDOW = new JWindow();
  }
  __glass();
  window.STATICCLASS_JWINDOW.open($sUrl, $params);
  window.JWINDOW_AFTERCLOSE = $fct;
}

/**
 * show and glass
 */
function __glass() {
  if (window.__glassNode==null) {
    window.__glassNode = document.createElement("DIV");
    __glassNode.id = "#JGlass";
    __glassNode.style.cssText = "position:absolute;left:0;top:expression(document.body.scrollTop);z-Index:150;width:expression(document.body.clientWidth);height:expression(document.body.clientHeight);";
    __glassNode.style.backgroundColor = "transparent";
    __glassNode.innerHTML = "<table style='height:100%;width:100%;font-size:12px'><tr><td align=center valign=middle></td></tr></table>";
    document.body.appendChild(__glassNode);
  }else {
    __glassNode.style.display = "";
  }
  //用jquery 修改兼容性
   var sels = $("SELECT");
    for (var i=0,len=sels.length;i<len;i++) {
      sels.get(i).style.visibility  = "hidden";
    }
}
/**
 * uuid static class it can generate an global uuid number it's char(36)
 */
function Uuid() {
  var _chars = "0123456789abcdef";
  this.create = create;
  function create() {
    var uuid = hex(new Date().getTime()) + "-" +
               hex(random()).substr(0,4) + "-" +
               hex(random()).substr(1,4) + "-" +
               hex(random()).substr(2,4) + "-" +
               hex(random()).substr(3,4) +
               hex(random()).substr(4,4) +
               hex(random()).substr(2,4);
    return uuid;
  };

  /**
   * random number
   */
  function random() {
    return Math.random() * 10000000000;
  };

  /**
   * translate the number to hex
   */
  function hex($num) {
    var str = "";
    for(var j = 7; j >= 0; j--)
      str += _chars.charAt(($num >> (j * 4)) & 0x0F);
    return str;
  };
}
/**
 * and window inner window use layer to be the layout
 */
function JWindow() {
  var _panel = new JPanel();
  var _panelNode = _panel.getNode();
  _panelNode.className = "app-jwin-page-bgcolor";
  var _shadowSize = 2;
  var _shadowColor = "#D5D9DA"; //#D5D9DA  #888888 #4875AC
  var _style  = "BACKGROUND-COLOR: ;PADDING:2 3 2 8;VERTICAL-ALIGN: bottom;letter-spacing:2px;color: black;font-weight:bold;font-family:宋体;font-size:14px;border-width:0 0 1 0;border-color:#CCCCCC;border-style:solid;height:24px;background-image:url('"+ _c + _image +"/img/jwin/b2.gif');background-repeat:repeat-x;";
  var _style1 = "BACKGROUND-COLOR: ;PADDING:0 5 4 3;VERTICAL-ALIGN: bottom;font-weight: normal;letter-spacing:4px;color: white;border-width:0 0 1 0;border-color:#CCCCCC;border-style:solid;height:24px;background-image:url('"+ _c + _image +"/img/jwin/b2.gif');background-repeat:repeat-x;";
  var _sHtml = "<table cellspacing=0 cellpadding=0 ><tr><td><TABLE style='BACKGROUND-COLOR: white;TABLE-LAYOUT: fixed;WIDTH: 300px;HEIGHT: 100%;BORDER: 1px solid;BORDER-COLOR: #808080;' cellSpacing=0><TR height=18><TD style=\"" + _style + "\">" +
               "</TD><td style=\"" + _style1 + "\" align=right valign=top><img style='cursor:hand' src="+ _c + _image +"/img/jwin/c.gif title='关闭窗口'></td></TR><tr><td colspan=2 align=center><img src="+ _c + _image +"/img/rloading.gif align=absmiddle></td></tr>" +
               "<TR><TD style='HEIGHT:100%;padding:0px;margin:0px' colspan=2>" +
               "<iframe name=JuiceFrame style='width:100%;height:100%' src='about:blank' FRAMEBORDER=0 ></iframe>" +
               "</TD></TR></TABLE></td><td valign=top  style='padding-top:" + _shadowSize + "px;width:" + _shadowSize + "px'>" +
               "<div style='width:100%;background-color:" + _shadowColor + "'></div></td></tr><tr><td style='padding-left:" + _shadowSize + "px;height:" + _shadowSize + "px;' colspan=2>" +
               "<div style='width:100%;height:100%;background-color:" + _shadowColor + ";font-size:1px'></div></td></tr></table>";
  _panelNode.innerHTML = _sHtml;
  var _domPanel = _panelNode.children[0].rows[0].cells[0].children[0];
  var _domBar = _domPanel.rows[0];
  var _domLoadStatusTr = _domPanel.rows[1];
  var _domFrameTr = _domPanel.rows[2];
  var _domFrame = _domFrameTr.cells[0].children[0];
  var _domClose = _domBar.cells[1].children[0];
	//修改兼容性
	if (window.addEventListener != undefined ){
	    _domClose.addEventListener("click", __close, false); 
	} else if (window.attachEvent != undefined){
	    _domClose.attachEvent("onclick", __close);
	}
  var _bLoading = false;
  var _lastUrl = null;
  var _params = null;
  var _lW = null;
  var _lH = null;
  var _bMax = false;
  var _domVLine = _panelNode.children[0].rows[0].cells[1].children[0];
	//修改兼容性
	if (window.addEventListener != undefined ){
	    _domBar.addEventListener("dblclick", maxsize, false); 
	} else if (window.attachEvent != undefined){
	    _domBar.attachEvent("ondblclick", maxsize);//双击最大化
	}

  /**
   * open and jwin
   */

  this.getContentWin = getContentWin;
  function getContentWin() {
    return _domFrame.contentWindow;
  }
  this.open = open;
  function open($sUrl, $params) {
  
    if ($sUrl.indexOf("/")==0 && $sUrl.indexOf(_c)!=0) {
      $sUrl = _c + $sUrl;
    }
    _params = __parseParams($params)
   // _params = $params;
	//判断是否要关闭按钮
    if (_params['noclose']==true) {
      _domClose.style.display = "none";
    }else {
      _domClose.style.display = "";
    }
    if (_params['nohead']==true) {
      _domBar.style.display = "none";
    }else {
      _domBar.style.display = "";
    }
	if (_lastUrl==$sUrl) {
      if (_params['fresh']!=true) {
        _panel.setVis(true);
      }else {
        setUI("LOADING");
      }
    }else {
      _lastUrl = $sUrl;
      setUI("LOADING");
    }
    
	/**设置移动属性*/
    if (_params['drag']==true) {
      _domBar.style.cursor = "move";
      __getDragable().add(_domBar, _panelNode);
    }else {
      _domBar.style.cursor = "default";
      __getDragable().remove(_domBar);
    }
  };

  /** is it the window it's opened */
  this.isOpen = isOpen;

  function isOpen() {
     return _panelNode.style.visibility=="visible";
  };

  /** when a page load if it has the special style then fix it */
  this.loadFix = loadFix;
  function loadFix() {
    if (_domFrame.readyState!="complete") {
      setTimeout(loadFix, 100);
      return;
    }
    var db = _domFrame.contentWindow.document.body;
    var p = db.getAttribute("tt.jwin");
    if (p!=null) {
      _params = __parseParams(p);
      updateVision();
    }
    //__hideSelects(_panelNode);
    /** change the docuemnt title */
    setTitle();
    _domBar.style.display = "";
    db.style.display = "";
  };
  function setTitle() {
  	var dbtitle;
  	try{
    	dbtitle = _domFrame.contentWindow.document.title;
    }catch(e){
    	dbtitle = _domFrame.document.title;
    }
    //_domBar.cells[0].innerHTML = "<img src=" + _c + "/parts/images/ie.gif align=absbottom style='margin-right:3px'>" + _domFrame.contentWindow.document.title;
    _domBar.cells[0].innerHTML = "" + dbtitle + "&nbsp;";
  }
  /** set up the ui vision */
  function setUI($type) {
	//韩飞 修改 兼容性
  	try{ if(event); }catch(e){ event = getEvent(); }
    switch($type) {
      case "LOADING":
        if (event!=null) {
            _panelNode.style.top = event.y + document.body.scrollTop;
            var ex = event.x + document.body.scrollLeft;
            if (ex + 300>document.body.clientWidth) {
              ex = ex - 300;
            }
            _panelNode.style.left = ex;
        }

        _domPanel.style.height = "auto";
        _domPanel.style.width = "300";
        _domBar.cells[0].innerHTML = "正在读取页面...";
        _domLoadStatusTr.style.display = "";
        _domFrameTr.style.display = "none";
        //修改兼容性
        if(document.attachEvent){ 
			_domFrame.onreadystatechange = notify;
		} else {
			setUI("COMPLETE");
		} 
        _panel.setVis(true);
        _bLoading = true;
        _domFrame.src = __noCacheUrl(_lastUrl);
        fixVLine();
        break;
      case "COMPLETE":
      	var db;
      	try{
        	db = _domFrame.contentWindow.document.body;
        }catch(e){
        	db = _domFrame.document.body;
        }
        if (db.getAttribute("tt.height")!=null) {
          _params['height'] = db.getAttribute("tt.height");
        }
        updateVision();
        setTitle();
        _domFrameTr.style.display = "";
        _domLoadStatusTr.style.display = "none";
        _domFrame.onreadystatechange = new Function("return false");
        _bLoading = false;
		__killSelects(_panelNode);
        /** set an attribute that i am in a jwin */
        db.setAttribute("tt.isJwindow", "true");
        if (typeof(_domFrame.contentWindow.onJwinComplete)!="undefined") {
          var w = _domFrame.contentWindow;
          checkComplete();
          function checkComplete($fct) {
            if (w._pageFullLoaded==false) {
              setTimeout(checkComplete, 10);
              return;
            }
            w.onJwinComplete();
          }
        }
        break;
      case "HIDE":
        _panel.setVis(false);
        return;
        break;
      case "MAXSIZE":
        if (!_bMax) {
          _lW = _domPanel.style.width;
          _lH = _domPanel.style.height;
          _domPanel.style.width = document.body.clientWidth - 20;
          _domPanel.style.height = document.body.clientHeight - 20;
          __center(_panelNode);
          _bMax = true;
        }else {
          _domPanel.style.width = _lW;
          _domPanel.style.height = _lH;
          __center(_panelNode);
          _bMax = false;
        }
        fixVLine();
        break;
    }
    //__hideSelects(_panelNode);
  }
  /**
   * change the jwindow's position
   */
  function updateVision() {
    /** set up the win style by the params */
      var sw = top._sw;
      /** if 800*600 use relate  */
      if (typeof(sw)=="undefined") {
        sw = screen.availWidth;
      }

      if (sw<1000) {
          _params['relate'] = 0.97;
      }
	if(_params['maxsize']==true){
      _domPanel.style.width = document.body.clientWidth * 0.97;
      _domPanel.style.height = document.body.clientHeight * 0.97;
      _params['float']="lefttop";
	}else if (_params['relate']!=null) {
      _domPanel.style.width = document.body.clientWidth * _params['relate'];
      _domPanel.style.height = document.body.clientHeight * _params['relate'];
    }else {
      /** 如果是大字体模式时有提供高与宽的都增加40*/
      if ("B"==_MY_SET['Style']) {
        if (_params['width']!=null) {
          _params['width'] = _params['width']*1+60;
        }
        if (_params['height']!=null) {
          _params['height'] = _params['height']*1+50;
        }
      }else {

        if (_params['width']!=null) {
          _params['width'] = _params['width']*1+20;
        }
        if (_params['height']!=null) {
          _params['height'] = _params['height']*1+30;
        }
      }

      if (_params['width']!=null) {
        _domPanel.style.width = _params['width'];
      }
      if (_params['height']!=null) {
        _domPanel.style.height = _params['height'];
      }
    }
    if (_params['center']!=null) {
      __center(_panelNode);
    }
	//设置在事件触发位置显示
    if (_params['eventPos']!=null) {
      var p = __getEventPos();
      _panelNode.style.top = p.y;
      _panelNode.style.left = p.x;
    }
    if (_params['float']=="righttop") {
      _panelNode.style.top = 2;
      _panelNode.style.left = document.body.clientWidth - _panelNode.clientWidth - 2;
    }else if (_params['float']=="lefttop") {
      _panelNode.style.top = 2;
      _panelNode.style.left = 2;
    }
    fixVLine();
  }

  /**
   * 动态设置window的属性，这可以运行页面时用脚本来动态改变JWin的属性
   */
  this.setJWinParams = setJWinParams;

  function setJWinParams($action, $params) {
    if ($action=="reset") {
      if ($params.nohead==true) {
        _domBar.style.display = "none";
      }
      if ($params.width!=null) {
        _domPanel.style.width = $params.width;
      }
      if ($params.height!=null) {
        _domPanel.style.height = $params.height;
        _panelNode.style.height = $params.height;
      }
      if ($params.center==true) {
        __center(_panelNode, _domPanel, 0, -50);
      }

      fixVLine();
      __hideSelects(_panelNode);
    }
  };

  function fixVLine() {
    _domVLine.style.height = 0;
    var p = _domVLine.parentElement.clientHeight - _shadowSize;
    _domVLine.style.height = p>0?p:0;
  }

  function maxsize() {
    setUI("MAXSIZE");
	__killSelects(_panelNode);
  }

  /**
   * close the panel
   */
  function doClose() {
    if (typeof(_domFrame.contentWindow.__jwindow_close)!="undefined") {
		_domFrame.src = "about:blank";
    }
  	try{
    	_domFrame.contentWindow.__pageOut = false;
    }catch(e){
    	_domFrame.document.__pageOut = false;
    }
    setUI("HIDE");
    //__showSelects();
  }

  this.close = close;
  function close() {
    doClose();

    /** 在关闭时触发子窗口的 _onclose 事件 */
    var ocFct = _domFrame.contentWindow._onClose;
    if (typeof(ocFct)!="undefined") {
      for (var i in ocFct) {
        ocFct[i]();
      }
    }
  };

  function closexp() {
    window.__close();
  }

  /**
   * when done
   */
  function notify() {
    if (_domFrame.readyState!="complete"){
      return;
    }
    setUI("COMPLETE");
  }
}
/**
 * JPanel it's the global panel that any layer style object will need it
 */
function JPanel() {
  var _sHtml = "<div style='position:absolute; left:2px; top:2px;z-index:199;color:black;visibility:hidden'></div>";
  var _node = __createElementByHTML(_sHtml);
  document.body.insertAdjacentElement("beforeEnd", _node);
  /**
   * get the root node
   */
  this.getNode = function() {
    return _node;
  };

  /**
   * show or hidden the panel
   */
  this.setVis = setVis;
  function setVis($bType) {
    _node.style.visibility = $bType?"visible":"hidden";
    if ($bType) {
      __killSelects(_node);
    }else {
      __releaseSelects();
    }
    _node.style.display = $bType?"":"none";
  };
}
/**
 * drag control set an object to be dragable 移动属性
 */
function Drag() {
  var _hm = [];
  var _activeObj = null;
  var _ctObj = null;
  var _x = null;
  var _y = null;
  var x = null;
  var y = null;
  var _os = null;
  var _nX = null;
  var _nY = null;

  /**
   * add an object to be dragable
   */
  this.add = add;
  function add($domCt, $domMove) {
	//修改兼容性
	if (window.addEventListener != undefined ){
	    $domCt.addEventListener("contextmenu", disable, false);
	    $domCt.addEventListener("dragstart", disable, false); 
	    $domCt.addEventListener("selectstart", disable, false); 
	    $domCt.addEventListener("mousedown", mousedown, false);  
	} else if (window.attachEvent != undefined){
	    $domCt.attachEvent("oncontextmenu", disable);
	    $domCt.attachEvent("ondragstart", disable);
	    $domCt.attachEvent("onselectstart", disable);
	    $domCt.attachEvent("onmousedown", mousedown);
	}

    $domCt.setAttribute("tt.drag.control", "true");

    if ($domMove==null)
      $domMove = $domCt;
    _hm[__setDomUuid($domCt)] = $domMove;
  };

  /**
   * remove the object's dragable style
   */
  this.remove = remove;
  function remove($domCt) {
  	if ($domCt.removeEventListener != undefined ){
  		$domCt.removeEventListener("contextmenu", disable,false);
	    $domCt.removeEventListener("dragstart", disable,false);
	    $domCt.removeEventListener("selectstart", disable,false);
	    $domCt.removeEventListener("mousedown", mousedown,false);
	} else {//if ($domCt.detachEvent != undefined){
	    $domCt.detachEvent("oncontextmenu", disable);
	    $domCt.detachEvent("ondragstart", disable);
	    $domCt.detachEvent("onselectstart", disable);
	    $domCt.detachEvent("onmousedown", mousedown);
	}
    _hm[__getDomUuid($domCt)] = null;
  };

  /**
   * disalbed the event
   */
  function disable() {
  	//修改兼容性
    getEvent().returnValue = false;
    getEvent().cancelBubble = true;
    return false;
  }

  /**
   * when mouse down track the move status
   */
  function mousedown() {
    _ctObj = myEvent();
    _ctObj = __getReal(_ctObj, "tt.drag.control", "true");
    var uuid = __getDomUuid(_ctObj);
    if (uuid==null) {
      return;
    }

    _ctObj.setCapture(true);
    _activeObj = _hm[uuid];
    //top(_activeObj);
	//修改兼容性
	if (window.addEventListener != undefined ){
	    document.addEventListener("mousemove", mousemove, false); 
	    document.addEventListener("mouseup", mouseup, false);  
	} else if (window.attachEvent != undefined){
	    document.attachEvent("onmousemove", mousemove);
    	document.attachEvent("onmouseup", mouseup);
	}
    _x = getEvent().x;
    _y = getEvent().y;
  }

  /**
   * mouse over tracker
   */
  function mousemove() {
  	try{
    x = getEvent().x - _x ;
    y = getEvent().y - _y ;
    _os = _activeObj.style;
    if (_os.left=="") {
      _nX = x;
    }else {
      _nX = parseInt(_os.left) + x;
    }

    if (_os.top=="") {
      _nY = y;
    }else {
      _nY = parseInt(_os.top) + y;
    }
    if (_os!=null) {
      _os.left = _nX ;
      _os.top = _nY;
    }
    _x = getEvent().x;
    _y = getEvent().y;
    __killSelects(_activeObj);
    }catch(e){}
  }

  /**
   * when mouse done
   */
  function mouseup() {
  	//修改兼容性
  	if (window.addEventListener != undefined ){
	    document.removeEventListener("mousemove", mousemove, false); 
	    document.removeEventListener("mouseup", mouseup, false); 
	} else if (window.detachEvent != undefined){
	    document.detachEvent("onmousemove", mousemove);
    	document.detachEvent("onmouseup", mouseup);
	}
    _ctObj.releaseCapture();
    //check the window it's in document area,if no then restore
    if (_os==null) {
      return;
    }
    var l = parseInt(_os.left);
    var t = parseInt(_os.top);
    if (l<0) {
      _os.left = "5px";
    }
    if (t<0) {
      _os.top = "5px";
    }
  }

  /**
   * this use an hard code 20 here, i think when a page's layer not over 20,this it's
   * ok, are you think so?
   */
  function top($dom) {
    $dom.style.zIndex = ++__global_top;
  }
}
/**
 * 获得触发事件的坐标
 */
function __getEventPos() {
  var x = getEvent().x + document.body.scrollLeft;
  var y = getEvent().y + document.body.scrollTop;
  return {'x':x,'y':y};
}
/**
 * set and object to be dragable
 */
function __getDragable() {
  if (window.STATICCLASS_DRAG==null) {
    window.STATICCLASS_DRAG = new Drag();
  }
  return window.STATICCLASS_DRAG;
}
/**
 * create and html dom object by html
 */
function __createElementByHTML($html) {
  var node = document.createElement("SPAN");
  node.innerHTML = $html;
  return node.children[0];
}
/**
 * parse the params for the given string like "width=100;fresh=true;max=true"
 */
function __parseParams($params) {
  var reg = /([^\=\;]*)\=([^\;]*)/gi;
  var rt = [];
  if ($params==null)
    return rt;
  var ar = $params.match(reg);
  var t = null;
  for (var i=0; i<ar.length; i++) {
    t = ar[i].split("=");
    if (t[1].toUpperCase()=="TRUE") {
      rt[t[0]] = true;
    }else {
      rt[t[0]] = t[1];
    }
  }
  return rt;
}
/**
 * 隐藏页面上select控件
 */
function __hideSelects($node) {
	//用jquery 修改兼容性
  var s = $("SELECT");
  for (var i=0; i<s.length; i++) {
    s.get(i).style.visibility = "hidden";
  }
}
/**
 * 显示页面上select控件
 */
function __showSelects() {
	//用jquery 修改兼容性
  var s = $("SELECT");
  for (var i=0; i<s.length; i++) {
    s.get(i).style.visibility = "visible";
  }
}
/**
 * release the select dom object
 */
function __releaseSelects($dom) {
  if (window.CACHE_SELECTS!=null) {
    for (var i=0, len=window.CACHE_SELECTS.length; i<len; i++) {
      window.CACHE_SELECTS[i].style.visibility = "visible";
    }
  }
}
/**
 * kill the page's select object in same bound with the given dom obj
 */
function __killSelects($dom) {
  if (window.CACHE_SELECT==null) {
    window.CACHE_SELECTS = [];
    window.CACHE_SELECTS_BOUNDS = [];
    //用jquery 修改兼容性
    var domSelects = $("SELECT");
    for (var i=0,len=domSelects.length; i<len; i++) {
      window.CACHE_SELECTS.push(domSelects.get(i));
      window.CACHE_SELECTS_BOUNDS.push(__getBound(domSelects.get(i)));
    }
  }
	//用jquery 修改兼容性
  for (var i=0, len=window.CACHE_SELECTS_BOUNDS.length; i<len; i++) {
    if (__isInBoundX(__getBound($dom), window.CACHE_SELECTS_BOUNDS[i])) {
      window.CACHE_SELECTS[i].style.visibility = "hidden";
    }else {
      window.CACHE_SELECTS[i].style.visibility = "visible";
    }
  }
}
/**
 * all trust the given dom object it's an array type
 */
function __arrayDoms($doms) {
  if ($doms==null)
    return new Array();
  for (i in $doms) {
    if (i=="length") {
      var tmp = new Array();
      for (var i=0; i<$doms.length; i++) {
        tmp.push($doms[i]);
      }
      return tmp;
    }
    break;
  }
  return new Array($doms);
}
/**
 * get object bound
 */
function __getBound($dom) {
  var pLt = __getPos($dom);
  var pRb = new Position(pLt.x + $dom.clientWidth, pLt.y + $dom.clientHeight);
  return new Bound(pLt.x, pLt.y, pRb.x, pRb.y);
}
/**
 * get object position
 */
function __getPos($dom) {
  var iXPos = 0;
  var iYPos = 0;
  while ($dom!=null) {
    iXPos += $dom["offsetLeft"];
    iYPos += $dom["offsetTop"];
    $dom = $dom.offsetParent;
  }
  return new Position(iXPos, iYPos);
}
/**
 * position model
 */
function Position(iX, iY) {
  this.x = iX;
  this.y = iY;
}
/**
 * bound model
 */
function Bound(iX1, iY1, iX2, iY2) {
  this.lt = new Position(iX1, iY1);
  this.rb = new Position(iX2, iY2);
}
/**
 * rect model
 */
function Rect(iW, iH) {
  this.w = iW;
  this.h = iH;
}
/**
 * check two object is it in area cross
 */
function __isInBoundX(boundA, boundB) {
  if (__inBound(boundA, boundB)) {
    return true;
  }
  if (__inBound(boundB, boundA)) {
    return true;
  }
  return false;
}
/**
 * check bound in same area
 */
function __inBound(boundA, boundB) {
  if (boundB.lt.x>boundA.lt.x &&
      boundB.lt.x<boundA.rb.x &&
      boundB.lt.y>boundA.lt.y &&
      boundB.lt.y<boundA.rb.y) {
      return true;
  }
  if (boundB.lt.x>boundA.lt.x &&
      boundB.lt.x<boundA.rb.x &&
      boundB.rb.y>boundA.lt.y &&
      boundB.rb.y<boundA.rb.y) {
      return true;
  }
  if (boundB.rb.x>boundA.lt.x &&
      boundB.rb.x<boundA.rb.x &&
      boundB.lt.y>boundA.lt.y &&
      boundB.lt.y<boundA.rb.y) {
      return true;
  }
  return false;
}
/**
 * path the url to be no cache, ie sometime very stupid it's not load the new data
 * when the url it's same, and also the network proxy gate can cause the problem too
 * so add the random url code to the src url and get rid of this
 */
function __noCacheUrl($url) {
  $url = __setUrlParam($url, "_JUICE_URL_UUID", __uuid());
  return $url;
}
/**
 * set the url params, when the url already have the value,it change it
 * if no it add it to the last
 */
function __setUrlParam($url, $name, $value) {
  var charFix = $url.indexOf("?")!=-1?"&":"?";
  var nIdxStart = $url.indexOf("&" + $name + "=");
  if (nIdxStart==-1) {
    nIdxStart = $url.indexOf("?" + $name + "=");
  }
  if (nIdxStart==-1) { //no params
    $url = $url + charFix + $name + "=" + $value;
    return $url;
  }
  /**
   * from upper it found the name start and then try to found the value end position
   * the value and be empty or it's the url end
   */
  var nIdxEnd = $url.indexOf("&", nIdxStart+1);
  var urlPreFix = $url.substr(0, nIdxStart+1);
  var urlEndFix = "";
  if (nIdxEnd!=-1) {
    urlEndFix = $url.substr(nIdxEnd);
  }
  $url = urlPreFix + $name + "=" + $value + urlEndFix;
  return $url;
}
/**
 * get the uuid
 */
function __uuid() {
  return _Uuid.create();
}
/**
 * center the dom object to the current page
 */
function __center($dom) {
  var rectPage = new Rect(document.body.clientWidth, document.body.clientHeight);
  var rectPanel = new Rect($dom.clientWidth, $dom.clientHeight);
    //修改兼容性
    if (window.addEventListener != undefined ){
		$dom.style.left = 5;
		$dom.style.top = 5;
	}
  if($dom.clientWidth<document.body.clientWidth && $dom.clientWidth!=0){
  	$dom.style.left = rectPage.w/2 - rectPanel.w/2;
  }else{
	$dom.style.left = 50;
  }
  if($dom.clientHeight<document.body.clientHeight && $dom.clientWidth!=0){
  	$dom.style.top = document.body.scrollTop + rectPage.h/2 - rectPanel.h/2;
  }else{
  	$dom.style.top = 50;
  }
}
/**
 * set and juice dom uuid to an object
 */
function __setDomUuid($dom) {
  $dom.setAttribute("tt.uuid", __uuid());
  return __getDomUuid($dom);
}
/**
 * get juice dom uuid
 */
function __getDomUuid($dom) {
  return $dom.getAttribute("tt.uuid");
}
/** 移动层
 * look up the dom's parent element when the condition match
 */
function __getReal($dom, $attr, $value) {
  while (($dom != null) && ($dom.tagName != "BODY")) {
    if ($dom.getAttribute($attr) == $value) {
      return $dom;
    }
    $dom = $dom.parentElement;
  }
  return null;
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

//兼容性 window.event.srcElement
function myEvent(){
	var evt= getEvent();
	var element=evt.srcElement || evt.target;
	return element;
}
//兼容性 window.event
function getEvent() {
	if(document.all) {
		return window.event;//如果是ie
	}
	func=getEvent.caller;
	while(func!=null) {
		var arg0=func.arguments[0];
		if(arg0) {
			if((arg0.constructor==Event || arg0.constructor ==MouseEvent)
			||(typeof(arg0)=="object" && arg0.preventDefault && arg0.stopPropagation)) {
				return arg0;
			}
		}
		func=func.caller;
	}
	return null;
}
