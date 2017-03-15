import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class restApiExample {
	private String ip;
	private String port;
	private HttpURLConnection conn;
	
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
	
	public String getSensor() {
		
		try
		{	
			try
			{
				connect("GetSensorValue");
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
			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				System.out.println(output);
			}
			
			//JSONObject obj = new JSONObject(output);

			//System.out.println(obj.getString("value"));
			
			disconnect();
			
			return output;
		}
		catch(IOException e)
		{
			System.out.println("Error while reading from server");
			e.printStackTrace();
			return "A";
		}
	}
	
	
	// http://localhost:8080/RESTfulExample/json/product/get
	public static void main(String[] args) {
		restApiExample teni = new restApiExample("127.0.0.1", "1337");
		teni.getSensor();
		teni.getSensor();
		teni.getSensor();
	}

}
