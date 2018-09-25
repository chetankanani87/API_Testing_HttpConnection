package httpConnection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Http_POST_Request {
	public static void main(String[] args) throws IOException, ParseException {

		URL url = new URL("http://httpbin.org/post");
		Map<String, Object> params = new LinkedHashMap<>();
		params.put("name", "Chetan Kanani");
		params.put("email", "youremail@gmail.com");
		params.put("phone", "1234567890");
		params.put("city", args[0]);

		StringBuilder strBuilder = new StringBuilder();
		for (Map.Entry<String, Object> param : params.entrySet()) {
			if (strBuilder.length() != 0)
				strBuilder.append('&');
			strBuilder.append(URLEncoder.encode(param.getKey(), "UTF-8"));
			strBuilder.append('=');
			strBuilder.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
		}
		//byte[] postDataBytes = strBuilder.toString().getBytes("UTF-8");

		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("POST");
		con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		con.setRequestProperty("Content-Length", String.valueOf(strBuilder.toString().getBytes("UTF-8").length));
		
		con.setDoOutput(true);
		con.getOutputStream().write(strBuilder.toString().getBytes("UTF-8"));

		Reader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));

		StringBuilder sb = new StringBuilder();
		for (int i; (i = in.read()) >= 0;)
			sb.append((char) i);
		String response = sb.toString();

		System.out.println(response);

		JSONParser jsonParser = new JSONParser();
		JSONObject jsonFullObj = (JSONObject) jsonParser.parse(sb.toString());
		
		System.out.println("Result after reading JSON response");
		System.out.println("args: " + jsonFullObj.get("args"));
		System.out.println("url: " + jsonFullObj.get("url"));

	}
}
