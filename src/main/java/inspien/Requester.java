package inspien;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class Requester {
    private String requestURL;
    private String inputData;
  
    public Requester(){}
    
    public Requester(String requestURL, String inputData) {
        this.requestURL = requestURL;
        this.inputData = inputData;
    }
    
    public String getRequestData() throws IOException {
        URL url = new URL(this.requestURL); //url 객체를 요청을 보낼 url 을 통해 생성
		HttpURLConnection httpUrlConnection = (HttpURLConnection)url.openConnection();//httpUrlConnection 객체를 가져옴
		httpUrlConnection.setRequestMethod("POST");	//http method 설정
		httpUrlConnection.setConnectTimeout(1000 * 1000);	
		httpUrlConnection.setReadTimeout(1000 * 500);
		httpUrlConnection.setRequestProperty("Content-type", "application/json;utf-8");	//http request message 의 헤더 설정
		httpUrlConnection.setRequestProperty("Accept", "application/json;");
		httpUrlConnection.setDoOutput(true);	
		
		//try-catch-finally 가 아니라서 writer close 를 안해도 되는 try-with-resources 방식을 사용
		try (PrintWriter printWriter = new PrintWriter(	//printWriter 객체를  connection 객체를통해 outputstream을 가져와 json 데이터를 쏨
				httpUrlConnection.getOutputStream())) {
			printWriter.print(inputData);
			printWriter.flush();
		}    
        
	    String readLine = null; //json 데이터를 넣을 변수
		StringBuilder buffer = null; //readLine 변수를 통해 한 줄씩 읽은 데이터를 넣을 버퍼
		buffer = new StringBuilder();
		
		try(BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
				httpUrlConnection.getInputStream(), "UTF-8"))) {
			
			if(httpUrlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) { //응답 코드가 200일 경우
	
				while((readLine = bufferedReader.readLine()) != null) {
					buffer.append(readLine)
							.append("\n");
				}
			} else {
				buffer.append("code : ")
						.append(httpUrlConnection.getResponseCode())
						.append("\n\n")
						.append("message : ")
						.append(httpUrlConnection.getResponseMessage())
						.append("\n\n");
			}
		}
		return buffer.toString();
    }
}
