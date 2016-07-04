// 自动生成编号ajax 需要在js中添加  $(function(){ doLoadValues_soli(strNumber,tableName,tableValue) })
// 1.strNumber 为 自动生成编号开头的格式 入 HH20090207001 中的 HH
// 2.tableName 编号所在的表明
// 3.tableValue 编号字段名
var soli_num;
var specValue;
var selectTable;
var selectValue;
function doLoadValues_soli(strNumber,tableName,tableValue){
	with(document.forms[0]){
		specValue = strNumber;
		selectTable = tableName;
		selectValue = tableValue;
		soli_num = specValue.length + 11;
		kt.doAjaxSelectAll(selectTable,selectValue,"id=(select Max(id) from "+selectTable+")",function(list){	
			if(list!=null&&list!=""&&list.length>0){
				var mapValue = list[0];
				var oldNum = mapValue[selectValue];
				if(oldNum.length==soli_num){
					var thisDate = doSetNum();
					var oldValueDate = oldNum.substring((soli_num-11),(soli_num-3));
					if( Number(thisDate) > Number(oldValueDate)){
						eval(selectValue).value = specValue + thisDate + "001";
					}else{
						var num = Number(oldNum.substring((soli_num-3),soli_num))+1;
						if( num < 10 ){
							eval(selectValue).value = oldNum.substring(0,(soli_num-3)) + "00" + num;
						}else if( num >= 10 && num < 100 ){
							eval(selectValue).value = oldNum.substring(0,(soli_num-3)) + "0" + num;
						}else{
							eval(selectValue).value = oldNum.substring(0,(soli_num-3)) + num;
						}
					}
				}else{
					eval(selectValue).value = specValue + doSetNum() + "001";
				}
			}else{
				eval(selectValue).value = specValue + doSetNum() + "001";
			}
			doNextFunction_soli();
		});
	}
}
//自动生成当前年月日 类型为 2009/02/07. 
function doSetNum(){
	var returnNumValue = "";
	date = new Date();
	returnNumValue += date.getYear();
	if( (date.getMonth()+1) < 10 ){
		returnNumValue += "0"+ (date.getMonth()+1);
	}else{
		returnNumValue += (date.getMonth()+1);
	}
	if( date.getDate() < 10 ){
		returnNumValue += "0"+date.getDate();
	}else{
		returnNumValue += date.getDate();
	}
	return returnNumValue;
}
// 自动生成编号字段需要添加一个 onpropertychange="doValidateValue_soli(this)" 的方法.
function doValidateValue_soli(obj){
	kt.doAjaxSelectAll(selectTable,"id",selectValue+"='"+obj.value+"'",function(list){	
		if(list!=null&&list!=""&&list.length>0){
			var num = Number(obj.value.substring((soli_num-3),soli_num))+1;
			if( num < 10 ){
				obj.value = obj.value.substring(0,(soli_num-3)) + "00" + num;
			}else if( num >= 10 && num < 100 ){
				obj.value = obj.value.substring(0,(soli_num-3)) + "0" + num;
			}else{
				obj.value = obj.value.substring(0,(soli_num-3)) + num;
			}
		}
	});
}
// 生成编号的方法后可以运行的js方法.
function doNextFunction_soli(){}