import java.io.FileReader;
import java.util.Base64;
import java.util.Map;
import java.util.Properties;

import com.fasterxml.jackson.databind.ObjectMapper;

import inspien.Requester;

public class Client {
	public static void main(String[] args) {
		try {
			String resource = "setting.properties";
			Properties properties = new Properties();
			FileReader fileReader = new FileReader(resource);
			
			properties.load(fileReader);
			
			//자바 9버전 이상부터 사용 가능한 Map.of 형태로 선언
			Map<String, String> info = Map.of(
					"NAME", properties.getProperty("client.name"),
					"PHONE_NUMBER", properties.getProperty("client.phoneNumber"),
					"E_MAIL", properties.getProperty("client.eMail")
					);
			ObjectMapper mapper = new ObjectMapper();	//jackson
			
			String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(info);	//json형태의 문자열로 변환
			
			Requester requester = new Requester(properties.getProperty("client.requestURL"),
						json);
			
			String responseData = requester.sendRequest();
			
			mapper = new ObjectMapper();
//			mapper.
			
//			System.out.println(jsonMap);
//			
//			System.out.println(jsonMap.get("DB_CONN_INFO"));
//			//자바 8부터 지원
//			byte[] decodedXml = Base64.getDecoder().decode(jsonMap.get("XML_DATA"));
//			byte[] decodedJson = Base64.getDecoder().decode(jsonMap.get("JSON_DATA"));
			
//			System.out.println(new String(decodedXml, "euc-kr"));
//			System.out.println(new String(decodedJson, "utf-8"));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
