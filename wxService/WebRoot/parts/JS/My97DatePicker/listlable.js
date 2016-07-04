/*业务开始时间*/
function timestart(){
	WdatePicker({startDate:'%y-%M-%d',
		 dateFmt:'yyyy-MM-dd',
		 maxDate:'#F{$dp.$D(\'endTime\')}',
		 readOnly:true,
		 alwaysUseStartDate:false,
		 autoPickDate:true});
};

/*业务结束时间*/
function timeend(){
	WdatePicker({startDate:'%y-%M-%d',
		 dateFmt:'yyyy-MM-dd',
		 minDate:'#F{$dp.$D(\'beginTime\')}',
		 readOnly:true,
		 alwaysUseStartDate:false,
		 autoPickDate:true});
};
/*添加选择时间*/
function WdateTime(){
	WdatePicker({startDate:'%y-%M-%d',
		 dateFmt:'yyyy-MM-dd',
		 minDate:'#F{$dp.$D(\'dateTime\')}',
		 readOnly:true,
		 alwaysUseStartDate:false,
		 autoPickDate:true});
};
/*会议开始时间*/
function startTime(){
	WdatePicker({startDate:'%y-%M-%d',
		 dateFmt:'yyyy-MM-dd HH:mm:ss',
		 minDate:'#F{$dp.$D(\'startDTime\')}',
		 readOnly:true,
		 alwaysUseStartDate:false,
		 autoPickDate:true});
};
/*会议结束时间*/
function endTime(){
	WdatePicker({startDate:'%y-%M-%d',
		 dateFmt:'yyyy-MM-dd HH:mm:ss',
		 minDate:'#F{$dp.$D(\'endDTime\')}',
		 readOnly:true,
		 alwaysUseStartDate:false,
		 autoPickDate:true});
};

