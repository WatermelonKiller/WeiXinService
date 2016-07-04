//添加行功能  引用 jquery
function addTRjq(obj) {
	var tr = obj.parentNode.parentNode;
	var tab = tr.parentNode;
	if(obj.title == "添加") {
		var trs = $(tr).clone();
		var newTr = trs.children();
		for(var i = 0; i < newTr.length; i++) {
			if(newTr.get(i).firstChild.tagName == "INPUT") {
				if(newTr.get(i).firstChild.type == "button"){
					continue;
			    }else{
					newTr.get(i).firstChild.value = "";
				}
			} else if (newTr.get(i).firstChild.tagName == "SELECT") {	
				newTr.get(i).firstChild.selectedIndex = 0;
			}
		}
		obj.title = "删除"; obj.innerHTML = "删除";
		tab.appendChild(trs.get(0));
	} else {
		tab.removeChild(tr);
	}
}
//添加行功能
function addTRsn(obj) {
	var tr = obj["parentNode"]["parentNode"];
	var tab = tr["parentNode"];
	if(obj["value"] == "添加") {
		var trs = tr["cloneNode"](1);
		var newTr = trs["childNodes"];
		for(var i = 0; i < newTr["length"]; i++) {
			if(newTr[i]["firstChild"]["tagName"] == "INPUT") {
				if(newTr[i]["firstChild"]["type"] == "button"){
					continue;
			    }else{
					newTr[i]["firstChild"]["value"] = "";
				}
			} else if (newTr[i]["firstChild"]["tagName"] == "SELECT") {	
				//newTr[i]["firstChild"]["value"] = "请选择";
			}
		}
		trs["lastChild"]["firstChild"]["value"] = "删除";
		tab["appendChild"](trs);
	} else {
		tab["removeChild"](tr);	
	}
}

//添加
function addTRs(obj) {
	var tr = obj["parentNode"]["parentNode"];
	var tab = tr["parentNode"];
	   if(obj["value"] == "添加") {
			var trs = tr["nextSibling"]["cloneNode"](1);
			var newTr = trs["childNodes"];
			for(var i = 0; i < newTr["length"]; i++) {
			  if(newTr[i]["firstChild"]["tagName"] == "INPUT") {
			    
			    if(newTr[i]["firstChild"]["type"] == "button"){
			     continue;}else{
			     
				newTr[i]["firstChild"]["value"] = "";
				}
			} else if (newTr[i]["firstChild"]["tagName"] == "SELECT") {	
				newTr[i]["firstChild"]["value"] = "请选择";
			}
		}
		trs["lastChild"]["firstChild"]["value"] = "删除";
		tab["appendChild"](trs);
	} else if(tab.childNodes.length>2) {
	    
		tab["removeChild"](tr);	
	}else{
	
	    alert("至少保留一条数");
	}
 	for(var i=1;i<tab.childNodes.length;i++){
 		tab.childNodes[i].firstChild.innerText = i;
 	}  
}

function addTRsd(obj) {
	var tr = obj["parentNode"]["parentNode"];
	var tab = tr["parentNode"];
	   if(obj["value"] == "添加") {
			var trs = tr["nextSibling"]["cloneNode"](1);
			var newTr = trs["childNodes"];
			for(var i = 0; i < newTr["length"]; i++) {
		  
			  if(newTr[i]["firstChild"]["tagName"] == "INPUT") {
			    
			    if(newTr[i]["firstChild"]["type"] == "button"){
			     continue;}else{
			     
				newTr[i]["firstChild"]["value"] = "";
				}
			} else if (newTr[i]["firstChild"]["tagName"] == "SELECT") {	
				newTr[i]["firstChild"]["value"] = "请选择";
			}
		}
		trs["lastChild"]["firstChild"]["value"] = "删除";
		tab["appendChild"](trs);
	} else if(tab.childNodes.length>2) {
	    
		tab["removeChild"](tr);	
	}else{
	
	    alert("至少保留一条数");
	}
	<!--songli update-->
 	for(var i=1;i<tab.childNodes.length;i++){
 		tab.childNodes[i].firstChild.innerText = i;
 	}  
}