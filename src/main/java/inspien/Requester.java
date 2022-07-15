package inspien;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;

import lombok.Getter;

@Getter
/*맨 처음 작동하는 서비스 호출 관련 클래스*/
public class Requester {
	private HttpURLConnection httpUrlConnection;
    private String connectionInfoText;
    private String body;
  
    /*생성자를 통해 http Header 설정 및 properties 파일에서 데이터를 가져와 body를 바로 초기화*/
    public Requester() throws IOException {
    	URL url = new URL(readFromProperties("client.requestURL"));
    	
    	this.httpUrlConnection = (HttpURLConnection)url.openConnection();
    	this.httpUrlConnection.setRequestMethod("POST");
    	this.httpUrlConnection.setConnectTimeout(1000 * 60);	
    	this.httpUrlConnection.setReadTimeout(1000 * 60);
    	this.httpUrlConnection.setRequestProperty("Content-type", "application/json;utf-8");
    	this.httpUrlConnection.setRequestProperty("Accept", "application/json;");
    	this.httpUrlConnection.setDoOutput(true);	
		
		String name = readFromProperties("client.name");
		String phoneNumber = readFromProperties("client.phoneNumber");
		String eMail = readFromProperties("client.eMail");
		
		this.body = ""
				+ "{"
				+ "\"NAME\": \""+name+"\","
				+ "\"PHONE_NUMBER\": \""+phoneNumber+"\","
				+ "\"E_MAIL\": \""+eMail+"\""
				+ "}";
    }
    
    public void request() throws IOException {
		/*try-catch-finally 가 아니라서 자원 close 를 자동으로 해주는 try-with-resources 방식을 사용*/
		try (PrintWriter printWriter = new PrintWriter(httpUrlConnection.getOutputStream())) {
			printWriter.print(this.body);
			printWriter.flush();
		}    
    }
    /*request 후 response를 통해 응답받은 연결정보 데이터를 set*/
    public void setConnectionInfoText() throws UnsupportedEncodingException, IOException {
	    String readLine = null;
		StringBuilder buffer = new StringBuilder();
		
		try(BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
				this.httpUrlConnection.getInputStream(), "UTF-8"))) {
			
			if(this.httpUrlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
	
				while((readLine = bufferedReader.readLine()) != null) {
					buffer.append(readLine)
							.append("\n");
				}
			} else {
				buffer.append("code : ")
						.append(this.httpUrlConnection.getResponseCode())
						.append("\n\n")
						.append("message : ")
						.append(this.httpUrlConnection.getResponseMessage())
						.append("\n\n");
			}
		}
		this.connectionInfoText = buffer.toString();
    }
    
    private String readFromProperties(String id) throws IOException {
		String resource = "src/main/resource/properties/requestInfo.properties";
		Properties properties = new Properties();
		FileReader fileReader = new FileReader(resource);
		properties.load(fileReader);
		
		return properties.getProperty(id);
    }
}
