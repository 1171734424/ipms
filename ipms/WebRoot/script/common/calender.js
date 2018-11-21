
function loadCalender() {
	var dateObj = new Date();
	var year = dateObj.getFullYear();
	var month = dateObj.getMonth() + 1;
	var date = dateObj.getDate();
	var current_date = year + "年" + month + "月" + date + "日";

	$("#calender_title").html("<font color='orange'>" + current_date + "</font>");
	
	var current_week = dateObj.getDay();
	var week;
	if(current_week == 0)  { week = "日"; }
	if(current_week == 1)  { week = "一"; }
	if(current_week == 2)  { week = "二"; }
	if(current_week == 3)  { week = "三"; }
	if(current_week == 4)  { week = "四"; }
	if(current_week == 5)  { week = "五"; }
	if(current_week == 6)  { week = "六"; }
	$("#calender_week").html("(周" + week + ")");
	var nl = showCal();
	$("#calender_nl").html("农历 : " + nl);
	
	getNowTime();
}

function getNowTime() {
	var dateObj = new Date();
	var hour = dateObj.getHours();
	var minutes = dateObj.getMinutes();
	var seconds = dateObj.getSeconds();
	minutes = minutes < 10 ? "0" + minutes : minutes;
	seconds = seconds < 10 ? "0" + seconds : seconds;
	$("#calender_time").html(hour + ":" + minutes + ":" + seconds);
}

function formatDate(date) {
	var y = date.getFullYear(); 
	var m = date.getMonth() + 1; 
	var d = date.getDate(); 
	return y + "" + (m < 10 ? "0" + m : m) + "" + (d < 10 ? "0" + d : d); 
}


