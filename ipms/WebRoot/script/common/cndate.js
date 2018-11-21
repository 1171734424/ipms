var CalendarData = new Array(100);
var madd = new Array(12);
var tgString = "甲乙丙丁戊己庚辛壬癸";
var dzString = "子丑寅卯辰巳午未申酉戌亥";
var numString = "一二三四五六七八九十";
var monString = "正二三四五六七八九十冬腊";
var weekString = "日一二三四五六";
var sx = "鼠牛虎兔龙蛇马羊猴鸡狗猪";

var cYear, cMonth, cDay, TheDate;
CalendarData = new Array(2635, 333387, 1701, 1748, 267701, 694, 2391, 133423,
		1175, 396438, 3402, 3749, 331177, 1453, 694, 201326, 2350, 465197,
		3221, 3402, 400202, 2901, 1386, 267611, 605, 2349, 137515, 2709,
		464533, 1738, 2901, 330421, 1242, 2651, 199255, 1323, 529706, 3733,
		1706, 398762, 2741, 1206, 267438, 2647, 1318, 204070, 3477, 461653,
		1386, 2413, 330077, 1197, 2637, 268877, 3365, 531109, 2900, 2922,
		398042, 2395, 1179, 267415, 2635, 661067, 1701, 1748, 398772, 2742,
		2391, 330031, 1175, 1611, 200010, 3749, 527717, 1452, 2742, 332397,
		2350, 3222, 268949, 3402, 3493, 133973, 1386, 464219, 605, 2349,
		334123, 2709, 2890, 267946, 2773, 592565, 1210, 2651, 395863, 1323,
		2707, 265877);

madd[0] = 0;
madd[1] = 31;
madd[2] = 59;
madd[3] = 90;
madd[4] = 120;
madd[5] = 151;
madd[6] = 181;
madd[7] = 212;
madd[8] = 243;
madd[9] = 273;
madd[10] = 304;
madd[11] = 334;

function GetBit(m, n) {
	return (m >> n) & 1;
}

function e2c() {
	TheDate = (arguments.length != 3) ? new Date() : new Date(arguments[0],
			arguments[1], arguments[2]);
	var total, m, n, k;
	var isEnd = false;
	var tmp = TheDate.getYear();
	if (tmp < 1900) {
		tmp += 1900;
	}
	total = (tmp - 1921) * 365 + Math.floor((tmp - 1921) / 4)
			+ madd[TheDate.getMonth()] + TheDate.getDate() - 38;
	if (TheDate.getYear() % 4 == 0 && TheDate.getMonth() > 1) {
		total++;
	}
	for (m = 0;; m++) {
		k = (CalendarData[m] < 4095) ? 11 : 12;
		for (n = k; n >= 0; n--) {
			if (total <= 29 + GetBit(CalendarData[m], n)) {
				isEnd = true;
				break;
			}
			total = total - 29 - GetBit(CalendarData[m], n);
		}
		if (isEnd) {
			break;
		}
	}
	cYear = 1921 + m;
	cMonth = k - n + 1;
	cDay = total;
	if (k == 12) {
		if (cMonth == Math.floor(CalendarData[m] / 65536) + 1) {
			cMonth = 1 - cMonth;
		}
		if (cMonth > Math.floor(CalendarData[m] / 65536) + 1) {
			cMonth--;
		}
	}
}

function GetcDateString() {
	var tmp = "", tmpM = "";
	tmp += (cDay < 11) ? "初" : ((cDay < 20) ? "十" : ((cDay < 30) ? "廿" : "三十"));
	if (cDay % 10 != 0 || cDay == 10) {
		tmp += numString.charAt((cDay - 1) % 10);
	}
	tmpM += monString.charAt(cMonth - 1);
	return tmpM + "月" + tmp;
}

function GetLunarDay(solarYear, solarMonth, solarDay) {
	// solarYear = solarYear<1900?(1900+solarYear):solarYear;
	if (solarYear < 1921 || solarYear > 2020) {
		return "";
	} else {
		solarMonth = (parseInt(solarMonth) > 0) ? (solarMonth - 1) : 11;
		e2c(solarYear, solarMonth, solarDay);
		return GetcDateString();
	}
}

var D = new Date();
var yy = D.getFullYear();
var mm = D.getMonth() + 1;
var dd = D.getDate();
var ww = D.getDay();
var ss = parseInt(D.getTime() / 1000);

if (yy < 100) {
	yy = "19" + yy;
}

function showCal() {
	return GetLunarDay(yy, mm, dd);
}


