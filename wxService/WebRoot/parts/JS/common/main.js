/* 关闭窗口 */
function CloseWindow(){
	if(confirm("您确定要退出本系统吗？")){
		top.window.close();
	}
}
//==================================================Home==================================================
/*
 * 点击主菜单改变菜单按钮的颜色
 */
var hwyy = 0;	//存储当前的下标
function setBackimages(obj,n) {
	MenusLt[n].background = "../parts/images/GTitle.gif";		//把当前点击的背景颜色改变
	if(n != hwyy) {
		MenusLt[hwyy].background = "../parts/images/GTitleH.gif";	//把原来的背景颜色还原回去
	}
	hwyy = n;	//把下标赋值给成员变量
}

//==================================================Home==================================================

function doLink(url,url2){
	var BottommainFrame = top.frames[1].BottommainFrame;
	BottommainFrame.location.href=url;
	top.frames[1].leftFrame.location.href=url2;
	top.frames[1].leftFrame.table_1.rows[0].children[1].innerText="AAAA";
}
//==================================================End===================================================
