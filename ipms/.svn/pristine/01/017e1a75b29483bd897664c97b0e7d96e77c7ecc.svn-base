package com.ideassoft.crm.controller;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ideassoft.util.JSONUtil;

@Controller
public class WeatherController {

	/*
	 * private static Logger logger = Logger.getLogger(WeatherController.class);
	 * 
	 * @RequestMapping("/load_weather.do") public void getWeatherInfo(String
	 * cityId, HttpServletResponse response) throws Exception { String message =
	 * ""; JSONObject result; String cityUrl = "http://nanjing.tianqi.com";
	 * 
	 * try { URL url = new URL(cityUrl); HttpURLConnection connect =
	 * (HttpURLConnection) url.openConnection(); connect.setDoInput(true);
	 * 
	 * connect.setConnectTimeout(30000); connect.setReadTimeout(30000);
	 * 
	 * BufferedReader in = new BufferedReader(new
	 * InputStreamReader(connect.getInputStream(), "GBK")); String line = null;
	 * StringBuffer scont = new StringBuffer(); while ((line = in.readLine()) !=
	 * null) { scont.append(line); } in.close(); in = null; url = null; message
	 * = scont.toString();
	 * 
	 * logger.debug("city [" + cityId + "] weather info [" + message + "]");
	 * 
	 * result = JSONObject.fromObject(message); } catch (Exception e) {
	 * logger.error("city [" + cityId + "] error info [" + e.getMessage() +
	 * "]"); result = JSONObject.fromObject("{'error':'network error'}"); }
	 * 
	 * JSONUtil.responseJSON(response, result); }
	 */
	
	/*@RequestMapping("/load_weather.do")
	public void getWeatherInfo(HttpServletResponse response) throws Exception {
		String linkHref = "";
		String linkText = "";
		String linkTextcont = "";
		String lastlinkText = "";
		String oneWeather = "";
		String anotherWeather = "";
		Document doc = Jsoup.connect("http://nanjing.tianqi.com").get();
		Elements todayWeather = doc.select(".cDRed");
		linkHref = todayWeather.text();
		if (linkHref == null || linkHref.equals("")) {
			linkHref = "晴";
		} else {
			linkHref = todayWeather.text();
		}
		Elements huaWeather = doc.select("#t_temp font");
		for (Element huaWeathers : huaWeather) {

			linkText = huaWeathers.text();
			linkTextcont += linkText + ",";

		}
		if (!linkTextcont.equals(",,")) {
			lastlinkText = linkTextcont.substring(0, linkTextcont.length() - 1);
			oneWeather = lastlinkText.split(",")[0];
			anotherWeather = lastlinkText.split(",")[1];
		} else {
			oneWeather = "";
			anotherWeather = "";
		}

		JSONObject weatherinfo = new JSONObject();
		weatherinfo.put("temp1", anotherWeather);
		weatherinfo.put("temp2", oneWeather);
		weatherinfo.put("weather", linkHref);

		JSONObject result = new JSONObject();
		result.put("weatherinfo", weatherinfo);
		JSONUtil.responseJSON(response, result);
	}
*/
}
