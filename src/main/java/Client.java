import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.fasterxml.jackson.databind.ObjectMapper;

import inspien.DAO;
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
			Map<String, String> info = new HashMap<>();
			info.put("NAME", properties.getProperty("client.name"));
			info.put("PHONE_NUMBER", properties.getProperty("client.phoneNumber"));
			info.put("E_MAIL", properties.getProperty("client.eMail"));
			
			ObjectMapper objectMapper = new ObjectMapper();	//jackson 의 클래스로 생성비용이 비쌈
			
			String jsonInputData = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(info);	//json형태의 문자열로 변환
			
			Requester requester = new Requester(properties.getProperty("client.requestURL"), jsonInputData);
			String responseData = requester.getRequestData(); //가져온 

			
			DataHandler dataHandler = new DataHandler();
			RequestDataVo requestDataVo = dataHandler.handlingRequestData(responseData);
			System.out.println(requestDataVo.getDbConnInfo());
			
			String xmlRequestData = dataHandler.dataDecoding(requestDataVo.getXmlData(), "euc-kr");
			String jsonRequestData = dataHandler.dataDecoding(requestDataVo.getJsonData(), "utf-8");
			
			dataHandler.setXmlData(xmlRequestData);
			dataHandler.setJsonData(jsonRequestData);
			
			dataHandler.deserializationXmlData();
			
			System.out.println(dataHandler.getJoinVoList());
			System.out.println(dataHandler.getJoinVoList().size());
			
			DAO dao = new DAO();
			dao.insert(dataHandler.getJoinVoList());
			dao.select();
			
			// DataHandler 객체에서 deserializationJsonData메소드로 jsonData VO 자료구조로 만들고 (그 전에 VO 를 패키지로 XML 과 JSON 으로 나누자)
			
			//ftp 파일 전송
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
