
function loadWeather(cityName) {
	var cityId = getCityId(cityName);
	$.ajax({
		url: base_path + "/load_weather.do",
		type: "post",
		dataType: "json",
		data: { cityId: cityId },
		success: function(json) {
			if (json.error){
				$("#weathernew").html("暂无数据");
				$("#uvioresistantdata").html("<span style='margin-top:20px;'>暂无数据</span>");
			} else {
				manageWeatherdata(json);
			}
		},
		error: function(data){
		}
	});
}

function getCityId(cityName) {
	var cityId = "";
	var weather = {"province":[
		{"cityname":"江苏省","citycode":"101190101"},
		{"cityname":"南京市","citycode":"101190101"},
		{"cityname":"苏州市","citycode":"101190401"},
		{"cityname":"昆山市","citycode":"101190404"},
		{"cityname":"南通市","citycode":"101190501"},
		{"cityname":"太仓市","citycode":"101190408"},
		{"cityname":"吴县市","citycode":"101190406"},
		{"cityname":"徐州市","citycode":"101190801"},
		{"cityname":"宜兴市","citycode":"101190203"},
		{"cityname":"镇江市","citycode":"101190301"},
		{"cityname":"淮安市","citycode":"101190901"},
		{"cityname":"常熟市","citycode":"101190402"},
		{"cityname":"盐城市","citycode":"101190701"},
		{"cityname":"泰州市","citycode":"101191201"},
		{"cityname":"无锡市","citycode":"101190201"},
		{"cityname":"连云港市","citycode":"101191001"},
		{"cityname":"扬州市","citycode":"101190601"},
		{"cityname":"常州市","citycode":"101191101"},
		{"cityname":"宿迁市","citycode":"101191301"}
	]};
	
	$.each(weather.province, function(i) {
		if (weather.province[i].cityname == cityName) {
			cityId = weather.province[i].citycode;
			return false;
		}
	});

	return cityId;
}

function manageWeatherdata(data) {
	if(data){
		var img = "", word;
		var weathers = data.weatherinfo.weather;
		
		for(var index in weathers) {
			word = weathers[index];
			if (word == "晴") {
				img = "qing.png";
			} else if (word == "云") {
				img = "yun.png";
			} else if (word == "阴") {
				img = "yin.png";
			} else if (word == "雨") {
				img = "yu.png";
			} else if (word == "雪") {
				img = "xue.png";
			} else if (word == "雾") {
				img = "wu.png";
			}
		}
		var winfo = "<div class='weatherImg' align='center'><img width='66' height='60' src='../../images/weather/"
				+ img + "' /><br/>" + weathers + "<br/>" + data.weatherinfo.temp1 + "~" + data.weatherinfo.temp2 +"</div>";
		$("#weatherimg").html(winfo);
	}else{
		$("#weatherimg").html("暂无数据");
	}
}


