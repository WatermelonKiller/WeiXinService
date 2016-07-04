<!--start
/*
 * added by liu_xc 2004.6.11
 * 约束输入框的内容
 * 适用于IE5.0及以上版本
 * oObj: 输入框控件对象
 * reg:  正则表达式
 * isChinese：是否允许打开输入法，true 允许打开，即允许输入中文，false 不能打开，默认为不允许打开
 * usage:
 *
 *   //只能输入数字和大小写字母
 *	<BODY onLoad="regInputRestriction(document.all.txt,/^[0-9a-zA-Z]*$/)">
 *	<input id="txt">
 * 	</BODY>
 */
function regInputRestriction(oObj,reg,isChinese)
{
	function regInput(obj, reg, inputStr)
	{
		var docSel	= document.selection.createRange();
		if (docSel.parentElement().tagName.toLowerCase() != "input")
			return false;
		oSel = docSel.duplicate();
		oSel.text = "";
		var srcRange = obj.createTextRange();
		oSel.setEndPoint("StartToStart", srcRange);
		var str = oSel.text + inputStr + srcRange.text.substr(oSel.text.length);
		return reg.test(str);
	}
	//限制输入法是否允许打开
	if(isChinese == null)
		isChinese = false;
	if(isChinese)
		oObj.style.imeMode = "auto";
	else
		oObj.style.imeMode = "disabled";
		
	//注册事件
	oObj.onkeypress = function()
	{		
		return regInput(this,reg,String.fromCharCode(event.keyCode));//输入时激发
	}
	oObj.onpaste = function()
	{
		return regInput(this,reg,window.clipboardData.getData('Text'));//粘贴时激发
	}
	oObj.ondrop = function()
	{
		return regInput(this,reg,event.dataTransfer.getData('Text'));//拖拽时激发
	}
}

//取消所有验证
function clearAll(oObj)
{
	oObj.onkeypress = null ;
	oObj.onpaste = null ;
	oObj.ondrop = null ;
}
//-->