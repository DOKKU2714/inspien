import java.io.FileReader;
import java.util.Map;
import java.util.Properties;

import com.fasterxml.jackson.databind.ObjectMapper;

import inspien.DataHandler;
import inspien.Requester;
import inspien.vo.RequestDataVo;

public class Client {
	//입력 값을 그냥 map 말고 문자열로 바로 json으로 쏘게 하자 (굳이 jackson으로 하지 말자)
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
			ObjectMapper mapper = new ObjectMapper();	//jackson 의 클래스로 생성비용이 비쌈
			
			String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(info);	//json형태의 문자열로 변환
			
			Requester requester = new Requester(properties.getProperty("client.requestURL"), json);
			
			String responseData = requester.getRequestData();
			
			DataHandler data = new DataHandler();
			
			RequestDataVo requestDataVo = data.handlingRequestData(responseData);
			String xml = data.dataDecoding(requestDataVo.getXmlData(), "euc-kr");
			data.deserializationXmlData(xml);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
