import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.*;

class restApiExample {
	private String ip;
	private String port;
	private HttpURLConnection conn;
	private String input;

	public restApiExample(String ip, String port) {
		this.ip = ip;
		this.port = port;
	}

	private void connect(String route) throws MalformedURLException, IOException {
		URL url = new URL("http://" + ip + ":" + port + "/" + route);
		conn = (HttpURLConnection) url.openConnection();
	}

	public void disconnect () {
		conn.disconnect();
	}
	
	public int getSensor(){
		try{
		JSONObject r = getIn("GetSensorValue");
		return Integer.parseInt(r.getString("value"));
		}
		catch(IOException e)
		{
			System.out.println("Error \n");
			e.printStackTrace();
			return -1;
		}
		
	}
	public int getOtherSensor(){
		try{
		JSONObject r = getIn("GetOtherSensor");
		return Integer.parseInt(r.getString("value2"));
		}
		catch (IOException e)
		{
			System.out.println("Error \n");
			e.printStackTrace();
			return -1;
		}
		
	}
	
	public JSONObject getIn(String input) throws IOException {
			try
			{
				connect(input);
			}
			catch(MalformedURLException e)
			{
				System.out.println("Could not connect to server");
				e.printStackTrace();
			}
			catch(IOException e)
			{
				System.out.println("Can't open data stream to server");
				e.printStackTrace();
				throw new IOException();
			}
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");
			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ conn.getResponseCode());
			}
			BufferedReader br = new BufferedReader(new InputStreamReader(
				(conn.getInputStream()))
			);

			String output;
			output = br.readLine();
				JSONObject obj = new JSONObject(output);

			disconnect();

			return obj;
		
	}



	public static void main(String[] args) {
		restApiExample teni = new restApiExample("127.0.0.1", "1337");
		System.out.println(teni.getSensor());
		System.out.println(teni.getSensor());
		System.out.println(teni.getSensor());

		System.out.println(teni.getOtherSensor());
	}

}
