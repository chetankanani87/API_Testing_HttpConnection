package httpConnection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Http_GET_Request {
	public static void main(String[] args) throws IOException, ParseException {
			Http_GET_Request.GetCall();
	}
	
	public static void GetCall() throws IOException, ParseException {
		String str ="https://api.thingspeak.com/talkbacks/28275/commands/14055970.json?api_key=X0EIXMI10CHMAQCR";
		System.out.println("Sending 'GET' request to URL: " + str);

		//URL can throw MalformedURLException and HttpURLConnection can throw ProtocolException
		URL url = new URL(str);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setConnectTimeout(5000);
		//add request header
		con.setRequestProperty("Content-type", "application/json");
		//optional - default is GET
		con.setRequestMethod("GET");
		
		//getResponseCode can throw IOException
		int responseCode = con.getResponseCode();
		System.out.println("Response Code: " + responseCode);

		BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
		String inputLine;
		StringBuffer strBuffer = new StringBuffer();
		while((inputLine = br.readLine()) != null) {
			strBuffer.append(inputLine);
		}

		br.close();
		con.disconnect();

		//print in String
		System.out.println(strBuffer.toString());

		//Way to convert StringBuffer to JSON Object
		JSONParser jsonParser = new JSONParser();
		JSONObject jsonFullObj = (JSONObject) jsonParser.parse(strBuffer.toString());

		System.out.println(jsonFullObj);
		System.out.println();
		System.out.println("id: " + jsonFullObj.get("id"));
		System.out.println("command_string: " + jsonFullObj.get("command_string"));
		System.out.println("position: " + jsonFullObj.get("position"));
		System.out.println("executed_at: " + jsonFullObj.get("executed_at"));
		System.out.println("created_at: " + jsonFullObj.get("created_at"));
		System.out.println("position: " + jsonFullObj.get("position"));

	}
}
