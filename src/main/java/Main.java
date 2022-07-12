import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

/*
 * 실행 클래스
 * 에서 서비스 호출 객체를 통해 데이터를 가져오고 (문자열)
 * 해당 문자열에서 XML_DATA를 핸들링 해주는 객체로 XML데이터를 객체로만듬(POJO)
 * 해당 문자열에서 JSON_DATA를 핸들링 해주는 객체로 JSON 데이터를 객체로만듬(POJO)
 * ORACLE 접속 객체의 메소드를 통해 insert하고
 * FTP 접속 객체의 메소드를 통해 파일 전송 
 * 
 * 끝
 * 
 * 서비스 호출을 통해 데이터를 가져오는 객체
 * XML_DATA 를 핸들링 해주는 객체
 * JSON_DATA를 핸들링 해주는 객체
 * ORACLE에 접속해주는 객체
 * FTP 서버에 접속해주는 객체
 * */

public class Main {
//	private final static int CONNECTION_TIMEOUT = 10000;
//	private final static int READ_TIMEOUT = 5000;
	private final static String API_URL = "http://211.106.171.36:50000/RESTAdapter/RecruitingTest";
	
	private static HttpURLConnection getHttpConnection(String apiUrl, String jsonInputString) throws IOException {
		URL url = null; // url객체를 통해 httpUrlConnection 객체를 얻음
		HttpURLConnection httpUrlConnection = null; //http 통신을 할 수 있게 해주는 객체
		url = new URL(apiUrl);	//url 객체 생성
		httpUrlConnection = (HttpURLConnection)url.openConnection();	//httpUrlConnection 객체 받아옴
		httpUrlConnection.setRequestMethod("POST");	//http method 설정
//		httpUrlConnection.setConnectTimeout(CONNECTION_TIMEOUT);	//연결 최대 시간 설정
//		httpUrlConnection.setReadTimeout(READ_TIMEOUT);	//
		httpUrlConnection.setRequestProperty("Content-type", "application/json;utf-8");	//http request message 의 header에서 컨텐츠 타입 설정
		httpUrlConnection.setRequestProperty("Accept", "application/json;");	//json 형태의 응답 ??
		httpUrlConnection.setDoOutput(true);	//??
		
		//try-catch-finally 를 통한 writer close 를 하지않고 try-with-resources 를 통해 간단하게 구현
		try (PrintWriter printWriter = new PrintWriter(	//printWriter 스트림객체를 통해 한 줄씩 읽어서 connection 의 outputstream을 통해 json 형태의 데이터 전송
				httpUrlConnection.getOutputStream())) {
			printWriter.print(jsonInputString);
			printWriter.flush();
		} 
		
		return httpUrlConnection;
	}
	
	private static String getFirstData(HttpURLConnection httpUrlConnection) throws IOException{
		String readLine = null; //json 형태의 데이터를 한 줄 씩 읽기 위한 변수
		StringBuilder buffer = null; //readLine 변수로 한 줄씩 읽은 데이터를 저장하기위한 버퍼
		buffer = new StringBuilder();
		//try-catch-finally 를 통한 writer close 를 하지않고 try-with-resources 를 통해 간단하게 구현
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
						.append("\n")
						.append("message : ")
						.append(httpUrlConnection.getResponseMessage())
						.append("\n");
			}
		}
		
		return buffer.toString();
	}
	
	public static void main(String[] args) {
		/* 요약
		 * 1. URL 을 통해 Connection 을 가져옴
		 * 2. HttpUrlConnection 객체를 이용해 http 통신
		 * 3. ���� ���� ����� InputStream ���� �޾� ���ۿ� ���������� ����
		 * */
		
		//자바 9버전 이상부터 사용 가능한 Map.of 형태로 선언
		Map<String, String> info = Map.of(
				"NAME", "김도현",
				"PHONE_NUMBER", "01086517204",
				"E_MAIL", "axehornhead@gmail.com"
		);
		try {
			ObjectMapper mapper = new ObjectMapper();	//jackson
			String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(info);	//json형태의 문자열로 변환
			
			HttpURLConnection httpUrlConnection = getHttpConnection(API_URL, json);
			String data = getFirstData(httpUrlConnection);	//
			System.out.println(data);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		//rest api ȣ��
		
		//���޹��� �����Ϳ��� DBMS ��������, FTP ���� ���������� �޾Ƽ�
		//����
		//XML ������ �Ľ� �� INSERT
		//JSON ������ �Ľ� �� ���ε�
		
	}
}
