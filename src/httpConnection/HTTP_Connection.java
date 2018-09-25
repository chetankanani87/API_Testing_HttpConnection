package httpConnection;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class HTTP_Connection {
	public static void main(String[] args) {
		try {
			/*
			 * A HttpUrlConnection instance is created by using the openConnection() method
			 * of the URL class. Note that this method only creates a connection object, but
			 * does not establish the connection yet.
			 */
			URL url = new URL("http://httpbin.org/ip");
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("PUT");

			/*
			 * When a request returns a status code 301 or 302, indicating a redirect, we
			 * can retrieve the Location header and create a new request to the new URL.
			
			int status = con.getResponseCode();
			if (status == HttpURLConnection.HTTP_MOVED_TEMP || status == HttpURLConnection.HTTP_MOVED_PERM) {
				String location = con.getHeaderField("Location");
				URL newUrl = new URL(location);
				con = (HttpURLConnection) newUrl.openConnection();
			}
			 */

			// Adding headers to a request can be achieved by using the setRequestProperty()
			// method:
			//con.setRequestProperty("Content-Type", "application/json");

			/*
			 * HttpUrlConnection class allows setting the connect and read timeouts. These
			 * values define the interval of time to wait for the connection to the server
			 * to be established or data to be available for reading.
			 */
			con.setConnectTimeout(5000);
			con.setReadTimeout(5000);

			/*
			 * If we want to add parameters to a request, we have to set the doOutput
			 * property to true, then write a String of the form param1=value&param2=value
			 * to the OutputStream of the HttpUrlConnection instance.
			 */
			con.setDoOutput(true);

			Map<String, String> parameters = new HashMap<>();
			parameters.put("Chetan", "Kanani");

			DataOutputStream out = new DataOutputStream(con.getOutputStream());
			//out.writeBytes(ParameterStringBuilder.getParamsString(parameters));
			out.flush();
			out.close();
			System.out.println(out);
			
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer content = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				content.append(inputLine);
			}
			in.close();

			con.disconnect();

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (ProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
