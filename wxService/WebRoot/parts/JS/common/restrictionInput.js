<!--start
/*
 * added by liu_xc 2004.6.11
 * Լ������������
 * ������IE5.0�����ϰ汾
 * oObj: �����ؼ�����
 * reg:  ������ʽ
 * isChinese���Ƿ���������뷨��true ����򿪣��������������ģ�false ���ܴ򿪣�Ĭ��Ϊ�������
 * usage:
 *
 *   //ֻ���������ֺʹ�Сд��ĸ
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
	//�������뷨�Ƿ������
	if(isChinese == null)
		isChinese = false;
	if(isChinese)
		oObj.style.imeMode = "auto";
	else
		oObj.style.imeMode = "disabled";
		
	//ע���¼�
	oObj.onkeypress = function()
	{		
		return regInput(this,reg,String.fromCharCode(event.keyCode));//����ʱ����
	}
	oObj.onpaste = function()
	{
		return regInput(this,reg,window.clipboardData.getData('Text'));//ճ��ʱ����
	}
	oObj.ondrop = function()
	{
		return regInput(this,reg,event.dataTransfer.getData('Text'));//��קʱ����
	}
}

//ȡ��������֤
function clearAll(oObj)
{
	oObj.onkeypress = null ;
	oObj.onpaste = null ;
	oObj.ondrop = null ;
}
//-->