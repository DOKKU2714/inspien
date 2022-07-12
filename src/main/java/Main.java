import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Main {
	private final static int CONNECTION_TIMEOUT = 10000;
	private final static int READ_TIMEOUT = 5000;
	private final static String API_URL = "http://211.106.171.36:50000/RESTAdapter/RecruitingTest";
	
	private static HttpURLConnection getHttpConnection(String apiUrl, String jsonInputString) throws IOException {
		URL url = null; // url�� ���� httpUrlConnection ��ü�� ��� ���� ��ü
		HttpURLConnection httpUrlConnection = null; //http ����� ������ �� �ֵ��� ���ִ� ��ü
		url = new URL(apiUrl);
		httpUrlConnection = (HttpURLConnection)url.openConnection();
		httpUrlConnection.setRequestMethod("POST");
		httpUrlConnection.setConnectTimeout(CONNECTION_TIMEOUT);
		httpUrlConnection.setReadTimeout(READ_TIMEOUT);
		httpUrlConnection.setRequestProperty("Content-type", "application/json;utf-8");
		httpUrlConnection.setRequestProperty("Accept", "application/json;");
		httpUrlConnection.setDoOutput(true);
		
		try (PrintWriter printWriter = new PrintWriter(
				httpUrlConnection.getOutputStream())) {
			printWriter.print(jsonInputString);
			printWriter.flush();
		} 
		
		OutputStream outputStream = httpUrlConnection.getOutputStream();
		
		byte[] input = jsonInputString.getBytes("utf-8");
		outputStream.write(input, 0, input.length);
		outputStream.flush();
		outputStream.close();
			
			
		
		return httpUrlConnection;
	}
	
	private static String getFirstData(HttpURLConnection httpUrlConnection) throws IOException{
		String readLine = null; //json �����͸� �� �پ� �б� ���� ����
		StringBuilder buffer = null; //readLine ������ �̿��� ���� ���ڿ��� �����ϱ����� ����
		//httpUrlConnection�� ���� ���޹��� �����͸� �б� ���� ��ü
		 //��û�� ������ ��ü
		
		buffer = new StringBuilder();
		//try-with-resources ����� ����ϸ� finally �� ���� �ڿ� ������ ������ �� �ʿ䰡 ���� �� AutoCloseable �������̽��� �����ϰ� �ִ� Ŭ������ ����
		try(BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
				httpUrlConnection.getInputStream(), "UTF-8"))) {
			if(httpUrlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				
				while((readLine = bufferedReader.readLine()) != null) {
					buffer.append(readLine).append("\n");
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
		/* ���
		 * 1. URL ��ü�� ���� Connection ��ü�� ����
		 * 2. HttpUrlConnection�� ���� http ����� ��
		 * 3. ���� ���� ����� InputStream ���� �޾� ���ۿ� ���������� ����
		 * */
		Map<String, String> info = Map.of(
				"NAME", "김도현",
				"PHONE_NUMBER", "01086517204",
				"E_MAIL", "axehornhead@gmail.com"
		);
		try {
			//jackson ���̺귯�� ���
			ObjectMapper mapper = new ObjectMapper();
			String json = mapper.writerWithDefaultPrettyPrinter()
					.writeValueAsString(info); //�̻ڰ� ����Ʈ
			
			HttpURLConnection httpUrlConnection = getHttpConnection(API_URL, json);
			String data = getFirstData(httpUrlConnection);
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
